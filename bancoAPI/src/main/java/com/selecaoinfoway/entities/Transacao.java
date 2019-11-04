package com.selecaoinfoway.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.selecaoinfoway.enums.TipoTransacao;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Transacao extends Base{
	private TipoTransacao tipo;
	private Double valor;
	private Conta contaOrigem;
	private Conta contaDestino;
	
	public TipoTransacao getTipo() {
		return tipo;
	}
	public void setTipo(TipoTransacao tipo) {
		this.tipo = tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = TipoTransacao.valueOf(tipo);
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public Conta getContaOrigem() {
		return contaOrigem;
	}
	public void setContaOrigem(Conta contaOrigem) {
		this.contaOrigem = contaOrigem;
	}
	public Conta getContaDestino() {
		return contaDestino;
	}
	public void setContaDestino(Conta contaDestino) {
		this.contaDestino = contaDestino;
	}
}
