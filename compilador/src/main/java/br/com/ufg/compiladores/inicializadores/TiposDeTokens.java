package br.com.ufg.compiladores.inicializadores;

/**
 * Created by rhuan on 08/05/2016.
 */
public enum TiposDeTokens {

    LITERAL("Literal"),
    INTEIRO("Inteiro"),
    REAL("Real"),
    NENHUM("");

    private String tipo;

    TiposDeTokens(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }
}
