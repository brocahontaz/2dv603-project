package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Class for handling connections between the software and the database.
 * Handling the queries and updates.
 * 
 * @author Johan Andersson
 *
 */
public class DBParser {

	private Connection connection;
	private String url = "";
	private String user = "";
	private String password = "";

	private PreparedStatement ps = null;
	// private ResultSet rs = null;

	/**
	 * Constructor.
	 * 
	 * Creates an DBParser object
	 */
	public DBParser() {

	}

	/**
	 * EXAMPLE FUNCTION.
	 * 
	 * Method to get customers by last name.
	 * 
	 * @param lastname
	 *            the last name
	 * @return <ArrayList> the results
	 */
	public ArrayList<model.Guest> getCustomerByLastName(String lastname) {

		ArrayList<model.Guest> guests = new ArrayList<model.Guest>();
		ResultSet rsTemp = executeSingleParamQuery(Queries.GET_CUSTOMER_BY_LASTNAME.toString(), lastname);

		try {
			while (rsTemp.next()) {
				guests.add(new model.Guest(rsTemp.getString("firstName"), rsTemp.getString("lastName"),
						rsTemp.getString("address"), rsTemp.getString("telephoneNumber"),
						rsTemp.getString("creditCard"), rsTemp.getString("passportNumber")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				rsTemp.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return guests;

		//return executeSingleParamQuery(Queries.GET_CUSTOMER_BY_LASTNAME.toString(), lastname);
	}

	/**
	 * Private help method to execute query with a single parameter on the
	 * database.
	 * 
	 * @param query
	 *            the query to be used in the prepared statement
	 */
	private ResultSet executeSingleParamQuery(String query, String param) {
		String[] params = { param };

		return this.executeQuery(query, params);
	}

	/**
	 * Private help method to execute update with a single parameter on the
	 * database.
	 * 
	 * @param query
	 *            the query to be used in the prepared statement
	 */
	private void executeSingleParamUpdate(String query, String param) {
		String[] params = { param };

		this.executeUpdate(query, params);
	}

	/**
	 * Private help method to execute queries on the database.
	 * 
	 * @param query
	 *            the query to be used in the prepared statement
	 * @param params
	 *            the parameters to be used for the prepared statement
	 */
	private ResultSet executeQuery(String query, String[] params) {
		this.initialize();
		ResultSet rs = null;

		try {

			this.ps = this.connection.prepareStatement(query);

			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					this.ps.setString(i + 1, params[i]);
				}
			}

			rs = this.ps.executeQuery();

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

	/**
	 * Private help method to execute updates on the database.
	 * 
	 * @param query
	 *            the query to be used in the prepared statement
	 * @param params
	 *            the parameters to be used for the prepared statement
	 */
	private void executeUpdate(String query, String[] params) {
		this.initialize();

		try {

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

	/**
	 * Private help method to initialize the database connection.
	 */
	private void initialize() {
		try {
			this.connection = DriverManager.getConnection(this.url, this.user, this.password);
			this.connection.setAutoCommit(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Private help method to shutdown the database connection.
	 */
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
