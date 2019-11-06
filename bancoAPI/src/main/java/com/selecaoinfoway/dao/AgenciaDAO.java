package com.selecaoinfoway.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.selecaoinfoway.entities.Agencia;
import com.selecaoinfoway.util.Util;

public class AgenciaDAO extends BaseDAO {
	
	private static final String TABLE = " Agencias ";
	private static final String SELECT = " SELECT * FROM " + TABLE + " WHERE inativo = 0 ";
	private static final String INSERT = " INSERT INTO " + TABLE + " (nome, numero, idBanco) VALUES (? , ? , ?)";
	private static final String UPDATE = " UPDATE " + TABLE + " SET nome = ?, numero = ? WHERE id = ? ";
	private static final String DELETE = " UPDATE " + TABLE + " SET inativo = 1 WHERE id = ? ";
	
	public Agencia inserir(Agencia vo) throws SQLException {
		int id;
		try {
			super.prepararDAO(INSERT);
			
			int indice = 1;
			st.setString(indice++, vo.getNome());
			st.setString(indice++, vo.getNumero());
			st.setInt(indice++, vo.getBanco().getId());
			
			id = super.atualizarERetornarID();
			vo.setId(id);
			
			return vo;
		} finally {
			super.fecharConexao();
		}
	}
	
	public void atualizar(Agencia vo) throws SQLException {
		try {
			super.prepararDAO(UPDATE);
			
			int indice = 1;
			st.setString(indice++, vo.getNome());
			st.setString(indice++, vo.getNumero());
			
			st.setInt(indice++, vo.getId());
			
			super.atualizar();
		} finally {
			super.fecharConexao();
		}
	}
	
	public Agencia recuperar(Agencia vo) throws SQLException {
		try {
			StringBuffer sql = new StringBuffer(SELECT);

			if(vo != null && vo.getId() != 0) {
				Util.incluirClausulaNoWhere_AND(sql, " id = ? ");
			}
			
			super.prepararDAO(sql);
			
			int indice = 1;
			
			if(vo != null && vo.getId() != 0) {
				st.setInt(indice++, vo.getId());
			}
			
			ResultSet rs = super.listar();
			
			Agencia agencia = null;
			
			if(rs.next()) {
				agencia = new Agencia();
				agencia.setId(rs.getInt("id"));
				agencia.setNome(rs.getString("nome"));
				agencia.setNumero(rs.getString("numero"));
			}
			
			rs.close();
			rs = null;
			
			return agencia;
		} finally {
			super.fecharConexao();
		}
	}
	
	public ArrayList<Object> listar(Agencia vo) throws SQLException {
		try {
			StringBuffer sql = new StringBuffer(SELECT);

			if(vo.getBanco() != null && vo.getBanco().getId() != 0) {
				Util.incluirClausulaNoWhere_AND(sql, " idBanco = ? ");
			}
			
			if(vo.getNome() != null) {
				Util.incluirClausulaNoWhere_AND(sql, " nome like ? ");
			}

			super.prepararDAO(sql);
			
			int indice = 1;
			
			if(vo.getBanco() != null && vo.getBanco().getId() != 0) {
				st.setInt(indice++, vo.getBanco().getId());
			}
			
			if(vo.getNome() != null) {
				st.setString(indice++, "%" + vo.getNome() + "%");
			}
			
			ResultSet rs = super.listar();
			
			Agencia agencia = null;
			ArrayList<Object> agencias = new ArrayList<Object>();
			
			while(rs.next()) {
				agencia = new Agencia();
				agencia.setId(rs.getInt("id"));
				agencia.setNome(rs.getString("nome"));
				agencia.setNumero(rs.getString("numero"));

				agencias.add(agencia);
			}
			
			rs.close();
			rs = null;
			
			return agencias;
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
}
