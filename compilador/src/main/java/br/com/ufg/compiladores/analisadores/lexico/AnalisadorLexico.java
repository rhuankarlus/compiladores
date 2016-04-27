package br.com.ufg.compiladores.analisadores.lexico;

import br.com.ufg.compiladores.analisadores.Analisador;
import br.com.ufg.compiladores.estados.Estado;
import br.com.ufg.compiladores.inicializadores.TabelaDeTransicaoInicializador;
import br.com.ufg.compiladores.tabelas.TabelaDeTransicao;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

/**
 * Created by Rhuan on 15/04/2016.
 */
public class AnalisadorLexico implements Analisador {

    private static final Logger LOG = Logger.getLogger(AnalisadorLexico.class);

    private static final TabelaDeTransicao TABELA_DE_TRANSICAO = TabelaDeTransicao.getInstancia();

    private File codigoFonte;

    public AnalisadorLexico(File arquivoCodigoFonte) {
        this.codigoFonte = arquivoCodigoFonte;
    }

    @Override
    public void analisar() {
        try {
            List<String> linhas = Files.readAllLines(codigoFonte.toPath());
            for (Integer linhaNum = 0; linhaNum < linhas.size(); linhaNum++) {
                // resetando o estado atual
                Estado estadoAtual = TabelaDeTransicao.getInstancia().getEstadoInicial();

                // iterando sobre os estados
                for (Character letra : linhas.get(linhaNum).toCharArray()) {
                    
                }
            }
        } catch (IOException e) {
            LOG.error("Não consegui ler as linhas do arquivo [" + codigoFonte.getAbsolutePath() + "]", e);
        }
    }
}
