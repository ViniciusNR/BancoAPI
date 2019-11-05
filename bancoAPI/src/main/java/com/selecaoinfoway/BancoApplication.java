package com.selecaoinfoway;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.selecaoinfoway.dao.FabricaDAO;

@SpringBootApplication
public class BancoApplication {

	public BancoApplication() {
		try {
			configurarBanco();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		SpringApplication.run(BancoApplication.class, args);
	}
	
	private void configurarBanco() throws IOException {
		Properties configuracao = new Properties();
		try {
			String path = getClass().getProtectionDomain().getCodeSource().getLocation().toString().substring(6);
			configuracao.load(new FileInputStream("/" + path + "application.properties"));
		} catch (IOException e) {
			InputStream is = getClass().getResourceAsStream("/application.properties");
			try {
				configuracao.load(is);
			} catch (IOException e1) {
				throw e1;
			}
		}
		
		FabricaDAO.setarParametrosConexao(configuracao.getProperty("mysql"), configuracao.getProperty("usuario"), configuracao.getProperty("senha"));
	}
}
