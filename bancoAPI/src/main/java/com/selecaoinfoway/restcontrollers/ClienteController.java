package com.selecaoinfoway.restcontrollers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.selecaoinfoway.entities.Cliente;
import com.selecaoinfoway.entities.Resultado;
import com.selecaoinfoway.entities.ResultadoLista;
import com.selecaoinfoway.fachada.ClienteFachada;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

	@PostMapping("/gravar")
	public Resultado gravarCliente(@RequestBody Cliente cliente) {
		ClienteFachada clienteFachada = new ClienteFachada();
		
		return clienteFachada.gravar(cliente);
	}
	
	@PostMapping("/listar")
	public ResultadoLista listar(@RequestBody Cliente cliente) {
		ClienteFachada clienteFachada = new ClienteFachada();
		
		return clienteFachada.listar(cliente);
	}
	
	@DeleteMapping("/remover/{id}")
	public Resultado remover(@PathVariable Integer id) {
		ClienteFachada clienteFachada = new ClienteFachada();
		
		return clienteFachada.remover(id);
	}
	
	@PostMapping("/transacoes")
	public Resultado transacoes(@RequestBody Cliente cliente) {
		ClienteFachada clienteFachada = new ClienteFachada();
		
		return clienteFachada.transacoes(cliente);
	}
}
