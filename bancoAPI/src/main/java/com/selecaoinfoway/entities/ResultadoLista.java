package com.selecaoinfoway.entities;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.selecaoinfoway.enums.StatusResultado;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultadoLista {
	ArrayList<Object> itens;
	StatusResultado status;
	String mensagem;
	
	public ArrayList<Object> getItens() {
		return itens;
	}
	public void setItens(ArrayList<Object> itens) {
		this.itens = itens;
	}
	public StatusResultado getStatus() {
		return status;
	}
	public void setStatus(StatusResultado status) {
		this.status = status;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
}
