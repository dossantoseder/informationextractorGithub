package br.com.sants.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.postgresql.Driver;

public class ConnectionSingleton {
	private static ConnectionSingleton connectionSingleton = null;
	private static Connection connection = null;
	private String url = "jdbc:postgresql://localhost:5432/git";
	private String username = "postgres";
	private String password = "p01s25@05";

	public static ConnectionSingleton getInstance() {

		if (connectionSingleton == null)
			connectionSingleton = new ConnectionSingleton();
		
		return connectionSingleton;
	}

	public Connection getConnection() {

		if (connection == null) {
			try {
				DriverManager.registerDriver(new Driver());
				connection = DriverManager.getConnection(url, username, password);
			} catch (SQLException e) {
				System.out.println("\n>> Problema ao obter conexao\n");
				e.printStackTrace();
			}
		}

		return connection;
	}

	public void closeConnection() {

		if (connection != null) {

			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println("\n>> Problema ao fechar conexao\n");
				e.printStackTrace();
			}
		}
	}

	public void finalize() {
		closeConnection();
	}

}
