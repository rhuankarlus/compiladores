package br.com.ufg.compiladores.lexico;

import br.com.ufg.compiladores.lexico.estados.EstadoController;

/**
 * Created by Rhuan on 15/04/2016.
 */
public class Analisador {

    private EstadoController estadoController;

    public Analisador() {
        this.estadoController = new EstadoController();
    }



}
