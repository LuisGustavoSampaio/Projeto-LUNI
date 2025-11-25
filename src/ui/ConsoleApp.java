package ui;

import persistence.RelatorioService;
import config.Config;
import engine.GameEngine;
import model.Startup;
import model.vo.Dinheiro;
import model.vo.Humor;

import java.util.*;

public class ConsoleApp {
    private final Config config = new Config();
    private final List<Startup> startups = new ArrayList<>();

    public void start() {
        Scanner in = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n==== MENU PRINCIPAL ====");
            System.out.println("1 - Novo jogo (adicionar startup)");
            System.out.println("2 - Continuar jogo");
            System.out.println("3 - Iniciar simulação");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");

            try {
                opcao = Integer.parseInt(in.nextLine().trim());
            } catch (Exception e) {
                opcao = -1;
            }

            switch (opcao) {
                case 1 -> criarStartup(in);
                case 2 -> continuarJogo(in);
                case 3 -> executarSimulacao();
                case 0 -> System.out.println("Encerrando...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void criarStartup(Scanner in) {
    System.out.print("Nome da startup: ");
    String nome = in.nextLine().trim();

    Startup s = new Startup(
        nome,
        new Dinheiro(100_000),
        new Dinheiro(10_000),
        new Humor(50),
        new Humor(50)
    );

    startups.add(s);
    System.out.println("Startup '" + nome + "' adicionada!");

    // --- NOVO: salvar no banco
    try {
        dao.StartupDAO dao = new dao.StartupDAO();
        dao.salvar(s);
        System.out.println(" Startup salva no banco de dados!");
        } catch (Exception e) {
        System.err.println(" Erro ao salvar no banco: " + e.getMessage());
        }
        }


    private void executarSimulacao() {
        if (startups.isEmpty()) {
            System.out.println("Nenhuma startup cadastrada!");
            return;
        }

        GameEngine engine = new GameEngine();

        for (Startup s : startups) {
            engine.executar(s, config.totalRodadas(), config.maxDecisoesPorRodada());
        }

        // === Ranking final ===
        System.out.println("\n====== RELATÓRIO FINAL ======");
        startups.sort(Comparator.comparingDouble(Startup::scoreFinal).reversed());

        int pos = 1;
        for (Startup s : startups) {
            System.out.printf(Locale.US,
                "%d) %s | SCORE: %.2f | Caixa: R$%.2f | ReceitaBase: R$%.2f | Rep: %d | Moral: %d%n",
                pos++, s.getNome(), s.scoreFinal(),
                s.getCaixa().valor(),
                s.getReceitaBase().valor(),
                s.getReputacao().valor(),
                s.getMoral().valor()
            );
        }
        
        System.out.println("==============================");

        // 🔽 gera o CSV com o mesmo ranking que acabou de aparecer na tela
        RelatorioService.exportarCSV(startups);
        
    }
    private void continuarJogo(Scanner in) {
    dao.StartupDAO dao = new dao.StartupDAO();
    List<Startup> lista = dao.listar();

    if (lista.isEmpty()) {
        System.out.println(" Nenhuma startup salva no banco ainda.");
        return;
    }

    System.out.println("\n=== Startups Salvas ===");
    for (int i = 0; i < lista.size(); i++) {
        System.out.println((i + 1) + " - " + lista.get(i).getNome());
    }

    System.out.print("Escolha o número da startup: ");
    String s = in.nextLine().trim();
    int idx;
    try {
        idx = Integer.parseInt(s) - 1;
    } catch (Exception e) {
        System.out.println("Entrada inválida!");
        return;
    }

    if (idx < 0 || idx >= lista.size()) {
        System.out.println("Opção fora do intervalo!");
        return;
    }

    Startup escolhida = lista.get(idx);
    System.out.println(" Continuando o jogo para: " + escolhida.getNome());

    GameEngine engine = new GameEngine();
    engine.executar(escolhida, config.totalRodadas(), config.maxDecisoesPorRodada());

    // Gera CSV só com essa startup que acabou de jogar
    List<Startup> apenasEssa = Collections.singletonList(escolhida);
    RelatorioService.exportarCSV(apenasEssa);

    }
}
