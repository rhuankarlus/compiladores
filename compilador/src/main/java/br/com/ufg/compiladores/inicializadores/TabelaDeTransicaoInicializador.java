package br.com.ufg.compiladores.inicializadores;

import br.com.ufg.compiladores.config.Configuracao;
import br.com.ufg.compiladores.config.Constants;
import br.com.ufg.compiladores.estados.Estado;
import br.com.ufg.compiladores.estados.Tipo;
import br.com.ufg.compiladores.tabelas.TabelaDeTransicao;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rhuan on 15/04/2016.
 */
public class TabelaDeTransicaoInicializador {

    public static final Object LOCK = new Object();
    public static final Logger LOG = Logger.getLogger(TabelaDeTransicaoInicializador.class);
    public static final TabelaDeTransicao TABELA_DE_TRANSICAO = TabelaDeTransicao.getInstancia();
    public static final Configuracao configuracao = Configuracao.getInstancia();

    public static TabelaDeTransicaoInicializador tabelaDeTransicaoInicializador;

    public List<Estado> estados;
    private Path palavrasReservadasPath;

    private TabelaDeTransicaoInicializador() {
        estados = new ArrayList<>(Constants.NUMERO_DE_ESTADOS_PADRAO);
    }

    public static void inicializar() {
        synchronized (LOCK) {
            if (tabelaDeTransicaoInicializador == null) {
                tabelaDeTransicaoInicializador = new TabelaDeTransicaoInicializador();
                try {
                    tabelaDeTransicaoInicializador.montarTabelaDeTransicao();
                } catch (IOException e) {
                    LOG.error("Erro ao inicializar a TABELA DE TRANSIÇÃO!", e);
                    System.exit(1);
                }
            }
        }
    }

    private void montarTabelaDeTransicao() throws IOException {
        LOG.info("Criando a tabela de transição...");
        Files.lines(getPalavrasReservadasPath()).parallel().forEach(linha -> {
            // este if descarta a primeira linha que serve apenas para organizar a tabela
            if (!linha.startsWith("EST")) {
                Estado estado = getEstado(linha);

                LOG.info("Adicionando um novo estado ao compilador [" + estado.getId() + "]");
                TABELA_DE_TRANSICAO.adicionarEstado(estado.getId(), estado);
            }
        });
        LOG.info("Tabela de transição criada.");
    }

    private Estado getEstado(String linha) {
        // separa o id do estado e os próximos estados do mesmo
        final String[] fragmentos = linha.split(Constants.SEPARADOR_DE_ESTADO);

        Estado estado = new Estado();

        // ID do estado sempre estara na primeira coluna
        estado.setId(fragmentos[0]);

        // tipo do estado sempre está na última coluna
        if (fragmentos[fragmentos.length - 1].equals("SIM")) estado.setTipo(Tipo.FINAL);
        else estado.setTipo(Tipo.NORMAL);

        // ja começa a iterar a partir da primeira entrada na tabela
        // e nao considera a última que é a do tipo
        for (int i = 1; i < fragmentos.length - 1; i++) {
            // eliminando os estados inválidos da lista
            if (!fragmentos[i].equals(Constants.ESTADO_INVALIDO)) {
                estado.adicionarProximoEstado(Tokens.getTokenPelaPosicao(i), fragmentos[i]);
            }
        }

        return estado;
    }

    private Path getPalavrasReservadasPath() {
        return Paths.get(
                configuracao.getDiretorioDeConfiguracao().getAbsolutePath(),
                Constants.ARQUIVO_DE_MATRIZ_DE_TRANSICAO
        );
    }
}
