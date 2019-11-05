package com.selecaoinfoway.restcontrollers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.selecaoinfoway.entities.Banco;
import com.selecaoinfoway.entities.Resultado;
import com.selecaoinfoway.entities.ResultadoLista;
import com.selecaoinfoway.fachada.BancoFachada;

@RestController
@RequestMapping("/banco")
public class BancoController {

	@PostMapping("/gravar")
	public Resultado gravar(@RequestBody Banco banco) {
		BancoFachada bancoFachada = new BancoFachada();
		
		return bancoFachada.gravar(banco);
	}
	
	@PostMapping("/listar")
	public ResultadoLista listar(@RequestBody Banco banco) {
		BancoFachada bancoFachada = new BancoFachada();
		
		return bancoFachada.listar(banco);
	}
	
	@DeleteMapping("/remover/{id}")
	public Resultado remover(@PathVariable Integer id) {
		BancoFachada bancoFachada = new BancoFachada();
		
		return bancoFachada.remover(id);
	}
}
