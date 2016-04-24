package br.com.ufg.compiladores.estados;

import br.com.ufg.compiladores.tabelas.TabelaDeTransicao;

/**
 * Created by Rhuan on 15/04/2016.
 */
public class EstadoHandler {

    private static final TabelaDeTransicao TABELA_DE_TRANSICAO = TabelaDeTransicao.getInstancia();

    public Estado getProximoEstado(Estado estadoAtual, String entrada) {
        if (estadoAtual != null && estadoAtual.getProximosEstados() != null) {
            final String estadoId = estadoAtual.getProximosEstados().get(entrada);
            if (estadoId != null && !estadoId.trim().equals("")) {
                return TABELA_DE_TRANSICAO.getEstado(estadoId);
            }
        }
        return null;
    }

}
