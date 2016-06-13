package br.com.ufg.compiladores.analisadores.lexico.estados;

import br.com.ufg.compiladores.tokens.Tokens;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rhuan on 15/04/2016.
 */
public class Estado {

    private String id;
    private Tipo tipo;
    private Map<String, String> proximosEstados;

    public Estado() {
        proximosEstados = new HashMap<>();
    }

    public Estado(String id, Tipo tipo, Map<String, String> proximosEstados) {
        this.id = id;
        this.tipo = tipo;
        this.proximosEstados = proximosEstados;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, String> getProximosEstados() {
        return proximosEstados;
    }

    public void setProximosEstados(Map<String, String> proximosEstados) {
        this.proximosEstados = proximosEstados;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Estado)) return false;
        Estado estado = (Estado) o;
        return getId().equals(estado.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    public void adicionarProximoEstado(Tokens token, String proximoEstado) {
        if (proximosEstados == null) proximosEstados = new HashMap<>();
        proximosEstados.put(token.getSimbolo(), proximoEstado);
    }

    public Boolean isFinal() {
        return this.getTipo() == Tipo.FINAL;
    }
}
