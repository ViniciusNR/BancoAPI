package com.selecaoinfoway.util;

public class Util {
	public static void incluirClausulaNoWhere_AND(StringBuffer select,
			String clausula) {

		if (select.toString().toUpperCase().lastIndexOf("WHERE") > select
				.toString().toUpperCase().lastIndexOf("FROM")) {
			select.append(" AND (");
			select.append(clausula);
			select.append(") ");
		} else {
			select.append(" WHERE (");
			select.append(clausula);
			select.append(") ");
		}
	}
	
	public static void incluirClausulaNoWhere_OR(StringBuffer select,
			String clausula) {

		if (select.toString().toUpperCase().lastIndexOf("WHERE") > select
				.toString().toUpperCase().lastIndexOf("FROM")) {
			select.append(" OR (");
			select.append(clausula);
			select.append(") ");
		} else {
			select.append(" WHERE (");
			select.append(clausula);
			select.append(") ");
		}
	}
}
