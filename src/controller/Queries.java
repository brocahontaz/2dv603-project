package controller;

/**
 * Enum class containing the queries used on the database.
 * @author Johan Andersson
 *
 */
public enum Queries {
	GET_CUSTOMER_BY_LASTNAME("SELECT * FROM customers WHERE lastname = ?;"),
	GET_ALL_GUESTS("SELECT * FROM guests;"),
	SEARCH_GUESTS("SELECT * FROM guests WHERE firstname = ? AND lastname = ? AND address = ? AND telephoneNumber = ? AND creditCard = ? AND passportNumber = ?"),
	ADD_NEW_GUEST("INSERT INTO guests (firstName, lastName, address, telephoneNumber, creditCard, passportNumber) VALUES(?, ?, ?, ?, ?, ?)");
	
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
