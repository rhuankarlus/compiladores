package br.com.ufg.compiladores.config;

public interface Constants {

	/**
	 * Pasta onde devem ser colocados os arquivos que ser�o compilados. Essa
	 * pasta � constantemente monitorada pela aplica��o.
	 */
	String PASTA_DE_MONITORAMENTO = "../work/entrada";

	/**
	 * Pasta onde ser�o jogados quaisquer arquivos que sejam despejados na
	 * entrada.
	 */
	String PASTA_DE_PROCESSADOS = "../work/processados";

	/**
	 * Pasta onde os resultados da compila��o ficar�o.
	 */
	String PASTA_DE_SAIDA = "../work/saida";

}
