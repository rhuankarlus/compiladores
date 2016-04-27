package br.com.ufg.compiladores.tabelas;

import br.com.ufg.compiladores.config.Constants;
import br.com.ufg.compiladores.estados.Estado;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rhuan on 24/04/2016.
 */
public class TabelaDeTransicao {

    private static final Object LOCK = new Object();

    private static TabelaDeTransicao tabelaDeTransicao;

    private Map<String, Estado> estados;

    private TabelaDeTransicao() {
        estados = new HashMap<>();
    }

    public static TabelaDeTransicao getInstancia() {
        synchronized (LOCK) {
            if (tabelaDeTransicao == null) {
                tabelaDeTransicao = new TabelaDeTransicao();
            }
        }
        return tabelaDeTransicao;
    }

    public void adicionarEstado(String id, Estado estado) {
        tabelaDeTransicao.estados.put(id, estado);
    }

    public Estado getEstado(String estadoId) {
        return tabelaDeTransicao.estados.get(estadoId);
    }

    public Estado getEstadoInicial() {
        return tabelaDeTransicao.estados.get(Constants.ESTADO_INICIAL);
    }

}
