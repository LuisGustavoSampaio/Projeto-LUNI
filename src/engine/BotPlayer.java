package engine;

import model.Startup;

import java.util.Random;

public class BotPlayer {

    private final Random rnd = new Random();

    /**
     * Escolhe uma ação para o bot com base no estado atual da startup.
     * Retorna um número de 1 a 5, igual ao menu:
     * 1 - MARKETING
     * 2 - PRODUTO
     * 3 - EQUIPE
     * 4 - INVESTIDORES
     * 5 - CORTAR_CUSTOS
     */
    public int escolherAcao(Startup s, int rodadaAtual) {
        double caixa = s.getCaixa().valor();
        int moral = s.getMoral().valor();
        int reputacao = s.getReputacao().valor();

        // Se o caixa está muito baixo: foca em grana
        if (caixa < 60_000) {
            // 50% de chance de buscar investidor ou cortar custos
            return rnd.nextBoolean() ? 4 : 5;
        }

        // Se a moral está baixa, prioriza Equipe
        if (moral < 40) {
            return 3; // EQUIPE
        }

        // Se a reputação está baixa, prioriza Marketing
        if (reputacao < 40) {
            return 1; // MARKETING
        }

        // Caso "saudável": escolhe aleatório entre 1 e 5
        return 1 + rnd.nextInt(5);
    }
}
