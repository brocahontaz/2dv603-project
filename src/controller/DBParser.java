package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBParser {

	private Connection connection;
	private String url = "";
	private String user = "";
	private String password = "";

	private PreparedStatement ps = null;

	public DBParser() {

	}

	private ResultSet executeQuery(String query, String[] params) {
		this.initialize();
		ResultSet rs = null;

		// params = params != null ? params : null;

		try {
			// connection.setAutoCommit(false);

			this.ps = this.connection.prepareStatement(query);

			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					this.ps.setString(i + 1, params[i]);
				}
			}

			rs = this.ps.executeQuery();

			// return rs;

		} catch (SQLException e) {
			try {
				System.err.print("Transaction is being rolled back");
				this.connection.rollback();
			} catch (SQLException excep) {
				excep.printStackTrace();
			}
		} finally {
			this.shutdown();
		}

		return rs;
	}

	private void executeUpdate(String query, String[] params) {
		this.initialize();

		try {
			// connection.setAutoCommit(false);

			this.ps = this.connection.prepareStatement(query);

			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					this.ps.setString(i + 1, params[i]);
				}
			}
			
			this.ps.executeUpdate();
			this.connection.commit();

		} catch (SQLException e) {
			try {
				System.err.print("Transaction is being rolled back");
				this.connection.rollback();
			} catch (SQLException excep) {
				excep.printStackTrace();
			}
		} finally {
			this.shutdown();
		}
	}

	private void initialize() {
		try {
			this.connection = DriverManager.getConnection(this.url, this.user, this.password);
			this.connection.setAutoCommit(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void shutdown() {
		try {
			this.connection.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (this.ps != null) {
				try {
					this.ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
