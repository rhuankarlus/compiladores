package br.com.ufg.compiladores.config;

public interface Constants {

	/**
	 * Pasta onde devem ser colocados os arquivos que serão compilados. Essa
	 * pasta é constantemente monitorada pela aplicação.
	 */
	String PASTA_DE_MONITORAMENTO = "../work/entrada";

	/**
	 * Pasta onde serão jogados quaisquer arquivos que sejam despejados na
	 * entrada.
	 */
	String PASTA_DE_PROCESSADOS = "../work/processados";

	/**
	 * Pasta onde os resultados da compilação ficarão.
	 */
	String PASTA_DE_SAIDA = "../work/saida";

}
