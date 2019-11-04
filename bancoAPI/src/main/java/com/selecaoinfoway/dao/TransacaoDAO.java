package com.selecaoinfoway.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import com.selecaoinfoway.entities.Cliente;
import com.selecaoinfoway.entities.Conta;
import com.selecaoinfoway.entities.Transacao;
import com.selecaoinfoway.util.Util;

public class TransacaoDAO extends BaseDAO {
	
	private static final String TABLE = " Transacoes ";
	private static final String INSERT = " INSERT INTO " + TABLE + " (tipo, valor, idContaOrigem, idContaDestino) VALUES (? , ? , ? , ?)";
	private static final String UPDATE_DEPOSITAR = " UPDATE Contas SET saldo = saldo + ? WHERE id = ? ";
	private static final String UPDATE_SACAR = " UPDATE Contas SET saldo = saldo - ? WHERE id = ? ";
	private static final String SELECT_VERIFICAR_SALDO = " SELECT saldo FROM Contas WHERE id = ? ";
	private static final String SELECT = " SELECT "
			+ " Transacoes.id, "
			+ " Transacoes.tipo, "
			+ " Transacoes.valor, "
			+ " ContasOrigem.id, "
			+ " ContasOrigem.numero, "
			+ " ContasDestino.id, "
			+ " ContasDestino.numero "
			+ " FROM " + TABLE 
			+ " LEFT JOIN Contas AS ContasOrigem ON ContasOrigem.id = Transacoes.idContaOrigem "
			+ " LEFT JOIN Contas AS ContasDestino ON ContasDestino.id = Transacoes.idContaDestino ";
	
	public Transacao inserir(Transacao vo) throws Exception {
		try {
			super.iniciarTransacao();
			
			inserirTransacao(vo);
			switch (vo.getTipo()) {
			case DEPOSITO:
				depositarConta(vo);
				break;
			case SAQUE:
				sacarConta(vo);
				break;
			case TRANSFERENCIA:
				sacarConta(vo);
				depositarConta(vo);
				break;
			default:
				break;
			}
			
			super.finalizarTransacao();
			
			return vo;
		} catch (Exception e) {
			super.desfazerTransacao();
			throw e;
		} finally {
			super.fecharConexao();
		}
	}
	
	public Transacao inserirTransacao(Transacao vo) throws SQLException {
		int id;
		try {
			super.prepararDAO(INSERT);
			
			int indice = 1;
			st.setString(indice++, vo.getTipo().toString());
			st.setDouble(indice++, vo.getValor());
			if(vo.getContaOrigem() != null && vo.getContaOrigem().getId() != 0) {
				st.setInt(indice++, vo.getContaOrigem().getId());
			} else {
				st.setNull(indice++, Types.INTEGER);
			}
			if(vo.getContaDestino() != null && vo.getContaDestino().getId() != 0) {
				st.setInt(indice++, vo.getContaDestino().getId());
			} else {
				st.setNull(indice++, Types.INTEGER);
			}
			
			id = super.atualizarERetornarID();
			vo.setId(id);
			
			return vo;
		} finally {
			super.fecharConexao();
		}
	}
	
	public void depositarConta(Transacao vo) throws SQLException {
		try {
			super.prepararDAO(UPDATE_DEPOSITAR);
			
			int indice = 1;

			st.setDouble(indice++, vo.getValor());
			st.setInt(indice++, vo.getContaDestino().getId());
			
			super.atualizar();
		} finally {
			super.fecharConexao();
		}
	}
	
	public void sacarConta(Transacao vo) throws SQLException {
		try {
			super.prepararDAO(UPDATE_SACAR);
			
			int indice = 1;

			st.setDouble(indice++, vo.getValor());
			st.setInt(indice++, vo.getContaOrigem().getId());
			
			super.atualizar();
		} finally {
			super.fecharConexao();
		}
	}
	
	public boolean verificarSaldoDisponivel(Transacao vo) throws SQLException {
		try {
			StringBuffer sql = new StringBuffer(SELECT_VERIFICAR_SALDO);

			super.prepararDAO(sql);
			
			st.setInt(1, vo.getContaOrigem().getId());
			
			ResultSet rs = super.listar();
			
			boolean saldoDisponivel = false;
			
			if(rs.next()) {
				saldoDisponivel = rs.getDouble("saldo") >= vo.getValor();
			}
			
			rs.close();
			rs = null;
			
			return saldoDisponivel;
		} finally {
			super.fecharConexao();
		}
	}
	
	public ArrayList<Transacao> listar(Cliente cliente) throws SQLException {
		try {
			StringBuffer sql = new StringBuffer(SELECT);
			
			Util.incluirClausulaNoWhere_OR(sql, " Transacoes.idContaOrigem = ? ");
			Util.incluirClausulaNoWhere_OR(sql, " Transacoes.idContaDestino = ? ");

			super.prepararDAO(sql);
			
			int indice = 1;
			
			st.setInt(indice++, cliente.getConta().getId());
			st.setInt(indice++, cliente.getConta().getId());
			
			ResultSet rs = super.listar();
			
			Transacao transacao = null;
			ArrayList<Transacao> transacoes = new ArrayList<Transacao>();
			
			while(rs.next()) {
				transacao = new Transacao();
				transacao.setId(rs.getInt("Transacoes.id"));
				transacao.setTipo(rs.getString("Transacoes.tipo"));
				transacao.setValor(rs.getDouble("Transacoes.valor"));
				
				if(rs.getInt("ContasOrigem.id") != 0) {
					transacao.setContaOrigem(new Conta());
					transacao.getContaOrigem().setId(rs.getInt("ContasOrigem.id"));
					transacao.getContaOrigem().setNumero(rs.getString("ContasOrigem.numero"));
				}

				if(rs.getInt("ContasDestino.id") != 0) {					
					transacao.setContaDestino(new Conta());
					transacao.getContaDestino().setId(rs.getInt("ContasDestino.id"));
					transacao.getContaDestino().setNumero(rs.getString("ContasDestino.numero"));
				}
				
				transacoes.add(transacao);
			}
			
			rs.close();
			rs = null;
			
			return transacoes;
		} finally {
			super.fecharConexao();
		}
	}
}
