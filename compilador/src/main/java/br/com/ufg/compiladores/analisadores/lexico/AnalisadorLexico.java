package br.com.ufg.compiladores.analisadores.lexico;

import br.com.ufg.compiladores.analisadores.Analisador;
import br.com.ufg.compiladores.estados.EstadoFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.apache.log4j.Logger;

/**
 * Created by Rhuan on 15/04/2016.
 */
public class AnalisadorLexico implements Analisador {

    private static final Logger LOG = Logger.getLogger(AnalisadorLexico.class);

    private EstadoFactory estadoFactory;
    private File codigoFonte;

    public AnalisadorLexico(File arquivoCodigoFonte) {
        this.estadoFactory = new EstadoFactory();
        this.codigoFonte = arquivoCodigoFonte;
    }

    @Override
    public void analisar() {
        try {
            Files.readAllLines(codigoFonte.toPath()).parallelStream().forEach(linha -> {
                System.out.println(linha);
            });
        } catch (IOException e) {
            LOG.error("Não consegui ler as linhas do arquivo [" + codigoFonte.getAbsolutePath() + "]", e);
        }
    }
}
