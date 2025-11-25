package persistence;

import model.Startup;

import java.io.PrintWriter;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class RelatorioService {

    /**
     * Gera um arquivo CSV com o resumo final das startups.
     * Arquivo: relatorio_final.csv (na pasta raiz do projeto).
     */
    public static void exportarCSV(List<Startup> startups) {
        // garante ponto como separador decimal (igual no restante do jogo)
        Locale.setDefault(Locale.US);

        String nomeArquivo = "relatorio_final.csv";

        try (PrintWriter pw = new PrintWriter(nomeArquivo)) {
            // Cabeçalho
            pw.println("Startup;Score;Caixa;ReceitaBase;Reputacao;Moral");

            // Linhas com os dados de cada startup
            for (Startup s : startups) {
                pw.printf(
                    "%s;%.2f;%.2f;%.2f;%d;%d%n",
                    s.getNome(),
                    s.scoreFinal(),
                    s.getCaixa().valor(),
                    s.getReceitaBase().valor(),
                    s.getReputacao().valor(),
                    s.getMoral().valor()
                );
            }

            System.out.println("Relatório gerado em: " + nomeArquivo);

        } catch (IOException e) {
            System.err.println("Erro ao gerar relatório CSV: " + e.getMessage());
        }
    }
}
