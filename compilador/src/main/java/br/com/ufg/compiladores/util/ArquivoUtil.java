package br.com.ufg.compiladores.util;

import java.io.File;

import org.apache.log4j.Logger;

/**
 * Created by Rhuan on 18/04/2016.
 */
public class ArquivoUtil {

	private static final Logger LOG = Logger.getLogger(ArquivoUtil.class);

	public static void descartarArquivo(File arquivo) {
		LOG.trace("Removendo arquivo [" + arquivo.getAbsolutePath() + "]");

		// remove o arquivo permanentemente
		arquivo.delete();
	}

}
