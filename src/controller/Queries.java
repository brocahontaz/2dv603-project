package controller;

/**
 * Enum class containing the queries used on the database.
 * @author Johan Andersson
 *
 */
public enum Queries {
	GET_CUSTOMER_BY_LASTNAME("SELECT * FROM customers WHERE lastname = ?");
	
	
	private String query;
	
	Queries(String query) {
		this.query = query;
	}
	
	public String quer() {
		return this.query;
	}
}
