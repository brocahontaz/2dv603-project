package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.sql.rowset.CachedRowSet;
import com.sun.rowset.CachedRowSetImpl;

import model.Discount;
import model.Guest;
import model.Hotel;
import model.Reservation;
import model.Room;
import model.RoomQuality;
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

	public boolean checkIn(String reservationID) {
		String[] temp = {"1", "0", reservationID};
		return this.executeUpdate(Queries.CHECK_GUEST_IN_N_OUT, temp);	
	}

	public boolean checkOut(String reservationID) {
		String[] temp = {"1", "1", reservationID};
		return this.executeUpdate(Queries.CHECK_GUEST_IN_N_OUT, temp);
	}

	public boolean makeReservation(String passportNumber, String roomNumber, String arrivalDate, String departureDate,
			String hotel, String price) {
		return false;
	}
	
	public ArrayList<Reservation> getReservationByPassport(String passportNumber) {

		ArrayList<Reservation> reservations = new ArrayList<Reservation>();
		String[] temp = {"%", passportNumber};
		CachedRowSetImpl crsTemp = executeQuery(Queries.GET_RESERVATION, temp);

		populateReservations(reservations, crsTemp);
		return reservations;
	}

	public model.Room getAllAvailableRooms() {
		return null;
	}

	public ArrayList<Room> getAllRooms() {
		ArrayList<Room> rooms = new ArrayList<Room>();
		String[] temp = {};

		CachedRowSetImpl crsTemp = executeQuery(Queries.GET_ROOMS, temp);

		populateRoomArray(rooms, crsTemp);

		return rooms;
	}
	
	public ArrayList<Room> searchRooms(String roomNumber, String hotelName, String quality, String available) {
		if (roomNumber.isEmpty() || roomNumber == null) {
			roomNumber = "%";
		} 
		if (hotelName.isEmpty() || hotelName == null) {
			hotelName = "%";
		} 
		if (quality.isEmpty() || quality == null) {
			quality = "%";
		} 
		/*if (numberOfBeds.isEmpty() || numberOfBeds == null) {
			numberOfBeds = "%";
		}*/
		if (available.isEmpty() || available == null) {
			available = "%";
		} else if (Boolean.parseBoolean(available) == true) {
			available = "1";
		} else if (Boolean.parseBoolean(available) == false) {
			available = "0";
		}
		
		ArrayList<Room> rooms = new ArrayList<Room>();
		String[] temp = {roomNumber, hotelName, quality, available};

		CachedRowSetImpl crsTemp = executeQuery(Queries.SEARCH_ROOMS, temp);

		populateRoomArray(rooms, crsTemp);

		return rooms;
	}

	
	public model.Guest getGuest(String passportNumber) {
		return null;
	}

	public model.Room getRoom(int roomNumber) {
		return null;
	}

	public ArrayList<RoomQuality> getQualities() {
		ArrayList<RoomQuality> qualities = new ArrayList<RoomQuality>();
		String[] temp = {};

		CachedRowSetImpl crsTemp = executeQuery(Queries.GET_QUALITIES, temp);

		populateQualities(qualities, crsTemp);

		return qualities;
	}
	
	public ArrayList<Discount> getDiscounts() {
		ArrayList<Discount> discounts = new ArrayList<Discount>();
		String[] temp = {};

		CachedRowSetImpl crsTemp = executeQuery(Queries.GET_DISCOUNTS, temp);

		populateDiscounts(discounts, crsTemp);

		return discounts;
	}

	public ArrayList<String> getAllDiscounts() {
		ArrayList<String> discounts = new ArrayList<String>();
		String[] temp = {};

		CachedRowSetImpl crsTemp = executeQuery(Queries.GET_ALL_HOTEL_DISCOUNTS, temp);

		populateDiscountArray(discounts, crsTemp);

		return discounts;

	}

	public ArrayList<String> getHotelsDiscounts(String hotelName) {
		ArrayList<String> discounts = new ArrayList<String>();
		String[] temp = { hotelName };

		CachedRowSetImpl crsTemp = executeQuery(Queries.GET_HOTEL_DISCOUNTS, temp);

		populateDiscountArray(discounts, crsTemp);

		return discounts;

	}

	public ArrayList<String> getAllRoomQualities() {
		ArrayList<String> qualities = new ArrayList<String>();
		String[] temp = {};

		CachedRowSetImpl crsTemp = executeQuery(Queries.GET_ALL_HOTEL_QUALITIES, temp);

		populateQualityArray(qualities, crsTemp);

		return qualities;
	}

	public ArrayList<String> getHotelsRoomQualities(String hotelName) {
		ArrayList<String> qualities = new ArrayList<String>();
		String[] temp = { hotelName };

		CachedRowSetImpl crsTemp = executeQuery(Queries.GET_HOTEL_QUALITIES, temp);

		populateQualityArray(qualities, crsTemp);

		return qualities;

	}

	public ArrayList<Hotel> getHotels() {
		ArrayList<Hotel> hotels = new ArrayList<Hotel>();
		String[] temp = {};
		CachedRowSetImpl crsTemp = executeQuery(Queries.GET_HOTELS, temp);

		populateHotelArray(hotels, crsTemp);

		return hotels;
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
	public ArrayList<Guest> searchGuests(String firstName, String lastName, String address, String telephoneNumber,
			String creditCard, String passportNumber) {

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

		ArrayList<Guest> guests = new ArrayList<Guest>();
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
	public boolean addNewGuest(String firstName, String lastName, String address, String telephoneNumber,
			String creditCard, String passportNumber) {

		String[] temp = { firstName, lastName, address, telephoneNumber, creditCard, passportNumber };
		return this.executeUpdate(Queries.ADD_NEW_GUEST, temp);
	}
	
	/**
	 * Update the guest to the database
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
	public boolean updateGuest(String firstName, String lastName, String address, String telephoneNumber,
			String creditCard, String passportNumber, String key) {

		String[] temp = {firstName, lastName, address, telephoneNumber, creditCard, passportNumber, key};
		return this.executeUpdate(Queries.UPDATE_GUEST, temp);
	}

	/**
	 * Get all the guests in the database
	 * 
	 * @return ArrayList<model.Guest> the guests
	 */
	public ArrayList<Guest> getAllGuests() {

		ArrayList<Guest> guests = new ArrayList<Guest>();
		String[] temp = {};
		CachedRowSetImpl crsTemp = executeQuery(Queries.GET_ALL_GUESTS, temp);

		populateGuestArray(guests, crsTemp);

		return guests;
	}
	
	/*
	 * Get Guest by Reservation ID
	 */
	public ArrayList<Guest> getGuestByReservationID(String reservationID) {

		ArrayList<Guest> guests = new ArrayList<Guest>();
		CachedRowSetImpl crsTemp = executeSingleParamQuery(Queries.GET_GUEST_FROM_RESERVATION_ID, reservationID);

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
	public ArrayList<Guest> getCustomerByLastName(String lastname) {

		ArrayList<Guest> guests = new ArrayList<Guest>();
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
	private void populateGuestArray(ArrayList<Guest> list, CachedRowSetImpl crsTemp) {
		try {
			while (crsTemp.next()) {
				list.add(new Guest(crsTemp.getString("firstName"), crsTemp.getString("lastName"),
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

	private void populateRoomArray(ArrayList<Room> list, CachedRowSetImpl crsTemp) {
		try {
			while (crsTemp.next()) {
				list.add(new Room(crsTemp.getInt("roomNumber"),
						crsTemp.getString("hotelName"), 
						crsTemp.getString("quality"), 
						crsTemp.getBoolean("available")));
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

	
	private void populateHotelArray(ArrayList<Hotel> list, CachedRowSetImpl crsTemp) {
		try {
			while (crsTemp.next()) {
				list.add(new Hotel(crsTemp.getString("hotelName"), crsTemp.getString("address")));
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

	private void populateDiscountArray(ArrayList<String> list, CachedRowSetImpl crsTemp) {
		try {
			while (crsTemp.next()) {
				list.add(crsTemp.getString("discountPercent"));
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

	private void populateQualityArray(ArrayList<String> list, CachedRowSetImpl crsTemp) {
		try {
			while (crsTemp.next()) {
				list.add(crsTemp.getString("quality"));
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

	private void populateQualities(ArrayList<RoomQuality> list, CachedRowSetImpl crsTemp) {
		try {
			while (crsTemp.next()) {
				list.add(new RoomQuality(crsTemp.getString("hotelName"), crsTemp.getString("quality"),
						crsTemp.getInt("price")));
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
	
	private void populateDiscounts(ArrayList<Discount> list, CachedRowSetImpl crsTemp) {
		try {
			while (crsTemp.next()) {
				list.add(new Discount(crsTemp.getString("hotelName"), crsTemp.getInt("discountPercent")));
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

	private void populateReservations(ArrayList<Reservation> list, CachedRowSetImpl crsTemp) {
		try {
			while (crsTemp.next()) {
				/*
				 * TODO: if departureDate > currentDate add to list, else don't.
				 * Done to only show active reservations
				 */
				list.add(new Reservation(crsTemp.getInt("id"), crsTemp.getString("passportNumber"), crsTemp.getInt("roomNumber"), crsTemp.getInt("arrivalDate"), 
						crsTemp.getInt("departureDate"), crsTemp.getBoolean("checkedIn"), crsTemp.getBoolean("checkedOut")));
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
	private boolean executeSingleParamUpdate(Queries query, String param) {
		String[] params = { param };

		return this.executeUpdate(query, params);
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
	private boolean executeUpdate(Queries query, String[] params) {
		this.initialize();
		boolean success = true;
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
			success = false;
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
		return success;
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
