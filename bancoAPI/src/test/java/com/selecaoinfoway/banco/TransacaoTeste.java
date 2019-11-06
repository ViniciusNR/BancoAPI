package com.selecaoinfoway.banco;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

import com.selecaoinfoway.dao.FabricaDAO;
import com.selecaoinfoway.entities.Cliente;
import com.selecaoinfoway.entities.Conta;
import com.selecaoinfoway.entities.Resultado;
import com.selecaoinfoway.entities.Transacao;
import com.selecaoinfoway.enums.StatusResultado;
import com.selecaoinfoway.enums.TipoTransacao;
import com.selecaoinfoway.fachada.ClienteFachada;
import com.selecaoinfoway.fachada.TransacaoFachada;

public class TransacaoTeste{
	
	TransacaoFachada transacaoFachada = new TransacaoFachada();
	ClienteFachada clienteFachada = new ClienteFachada();
	
	@Test
	public void transacaoValorNegativo() {
		Resultado resultado = new Resultado();
		Transacao transacao = new Transacao();
		transacao.setValor(-1.0);
		
		resultado = transacaoFachada.gravar(transacao);
		Assert.assertEquals(StatusResultado.FALHA, resultado.getStatus());
	}
	
	@Test
	public void transacaoSaldoInsuficiente() {
		try {
			configurarBanco();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Resultado resultado = new Resultado();
		Transacao transacao = new Transacao();
		transacao.setTipo(TipoTransacao.SAQUE);
		transacao.setValor(10000.0);
		
		transacao.setContaOrigem(new Conta());
		transacao.getContaOrigem().setId(1);
		
		resultado = transacaoFachada.gravar(transacao);
		Assert.assertEquals(StatusResultado.FALHA, resultado.getStatus());
	}
	
	@Test
	public void verificarDeposito() {
		try {
			configurarBanco();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		double valorDeposito = 10.0;
		
		Resultado resultado = new Resultado();
		resultado = new Resultado();

		Cliente clienteAnteriorDeposito = (Cliente)clienteFachada.recuperar(new Cliente(1)).getItem();
		
		Transacao transacao = new Transacao();
		transacao.setTipo(TipoTransacao.DEPOSITO);
		transacao.setValor(valorDeposito);
		transacao.setContaDestino(new Conta());
		transacao.getContaDestino().setId(1);
		
		resultado = transacaoFachada.gravar(transacao);
		
		Cliente clienteAposDeposito = (Cliente)clienteFachada.recuperar(new Cliente(1)).getItem();
		
		Assert.assertEquals(StatusResultado.SUCESSO, resultado.getStatus());
		Assert.assertEquals((double) clienteAnteriorDeposito.getConta().getSaldo() + valorDeposito, (double)clienteAposDeposito.getConta().getSaldo(),0.0001);
	}
	
	@Test
	public void verificarSaque() {
		try {
			configurarBanco();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		double valorSaque = 10.0;
		
		Resultado resultado = new Resultado();
		resultado = new Resultado();

		Cliente clienteAnteriorSaque = (Cliente)clienteFachada.recuperar(new Cliente(1)).getItem();
		
		Transacao transacao = new Transacao();
		transacao.setTipo(TipoTransacao.SAQUE);
		transacao.setValor(valorSaque);
		transacao.setContaOrigem(new Conta());
		transacao.getContaOrigem().setId(1);
		
		resultado = transacaoFachada.gravar(transacao);
		
		Cliente clienteAposSaque = (Cliente)clienteFachada.recuperar(new Cliente(1)).getItem();
		
		Assert.assertEquals(StatusResultado.SUCESSO, resultado.getStatus());
		Assert.assertEquals((double) clienteAnteriorSaque.getConta().getSaldo() - valorSaque, (double)clienteAposSaque.getConta().getSaldo(),0.0001);
	}
	
	private void configurarBanco() throws IOException {
		Properties configuracao = new Properties();
		try {
			String path = getClass().getProtectionDomain().getCodeSource().getLocation().toString().substring(6);
			configuracao.load(new FileInputStream("/" + path + "application.properties"));
		} catch (IOException e) {
			InputStream is = getClass().getResourceAsStream("/application.properties");
			try {
				configuracao.load(is);
			} catch (IOException e1) {
				throw e1;
			}
		}
		
		FabricaDAO.setarParametrosConexao(configuracao.getProperty("mysql"), configuracao.getProperty("usuario"), configuracao.getProperty("senha"));
	}
	
}
