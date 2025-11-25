package actions;

import model.Startup;
import model.Deltas;
import java.util.Random;

public class MarketingStrategy implements DecisaoStrategy {

    private static final String[] FRASES_POSITIVAS = {
        "A campanha ganhou destaque nas redes sociais e aumentou a visibilidade da marca.",
        "O público recebeu muito bem a nova estratégia e isso elevou sua reputação.",
        "Blogueiros e influenciadores começaram a falar da sua startup positivamente."
    };

    private static final String[] FRASES_NEUTRAS = {
        "A campanha teve um desempenho razoavel, nada muito marcante.",
        "Algumas pessoas gostaram, outras nem tanto. O impacto foi moderado.",
        "A estrategia funcionou parcialmente, mas nao convenceu todo o publico."
    };

    private static final String[] FRASES_NEGATIVAS = {
        "O gasto em marketing assustou alguns investidores.",
        "A campanha nao teve o efeito esperado e o retorno foi menor que o investimento.",
        "Parte do publico achou a campanha fraca, e o financeiro reclamou do custo."
    };


    @Override
    public Deltas aplicar(Startup s) {

        Random rnd = new Random();

        // Valores aleatórios dentro de um intervalo
        double caixaDelta = -8000 - rnd.nextInt(4000); // entre -8000 e -12000
        int reputacaoDelta = 3 + rnd.nextInt(6);        // entre 3 e 8
        double bonusReceita = (2 + rnd.nextInt(4)) / 100.0; // entre 2% e 5%

        System.out.println("Executando estrategia de Marketing...");
        System.out.println();

        // Impacto da Jogada
        System.out.println("Impacto da Jogada:");
        System.out.printf(" %+.0f de Caixa%n", caixaDelta);
        System.out.printf(" +%d de Reputacao%n", reputacaoDelta);
        System.out.printf(" +%.0f%% de Receita na proxima rodada%n", bonusReceita * 100);
        System.out.println();

        // Escolher frase coerente
        String frase;

        if (reputacaoDelta >= 6) {
            frase = FRASES_POSITIVAS[rnd.nextInt(FRASES_POSITIVAS.length)];
        } else if (reputacaoDelta >= 4) {
            frase = FRASES_NEUTRAS[rnd.nextInt(FRASES_NEUTRAS.length)];
        } else {
            frase = FRASES_NEGATIVAS[rnd.nextInt(FRASES_NEGATIVAS.length)];
        }

        System.out.println(frase);
        System.out.println();

        // Registrar histórico
        s.registrar(
            "Marketing: " + caixaDelta +
            " caixa, +" + reputacaoDelta +
            " reputacao, +" + (bonusReceita * 100) + "% receita"
        );

        return new Deltas(caixaDelta, reputacaoDelta, 0, bonusReceita);
    }
}
