package br.com.ufg.compiladores.tokens;

/**
 * Created by rhuan on 24/04/2016.
 */
public enum Tokens {

    IDENTIFICADOR(1, "IDENTIFICADOR", "id", "E09"),
    NUMERO(2, "NUMERO", "num", "E01", "E03", "E06"),
    MAIS(3, "+", "opm", "E20"),
    MENOS(4, "-", "opm", "E21"),
    MULTIPLICACAO(5, "*", "opm", "E22"),
    DIVISAO(6, "/", "opm", "E23"),
    PONTO(7, ".", ""),
    ELEVADO(8, "E", ""),
    ASPAS(9, "\"", "literal", "E08"),
    ABRE_CHAVES(10, "{", ""),
    FECHA_CHAVES(11, "}", ""),
    ESPACO(12, " ", ""),
    FIM_DE_LINHA(13, System.getProperty("line.separator"), "EOF"),
    MENOR_QUE(14, "<", "opr", "E13"),
    MAIOR_QUE(15, ">", "opr", "E17"),
    IGUAL(16, "=", "opr", "E19"),
    ABRE_PARENTESIS(17, "(", "(", "E24"),
    FECHA_PARENTESIS(18, ")", ")", "E25"),
    PONTO_E_VIRGULA(19, ";", ";", "E12"),
    PALAVRA_RESERVADA(20, "PALAVRA_RESERVADA", ""),
    EOF(21, "", "EOF", "E11"),
    COMENTARIO(22, "COMENTARIO", "Coment\u00e1rio"),
    MAIOR_OU_IGUAL(23, "MAIOR_OU_IGUAL", "opr", "E18"),
    MENOR_OU_IGUAL(24, "MENOR_OU_IGUAL", "opr", "E16"),
    ATRIBUICAO(25, "ATRIBUICAO", "rcb", "E14"),
    DIFERENTE(26, "DIFERENTE", "opr", "E15");

    private Integer posicao;
    private String simbolo;
    private String tokenId;
    private String[] estadosFinais;

    /**
     * Construtor de cada token
     *
     * @param posicao       A posi&ccedil;&atilde;o do token na tabela de transi&ccedil;&atilde;o
     * @param simbolo       O s&iacute;mbolo que representa este token na leitura do c&oacute;digo fonte
     * @param tokenId       O ID do token segundo a especifica&ccedil;&atilde;o da professora
     * @param estadosFinais Os estados finais atendidos por este token segundo o aut&ocirc;mato
     */
    Tokens(Integer posicao, String simbolo, String tokenId, String... estadosFinais) {
        this.posicao = posicao;
        this.simbolo = simbolo;
        this.tokenId = tokenId;
        this.estadosFinais = estadosFinais;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public String getTokenId() {
        return tokenId;
    }

    public static Tokens getTokenPelaPosicao(Integer pos) {
        for (Tokens token : Tokens.values()) {
            if (token.posicao.equals(pos)) return token;
        }
        return null;
    }

    public static Tokens getTokenPeloSimbolo(Character simb) {
        for (Tokens token : Tokens.values()) {
            if (token.simbolo.equals(simb + "")) return token;
        }
        return null;
    }

    public static Tokens getTokenDoEstadoFinal(String estadoFinalId) {
        for (Tokens token : Tokens.values()) {
            for (String estadoFinal : token.estadosFinais) {
                if (estadoFinal.equals(estadoFinalId)) return token;
            }
        }
        return null;
    }

    public Integer getPosicao() {
        return posicao;
    }

    public void setPosicao(Integer posicao) {
        this.posicao = posicao;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String[] getEstadosFinais() {
        return estadosFinais;
    }

    public void setEstadosFinais(String[] estadosFinais) {
        this.estadosFinais = estadosFinais;
    }
}
