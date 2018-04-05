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
	GET_ALL_GUESTS("SELECT * FROM Guests"),
	SEARCH_GUESTS("SELECT * FROM Guests WHERE firstName LIKE ? AND lastName LIKE ? AND address LIKE ? AND telephoneNumber LIKE ? AND creditCard LIKE ? AND passportNumber LIKE ?"),
	FILTERED_SEARCH("SELECT * FROM Guests WHERE "),
	ADD_NEW_GUEST("INSERT INTO Guests (firstName, lastName, address, telephoneNumber, creditCard, passportNumber) VALUES(?, ?, ?, ?, ?, ?)");
	
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