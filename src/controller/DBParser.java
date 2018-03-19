package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBParser {

	private Connection connection;
	private String url = "";
	private String user = "";
	private String password = "";

	private PreparedStatement ps = null;

	public DBParser() {

	}

	private void executeQuery(String query, String[] params) {
		this.initialize();
		try {
			//connection.setAutoCommit(false);
			
			ps = connection.prepareStatement(query);
			
		} catch (SQLException e) {
			try {
				System.err.print("Transaction is being rolled back");
				connection.rollback();
			} catch (SQLException excep) {
				excep.printStackTrace();
			}
		} finally {
			this.shutdown();
		}
	}

	private void initialize() {
		try {
			connection = DriverManager.getConnection(url, user, password);
			connection.setAutoCommit(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void shutdown() {
		try {
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
