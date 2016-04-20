package br.com.ufg.compiladores;

import org.apache.log4j.Logger;

import br.com.ufg.compiladores.config.Configuracao;
import br.com.ufg.compiladores.watchers.DiretorioWatcher;

/**
 * Created by Rhuan on 15/04/2016.
 */
public class Main {

	private static final Logger LOG = Logger.getLogger(Main.class);

	public static void main(String[] args) {

		LOG.info("Iniciando compilador...");

		Configuracao config = Configuracao.getInstancia();
		new Thread(new DiretorioWatcher()).start();

		LOG.info("Compilador iniciado!");

	}

}
