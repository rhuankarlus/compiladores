package br.com.ufg.compiladores.lexico.estados;

import java.util.Map;

/**
 * Created by Rhuan on 15/04/2016.
 */
public class Estado {

	private String id;
	private Tipo tipo;
	private Map<Character, Estado> proximosEstados;

	Estado() {
	}

	Estado(String id, Tipo tipo, Map<Character, Estado> proximosEstados) {
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

	public Map<Character, Estado> getProximosEstados() {
		return proximosEstados;
	}

	public void setProximosEstados(Map<Character, Estado> proximosEstados) {
		this.proximosEstados = proximosEstados;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

}
