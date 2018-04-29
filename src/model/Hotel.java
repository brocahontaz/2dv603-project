package model;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Class representing a Hotel
 * 
 * @author Johan Andersson, Fredrik Norrman, David Larsson
 *
 */
public class Hotel {

	private String name;
	private String address;
	private ArrayList<Integer> discounts = new ArrayList<Integer>();
	private ArrayList<RoomQuality> qualities = new ArrayList<RoomQuality>();

	/**
	 * Default constructor
	 * 
	 * @constructor
	 */
	public Hotel() {

	}

	/**
	 * Constructor, create a Hotel object
	 * 
	 * @param name
	 *            the name of the hotel
	 * @param address
	 *            the address of the hotel
	 */
	public Hotel(String name, String address) {
		this.name = name;
		this.address = address;
	}

	/**
	 * Get the name of the Hotel
	 * @return<String> the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Set the name of the Hotel
	 * 
	 * @param name
	 *            the name of the hotel
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the address of the Hotel
	 * @return<String> the address
	 */
	public String getAddress() {
		return this.address;
	}

	/**
	 * Set the address of the Hotel
	 * 
	 * @param address
	 *            the address of the hotel
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Get the list of discounts of the Hotel
	 * @return<ArrayList> the discounts
	 */
	public ArrayList<Integer> getDiscounts() {
		return discounts;
	}

	/**
	 * Set the list of discounts for the Hotel
	 * 
	 * @param discounts
	 *            the discounts
	 */
	public void setDiscounts(ArrayList<Integer> discounts) {
		this.discounts = discounts;
	}

	/**
	 * Get the list of qualities of the Hotel
	 * @return<ArrayList> the qualities
	 */
	public ArrayList<RoomQuality> getQualities() {
		return qualities;
	}

	/**
	 * Set the list of qualities for the Hotel
	 * 
	 * @param qualities
	 *            the qualities
	 */
	public void setQualities(ArrayList<RoomQuality> qualities) {
		this.qualities = qualities;
	}

	/**
	 * Get the hash code for the Hotel object
	 */
	@Override
	public int hashCode() {
		return Objects.hashCode(this.name);
	}

	/**
	 * Equals method for comparing two Hotel objects
	 * 
	 * @param obj
	 *            the other object to compare against
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (!(obj instanceof Hotel)) {
			return false;
		}

		Hotel other = (Hotel) obj;

		return this.name.equals(other.name);
	}

	/**
	 * Get a String representation of the Hotel object
	 */
	@Override
	public String toString() {
		return "Hotel: " + this.name + "\nAddress: " + this.address;
	}

}
