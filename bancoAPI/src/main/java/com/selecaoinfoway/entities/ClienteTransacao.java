package com.selecaoinfoway.entities;

import java.util.ArrayList;

public class ClienteTransacao{
	private Cliente cliente;
	private ArrayList<Transacao> transacoes;
	
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public ArrayList<Transacao> getTransacoes() {
		return transacoes;
	}
	public void setTransacoes(ArrayList<Transacao> transacoes) {
		this.transacoes = transacoes;
	}
}
