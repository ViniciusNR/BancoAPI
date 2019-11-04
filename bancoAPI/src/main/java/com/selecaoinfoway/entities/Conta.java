package com.selecaoinfoway.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.selecaoinfoway.enums.TipoConta;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Conta extends Base{
	private String numero;
	private Double saldo;
	private TipoConta tipo;
	
	private Agencia agencia;
	
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public Double getSaldo() {
		return saldo;
	}
	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}
	public TipoConta getTipo() {
		return tipo;
	}
	public void setTipo(TipoConta tipo) {
		this.tipo = tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = TipoConta.valueOf(tipo);
	}
	public Agencia getAgencia() {
		return agencia;
	}
	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}
	
	
}
