package model;

import java.util.Objects;

/**
 * Class representing a guest at the hotel
 * 
 * @author Johan Andersson, Fredrik Norrman, David Larsson
 *
 */
public class Guest {

	private String firstName;
	private String lastName;
	private String address;
	private String telephoneNumber;
	private String creditCard;
	private String passportNumber;

	/**
	 * Default constructor
	 * 
	 * @constructor
	 */
	public Guest() {

	}

	/**
	 * Constructor, creates a Guest object
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
	 *            the credit card number
	 * @param passportNumber
	 *            the passport number
	 */
	public Guest(String firstName, String lastName, String address, String telephoneNumber, String creditCard,
			String passportNumber) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.telephoneNumber = telephoneNumber;
		this.creditCard = creditCard;
		this.passportNumber = passportNumber;
	}

	/**
	 * Get the first name of the Guest
	 * @return<String> the first name
	 */
	public String getFirstName() {
		return this.firstName;
	}

	/**
	 * Set the first name of the Guest
	 * 
	 * @param firstName
	 *            the first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Get the last name of the Guest
	 * @return<String> the last name
	 */
	public String getLastName() {
		return this.lastName;
	}

	/**
	 * Set the last name of the Guest
	 * 
	 * @param lastName
	 *            the last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Get the address of the Guest
	 * @return<String> the address
	 */
	public String getAddress() {
		return this.address;
	}

	/**
	 * Set the address of the Guest
	 * 
	 * @param address
	 *            the address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Get the credit card number of the Guest
	 * @return<String> the credit card number
	 */
	public String getCreditCard() {
		return this.creditCard;
	}

	/**
	 * Set the credit card of the Guest
	 * 
	 * @param creditCard
	 *            the credit card
	 */
	public void setCreditCard(String creditCard) {
		this.creditCard = creditCard;
	}

	/**
	 * Get the telephone number of the Guest
	 * @return<String> the telephone number
	 */
	public String getTelephoneNumber() {
		return this.telephoneNumber;
	}

	/**
	 * Set the telephone number of the Guest
	 * 
	 * @param telephoneNumber
	 *            the telephone number
	 */
	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	/**
	 * Get the passport number of the Guest
	 * @return<String> the telephone number
	 */
	public String getPassportNumber() {
		return this.passportNumber;
	}

	/**
	 * Set the passport number of the guest
	 * 
	 * @param passportNumber
	 *            the passport number
	 */
	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	/**
	 * Get the hashCode for the Guest object
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.passportNumber);
	}

	/**
	 * Equals method for comparing two Guest objects
	 * 
	 * @param obj
	 *            the other object to compare against
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof Guest)) {
			return false;
		}

		Guest other = (Guest) obj;

		return this.passportNumber.equals(other.passportNumber);
	}

	/**
	 * Get a String representation of the Guest object
	 */
	@Override
	public String toString() {
		return this.firstName + " " + this.lastName + " / ID: " + this.passportNumber + "\nTel: " + this.telephoneNumber
				+ "\nAddress: " + this.address + "\nCredit card: " + this.creditCard;
	}

}
