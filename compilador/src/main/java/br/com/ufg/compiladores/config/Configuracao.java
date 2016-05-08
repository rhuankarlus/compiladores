package br.com.ufg.compiladores.config;

import java.io.File;

import org.apache.log4j.Logger;

public class Configuracao {

    public static final Logger LOG = Logger.getLogger(Configuracao.class);
    public static final Object LOCK = new Object();

    public static Configuracao configuracao;

    private File diretorioDeConfiguracao;
    private File diretorioEntrada;
    private File diretorioProcessados;
    private File diretorioSaida;

    private Configuracao() {
    }

    public static Configuracao getInstancia() {
        if (configuracao == null) {
            LOG.info("Carregando a configura\u00e7\u00e3o para o compilador...");
            configuracao = new Configuracao();
            configuracao.construir();
            LOG.info("Confiugra\u00e7\u00e3o carregada!");
        }

        return configuracao;
    }

    private void construir() {
        configuracao.diretorioDeConfiguracao = carregarDiretorioValido(Constants.PASTA_DE_CONFIGURACAO);
        configuracao.diretorioEntrada = carregarDiretorioValido(Constants.PASTA_DE_MONITORAMENTO);
        configuracao.diretorioProcessados = carregarDiretorioValido(Constants.PASTA_DE_PROCESSADOS);
        configuracao.diretorioSaida = carregarDiretorioValido(Constants.PASTA_DE_SAIDA);
    }

    private File carregarDiretorioValido(String path) {
        LOG.info("Carregando diret\u00f3rio [" + path + "]");
        File diretorio = new File(path);

        if (!diretorio.exists()) {
            LOG.info("O diret\u00f3rio n\u00e3o existe. Vou constru\u00ed-lo!");
            diretorio.mkdirs();
        }

        return diretorio;
    }

    public File getDiretorioDeEntrada() {
        return diretorioEntrada;
    }

    public File getDiretorioDeProcessados() {
        return diretorioProcessados;
    }

    public File getDiretorioDeSaida() {
        return diretorioSaida;
    }

    public File getDiretorioDeConfiguracao() {
        return diretorioDeConfiguracao;
    }

}
