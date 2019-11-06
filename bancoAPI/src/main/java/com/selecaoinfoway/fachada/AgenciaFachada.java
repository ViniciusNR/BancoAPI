package com.selecaoinfoway.fachada;

import java.util.ArrayList;

import com.selecaoinfoway.dao.AgenciaDAO;
import com.selecaoinfoway.entities.Agencia;
import com.selecaoinfoway.entities.Resultado;
import com.selecaoinfoway.entities.ResultadoLista;
import com.selecaoinfoway.enums.StatusResultado;

public class AgenciaFachada {
	
	public Resultado gravar(Agencia agencia) {
		Resultado resultado = new Resultado();

		if(agencia.getId() == 0) {
			resultado = inserir(agencia);
		} else {
			resultado = atualizar(agencia);
		}
		
		return resultado;
	}
	
	public Resultado inserir(Agencia agencia) {
		Resultado resultado = new Resultado();
		AgenciaDAO agenciaDAO = new AgenciaDAO();
		try {
			agencia = agenciaDAO.inserir(agencia);
			
			resultado.setItem(agencia);
			resultado.setStatus(StatusResultado.SUCESSO);
			resultado.setMensagem("Agencia inserida com sucesso!");
		} catch (Exception e) {
			resultado.setItem(new Agencia());
			resultado.setStatus(StatusResultado.FALHA);
			resultado.setMensagem("Falha ao inserir Agencia!");
		}
		
		return resultado;
	}
	
	public Resultado atualizar(Agencia agencia) {
		Resultado resultado = new Resultado();
		AgenciaDAO agenciaDAO = new AgenciaDAO();
		try {
			agenciaDAO.atualizar(agencia);
			
			resultado.setItem(agencia);
			resultado.setStatus(StatusResultado.SUCESSO);
			resultado.setMensagem("Agencia atualizada com sucesso!");
		} catch (Exception e) {
			resultado.setItem(new Agencia());
			resultado.setStatus(StatusResultado.FALHA);
			resultado.setMensagem("Falha ao atualizar Agencia!");
		}
		
		return resultado;
	}
	
	public Resultado recuperar(Agencia agencia) {
		Resultado resultado = new Resultado();
		AgenciaDAO agenciaDAO = new AgenciaDAO();
		try {
			agencia = agenciaDAO.recuperar(agencia);
			
			if(agencia != null) {
				resultado.setItem(agencia);
				resultado.setStatus(StatusResultado.SUCESSO);
				resultado.setMensagem("Agencia recuperada com sucesso!");
			} else {
				resultado.setItem(agencia);
				resultado.setStatus(StatusResultado.FALHA);
				resultado.setMensagem("Agencia não encontrada!");
			}
		} catch (Exception e) {
			resultado.setStatus(StatusResultado.FALHA);
			resultado.setMensagem("Falha ao recuperar Agencia!");
		}
		
		return resultado;
	}
	
	public ResultadoLista listar(Agencia agencia) {
		ResultadoLista resultado = new ResultadoLista();
		AgenciaDAO agenciaDAO = new AgenciaDAO();
		ArrayList<Object> agencias = new ArrayList<Object>();
		try {
			agencias = agenciaDAO.listar(agencia);
			
			resultado.setItens(agencias);
			resultado.setStatus(StatusResultado.SUCESSO);
			resultado.setMensagem("Lista de Agencias retornada com sucesso!");
		} catch (Exception e) {
			resultado.setItens(new ArrayList<Object>());
			resultado.setStatus(StatusResultado.FALHA);
			resultado.setMensagem("Falha ao listar Agencias!");
		}
		
		return resultado;
	}
	
	public Resultado remover(int id) {
		Resultado resultado = new Resultado();
		AgenciaDAO agenciaDAO = new AgenciaDAO();
		try {
			if(agenciaDAO.remover(id) != 1) {
				resultado.setStatus(StatusResultado.FALHA);
				resultado.setMensagem("Agencia não encontrada!");
				return resultado;
			}
			
			resultado.setStatus(StatusResultado.SUCESSO);
			resultado.setMensagem("Agencia removida com sucesso!");
		} catch (Exception e) {
			resultado.setItem(new Agencia());
			resultado.setStatus(StatusResultado.FALHA);
			resultado.setMensagem("Falha ao remover Agencia!");
		}
		
		return resultado;
	}
}
