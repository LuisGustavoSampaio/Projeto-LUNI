package actions;

import model.Startup;
import model.Deltas;
import java.util.Random;

public class ProdutoStrategy implements DecisaoStrategy {

    private static final String[] FRASES_POSITIVAS = {
        "Os usuários adoraram as novas funcionalidades e elogiaram bastante!",
        "Seu produto ficou mais consistente e agora está destacando no mercado.",
        "A atualização foi um sucesso! As avaliações positivas subiram bastante."
    };

    private static final String[] FRASES_NEUTRAS = {
        "Alguns usuários gostaram, outros acharam que não mudou muita coisa.",
        "A melhoria foi boa, mas o impacto geral não foi tão alto quanto o esperado.",
        "O produto está melhor, mas ainda precisa de mais refinamento."
    };

    private static final String[] FRASES_NEGATIVAS = {
        "O investimento foi alto e alguns bugs surgiram depois do update.",
        "Os usuários não notaram grandes mudanças e o retorno foi menor que o esperado.",
        "A equipe técnica reclamou da pressa, o update saiu meio cru."
    };

    @Override
    public Deltas aplicar(Startup s) {

        Random rnd = new Random();

        // Gasto aleatório entre -6000 e -10000
        double caixaDelta = -6000 - rnd.nextInt(4000);

        // Bônus de receita entre 3% e 7%
        double bonusReceita = (3 + rnd.nextInt(5)) / 100.0;

        // Reputação pode subir levemente (+0 a +3)
        int reputacaoDelta = rnd.nextInt(4);

        System.out.println("Executando estratégia de Produto...");
        System.out.println();

        // Impacto da Jogada
        System.out.println("Impacto da Jogada:");
        System.out.printf(" %.0f de Caixa%n", caixaDelta);
        System.out.printf(" +%.0f%% de Receita%n", bonusReceita * 100);
        System.out.printf(" +%d de Reputação%n", reputacaoDelta);
        System.out.println();

        // Escolher frase coerente
        String frase;
        if (bonusReceita >= 0.06) {
            frase = FRASES_POSITIVAS[rnd.nextInt(FRASES_POSITIVAS.length)];
        } else if (bonusReceita >= 0.04) {
            frase = FRASES_NEUTRAS[rnd.nextInt(FRASES_NEUTRAS.length)];
        } else {
            frase = FRASES_NEGATIVAS[rnd.nextInt(FRASES_NEGATIVAS.length)];
        }

        System.out.println(frase);
        System.out.println();

        // Registrar histórico
        s.registrar(
            "Produto: " + caixaDelta +
            " caixa, +" + (bonusReceita * 100) +
            "% receita, +" + reputacaoDelta +
            " reputação"
        );

        return new Deltas(caixaDelta, reputacaoDelta, 0, bonusReceita);
    }
}
