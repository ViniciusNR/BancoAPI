package com.selecaoinfoway.restcontrollers;

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
}
