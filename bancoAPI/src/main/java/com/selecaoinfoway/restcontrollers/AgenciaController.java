package com.selecaoinfoway.restcontrollers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.selecaoinfoway.entities.Agencia;
import com.selecaoinfoway.entities.Resultado;
import com.selecaoinfoway.entities.ResultadoLista;
import com.selecaoinfoway.fachada.AgenciaFachada;

@RestController
@RequestMapping("/agencia")
public class AgenciaController {

	@PostMapping("/gravar")
	public Resultado gravar(@RequestBody Agencia agencia) {
		AgenciaFachada agenciaFachada = new AgenciaFachada();
		
		return agenciaFachada.gravar(agencia);
	}
	
	@PostMapping("/listar")
	public ResultadoLista listar(@RequestBody Agencia agencia) {
		AgenciaFachada agenciaFachada = new AgenciaFachada();
		
		return agenciaFachada.listar(agencia);
	}
}
