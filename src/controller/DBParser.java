package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.sql.rowset.CachedRowSet;
import com.sun.rowset.CachedRowSetImpl;

import test.testTableClass;

/**
 * Class for handling connections between the software and the database.
 * Handling the queries and updates.
 * 
 * @author Johan Andersson, Fredrik Norrman, David Larsson
 *
 */
public class DBParser {

	private Connection connection;
	private String url = ConnectInfo.getURL();
	private String user = ConnectInfo.getUser();
	private String password = ConnectInfo.getPassword();

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

	public boolean checkIn(int reservationID) {
		return false;
	}

	public boolean checkOut(int reservationID) {
		return false;
	}

	public boolean makeReservation() {
		return false;
	}

	public model.Room getAllAvailableRooms() {
		return null;
	}

	public model.Guest getGuest(String passportNumber) {
		return null;
	}

	public model.Room getRoom(int roomNumber) {
		return null;
	}

	/**
	 * Search for guests
	 * 
	 * @param firstName
	 *            the first name
	 * @param lastName
	 *            the last name
	 * @param address
	 *            the address
	 * @param telephoneNumber
	 *            the telephone number
	 * @param creditCard
	 *            the credit card
	 * @param passportNumber
	 *            the passport number
	 * @return <ArrayList> the guests
	 */
	public ArrayList<model.Guest> searchGuests(String firstName, String lastName, String address,
			String telephoneNumber, String creditCard, String passportNumber) {

		if (firstName.isEmpty() || firstName == null) {
			firstName = "%";
		}
		if (lastName.isEmpty() || lastName == null) {
			lastName = "%";
		}
		if (address.isEmpty() || address == null) {
			address = "%";
		}
		if (telephoneNumber.isEmpty() || telephoneNumber == null) {
			telephoneNumber = "%";
		}
		if (creditCard.isEmpty() || creditCard == null) {
			creditCard = "%";
		}
		if (passportNumber.isEmpty() || passportNumber == null) {
			passportNumber = "%";
		}

		ArrayList<model.Guest> guests = new ArrayList<model.Guest>();
		String[] temp = { firstName, lastName, address, telephoneNumber, creditCard, passportNumber };
		CachedRowSetImpl crsTemp = executeQuery(Queries.SEARCH_GUESTS, temp);

		populateGuestArray(guests, crsTemp);

		return guests;
	}

	/**
	 * Add a new guest to the database
	 * 
	 * @param firstName
	 *            the first name of the guest
	 * @param lastName
	 *            the last name of the guest
	 * @param address
	 *            the address of the guest
	 * @param telephoneNumber
	 *            the telephone number of the guest
	 * @param creditCard
	 *            the credit card number of the guest
	 * @param passportNumber
	 *            the passport number of the guest
	 */
	public void addNewGuest(String firstName, String lastName, String address, String telephoneNumber,
			String creditCard, String passportNumber) {

		String[] temp = { firstName, lastName, address, telephoneNumber, creditCard, passportNumber };
		this.executeUpdate(Queries.ADD_NEW_GUEST, temp);
	}

	/**
	 * Get all the guests in the database
	 * 
	 * @return ArrayList<model.Guest> the guests
	 */
	public ArrayList<model.Guest> getAllGuests() {

		ArrayList<model.Guest> guests = new ArrayList<model.Guest>();
		String[] temp = {};
		CachedRowSetImpl crsTemp = executeQuery(Queries.GET_ALL_GUESTS, temp);

		populateGuestArray(guests, crsTemp);

		return guests;
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
		CachedRowSetImpl crsTemp = executeSingleParamQuery(Queries.GET_CUSTOMER_BY_LASTNAME, lastname);

		populateGuestArray(guests, crsTemp);

		return guests;
	}

	/**
	 * Private help method to populate ArrayList with guests from cached row set
	 * 
	 * @param list
	 *            the ArrayList to be populated
	 * @param crsTemp
	 *            the cached row set
	 */
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
	private CachedRowSetImpl executeSingleParamQuery(Queries query, String param) {
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
	private void executeSingleParamUpdate(Queries query, String param) {
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
	private CachedRowSetImpl executeQuery(Queries query, String[] params) {
		this.initialize();
		ResultSet rs = null;
		CachedRowSetImpl crs = null;

		try {

			this.ps = this.connection.prepareStatement(query.toString());

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
	private void executeUpdate(Queries query, String[] params) {
		this.initialize();

		try {

			this.ps = this.connection.prepareStatement(query.toString());

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
