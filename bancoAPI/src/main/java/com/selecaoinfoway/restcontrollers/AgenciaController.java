package com.selecaoinfoway.restcontrollers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.selecaoinfoway.entities.Agencia;
import com.selecaoinfoway.entities.Resultado;
import com.selecaoinfoway.entities.ResultadoLista;
import com.selecaoinfoway.fachada.AgenciaFachada;

@RestController
public class AgenciaController {

	@PostMapping("/agencias")
	public Resultado gravar(@RequestBody Agencia agencia) {
		AgenciaFachada agenciaFachada = new AgenciaFachada();
		
		return agenciaFachada.gravar(agencia);
	}
	
	@GetMapping("/agencias")
	public ResultadoLista listar() {
		AgenciaFachada agenciaFachada = new AgenciaFachada();
		Agencia agencia = new Agencia();
		
		return agenciaFachada.listar(agencia);
	}
	
	@GetMapping("/agencias/{id}")
	public Resultado recuperar(@PathVariable Integer id) {
		AgenciaFachada agenciaFachada = new AgenciaFachada();
		Agencia agencia = new Agencia();
		agencia.setId(id);
		
		return agenciaFachada.recuperar(agencia);
	}
	
	@DeleteMapping("/agencias/{id}")
	public Resultado remover(@PathVariable Integer id) {
		AgenciaFachada agenciaFachada = new AgenciaFachada();
		
		return agenciaFachada.remover(id);
	}
}
