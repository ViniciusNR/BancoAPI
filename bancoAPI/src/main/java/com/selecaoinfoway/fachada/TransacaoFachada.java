package com.selecaoinfoway.fachada;

import com.selecaoinfoway.dao.TransacaoDAO;
import com.selecaoinfoway.entities.Transacao;
import com.selecaoinfoway.entities.Resultado;
import com.selecaoinfoway.enums.StatusResultado;
import com.selecaoinfoway.enums.TipoTransacao;

public class TransacaoFachada {
	
	public Resultado gravar(Transacao transacao) {
		Resultado resultado = new Resultado();
		TransacaoDAO transacaoDAO = new TransacaoDAO();
		try {
			if(!transacaoDAO.verificarSaldoDisponivel(transacao)) {
				resultado.setItem(transacao);
				resultado.setStatus(StatusResultado.FALHA);
				resultado.setMensagem("Saldo insuficiente para " + (transacao.getTipo().toString().equals(TipoTransacao.TRANSFERENCIA.toString()) ? "transferência" : "saque" ) + "!");
				
				return resultado;
			}
			
			transacao = transacaoDAO.inserir(transacao);
			
			resultado.setItem(transacao);
			resultado.setStatus(StatusResultado.SUCESSO);
			resultado.setMensagem("Transacao realizada com sucesso!");
		} catch (Exception e) {
			resultado.setItem(new Transacao());
			resultado.setStatus(StatusResultado.FALHA);
			resultado.setMensagem("Falha ao realizar Transacao!");
			e.printStackTrace();
		}
		
		return resultado;
	}
}
