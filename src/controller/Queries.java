package controller;

/**
 * Enum class containing the queries used on the database.
 * @author Johan Andersson
 *
 */
public enum Queries {
	GET_CUSTOMER_BY_LASTNAME("SELECT * FROM customers WHERE lastname = ?"),
	INSERT_TEST_TABLE("INSERT INTO testTable (testid, testname) values (?, ?)"),
	GET_ALL_FROM_TEST_TABLE("SELECT * FROM testTable");
	
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
