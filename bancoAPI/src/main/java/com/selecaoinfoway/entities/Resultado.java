package com.selecaoinfoway.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.selecaoinfoway.enums.StatusResultado;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Resultado {
	Object item;
	StatusResultado status;
	String mensagem;
	
	public Object getItem() {
		return item;
	}
	public void setItem(Object item) {
		this.item = item;
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
