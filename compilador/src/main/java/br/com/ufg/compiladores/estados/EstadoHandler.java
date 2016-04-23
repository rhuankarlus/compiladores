package br.com.ufg.compiladores.estados;

/**
 * Created by Rhuan on 15/04/2016.
 */
public class EstadoHandler {

    public Estado getProximoEstado(Estado estadoAtual, Character entrada) {
        if (estadoAtual != null && estadoAtual.getProximosEstados() != null) {
            return estadoAtual.getProximosEstados().get(entrada);
        }
        return null;
    }

}
