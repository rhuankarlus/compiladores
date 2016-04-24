package br.com.ufg.compiladores.inicializadores;

/**
 * Created by rhuan on 24/04/2016.
 */
public enum TabelaDeTransicaoPosicoes {

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
    HIFEN(17, "-"),
    ABRE_PARENTESIS(18, "(1, "),
    FECHA_PARENTESIS(19, ")"),
    PONTO_E_VIRGULA(20, ";");

    private Integer posicao;
    private String token;

    TabelaDeTransicaoPosicoes(Integer posicao, String token) {
        this.posicao = posicao;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public static TabelaDeTransicaoPosicoes getTokenPelaPosicao(Integer pos) {
        for (TabelaDeTransicaoPosicoes token : TabelaDeTransicaoPosicoes.values()) {
            if (token.posicao.equals(pos)) return token;
        }
        return null;
    }

}
