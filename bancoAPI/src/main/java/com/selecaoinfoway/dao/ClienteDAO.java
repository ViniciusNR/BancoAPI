package com.selecaoinfoway.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.selecaoinfoway.entities.Agencia;
import com.selecaoinfoway.entities.Banco;
import com.selecaoinfoway.entities.Cliente;
import com.selecaoinfoway.entities.ClienteTransacao;
import com.selecaoinfoway.entities.Conta;
import com.selecaoinfoway.util.Util;

public class ClienteDAO extends BaseDAO {
	
	private static final String TABLE = " Clientes ";
	private static final String SELECT = " SELECT "
			+ " Clientes.id, "
			+ " Clientes.nome, "
			+ " Clientes.cpf, "
			+ " Clientes.telefone, "
			+ " Contas.id, "
			+ " Contas.numero, "
			+ " Contas.tipo, "
			+ " Contas.saldo, "
			+ " Agencias.id, "
			+ " Agencias.nome, "
			+ " Agencias.numero, "
			+ " Bancos.id, "
			+ " Bancos.nome, "
			+ " Bancos.sigla "
			+ " FROM " + TABLE 
			+ " JOIN Contas ON Contas.idCliente = Clientes.id "
			+ " JOIN Agencias ON Agencias.id = Contas.idAgencia "
			+ " JOIN Bancos ON Bancos.id = Agencias.idBanco "
			+ " WHERE Clientes.inativo = 0 ";
	private static final String INSERT = " INSERT INTO " + TABLE + " (nome, cpf, telefone, idAgencia) VALUES (? , ? , ? , ?)";
	private static final String INSERT_CONTA = " INSERT INTO Contas (numero, tipo, idCliente, idAgencia) VALUES (? , ? , ? , ?)";
	private static final String UPDATE = " UPDATE " + TABLE + " SET nome = ?, cpf = ?, telefone = ? WHERE id = ? ";
	private static final String UPDATE_CONTA = " UPDATE Contas SET numero = ?, tipo = ? WHERE id = ? ";
	private static final String DELETE = " UPDATE " + TABLE + " SET inativo = 1 WHERE id = ? ";
	
	public Cliente inserir(Cliente vo) throws Exception {
		try {
			super.iniciarTransacao();
			
			inserirCliente(vo);
			inserirConta(vo);
			
			super.finalizarTransacao();
			
			return vo;
		} catch (Exception e) {
			super.desfazerTransacao();
			throw e;
		} finally {
			super.fecharConexao();
		}
	}
	
	public Cliente inserirCliente(Cliente vo) throws SQLException {
		int id;
		try {
			super.prepararDAO(INSERT);
			
			int indice = 1;
			st.setString(indice++, vo.getNome());
			st.setString(indice++, vo.getCpf());
			st.setString(indice++, vo.getTelefone());
			
			st.setInt(indice++, vo.getConta().getAgencia().getId());
			
			id = super.atualizarERetornarID();
			vo.setId(id);
			
			return vo;
		} finally {
			super.fecharConexao();
		}
	}
	
	public Cliente inserirConta(Cliente vo) throws SQLException {
		int id;
		try {
			super.prepararDAO(INSERT_CONTA);
			
			int indice = 1;
			st.setString(indice++, vo.getConta().getNumero());
			st.setString(indice++, vo.getConta().getTipo().toString());
			st.setInt(indice++, vo.getId());
			
			st.setInt(indice++, vo.getConta().getAgencia().getId());
			
			id = super.atualizarERetornarID();
			vo.getConta().setId(id);
			
			return vo;
		} finally {
			super.fecharConexao();
		}
	}
	
	public void atualizar(Cliente vo) throws Exception {
		try {
			super.iniciarTransacao();
			
			atualizarCliente(vo);
			atualizarConta(vo);
			
			super.finalizarTransacao();
			
		} catch (Exception e) {
			super.desfazerTransacao();
			throw e;
		} finally {
			super.fecharConexao();
		}
	}
	
	public void atualizarCliente(Cliente vo) throws SQLException {
		try {
			super.prepararDAO(UPDATE);
			
			int indice = 1;
			st.setString(indice++, vo.getNome());
			st.setString(indice++, vo.getCpf());
			st.setString(indice++, vo.getTelefone());
			
			st.setInt(indice++, vo.getId());
			
			super.atualizar();
		} finally {
			super.fecharConexao();
		}
	}
	
	public Cliente atualizarConta(Cliente vo) throws SQLException {
		int id;
		try {
			super.prepararDAO(UPDATE_CONTA);
			
			int indice = 1;
			st.setString(indice++, vo.getConta().getNumero());
			st.setString(indice++, vo.getConta().getTipo().toString());
			
			st.setInt(indice++, vo.getId());
			
			id = super.atualizarERetornarID();
			vo.getConta().setId(id);
			
			return vo;
		} finally {
			super.fecharConexao();
		}
	}
	
