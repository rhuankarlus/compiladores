package br.com.ufg.compiladores.inicializadores;

/**
 * Created by rhuan on 24/04/2016.
 */
public enum Tokens {

    LETRA(1, "L"),
    DIGITO(2, "D"),
    MAIS(3, "+"),
    MENOS(4, "-"),
    MULTIPLICACAO(5, "*"),
    DIVISAO(6, "/"),
    PONTO(7, "."),
    ELEVADO(8, "E"),
    ASPAS(9, "\""),
    ABRE_CHAVES(10, "{"),
    FECHA_CHAVES(11, "}"),
    ESPAÇO(12, " "),
    FIM_DE_LINHA(13, System.getProperty("line.separator")),
    MENOR_QUE(14, "<"),
    MAIOR_QUE(15, ">"),
    IGUAL(16, "="),
    ABRE_PARENTESIS(17, "("),
    FECHA_PARENTESIS(18, ")"),
    PONTO_E_VIRGULA(19, ";"),
    EOF(20, "EOF");

    private Integer posicao;
    private String simbolo;

    Tokens(Integer posicao, String token) {
        this.posicao = posicao;
        this.simbolo = token;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public static Tokens getTokenPelaPosicao(Integer pos) {
        for (Tokens token : Tokens.values()) {
            if (token.posicao.equals(pos)) return token;
        }
        return null;
    }

    public static Tokens getTokenPeloSimbolo(String simb) {
        for (Tokens token : Tokens.values()) {
            if (token.simbolo.equals(simb)) return token;
        }
        return null;
    }

    @Override
    public String toString() {
        return super.toString() + " | " + this.simbolo;
    }
}
