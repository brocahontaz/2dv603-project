package model;

/**
 * Class representing a guest at the hotel
 * @author Johan Andersson
 *
 */
public class Guest {
	
	private String firstName;
	private String lastName;
	private String address;
	private String telephoneNumber;
	private String creditCard;
	private String passportNumber;
	
	public Guest() {
		
	}
	
	public Guest(String firstName, String lastName, String address, String telephoneNumber, String creditCard, String passportNumber) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.telephoneNumber = telephoneNumber;
		this.creditCard = creditCard;
		this.passportNumber = passportNumber;
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public String getAddress() {
		return this.address;
	}
	
	public String getCreditCard() {
		return this.creditCard;
	}
	
	public String getTelephoneNumber() {
		return this.telephoneNumber;
	}
	
	public String getPassportNumber() {
		return this.passportNumber;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}
	
	public void setCreditCard(String creditCard) {
		this.creditCard = creditCard;
	}
	
	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}
	
	@Override
	public String toString() {
		return this.firstName + " " + this.lastName + " " + this.passportNumber;
	}

}
