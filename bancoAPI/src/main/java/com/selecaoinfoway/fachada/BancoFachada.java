package com.selecaoinfoway.fachada;

import java.util.ArrayList;

import com.selecaoinfoway.dao.BancoDAO;
import com.selecaoinfoway.entities.Banco;
import com.selecaoinfoway.entities.Resultado;
import com.selecaoinfoway.entities.ResultadoLista;
import com.selecaoinfoway.enums.StatusResultado;

public class BancoFachada {
	
	public Resultado gravar(Banco banco) {
		Resultado resultado = new Resultado();
		BancoDAO bancoDAO = new BancoDAO();
		try {
			banco = bancoDAO.inserir(banco);
			
			resultado.setItem(banco);
			resultado.setStatus(StatusResultado.SUCESSO);
			resultado.setMensagem("Banco inserido com sucesso!");
		} catch (Exception e) {
			resultado.setItem(new Banco());
			resultado.setStatus(StatusResultado.FALHA);
			resultado.setMensagem("Falha ao inserir Banco!");
		}
		
		return resultado;
	}
	
	public ResultadoLista listar(Banco banco) {
		ResultadoLista resultado = new ResultadoLista();
		BancoDAO bancoDAO = new BancoDAO();
		ArrayList<Object> bancos = new ArrayList<Object>();
		try {
			bancos = bancoDAO.listar(banco);
			
			resultado.setItens(bancos);
			resultado.setStatus(StatusResultado.SUCESSO);
			resultado.setMensagem("Lista de Bancos retornada com sucesso!");
		} catch (Exception e) {
			resultado.setItens(new ArrayList<Object>());
			resultado.setStatus(StatusResultado.FALHA);
			resultado.setMensagem("Falha ao listar Bancos!");
		}
		
		return resultado;
	}
}
