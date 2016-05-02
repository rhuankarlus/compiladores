package br.com.ufg.compiladores.tabelas;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rhuan on 24/04/2016.
 */
public class TabelaDePalavrasReservadas {

    private static final Object LOCK = new Object();

    private static TabelaDePalavrasReservadas tabelaDePalavrasReservadas;

    /**
     * O mapa de palavras reservadas contém a palavra reservada e sua descrição
     */
    private Map<String, String> palavrasReservadas;

    private TabelaDePalavrasReservadas() {
        palavrasReservadas = new HashMap<>();
    }

    public static TabelaDePalavrasReservadas getInstancia() {
        synchronized (LOCK) {
            if (tabelaDePalavrasReservadas == null) {
                tabelaDePalavrasReservadas = new TabelaDePalavrasReservadas();
            }
        }
        return tabelaDePalavrasReservadas;
    }

    public void adicionarPalavra(String palavra, String descricao) {
        tabelaDePalavrasReservadas.palavrasReservadas.put(palavra, descricao);
    }

    public static Boolean isPalavraReservada(String palavra) {
        return tabelaDePalavrasReservadas.palavrasReservadas.containsKey(palavra);
    }

}
