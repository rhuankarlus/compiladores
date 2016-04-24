package br.com.ufg.compiladores.inicializadores;

import br.com.ufg.compiladores.config.Configuracao;
import br.com.ufg.compiladores.config.Constants;
import br.com.ufg.compiladores.tabelas.TabelaDePalavrasReservadas;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TabelaDePalavrasReservadasInicializador {

    public static final Object LOCK = new Object();
    public static final Logger LOG = Logger.getLogger(TabelaDePalavrasReservadasInicializador.class);
    public static final TabelaDePalavrasReservadas TABELA_DE_PALAVRAS_RESERVADAS = TabelaDePalavrasReservadas.getInstancia();
    public static final Configuracao configuracao = Configuracao.getInstancia();

    public static TabelaDePalavrasReservadasInicializador tabelaDePalavrasReservadasInicializador;

    private TabelaDePalavrasReservadasInicializador() {
    }

    public static void inicializar() {
        synchronized (LOCK) {
            if (tabelaDePalavrasReservadasInicializador == null) {
                tabelaDePalavrasReservadasInicializador = new TabelaDePalavrasReservadasInicializador();
                try {
                    tabelaDePalavrasReservadasInicializador.montarTabelaDePalavrasReservadas();
                } catch (IOException e) {
                    LOG.error("Erro ao inicializar a tabela de palavras reservadas!", e);
                    System.exit(1);
                }
            }
        }
    }

    private void montarTabelaDePalavrasReservadas() throws IOException {
        LOG.info("Criando a tabela de palavras reservadas...");
        Files.lines(getPalavrasReservadasPath()).parallel().forEach(linha -> {
            final String palavra = linha.split(Constants.SEPARADOR_DE_PALAVRA_RESERVADA)[0].trim();
            final String descricao = linha.split(Constants.SEPARADOR_DE_PALAVRA_RESERVADA)[1].trim();

            LOG.info("Adicionando nova palavra reservada ao compilador [" + palavra + "]");
            TABELA_DE_PALAVRAS_RESERVADAS.adicionarPalavra(palavra, descricao);
        });
        LOG.info("Tabela de palavras reservadas criada.");
    }

    private Path getPalavrasReservadasPath() {
        return Paths.get(
                configuracao.getDiretorioDeConfiguracao().getAbsolutePath(),
                Constants.ARQUIVO_DE_PALAVRAS_RESERVADAS
        );
    }

}
