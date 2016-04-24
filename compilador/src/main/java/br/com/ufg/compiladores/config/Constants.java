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

    /**
     * Pasta onde são armazenados os arquivos de configuração
     */
    String PASTA_DE_CONFIGURACAO = "../config";

    /**
     * Por padrão são 26 estados ao todo
     */
    Integer NUMERO_DE_ESTADOS_PADRAO = 26;

    /**
     * O separador de estados na tabela de transição
     */
    String SEPARADOR_DE_ESTADO = "---";

    /**
     * O separador de palavras chave e detalhes padrão
     */
    String SEPARADOR_DE_PALAVRA_RESERVADA = ":";

    /**
     * Valor de um estado inválido na tabela de transição
     */
    String ESTADO_INVALIDO = "###";

    /**
     * Nome do arquivo de palavras reservadas
     */
    String ARQUIVO_DE_PALAVRAS_RESERVADAS = "palavras-reservadas";

    /**
     * Nome do arquivo de tabela de transição
     */
    String ARQUIVO_DE_MATRIZ_DE_TRANSICAO = "matriz-de-transicao";
}
