package com.selecaoinfoway.restcontrollers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.selecaoinfoway.entities.Resultado;
import com.selecaoinfoway.entities.Transacao;
import com.selecaoinfoway.enums.TipoTransacao;
import com.selecaoinfoway.fachada.TransacaoFachada;

@RestController
public class TransacaoController {

	@PostMapping("/deposito")
	public Resultado deposito(@RequestBody Transacao transacao) {
		TransacaoFachada transacaoFachada = new TransacaoFachada();
		transacao.setTipo(TipoTransacao.DEPOSITO);
		
		return transacaoFachada.gravar(transacao);
	}
	
	@PostMapping("/saque")
	public Resultado saque(@RequestBody Transacao transacao) {
		TransacaoFachada transacaoFachada = new TransacaoFachada();
		transacao.setTipo(TipoTransacao.SAQUE);
		
		return transacaoFachada.gravar(transacao);
	}
	
	@PostMapping("/transferencia")
	public Resultado transferencia(@RequestBody Transacao transacao) {
		TransacaoFachada transacaoFachada = new TransacaoFachada();
		transacao.setTipo(TipoTransacao.TRANSFERENCIA);
		
		return transacaoFachada.gravar(transacao);
	}
}
