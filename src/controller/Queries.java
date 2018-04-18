package controller;

/**
 * Enum class containing the queries used on the database.
 * @author Johan Andersson
 *
 */
public enum Queries {
	GET_CUSTOMER_BY_LASTNAME("SELECT * FROM Guests WHERE lastname = ?;"),
	GET_HOTELS("SELECT * FROM Hotels"),
	GET_HOTEL_QUALITIES("SELECT quality FROM RoomQualities where hotelName = ?"),
	GET_ALL_HOTEL_QUALITIES("SELECT DISTINCT quality FROM RoomQualities"),
	GET_HOTEL_DISCOUNTS("SELECT discountPercent FROM Discounts where hotelName = ?"),
	GET_ALL_HOTEL_DISCOUNTS("SELECT DISTINCT discountPercent FROM Discounts"),
	GET_QUALITIES("SELECT * FROM RoomQualities"),
	GET_DISCOUNTS("SELECT * FROM Discounts"),
	GET_ALL_GUESTS("SELECT * FROM Guests"),
	SEARCH_GUESTS("SELECT * FROM Guests WHERE firstName LIKE ? AND lastName LIKE ? AND address LIKE ? AND telephoneNumber LIKE ? AND creditCard LIKE ? AND passportNumber LIKE ?"),
	FILTERED_SEARCH("SELECT * FROM Guests WHERE "),
	ADD_NEW_GUEST("INSERT INTO Guests (firstName, lastName, address, telephoneNumber, creditCard, passportNumber) VALUES(?, ?, ?, ?, ?, ?)"),
	UPDATE_GUEST("UPDATE Guests SET firstName = ?, lastName = ?, address = ?, telephoneNumber = ?, creditCard = ?, passportNumber = ? WHERE passportNumber = ?"),
	GET_ROOMS("SELECT * FROM Rooms"),
	SEARCH_ROOMS("SELECT * FROM Rooms WHERE roomNumber LIKE ? AND hotelName LIKE ?  AND quality LIKE ?"),
	GET_RESERVATION("SELECT * FROM Reservations WHERE id LIKE ? AND passportNumber LIKE ?"),
	GET_GUEST_FROM_RESERVATION_ID("SELECT * FROM Guests WHERE passportNumber=(SELECT passportNumber FROM Reservations WHERE id = ?)"),
	CHECK_GUEST_IN_N_OUT("UPDATE Reservations SET checkedIn = ?, checkedOut = ? WHERE id = ?"),
	GET_GUESTS_N_RES_BY_ID("SELECT Guests.passportNumber, Guests.firstName, Guests.lastName, Guests.address, Guests.telephoneNumber, "
			+ "Guests.creditCard, Reservations.id, Reservations.roomNumber,  Reservations.arrivalDate, Reservations.departureDate, "
			+ "Reservations.checkedIn, Reservations.checkedOut FROM Guests INNER JOIN Reservations ON Guests.passportNumber=Reservations.passportNumber WHERE Reservations.id = ?"),
	CHECK_AVAILABLE_ROOMS("SELECT DISTINCT Rooms.roomNumber, Rooms.hotelName, Rooms.quality " + 
			"FROM Rooms " + 
			"LEFT JOIN Reservations ON Rooms.roomNumber = Reservations.roomNumber " + 
			"AND Rooms.hotelName = Reservations.hotelName " + 
			"WHERE Reservations.arrivalDate IS NULL OR Reservations.arrivalDate NOT BETWEEN ? AND ? " + 
			"AND Reservations.departureDate IS NULL OR Reservations.departureDate NOT BETWEEN ? AND ?");
	

	
	private String query;
	
	Queries(String query) {
		this.query = query;
	}
	
	public String query() {
		return this.query;
	}
	
	@Override
	public String toString() {
		return this.query;
	}
	
}