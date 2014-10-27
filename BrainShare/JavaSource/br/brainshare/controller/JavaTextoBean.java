package br.brainshare.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name="textoBean")
@RequestScoped
public class JavaTextoBean {

	private String texto;

	public String getTexto() {

		return texto;

	}

	public void setTexto(String texto) {

		this.texto = texto;

	}

}