	public int remover(int id) throws SQLException {
		try {
			super.prepararDAO(DELETE);
			
			int indice = 1;
			st.setInt(indice++, id);
			
			return super.atualizar();
		} finally {
			super.fecharConexao();
		}
	}
	
	public Cliente recuperar(Cliente vo) throws SQLException {
		try {
			StringBuffer sql = new StringBuffer(SELECT);
			
			if(vo != null && vo.getId() != 0) {
				Util.incluirClausulaNoWhere_AND(sql, " Clientes.id = ? ");				
			}
			if(vo != null && vo.getNome() != null && !vo.getNome().isEmpty()) {
				Util.incluirClausulaNoWhere_AND(sql, " Clientes.nome = ? ");				
			}

			super.prepararDAO(sql);
			
			int indice = 1;
			
			if(vo != null && vo.getId() != 0) {
				st.setInt(indice++, vo.getId());
			}
			if(vo != null && vo.getNome() != null && !vo.getNome().isEmpty()) {
				st.setString(indice++, vo.getNome());				
			}
			
			ResultSet rs = super.listar();
			
			Cliente cliente = null;
			
			if(rs.next()) {
				cliente = new Cliente();
				cliente.setId(rs.getInt("Clientes.id"));
				cliente.setNome(rs.getString("Clientes.nome"));
				cliente.setTelefone(rs.getString("Clientes.telefone"));
				
				cliente.setConta(new Conta());
				cliente.getConta().setId(rs.getInt("Contas.id"));
				cliente.getConta().setNumero(rs.getString("Contas.numero"));
				cliente.getConta().setTipo(rs.getString("Contas.tipo"));
				cliente.getConta().setSaldo(rs.getDouble("Contas.saldo"));
				cliente.getConta().setAgencia(new Agencia());
				
				cliente.getConta().getAgencia().setId(rs.getInt("Agencias.id"));
				cliente.getConta().getAgencia().setNome(rs.getString("Agencias.nome"));
				cliente.getConta().getAgencia().setNumero(rs.getString("Agencias.numero"));
				
				cliente.getConta().getAgencia().setBanco(new Banco());
				cliente.getConta().getAgencia().getBanco().setId(rs.getInt("Bancos.id"));
				cliente.getConta().getAgencia().getBanco().setNome(rs.getString("Bancos.nome"));
				cliente.getConta().getAgencia().getBanco().setSigla(rs.getString("Bancos.sigla"));
			}
			
			rs.close();
			rs = null;
			
			return cliente;
		} finally {
			super.fecharConexao();
		}
	}
	
	public ClienteTransacao recuperarClienteTransacoes(Cliente vo) throws SQLException {
		TransacaoDAO transacaoDAO = new TransacaoDAO();
		ClienteTransacao clienteTransacao = new ClienteTransacao();
		
		clienteTransacao.setCliente(recuperar(vo));
		clienteTransacao.setTransacoes(transacaoDAO.listar(clienteTransacao.getCliente()));
		
		return clienteTransacao;
	}
	
	public ArrayList<Object> listar(Cliente vo) throws SQLException {
		try {
			StringBuffer sql = new StringBuffer(SELECT);

			if(vo.getNome() != null) {
				Util.incluirClausulaNoWhere_AND(sql, " Clientes.nome like ? ");
			}

			super.prepararDAO(sql);
			
			int indice = 1;
			
			if(vo.getNome() != null) {
				st.setString(indice++, "%" + vo.getNome() + "%");
			}
			
			ResultSet rs = super.listar();
			
			Cliente cliente = null;
			ArrayList<Object> clientes = new ArrayList<Object>();
			
			while(rs.next()) {
				cliente = new Cliente();
				cliente.setId(rs.getInt("Clientes.id"));
				cliente.setNome(rs.getString("Clientes.nome"));
				cliente.setTelefone(rs.getString("Clientes.telefone"));
				
				cliente.setConta(new Conta());
				cliente.getConta().setId(rs.getInt("Contas.id"));
				cliente.getConta().setNumero(rs.getString("Contas.numero"));
				cliente.getConta().setTipo(rs.getString("Contas.tipo"));
				cliente.getConta().setSaldo(rs.getDouble("Contas.saldo"));
				cliente.getConta().setAgencia(new Agencia());
				
				cliente.getConta().getAgencia().setId(rs.getInt("Agencias.id"));
				cliente.getConta().getAgencia().setNome(rs.getString("Agencias.nome"));
				cliente.getConta().getAgencia().setNumero(rs.getString("Agencias.numero"));
				
				cliente.getConta().getAgencia().setBanco(new Banco());
				cliente.getConta().getAgencia().getBanco().setId(rs.getInt("Bancos.id"));
				cliente.getConta().getAgencia().getBanco().setNome(rs.getString("Bancos.nome"));
				cliente.getConta().getAgencia().getBanco().setSigla(rs.getString("Bancos.sigla"));

				clientes.add(cliente);
			}
			
			rs.close();
			rs = null;
			
			return clientes;
		} finally {
			super.fecharConexao();
		}
	}
}
