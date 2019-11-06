package com.selecaoinfoway.restcontrollers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.selecaoinfoway.entities.Cliente;
import com.selecaoinfoway.entities.Resultado;
import com.selecaoinfoway.entities.ResultadoLista;
import com.selecaoinfoway.fachada.ClienteFachada;

@RestController
public class ClienteController {

	@PostMapping("/clientes")
	public Resultado gravarCliente(@RequestBody Cliente cliente) {
		ClienteFachada clienteFachada = new ClienteFachada();
		
		return clienteFachada.gravar(cliente);
	}
	
	@GetMapping("/clientes")
	public ResultadoLista listar() {
		ClienteFachada clienteFachada = new ClienteFachada();
		Cliente cliente = new Cliente();
		
		return clienteFachada.listar(cliente);
	}
	
	@GetMapping("/clientes/{id}")
	public Resultado recuperar(@PathVariable Integer id) {
		ClienteFachada clienteFachada = new ClienteFachada();
		
		Cliente cliente = new Cliente(id);
		return clienteFachada.recuperar(cliente);
	}
	
	@DeleteMapping("/clientes/{id}")
	public Resultado remover(@PathVariable Integer id) {
		ClienteFachada clienteFachada = new ClienteFachada();
		
		return clienteFachada.remover(id);
	}
	
	@GetMapping("/clientes/{id}/transacoes")
	public Resultado transacoes(@PathVariable Integer id) {
		ClienteFachada clienteFachada = new ClienteFachada();
		Cliente cliente = new Cliente(id);
		
		return clienteFachada.transacoes(cliente);
	}
}
