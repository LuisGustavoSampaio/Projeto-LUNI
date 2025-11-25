package actions;

import model.Startup;
import model.Deltas;
import java.util.Random;

public class CortarCustosStrategy implements DecisaoStrategy {

    private static final String[] FRASES_POSITIVAS = {
        "O corte foi eficiente e reduziu gastos sem impacto grave na moral.",
        "A equipe entendeu a necessidade do ajuste e manteve o ritmo de trabalho.",
        "Os acionistas aprovaram o controle rígido dos custos."
    };

    private static final String[] FRASES_NEUTRAS = {
        "Os cortes passaram, mas deixaram parte da equipe desconfiada.",
        "A decisão reduziu gastos, mas trouxe um clima de incerteza.",
        "Alguns setores reclamaram do corte, mas nada crítico."
    };

    private static final String[] FRASES_NEGATIVAS = {
        "A equipe ficou desmotivada com a redução de recursos.",
        "Houve reclamações internas e queda no ambiente de trabalho.",
        "Os cortes foram vistos como agressivos e prejudicaram a moral."
    };

    @Override
    public Deltas aplicar(Startup s) {

        Random rnd = new Random();

        // Ganho de caixa entre +3000 e +7000
        double caixaDelta = 3000 + rnd.nextInt(4000);

        // Moral cai entre -8 e -3
        int moralDelta = -(3 + rnd.nextInt(6));

        // Reputação pode cair leve (-2 a 0)
        int reputacaoDelta = -rnd.nextInt(3);

        // Não dá bônus de receita
        double bonusReceita = 0.0;

        System.out.println("Executando estratégia de Cortar Custos...");
        System.out.println();

        // Impacto da Jogada
        System.out.println("Impacto da Jogada:");
        System.out.printf(" +%.0f de Caixa%n", caixaDelta);
        System.out.printf(" %+d de Reputação%n", reputacaoDelta);
        System.out.printf(" %+d de Moral%n", moralDelta);
        System.out.println();

        // Escolher frase coerente
        String frase;

        if (moralDelta > -4) {
            frase = FRASES_POSITIVAS[rnd.nextInt(FRASES_POSITIVAS.length)];
        } else if (moralDelta > -7) {
            frase = FRASES_NEUTRAS[rnd.nextInt(FRASES_NEUTRAS.length)];
        } else {
            frase = FRASES_NEGATIVAS[rnd.nextInt(FRASES_NEGATIVAS.length)];
        }

        System.out.println(frase);
        System.out.println();

        // Registrar histórico
        s.registrar(
            "Cortar Custos: +" + caixaDelta +
            " caixa, " + reputacaoDelta + " reputação, " +
            moralDelta + " moral"
        );

        return new Deltas(caixaDelta, reputacaoDelta, moralDelta, bonusReceita);
    }
}
