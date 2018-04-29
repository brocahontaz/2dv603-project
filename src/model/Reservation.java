package model;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Class representing a Reservation
 * 
 * @author Johan Andersson, Fredrik Norrman, David Larsson
 *
 */
public class Reservation {

	private int id;
	private String passportNumber;
	private String hotel;
	private int roomNumber;
	private LocalDate arrivalDate;
	private LocalDate departureDate;
	private Boolean checkedIn;
	private Boolean checkedOut;
	private int price;

	/**
	 * Default constructor
	 * 
	 * @constructor
	 */
	public Reservation() {

	}

	/**
	 * Constructor, creates a Reservation object
	 * 
	 * @param id
	 *            the id of the reservation
	 * @param passportNumber
	 *            the passport number of the guest
	 * @param hotel
	 *            the name of the hotel
	 * @param roomNumber
	 *            the room number
	 * @param arrivalDate
	 *            the arrival date
	 * @param departureDate
	 *            the departure date
	 * @param checkedIn
	 *            flag for checked in
	 * @param checkedOut
	 *            flag for checked out
	 * @param price
	 *            the price
	 */
	public Reservation(int id, String passportNumber, String hotel, int roomNumber, LocalDate arrivalDate,
			LocalDate departureDate, Boolean checkedIn, Boolean checkedOut, int price) {
		this.id = id;
		this.passportNumber = passportNumber;
		this.hotel = hotel;
		this.roomNumber = roomNumber;
		this.arrivalDate = arrivalDate;
		this.departureDate = departureDate;
		this.setCheckedIn(checkedIn);
		this.setCheckedOut(checkedOut);
		this.price = price;
	}

	/**
	 * Get the id of the Reservation
	 * @return<Integer> the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Set the id for the Reservation
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Get the Guests passport number of the Reservation
	 * 
	 * @return<String> the passport number of the guest
	 */
	public String getPassportNumber() {
		return passportNumber;
	}

	/**
	 * Set the passport number of the Guest for the Reservation
	 * 
	 * @param passportNumber
	 */
	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	/**
	 * Get the name of the Hotel of the Reservation
	 * @return<String> the name of the Hotel
	 */
	public String getHotel() {
		return hotel;
	}

	/**
	 * Set the name of the Hotel for the Reservation
	 * 
	 * @param hotel
	 *            the name of the hotel
	 */
	public void setHotel(String hotel) {
		this.hotel = hotel;
	}

	/**
	 * Get the room number of the Reservation
	 * @return<Integer> the room number
	 */
	public int getRoomNumber() {
		return roomNumber;
	}

	/**
	 * Set the room number for the Reservation
	 * 
	 * @param roomNumber
	 *            the room number
	 */
	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}

	/**
	 * Get the arrival date of the Reservation
	 * @return<LocalDate> the arrival date
	 */
	public LocalDate getArrivalDate() {
		return arrivalDate;
	}

	/**
	 * Set the arrival date for the Reservation
	 * 
	 * @param arrivalDate
	 *            the arrival date
	 */
	public void setArrivalDate(LocalDate arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	/**
	 * Get the departure date of the Reservation
	 * @return<LocalDate> the departure date
	 */
	public LocalDate getDepartureDate() {
		return departureDate;
	}

	/**
	 * Set the departure date for the Reservation
	 * 
	 * @param departureDate
	 *            the departure date
	 */
	public void setDepartureDate(LocalDate departureDate) {
		this.departureDate = departureDate;
	}

	/**
	 * Get the duration of the Reservation in number of days
	 * @return<Long> the duration in number of days
	 */
	public Long getDuration() {
		return this.departureDate.toEpochDay() - this.arrivalDate.toEpochDay();
	}

	/**
	 * Get the flag for checked in - true if checked in, false otherwise
	 * @return<boolean> checked in or not
	 */
	public boolean getCheckedIn() {
		return checkedIn;
	}

	/**
	 * Set the flag for checked in
	 * 
	 * @param checkedIn
	 *            the flag
	 */
	public void setCheckedIn(Boolean checkedIn) {
		this.checkedIn = checkedIn;
	}

	/**
	 * Get the flag for checked out - true if checked in, false otherwise
	 * @return<boolean> checked out or not
	 */
	public boolean getCheckedOut() {
		return checkedOut;
	}

	/**
	 * Set the flag for checked out
	 * 
	 * @param checkedOut
	 *            the flag
	 */
	public void setCheckedOut(Boolean checkedOut) {
		this.checkedOut = checkedOut;
	}

	/**
	 * Get the price of the Reservation
	 * @return<Integer> the price
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * Set the price of the Reservation
	 * 
	 * @param price
	 *            the price
	 */
	public void setPrice(int price) {
		this.price = price;
	}

	/**
	 * Get the hash code of the Reservation object
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.id);
	}

	/**
	 * Equals method for comparing two Reservation objects
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

		Reservation other = (Reservation) obj;

		return this.id == other.id;
	}

	/**
	 * Get a string representation of the Reservation object
	 */
	public String toString() {
		return "ID: " + this.id + "\nGuest passport: " + this.passportNumber + "\nHotel: " + this.hotel
				+ "\nRoom number: " + this.roomNumber + "\nArrival date: " + this.arrivalDate.toString()
				+ "\nDeparture date: " + this.departureDate.toString() + "\nChecked in " + this.checkedIn
				+ "\nChecked out: " + this.checkedOut + "\nPrice: " + this.price;
	}

}
