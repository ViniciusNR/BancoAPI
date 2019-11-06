package com.selecaoinfoway.fachada;

import java.util.ArrayList;

import com.selecaoinfoway.dao.ClienteDAO;
import com.selecaoinfoway.entities.Cliente;
import com.selecaoinfoway.entities.ClienteTransacao;
import com.selecaoinfoway.entities.Resultado;
import com.selecaoinfoway.entities.ResultadoLista;
import com.selecaoinfoway.enums.StatusResultado;

public class ClienteFachada {
	
	public Resultado gravar(Cliente cliente) {
		Resultado resultado = new Resultado();

		if(cliente.getId() == 0) {
			resultado = inserir(cliente);
		} else {
			resultado = atualizar(cliente);
		}
		
		return resultado;
	}
	
	public Resultado inserir(Cliente cliente) {
		Resultado resultado = new Resultado();
		ClienteDAO clienteDAO = new ClienteDAO();
		try {
			cliente = clienteDAO.inserir(cliente);
			
			resultado.setItem(cliente);
			resultado.setStatus(StatusResultado.SUCESSO);
			resultado.setMensagem("Cliente inserido com sucesso!");
		} catch (Exception e) {
			resultado.setItem(new Cliente());
			resultado.setStatus(StatusResultado.FALHA);
			resultado.setMensagem("Falha ao inserir Cliente!");
			e.printStackTrace();
		}
		
		return resultado;
	}
	
	public Resultado atualizar(Cliente cliente) {
		Resultado resultado = new Resultado();
		ClienteDAO clieteDAO = new ClienteDAO();
		try {
			clieteDAO.atualizar(cliente);
			
			resultado.setItem(cliente);
			resultado.setStatus(StatusResultado.SUCESSO);
			resultado.setMensagem("Cliente atualizado com sucesso!");
		} catch (Exception e) {
			resultado.setItem(new Cliente());
			resultado.setStatus(StatusResultado.FALHA);
			resultado.setMensagem("Falha ao atualizar Cliente!");
		}
		
		return resultado;
	}
	
	public Resultado recuperar(Cliente cliente) {
		Resultado resultado = new Resultado();
		ClienteDAO clienteDAO = new ClienteDAO();
		try {
			cliente = clienteDAO.recuperar(cliente);
			
			resultado.setItem(cliente);
			resultado.setStatus(StatusResultado.SUCESSO);
			resultado.setMensagem("Clientes recuperado com sucesso!");
		} catch (Exception e) {
			resultado.setItem(new Cliente());
			resultado.setStatus(StatusResultado.FALHA);
			resultado.setMensagem("Falha ao recuperar Clientes!");
			e.printStackTrace();
		}
		
		return resultado;
	}
	
	public ResultadoLista listar(Cliente cliente) {
		ResultadoLista resultado = new ResultadoLista();
		ClienteDAO clienteDAO = new ClienteDAO();
		ArrayList<Object> clientes = new ArrayList<Object>();
		try {
			clientes = clienteDAO.listar(cliente);
			
			resultado.setItens(clientes);
			resultado.setStatus(StatusResultado.SUCESSO);
			resultado.setMensagem("Lista de Clientes retornada com sucesso!");
		} catch (Exception e) {
			resultado.setItens(new ArrayList<Object>());
			resultado.setStatus(StatusResultado.FALHA);
			resultado.setMensagem("Falha ao listar Clientes!");
			e.printStackTrace();
		}
		
		return resultado;
	}
	
	public Resultado remover(int id) {
		Resultado resultado = new Resultado();
		ClienteDAO clienteDAO = new ClienteDAO();
		try {
			if(clienteDAO.remover(id) != 1) {
				resultado.setStatus(StatusResultado.FALHA);
				resultado.setMensagem("Cliente não encontrado!");
				return resultado;
			}
			
			resultado.setStatus(StatusResultado.SUCESSO);
			resultado.setMensagem("Cliente removido com sucesso!");
		} catch (Exception e) {
			resultado.setItem(new Cliente());
			resultado.setStatus(StatusResultado.FALHA);
			resultado.setMensagem("Falha ao remover Cliente!");
		}
		
		return resultado;
	}
	
	public Resultado transacoes(Cliente cliente) {
		Resultado resultado = new Resultado();
		ClienteDAO clienteDAO = new ClienteDAO();
		ClienteTransacao clienteTransacao = new ClienteTransacao();
		try {
			clienteTransacao = clienteDAO.recuperarClienteTransacoes(cliente);
			
			resultado.setItem(clienteTransacao);
			resultado.setStatus(StatusResultado.SUCESSO);
			resultado.setMensagem("Extrato bancário retornado com sucesso!");
		} catch (Exception e) {
			resultado.setItem(new Object());
			resultado.setStatus(StatusResultado.FALHA);
			resultado.setMensagem("Falha ao retornar extrato bancário!");
			e.printStackTrace();
		}
		
		return resultado;
	}
}
