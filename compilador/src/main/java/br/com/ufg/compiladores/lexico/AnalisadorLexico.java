package br.com.ufg.compiladores.lexico;

import br.com.ufg.compiladores.lexico.estados.EstadoController;

import java.io.File;

/**
 * Created by Rhuan on 15/04/2016.
 */
public class AnalisadorLexico {

    private EstadoController estadoController;
    private File codigoFonte;

    public AnalisadorLexico() {
        this.estadoController = new EstadoController();
    }

    public AnalisadorLexico(File arquivoCodigoFonte) {
        this.estadoController = new EstadoController();
        this.codigoFonte = arquivoCodigoFonte;
    }

}
