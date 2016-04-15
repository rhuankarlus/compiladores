package br.com.ufg.compiladores.lexico.estados;

import java.util.Map;

/**
 * Created by Rhuan on 15/04/2016.
 */
public class EstadoController {

    public Estado criarNovoEstado() {
        return criarNovoEstado("");
    }


    public Estado criarNovoEstado(String id) {
        return criarNovoEstado(id, null);
    }

    public Estado criarNovoEstado(String id, Map<Character, Estado> proximosEstados) {
        return new Estado(id, proximosEstados);
    }


}
