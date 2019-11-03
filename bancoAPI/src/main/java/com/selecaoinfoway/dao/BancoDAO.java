package com.selecaoinfoway.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.selecaoinfoway.entities.Banco;
import com.selecaoinfoway.util.Util;

public class BancoDAO extends BaseDAO {
	
	private static final String TABLE = " Bancos ";
	private static final String INSERT = " INSERT INTO " + TABLE + " (nome, sigla) VALUES (? , ? )";
	private static final String SELECT = " SELECT * FROM " + TABLE;
	
	public Banco inserir(Banco vo) throws SQLException {
		int id;
		try {
			super.prepararDAO(INSERT);
			
			int indice = 1;
			st.setString(indice++, vo.getNome());
			st.setString(indice++, vo.getSigla());
			
			id = super.atualizarERetornarID();
			vo.setId(id);
			
			return vo;
		} finally {
			super.fecharConexao();
		}
	}
	
	public ArrayList<Object> listar(Banco vo) throws SQLException {
		try {
			StringBuffer sql = new StringBuffer(SELECT);

			if(vo.getNome() != null) {
				Util.incluirClausulaNoWhere_AND(sql, " nome like ? ");
			}

			super.prepararDAO(sql);
			
			int indice = 1;
			
			if(vo.getNome() != null) {
				st.setString(indice++, "%" + vo.getNome() + "%");
			}
			
			ResultSet rs = super.listar();
			
			Banco banco = null;
			ArrayList<Object> bancos = new ArrayList<Object>();
			
			while(rs.next()) {
				banco = new Banco();
				banco.setId(rs.getInt("id"));
				banco.setNome(rs.getString("nome"));
				banco.setSigla(rs.getString("sigla"));

				bancos.add(banco);
			}
			
			rs.close();
			rs = null;
			
			return bancos;
		} finally {
			super.fecharConexao();
		}
	}
}
