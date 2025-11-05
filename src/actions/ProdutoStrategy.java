package actions;

import model.Startup;
import model.Deltas;

public class ProdutoStrategy implements DecisaoStrategy {
    @Override
    public Deltas aplicar(Startup s) {
        System.out.println("Executando estratégia de Produto...");
        s.registrar("Produto: -R$8k caixa, +4% na receita");
        // (caixaDelta, reputacaoDelta, moralDelta, bonusDelta)
        return new Deltas(-8_000.0, 0, 0, 0.04);
    }
}
//Gabi voce esta aqui?
//Daniel voce esta aqui?
//Luis voce nao esta aqui