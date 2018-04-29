package model;

import java.util.Objects;

/**
 * Class representing a discount choice for a Hotel
 * 
 * @author Johan Andersson, Fredrik Norrman, David Larsson
 *
 */
public class Discount {

	private String hotelName;
	private int discountPercentage;

	/**
	 * Default constructor
	 * 
	 * @constructor
	 */
	public Discount() {

	}

	/**
	 * Constructor, creates a Discount object
	 * 
	 * @param hotelName
	 *            the name of the hotel
	 * @param discountPercentage
	 *            the percentage
	 * @constructor
	 */
	public Discount(String hotelName, int discountPercentage) {
		this.hotelName = hotelName;
		this.discountPercentage = discountPercentage;
	}

	/**
	 * Get the name of the hotel
	 * @return<String> the hotel name
	 */
	public String getHotelName() {
		return hotelName;
	}

	/**
	 * Set the name of the hotel
	 * 
	 * @param hotelName
	 *            the hotel name
	 */
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	/**
	 * Get the discount percentage
	 * @return<Integer> the discount
	 */
	public int getDiscountPercentage() {
		return discountPercentage;
	}

	/**
	 * Set the discount percentage
	 * 
	 * @param discountPercentage
	 *            the discount
	 */
	public void setDiscountPercentage(int discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	/**
	 * Get the hash code for the discount object
	 */
	@Override
	public int hashCode() {
		return Objects.hash(discountPercentage);
	}

	/**
	 * Equals method for comparing two Discount objects
	 * 
	 * @param obj
	 *            the other object to compare against
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		Discount other = (Discount) obj;
		if (this.discountPercentage != other.discountPercentage)
			return false;
		return true;
	}

	/**
	 * Get a string representation of the discount
	 */
	@Override
	public String toString() {
		return "Hotel: " + this.hotelName + " Discount: " + this.discountPercentage;
	}

}
