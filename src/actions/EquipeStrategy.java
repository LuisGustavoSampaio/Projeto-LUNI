package actions;

import model.Startup;
import model.Deltas;
import java.util.Random;

public class EquipeStrategy implements DecisaoStrategy {

    private static final String[] FRASES_POSITIVAS = {
        "O time saiu motivado da reuniao. Todo mundo sentiu que esta sendo ouvido.",
        "As pessoas comecaram a colaborar mais entre si e o clima melhorou bastante.",
        "O investimento em treinamento deu resultado: o time esta mais confiante e unido."
    };

    private static final String[] FRASES_NEUTRAS = {
        "A reuniao foi ok, mas algumas pessoas ainda estao em duvida sobre os proximos passos.",
        "O time ate gostou das mudancas, mas ainda existe um pouco de resistencia.",
        "Algumas pessoas ficaram animadas, outras acharam que nada mudou muito."
    };

    private static final String[] FRASES_NEGATIVAS = {
        "Parte da equipe achou que foi so discurso e pouca acao concreta.",
        "Alguns membros do time se sentiram sobrecarregados com as novas demandas.",
        "O clima ficou um pouco pesado, parece que nem todos concordaram com as decisoes."
    };

    @Override
    public Deltas aplicar(Startup s) {
        Random rnd = new Random();

        // Gastar um pouco de caixa para cuidar da equipe (entre -3000 e -7000)
        double caixaDelta = -3000 - rnd.nextInt(4000);

        // Moral aumenta bem (entre +4 e +10)
        int moralDelta = 4 + rnd.nextInt(7);

        // Reputacao pode subir um pouco, ficar igual ou ate cair levemente (-1 a +2)
        int reputacaoDelta = -1 + rnd.nextInt(4);

        System.out.println("Executando estrategia de Equipe...");
        System.out.println();

        System.out.println("Impacto da Jogada:");
        System.out.printf(" %.0f de Caixa%n", caixaDelta);
        System.out.printf(" %+d de Moral%n", moralDelta);
        System.out.printf(" %+d de Reputacao%n", reputacaoDelta);
        System.out.println();

        String frase;
        if (moralDelta >= 8) {
            frase = FRASES_POSITIVAS[rnd.nextInt(FRASES_POSITIVAS.length)];
        } else if (moralDelta >= 5) {
            frase = FRASES_NEUTRAS[rnd.nextInt(FRASES_NEUTRAS.length)];
        } else {
            frase = FRASES_NEGATIVAS[rnd.nextInt(FRASES_NEGATIVAS.length)];
        }

        System.out.println(frase);
        System.out.println();

        s.registrar(
            "Equipe: " + caixaDelta +
            " caixa, +" + moralDelta +
            " moral, " + reputacaoDelta + " reputacao"
        );

        // (caixaDelta, reputacaoDelta, moralDelta, bonusPercentReceitaProx)
        return new Deltas(caixaDelta, reputacaoDelta, moralDelta, 0.0);
    }
}
