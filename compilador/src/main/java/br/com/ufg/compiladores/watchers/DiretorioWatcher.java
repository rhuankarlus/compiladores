package br.com.ufg.compiladores.watchers;

import java.io.File;

import org.apache.log4j.Logger;

import br.com.ufg.compiladores.config.Configuracao;
import br.com.ufg.compiladores.lexico.AnalisadorLexico;
import br.com.ufg.compiladores.util.ArquivoUtil;

public class DiretorioWatcher implements Runnable {

	// tempo de espera para leitura do diretorio
	private static final Long TEMPO_DE_ESPERA = 2000L;
	private static final Logger LOG = Logger.getLogger(DiretorioWatcher.class);

	private File diretorio;
	private AnalisadorLexico analisadorLexico;

	public DiretorioWatcher() {
		diretorio = Configuracao.getInstancia().getDiretorioDeEntrada();
		LOG.info("Diret\u00f3rio monitorado: " + diretorio.getAbsolutePath());

		// validando o diretorio
		if (diretorio == null || !diretorio.exists() || diretorio.isFile()) {
			relatarErro("O diret\u00f3rio a ser monitorado \u00e9 inv\u00e1lido!");
		}

	}

	public void run() {
		if (diretorio == null) {
			relatarErro("O diret\u00f3rio de monitoramento est\u00e1 vazio ou n\u00e3o existe! Configure-o corretamente.");
		}

		while (true) {
			// parando a thread antes de continuar a monitorar o diretorio
			dormir();

			// listando os arquivos dentro do diretorio
			for (File arquivo : diretorio.listFiles()) {
				if (isArquivoValido(arquivo)) {
					LOG.info("Encontrei um arquivo a ser compilado!");

					// preparando o analisador lexico
					analisadorLexico = new AnalisadorLexico(arquivo);
				}

				// descartando arquivo
				ArquivoUtil.descartarArquivo(arquivo);
			}
		}
	}

	private boolean isArquivoValido(File arquivo) {
		// deve verificar se o arquivo
		// e valido para ser compilado
		return true;
	}

	private void dormir() {
		try {
			Thread.sleep(TEMPO_DE_ESPERA);
		} catch (InterruptedException e) {
			LOG.error(
					"A thread de monitoramento de diret\u00f3rio foi interrompida abruptamente!",
					e);
		}
	}

	private void relatarErro(final String errorMsg) {
		LOG.error(errorMsg);
		throw new RuntimeException(errorMsg);
	}

}
