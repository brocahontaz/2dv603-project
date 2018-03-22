package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.sql.rowset.CachedRowSet;
import com.sun.rowset.CachedRowSetImpl;

import model.testTableClass;

/**
 * Class for handling connections between the software and the database.
 * Handling the queries and updates.
 * 
 * @author Johan Andersson
 *
 */
public class DBParser {

	private Connection connection;
	private String url = "jdbc:mysql://soggarna.se:3306/2dv603";
	private String user = "2dv603";
	private String password = "gmU<B44SWnhdjATJ";

	private PreparedStatement ps = null;
	private CachedRowSetImpl crs;
	// private ResultSet rs = null;

	/**
	 * Constructor.
	 * 
	 * Creates an DBParser object
	 */
	public DBParser() {

	}
	
	/**
	 * TEST
	 * @param testid
	 * @param testname
	 */
	public void insertIntoTestTableTest(String testid, String testname) {
		
		String[] temp = {testid, testname};
		
		this.executeUpdate(Queries.INSERT_TEST_TABLE.toString(), temp);
	}
	
	/**
	 * TEST
	 * @return
	 */
	public ArrayList<testTableClass> getTestTableClass() {
		
		ArrayList<testTableClass> test = new ArrayList<testTableClass>();
		
		String[] params = null;
		this.populateTestArray(test, this.executeQuery(Queries.GET_ALL_FROM_TEST_TABLE.toString(), params));
		
		return test;
	}
	
	/**
	 * TEST
	 * @param list
	 * @param crsTemp
	 */
	private void populateTestArray(ArrayList<model.testTableClass> list, CachedRowSetImpl crsTemp) {
		try {
			while (crsTemp.next()) {
				list.add(new model.testTableClass(crsTemp.getInt("testid"), crsTemp.getString("testname")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				crsTemp.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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
		CachedRowSetImpl crsTemp = executeSingleParamQuery(Queries.GET_CUSTOMER_BY_LASTNAME.toString(), lastname);

		populateGuestArray(guests, crsTemp);

		return guests;

		// return executeSingleParamQuery(Queries.GET_CUSTOMER_BY_LASTNAME.toString(),
		// lastname);
	}
	
	private void populateGuestArray(ArrayList<model.Guest> list, CachedRowSetImpl crsTemp) {
		try {
			while (crsTemp.next()) {
				list.add(new model.Guest(crsTemp.getString("firstName"), crsTemp.getString("lastName"),
						crsTemp.getString("address"), crsTemp.getString("telephoneNumber"),
						crsTemp.getString("creditCard"), crsTemp.getString("passportNumber")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				crsTemp.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Private help method to execute query with a single parameter on the database.
	 * 
	 * @param query
	 *            the query to be used in the prepared statement
	 * @param param
	 *            the parameter to be used
	 */
	private CachedRowSetImpl executeSingleParamQuery(String query, String param) {
		String[] params = { param };

		return this.executeQuery(query, params);
	}

	/**
	 * Private help method to execute update with a single parameter on the
	 * database.
	 * 
	 * @param query
	 *            the query to be used in the prepared statement
	 * @param param
	 *            the parameter to be used
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
	private CachedRowSetImpl executeQuery(String query, String[] params) {
		this.initialize();
		ResultSet rs = null;
		CachedRowSetImpl crs = null;

		try {

			this.ps = this.connection.prepareStatement(query);

			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					this.ps.setString(i + 1, params[i]);
				}
			}

			rs = this.ps.executeQuery();
			crs = new CachedRowSetImpl();
			crs.populate(rs);
			rs.close();

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

		return crs;
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
				e.printStackTrace();
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
			Class.forName("com.mysql.jdbc.Driver").newInstance();
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
