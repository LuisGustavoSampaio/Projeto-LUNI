package actions;

import model.Startup;
import model.Deltas;
import java.util.Random;

public class InvestidoresStrategy implements DecisaoStrategy {

    private static final String[] FRASES_POSITIVAS = {
        "Os investidores ficaram impressionados com seus resultados recentes.",
        "Uma rodada rápida de investidores decidiu aportar mais capital na sua startup.",
        "A confiança do mercado subiu, e novos investidores apareceram interessados!"
    };

    private static final String[] FRASES_NEUTRAS = {
        "Os investidores ficaram cautelosos, mas ainda mantêm confiança moderada.",
        "Houve interesse, mas ninguém quis arriscar valores muito altos.",
        "Os resultados foram ok, os investidores continuam de olho."
    };

    private static final String[] FRASES_NEGATIVAS = {
        "Os investidores ficaram desconfiados das últimas decisões.",
        "Houve críticas sobre a direção atual da empresa.",
        "O conselho reclamou do risco envolvido e segurou novos investimentos."
    };

    @Override
    public Deltas aplicar(Startup s) {

        Random rnd = new Random();

        // Ganha dinheiro: entre +15000 e +30000
        double caixaDelta = 15000 + rnd.nextInt(15000);

        // Reputação pode subir ou cair levemente (-2 a +4)
        int reputacaoDelta = rnd.nextInt(7) - 2;

        // Moral pode cair (investidores pressionam): entre -4 e -1
        int moralDelta = -(1 + rnd.nextInt(4));

        // Pequeno bônus de receita (0% a 2%)
        double bonusReceita = rnd.nextInt(3) / 100.0;

        System.out.println("Executando estratégia de Investidores...");
        System.out.println();

        // Impacto da Jogada
        System.out.println("Impacto da Jogada:");
        System.out.printf(" +%.0f de Caixa%n", caixaDelta);
        System.out.printf(" %+d de Reputação%n", reputacaoDelta);
        System.out.printf(" %+d de Moral%n", moralDelta);
        System.out.printf(" +%.0f%% de Receita (pequeno bônus)%n", bonusReceita * 100);
        System.out.println();

        // Escolher frase coerente
        String frase;

        if (reputacaoDelta >= 3) {
            frase = FRASES_POSITIVAS[rnd.nextInt(FRASES_POSITIVAS.length)];
        } else if (reputacaoDelta >= 0) {
            frase = FRASES_NEUTRAS[rnd.nextInt(FRASES_NEUTRAS.length)];
        } else {
            frase = FRASES_NEGATIVAS[rnd.nextInt(FRASES_NEGATIVAS.length)];
        }

        System.out.println(frase);
        System.out.println();

        // Registrar histórico
        s.registrar(
            "Investidores: +" + caixaDelta +
            " caixa, " + reputacaoDelta + " reputação, " +
            moralDelta + " moral, +" + (bonusReceita * 100) + "% receita"
        );

        return new Deltas(caixaDelta, reputacaoDelta, moralDelta, bonusReceita);
    }
}
