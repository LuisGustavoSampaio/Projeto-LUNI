package engine;

import dao.RodadaDAO;

import java.util.Scanner;

import actions.DecisaoFactory;
import actions.DecisaoStrategy;
import model.Deltas;
import model.Startup;
import model.vo.Dinheiro;
import model.vo.Humor;

public class GameEngine {

    // mapeamento numérico -> string aceita pela fábrica
    private static final String[] OPCOES = {
        "MARKETING", "PRODUTO", "EQUIPE", "INVESTIDORES", "CORTAR_CUSTOS"
    };

    // Agora o engine tem seu próprio Scanner e DAO (reutilizáveis)
    private final Scanner in = new Scanner(System.in);
    private final RodadaDAO rodadaDAO = new RodadaDAO();

    /**
     * Modo "normal": jogador humano escolhe no teclado.
     */
    public void executar(Startup startup, int totalRodadas, int maxDecisoesPorRodada) {

        System.out.println("\n=== Iniciando o jogo para " + startup.getNome() + " ===");

        for (int rodada = 1; rodada <= totalRodadas; rodada++) {
            // guarda a rodada atual dentro da startup (pra bot e DAO usarem)
            startup.setRodadaAtual(rodada);

            System.out.println("\n===== Rodada " + rodada + " de " + totalRodadas + " =====");

            // até N decisões por rodada
            for (int i = 0; i < maxDecisoesPorRodada; i++) {
                String tipo = perguntarDecisao(in);
                if (tipo == null) break; // usuário encerrou

                DecisaoStrategy estrategia = DecisaoFactory.criar(tipo);
                Deltas delta = estrategia.aplicar(startup);
                aplicarDeltas(startup, delta); // converte Deltas (numéricos) para VO na Startup
                startup.registrar("Decisão aplicada: " + tipo);
                rodadaDAO.salvarDecisao(startup, rodada, tipo);
            }

            // usa o mesmo fechamento que o bot vai usar
            fecharRodada(startup);
        }

        // fim do jogo
        System.out.println("\n=== Fim do jogo para " + startup.getNome() + " ===");
        System.out.println("Histórico:");
        for (String linha : startup.getHistorico()) {
            System.out.println(" - " + linha);
        }
    }

    // ============================================================
    // Métodos auxiliares (modo humano)
    // ============================================================

    private String perguntarDecisao(Scanner in) {
        System.out.println("\nEscolha uma decisão (Enter/0 = finalizar escolhas da rodada):");
        for (int i = 0; i < OPCOES.length; i++) {
            System.out.println((i + 1) + " - " + OPCOES[i]);
        }
        System.out.print("Opção: ");
        String s = in.nextLine().trim();
        if (s.isEmpty() || s.equals("0")) return null;

        int idx;
        try {
            idx = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return null;
        }

        if (idx < 1 || idx > OPCOES.length) return null;
        return OPCOES[idx - 1];
    }

    private void aplicarDeltas(Startup s, Deltas d) {
        if (d == null) return;

        s.setCaixa(new Dinheiro(s.getCaixa().valor() + d.caixaDelta()));
        s.setReputacao(new Humor(s.getReputacao().valor() + d.reputacaoDelta()));
        s.setMoral(new Humor(s.getMoral().valor() + d.moralDelta()));
        s.addBonusPercentReceitaProx(d.bonusDelta());
        s.clamparHumor();
    }

    // ============================================================
    // Métodos usados pelo BOT (e que também podem ser usados depois no humano)
    // ============================================================

    /**
     * Executa UMA ação (uma escolha do jogador/bot).
     * Retorna true se a opção era válida (1..5), false caso contrário.
     *
     * Esse método usa startup.getRodadaAtual() pra saber em qual rodada salvar.
     */
    public boolean executarAcao(Startup s, int opcao) {
        if (opcao < 1 || opcao > OPCOES.length) {
            return false;
        }

        String tipo = OPCOES[opcao - 1];

        DecisaoStrategy estrategia = DecisaoFactory.criar(tipo);
        Deltas delta = estrategia.aplicar(s);
        aplicarDeltas(s, delta);

        s.registrar("Decisão aplicada: " + tipo);

        // usa a rodada atual guardada na Startup
        int rodadaAtual = s.getRodadaAtual();
        rodadaDAO.salvarDecisao(s, rodadaAtual, tipo);

        return true;
    }

    /**
     * Fecha a rodada atual da startup:
     * - calcula a receita (usando bônus)
     * - atualiza caixa
     * - cresce receitaBase conforme reputação/moral
     * - salva a rodada no banco
     * - registra no histórico
     */
    public void fecharRodada(Startup startup) {
        // receita da rodada (usa bonusPercentReceitaProx e zera)
        double receita = startup.receitaRodada();
        startup.setCaixa(new Dinheiro(startup.getCaixa().valor() + receita));

        // crescimento leve da receitaBase em função de reputação/moral
        double fatorCrescimento = 1.0
                + (startup.getReputacao().valor() / 100.0) * 0.01
                + (startup.getMoral().valor() / 100.0) * 0.005;

        startup.setReceitaBase(new Dinheiro(startup.getReceitaBase().valor() * fatorCrescimento));

        System.out.printf(java.util.Locale.US,
            "Fechamento -> Receita: R$%.2f | Nova ReceitaBase: R$%.2f | Caixa: R$%.2f%n",
            receita,
            startup.getReceitaBase().valor(),
            startup.getCaixa().valor()
        );

        // salva no banco usando a rodadaAtual da startup
        int rodadaAtual = startup.getRodadaAtual();
        rodadaDAO.salvarRodada(
            startup,
            rodadaAtual,
            receita,
            startup.getCaixa().valor(),
            startup.getReceitaBase().valor()
        );

        startup.registrar(String.format(java.util.Locale.US,
            "Fechamento : receita R$%.2f; nova receitaBase R$%.2f; caixa R$%.2f",
            receita, startup.getReceitaBase().valor(), startup.getCaixa().valor()
        ));

        startup.registrar("Rodada " + rodadaAtual + " concluída com sucesso!");
        startup.clamparHumor();
    }
}
