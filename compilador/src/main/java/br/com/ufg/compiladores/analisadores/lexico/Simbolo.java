package br.com.ufg.compiladores.analisadores.lexico;

import br.com.ufg.compiladores.tokens.Tokens;

/**
 * Created by rhuan on 01/05/2016.
 */
public class Simbolo {

    private Tokens token;
    private String lexema;
    private String tipo;

    public Simbolo(Tokens token, String lexema, String tipo) {
        this.token = token;
        this.lexema = lexema;
        this.tipo = tipo;
    }

    public Tokens getToken() {
        return token;
    }

    public void setToken(Tokens token) {
        this.token = token;
    }

    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Simbolo)) return false;

        Simbolo simbolo = (Simbolo) o;

        return getLexema() != null ? getLexema().equals(simbolo.getLexema()) : simbolo.getLexema() == null;
    }

    @Override
    public int hashCode() {
        return getLexema() != null ? getLexema().hashCode() : 0;
    }
}
