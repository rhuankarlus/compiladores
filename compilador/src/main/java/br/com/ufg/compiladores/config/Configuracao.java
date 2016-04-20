package br.com.ufg.compiladores.config;

import java.io.File;

import org.apache.log4j.Logger;

public class Configuracao {

	public static final Logger LOG = Logger.getLogger(Configuracao.class);
	public static final Object LOCK = new Object();

	public static Configuracao configuracao;

	private File diretorioEntrada;
	private File diretorioProcessados;
	private File diretorioSaida;

	private Configuracao() {
	}

	public static Configuracao getInstancia() {
		if (configuracao == null) {
			LOG.info("Carregando a configuração para o compilador...");
			configuracao = new Configuracao();
			configuracao.construir();
			LOG.info("Confiugração carregada!");
		}

		return configuracao;
	}

	private void construir() {
		configuracao.diretorioEntrada = carregarDiretorioValido(Constants.PASTA_DE_MONITORAMENTO);
		configuracao.diretorioProcessados = carregarDiretorioValido(Constants.PASTA_DE_PROCESSADOS);
		configuracao.diretorioSaida = carregarDiretorioValido(Constants.PASTA_DE_SAIDA);

	}

	private File carregarDiretorioValido(String path) {
		LOG.info("Carregando diretório [" + path + "]");
		File diretorio = new File(path);

		if (!diretorio.exists()) {
			LOG.info("O diretório não existe. Vou construí-lo!");
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

}
