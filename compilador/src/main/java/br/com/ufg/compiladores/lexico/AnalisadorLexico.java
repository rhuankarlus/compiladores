package br.com.ufg.compiladores.lexico;

import br.com.ufg.compiladores.lexico.estados.EstadoController;

import java.io.File;

import org.apache.log4j.Logger;

/**
 * Created by Rhuan on 15/04/2016.
 */
public class AnalisadorLexico {

	private static final Logger LOG = Logger.getLogger(AnalisadorLexico.class);

	private EstadoController estadoController;
	private File codigoFonte;

	public AnalisadorLexico(File arquivoCodigoFonte) {
		LOG.info("Iniciando an�lise l�xica do c�digo fonte.");

		this.estadoController = new EstadoController();
		this.codigoFonte = arquivoCodigoFonte;

		LOG.info("An�lise l�xica conclu�da!");
	}

}
