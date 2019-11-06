package com.selecaoinfoway.restcontrollers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.selecaoinfoway.entities.Banco;
import com.selecaoinfoway.entities.Resultado;
import com.selecaoinfoway.entities.ResultadoLista;
import com.selecaoinfoway.fachada.BancoFachada;

@RestController
public class BancoController {

	@PostMapping("/bancos")
	public Resultado gravar(@RequestBody Banco banco) {
		BancoFachada bancoFachada = new BancoFachada();
		
		return bancoFachada.gravar(banco);
	}
	
	@GetMapping("/bancos")
	public ResultadoLista listar() {
		BancoFachada bancoFachada = new BancoFachada();
		Banco banco = new Banco();
		return bancoFachada.listar(banco);
	}
	
	@GetMapping("/bancos/{id}")
	public Resultado recuperar(@PathVariable Integer id) {
		BancoFachada bancoFachada = new BancoFachada();
		Banco banco = new Banco();
		banco.setId(id);
		
		return bancoFachada.recuperar(banco);
	}
	
	@DeleteMapping("/bancos/{id}")
	public Resultado remover(@PathVariable Integer id) {
		BancoFachada bancoFachada = new BancoFachada();
		
		return bancoFachada.remover(id);
	}
}
