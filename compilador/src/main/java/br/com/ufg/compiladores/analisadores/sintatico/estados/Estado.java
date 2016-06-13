package br.com.ufg.compiladores.analisadores.sintatico.estados;

/**
 * Created by rhuan on 11/06/2016.
 */
public class Estado {

    private String numero;

    public Estado() {
        this.numero = "0";
    }

    public Estado(String numero) {
        this.numero = numero;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}
