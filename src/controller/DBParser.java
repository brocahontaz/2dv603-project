package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import com.mysql.jdbc.Statement;
import com.sun.rowset.CachedRowSetImpl;

import model.Discount;
import model.Guest;
import model.Hotel;
import model.Reservation;
import model.Room;
import model.RoomQuality;

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

	/**
	 * Constructor.
	 * 
	 * Creates an DBParser object
	 */
	public DBParser() {

	}

	/**
	 * Search for Reservations with date filtering
	 * 
	 * @param passportNumber
	 *            the passport number of the Guest
	 * @param hotelName
	 *            the name of the Hotel
	 * @return<ArrayList> the list of Reservations
	 */
	public ArrayList<Reservation> searchReservationsWithDates(String passportNumber, Long arrivalDate,
			Long departureDate, String hotelName) {

		ArrayList<Reservation> data = new ArrayList<Reservation>();

		if (passportNumber.isEmpty() || passportNumber == null) {
			passportNumber = "%";
		}
		if (hotelName.isEmpty() || hotelName == null) {
			hotelName = "%";
		}

		String[] temp = { passportNumber, arrivalDate.toString(), departureDate.toString(), hotelName };
		CachedRowSetImpl crsTemp = executeQuery(Queries.SEARCH_RESERVATIONS_DATES, temp);
		populateReservations(data, crsTemp);
		return data;
	}

	/**
	 * Search for Reservations with arrival date filtering
	 * 
	 * @param passportNumber
	 *            the passport number of the Guest
	 * @param hotelName
	 *            the name of the Hotel
	 * @return<ArrayList> the list of Reservations
	 */
	public ArrayList<Reservation> searchReservationsWithArrivalDate(String passportNumber, Long arrivalDate,
			String hotelName) {

		ArrayList<Reservation> data = new ArrayList<Reservation>();

		if (passportNumber.isEmpty() || passportNumber == null) {
			passportNumber = "%";
		}
		if (hotelName.isEmpty() || hotelName == null) {
			hotelName = "%";
		}

		String[] temp = { passportNumber, arrivalDate.toString(), hotelName };
		CachedRowSetImpl crsTemp = executeQuery(Queries.SEARCH_RESERVATIONS_ARRIVAL, temp);
		populateReservations(data, crsTemp);
		return data;
	}

	/**
	 * Search for Reservations with departure date filtering
	 * 
	 * @param passportNumber
	 *            the passport number of the Guest
	 * @param hotelName
	 *            the name of the Hotel
	 * @return<ArrayList> the list of Reservations
	 */
	public ArrayList<Reservation> searchReservationsWithDepartureDate(String passportNumber, Long departureDate,
			String hotelName) {

		ArrayList<Reservation> data = new ArrayList<Reservation>();

		if (passportNumber.isEmpty() || passportNumber == null) {
			passportNumber = "%";
		}
		if (hotelName.isEmpty() || hotelName == null) {
			hotelName = "%";
		}

		String[] temp = { passportNumber, departureDate.toString(), hotelName };
		CachedRowSetImpl crsTemp = executeQuery(Queries.SEARCH_RESERVATIONS_DEPARTURE, temp);
		populateReservations(data, crsTemp);
		return data;
	}

	/**
	 * Search for Reservations without date filtering
	 * 
	 * @param passportNumber
	 *            the passport number of the Guest
	 * @param hotelName
	 *            the name of the Hotel
	 * @return<ArrayList> the list of Reservations
	 */
	public ArrayList<Reservation> searchReservationsWithoutDates(String passportNumber, String hotelName) {

		ArrayList<Reservation> data = new ArrayList<Reservation>();

		if (passportNumber.isEmpty() || passportNumber == null) {
			passportNumber = "%";
		}
		if (hotelName.isEmpty() || hotelName == null) {
			hotelName = "%";
		}

		String[] temp = { passportNumber, hotelName };
		CachedRowSetImpl crsTemp = executeQuery(Queries.SEARCH_RESERVATIONS_NO_DATES, temp);
		populateReservations(data, crsTemp);
		return data;
	}

	/**
	 * Get all available Rooms between two dates
	 * 
	 * @param arrivalDate
	 *            the arrival date
	 * @param departureDate
	 *            the departure date
	 * @param hotelName
	 *            the name of the Hotel
	 * @param quality
	 *            the RoomQuality
	 * @return<Arraylist> the list of the Rooms
	 */
	public ArrayList<Room> checkAvailableRoomsBetweenDates(Long arrivalDate, Long departureDate, String hotelName,
			String quality) {
		ArrayList<Room> data = new ArrayList<Room>();
		if (hotelName.isEmpty() || hotelName == null) {
			hotelName = "%";
		}
		if (quality.isEmpty() || quality == null) {
			quality = "%";
		}

		String arrDate = arrivalDate.toString();
		String depDate = departureDate.toString();

		String[] temp = { arrDate, arrDate, depDate, depDate, arrDate, depDate, hotelName, quality };
		CachedRowSetImpl crsTemp = executeQuery(Queries.CHECK_AVAILABLE_ROOMS, temp);
		populateRoomArray(data, crsTemp);
		return data;
	}

	/**
	 * Check in a Reservation
	 * 
	 * @param reservationID
	 *            the id of the Reservation
	 * @return<boolean> successful or not
	 */
	public boolean checkIn(String reservationID) {
		String[] temp = { "1", "0", reservationID };
		return this.executeUpdate(Queries.CHECK_GUEST_IN_N_OUT, temp);
	}

	/**
	 * Check out a Reservation
	 * 
	 * @param reservationID
	 *            the id of the Reservation
	 * @return<boolean> successful or not
	 */
	public boolean checkOut(String reservationID) {
		String[] temp = { "0", "1", reservationID };
		return this.executeUpdate(Queries.CHECK_GUEST_IN_N_OUT, temp);
	}

	/**
	 * Make a Reservation. Returns the ID of the Reservation if successful, -1
	 * otherwise.
	 * 
	 * @param passportNumber
	 *            the passport number of the Guest
	 * @param roomNumber
	 *            the room number
	 * @param hotel
	 *            the hotel
	 * @param arrivalDate
	 *            the arrival date
	 * @param departureDate
	 *            the departure date
	 * @param price
	 *            the price
	 * @return<Integer> the id
	 */
	public int makeReservation(String passportNumber, String roomNumber, String hotel, Long arrivalDate,
			Long departureDate, String price) {

		String[] temp = { passportNumber, roomNumber, hotel, arrivalDate.toString(), departureDate.toString(), price };

		return this.executeUpdateReturnKey(Queries.MAKE_RESERVATION, temp);
	}

	/**
	 * Cancel a Reservation. Return true if operation is successful, false
	 * otherwise.
	 * 
	 * @param id
	 *            the id of the Reservation
	 * @return<boolean> success or not
	 */
	public boolean cancelReservation(String id) {

		return executeSingleParamUpdate(Queries.CANCEL_RESERVATION, id);

	}

	/**
	 * Get the Reservation and Guest for a specified Reservation id
	 * 
	 * @param reservationID
	 *            the Reservation id
	 * @return<ArrayList> the list of Reservation and Guest objects
	 */
	public ArrayList<Object> getGuestAndReservationById(String reservationID) {

		ArrayList<Object> data = new ArrayList<Object>();
		String[] temp = { reservationID };
		CachedRowSetImpl crsTemp = executeQuery(Queries.GET_GUESTS_N_RES_BY_ID, temp);
		populateGuestReservation(data, crsTemp);
		return data;
	}

	/**
	 * Get the Reservations for a Guest
	 * 
	 * @param passportNumber
	 *            the passport number of the Guest
	 * @return<ArrayList> the list of Reservations
	 */
	public ArrayList<Reservation> getReservationsByPassport(String passportNumber) {

		ArrayList<Reservation> reservations = new ArrayList<Reservation>();
		String[] temp = { passportNumber };
		CachedRowSetImpl crsTemp = executeQuery(Queries.GET_RESERVATIONS_BY_PASSPORT, temp);

		populateReservations(reservations, crsTemp);
		return reservations;
	}

	/**
	 * Get all Rooms from the database
	 * @return<ArrayList> the list of Rooms
	 */
	public ArrayList<Room> getAllRooms() {
		ArrayList<Room> rooms = new ArrayList<Room>();
		String[] temp = {};

		CachedRowSetImpl crsTemp = executeQuery(Queries.GET_ROOMS, temp);

		populateRoomArray(rooms, crsTemp);

		return rooms;
	}

	/**
	 * Search Rooms in the database, filtered on room number, hotel name and quality
	 * 
	 * @param roomNumber
	 *            the room number
	 * @param hotelName
	 *            the hotel name
	 * @param quality
	 *            the quality
	 * @return<ArrayList> the list of Rooms
	 */
	public ArrayList<Room> searchRooms(String roomNumber, String hotelName, String quality) {
		if (roomNumber.isEmpty() || roomNumber == null) {
			roomNumber = "%";
		}
		if (hotelName.isEmpty() || hotelName == null) {
			hotelName = "%";
		}
		if (quality.isEmpty() || quality == null) {
			quality = "%";
		}

		ArrayList<Room> rooms = new ArrayList<Room>();
		String[] temp = { roomNumber, hotelName, quality };

		CachedRowSetImpl crsTemp = executeQuery(Queries.SEARCH_ROOMS, temp);

		populateRoomArray(rooms, crsTemp);

		return rooms;
	}

	/**
	 * Get the RoomQuality from the database with specified Hotel name and name
	 * 
	 * @param hotelName
	 *            the Hotel Name
	 * @param quality
	 *            the name
	 * @return<ArrayList> list with the RoomQualities
	 */
	public RoomQuality getQuality(String hotelName, String quality) {
		ArrayList<RoomQuality> qualities = new ArrayList<RoomQuality>();
		String[] temp = { hotelName, quality };

		CachedRowSetImpl crsTemp = executeQuery(Queries.GET_QUALITY, temp);

		populateQualities(qualities, crsTemp);

		return qualities.get(0);

	}

	/**
	 * Get all the RoomQualities from the database
	 * @return<ArrayList> list with the RoomQualities
	 */
	public ArrayList<RoomQuality> getQualities() {
		ArrayList<RoomQuality> qualities = new ArrayList<RoomQuality>();
		String[] temp = {};

		CachedRowSetImpl crsTemp = executeQuery(Queries.GET_QUALITIES, temp);

		populateQualities(qualities, crsTemp);

		return qualities;
	}

	/**
	 * Get all the Discounts from the database
	 * @return<ArrayList> list with the Discounts
	 */
	public ArrayList<Discount> getDiscounts() {
		ArrayList<Discount> discounts = new ArrayList<Discount>();
		String[] temp = {};

		CachedRowSetImpl crsTemp = executeQuery(Queries.GET_DISCOUNTS, temp);

		populateDiscounts(discounts, crsTemp);

		return discounts;
	}

	/**
	 * Get all the Discount percentages from the database
	 * @return<ArrayList> list with the Discount percentages
	 */
	public ArrayList<String> getAllDiscounts() {
		ArrayList<String> discounts = new ArrayList<String>();
		String[] temp = {};

		CachedRowSetImpl crsTemp = executeQuery(Queries.GET_ALL_HOTEL_DISCOUNTS, temp);

		populateDiscountArray(discounts, crsTemp);

		return discounts;

	}

	/**
	 * Get all the Discount percentages for a specified Hotel from the database
	 * 
	 * @param hotelName
	 *            the Hotel name
	 * @return<ArrayList> list with the Discount percentages
	 */
	public ArrayList<String> getHotelsDiscounts(String hotelName) {
		ArrayList<String> discounts = new ArrayList<String>();
		String[] temp = { hotelName };

		CachedRowSetImpl crsTemp = executeQuery(Queries.GET_HOTEL_DISCOUNTS, temp);

		populateDiscountArray(discounts, crsTemp);

		return discounts;

	}

	/**
	 * Get all the RoomQualities names from the database
	 * @return<ArrayList> list with the RoomQualities names
	 */
	public ArrayList<String> getAllRoomQualities() {
		ArrayList<String> qualities = new ArrayList<String>();
		String[] temp = {};

		CachedRowSetImpl crsTemp = executeQuery(Queries.GET_ALL_HOTEL_QUALITIES, temp);

		populateQualityArray(qualities, crsTemp);

		return qualities;
	}

	/**
	 * Get all the RoomQualities names for a specified Hotel from the database
	 * 
	 * @param hotelName
	 *            the Hotel name
	 * @return<ArrayList> list with the RoomQualities names
	 */
	public ArrayList<String> getHotelsRoomQualities(String hotelName) {
		ArrayList<String> qualities = new ArrayList<String>();
		String[] temp = { hotelName };

		CachedRowSetImpl crsTemp = executeQuery(Queries.GET_HOTEL_QUALITIES, temp);

		populateQualityArray(qualities, crsTemp);

		return qualities;

	}

	/**
	 * Get all the Hotels from the database
	 * @return<ArrayList> list with the Hotels
	 */
	public ArrayList<Hotel> getHotels() {
		ArrayList<Hotel> hotels = new ArrayList<Hotel>();
		String[] temp = {};
		CachedRowSetImpl crsTemp = executeQuery(Queries.GET_HOTELS, temp);

		populateHotelArray(hotels, crsTemp);

		return hotels;
	}

	/**
	 * Search for Guests
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
	 * Add a new Guest to the database
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
	 * Update the Guest in the database
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

		String[] temp = { firstName, lastName, address, telephoneNumber, creditCard, passportNumber, key };
		return this.executeUpdate(Queries.UPDATE_GUEST, temp);
	}

	/**
	 * Get all the Guests in the database
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

	/**
	 * Get Guest by Reservation ID
	 * 
	 * @param reservationID
	 *            the reservation id
	 * @return<Guest> the guest
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
	 * Populate ArrayList with Guest, Reservation and Room objects, from a
	 * CachedRowSet
	 * 
	 * @param list
	 *            the list
	 * @param crsTemp
	 *            the CachedRowSet
	 */
	private void populateGuestReservation(ArrayList<Object> list, CachedRowSetImpl crsTemp) {
		try {
			while (crsTemp.next()) {
				list.add(new Guest(crsTemp.getString("firstName"), crsTemp.getString("lastName"),
						crsTemp.getString("address"), crsTemp.getString("telephoneNumber"),
						crsTemp.getString("creditCard"), crsTemp.getString("passportNumber")));
				list.add(new Reservation(crsTemp.getInt("id"), crsTemp.getString("passportNumber"),
						crsTemp.getString("hotelName"), crsTemp.getInt("roomNumber"),
						LocalDate.ofEpochDay(crsTemp.getLong("arrivalDate")),
						LocalDate.ofEpochDay(crsTemp.getLong("departureDate")), crsTemp.getBoolean("checkedIn"),
						crsTemp.getBoolean("checkedOut"), crsTemp.getInt("price")));
				list.add(new Room(Integer.parseInt(crsTemp.getString("roomNumber")), crsTemp.getString("hotelName"),
						crsTemp.getString("quality")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				crsTemp.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Populate ArrayList with Guest objects, from a CachedRowSet
	 * 
	 * @param list
	 *            the list
	 * @param crsTemp
	 *            the CachedRowSet
	 */
	private void populateGuestArray(ArrayList<Guest> list, CachedRowSetImpl crsTemp) {
		try {
			while (crsTemp.next()) {
				list.add(new Guest(crsTemp.getString("firstName"), crsTemp.getString("lastName"),
						crsTemp.getString("address"), crsTemp.getString("telephoneNumber"),
						crsTemp.getString("creditCard"), crsTemp.getString("passportNumber")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				crsTemp.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Populate ArrayList with Room objects, from a CachedRowSet
	 * 
	 * @param list
	 *            the list
	 * @param crsTemp
	 *            the CachedRowSet
	 */
	private void populateRoomArray(ArrayList<Room> list, CachedRowSetImpl crsTemp) {
		try {
			while (crsTemp.next()) {
				list.add(new Room(crsTemp.getInt("roomNumber"), crsTemp.getString("hotelName"),
						crsTemp.getString("quality")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				crsTemp.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Populate ArrayList with Hotel objects, from a CachedRowSet
	 * 
	 * @param list
	 *            the list
	 * @param crsTemp
	 *            the CachedRowSet
	 */
	private void populateHotelArray(ArrayList<Hotel> list, CachedRowSetImpl crsTemp) {
		try {
			while (crsTemp.next()) {
				list.add(new Hotel(crsTemp.getString("hotelName"), crsTemp.getString("address")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				crsTemp.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Populate ArrayList with Discount percentages, from a CachedRowSet
	 * 
	 * @param list
	 *            the list
	 * @param crsTemp
	 *            the CachedRowSet
	 */
	private void populateDiscountArray(ArrayList<String> list, CachedRowSetImpl crsTemp) {
		try {
			while (crsTemp.next()) {
				list.add(crsTemp.getString("discountPercent"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				crsTemp.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Populate ArrayList with RoomQuality names, from a CachedRowSet.
	 * 
	 * @param list
	 *            the list
	 * @param crsTemp
	 *            the CachedRowSet
	 */
	private void populateQualityArray(ArrayList<String> list, CachedRowSetImpl crsTemp) {
		try {
			while (crsTemp.next()) {
				list.add(crsTemp.getString("quality"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				crsTemp.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Populate ArrayList with RoomQuality objects, from a CachedRowSet. With all
	 * information regarding the objects.
	 * 
	 * @param list
	 *            the list
	 * @param crsTemp
	 *            the CachedRowSet
	 */
	private void populateQualities(ArrayList<RoomQuality> list, CachedRowSetImpl crsTemp) {
		try {
			while (crsTemp.next()) {
				list.add(new RoomQuality(crsTemp.getString("hotelName"), crsTemp.getString("quality"),
						crsTemp.getInt("numberOfBeds"), crsTemp.getInt("price")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				crsTemp.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Populate ArrayList with Discount objects, from a CachedRowSet
	 * 
	 * @param list
	 *            the list
	 * @param crsTemp
	 *            the CachedRowSet
	 */
	private void populateDiscounts(ArrayList<Discount> list, CachedRowSetImpl crsTemp) {
		try {
			while (crsTemp.next()) {
				list.add(new Discount(crsTemp.getString("hotelName"), crsTemp.getInt("discountPercent")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				crsTemp.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Populate ArrayList with Reservation objects, from a CachedRowSet
	 * 
	 * @param list
	 *            the list
	 * @param crsTemp
	 *            the CachedRowSet
	 */
	private void populateReservations(ArrayList<Reservation> list, CachedRowSetImpl crsTemp) {
		try {
			while (crsTemp.next()) {
				list.add(new Reservation(crsTemp.getInt("id"), crsTemp.getString("passportNumber"),
						crsTemp.getString("hotelName"), crsTemp.getInt("roomNumber"),
						LocalDate.ofEpochDay(crsTemp.getLong("arrivalDate")),
						LocalDate.ofEpochDay(crsTemp.getLong("departureDate")), crsTemp.getBoolean("checkedIn"),
						crsTemp.getBoolean("checkedOut"), crsTemp.getInt("price")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				crsTemp.close();
			} catch (SQLException e) {
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
	 * @return<boolean>
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
	 * Private help method to execute updates on the database. Returns a key
	 * consisting of the newly inserted auto increment field.
	 * 
	 * @param query
	 *            the query to be used in the prepared statement
	 * @param params
	 *            the parameters to be used for the prepared statement
	 * @return<Integer> the key
	 */
	private int executeUpdateReturnKey(Queries query, String[] params) {
		this.initialize();
		int generatedKey = -1;
		try {

			this.ps = this.connection.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);

			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					this.ps.setString(i + 1, params[i]);
				}
			}

			this.ps.executeUpdate();
			this.connection.commit();

			ResultSet generatedKeys = this.ps.getGeneratedKeys();

			if (generatedKeys.next()) {
				generatedKey = generatedKeys.getInt(1);
			}

		} catch (SQLException e) {
			generatedKey = -1;
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
		return generatedKey;
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
