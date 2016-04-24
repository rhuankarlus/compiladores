package br.com.ufg.compiladores;

import br.com.ufg.compiladores.inicializadores.TabelaDePalavrasReservadasInicializador;
import br.com.ufg.compiladores.inicializadores.TabelaDeTransicaoInicializador;
import br.com.ufg.compiladores.tabelas.TabelaDePalavrasReservadas;
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

        // inicializando a configuração do sistema
        Configuracao.getInstancia();

        // inicializando a tabela de palavras reservadas
        TabelaDePalavrasReservadasInicializador.inicializar();

        // inicializando a tabela de transição
        TabelaDeTransicaoInicializador.inicializar();

//        new Thread(new DiretorioWatcher()).start();

        LOG.info("Compilador iniciado!");

    }

}
