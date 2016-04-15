package br.com.ufg.compiladores.lexico.estados;

import java.util.Map;

/**
 * Created by Rhuan on 15/04/2016.
 */
public class Estado {

    private String id;
    private Map<Character, Estado> proximosEstados;

    Estado() {
    }

    Estado(String id, Map<Character, Estado> proximosEstados) {
        this.id = id;
        this.proximosEstados = proximosEstados;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<Character, Estado> getProximosEstados() {
        return proximosEstados;
    }

    public void setProximosEstados(Map<Character, Estado> proximosEstados) {
        this.proximosEstados = proximosEstados;
    }

}
