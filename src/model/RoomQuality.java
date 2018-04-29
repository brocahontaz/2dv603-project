package model;

import java.util.Objects;

/**
 * Class representing a RoomQuality
 * 
 * @author Johan Andersson, Fredrik Norrman, David Larsson
 *
 */
public class RoomQuality {

	private String hotelName;
	private String quality;
	private int numberOfBeds;
	private int price;

	/**
	 * Default constructor
	 * 
	 * @constructor
	 */
	public RoomQuality() {

	}

	/**
	 * Constructor, creates a RoomQuality object
	 * 
	 * @param hotelName
	 *            the name of the hotel
	 * @param quality
	 *            the name of the quality
	 * @param numberOfBeds
	 *            the number of beds
	 * @param price
	 *            the price of the quality
	 */
	public RoomQuality(String hotelName, String quality, int numberOfBeds, int price) {
		this.hotelName = hotelName;
		this.quality = quality;
		this.numberOfBeds = numberOfBeds;
		this.price = price;
	}

	/**
	 * Get the Hotel name of the RoomQuality
	 * @return<String> the hotel name
	 */
	public String getHotelName() {
		return hotelName;
	}

	/**
	 * Set the Hotel name for the RoomQuality
	 * 
	 * @param hotelName
	 *            the hotel name
	 */
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	/**
	 * Get the name of the Quality
	 * @return<String> the name of the Quality
	 */
	public String getQuality() {
		return quality;
	}

	/**
	 * Set the name of the Quality
	 * 
	 * @param quality
	 *            the name of the Quality
	 */
	public void setQuality(String quality) {
		this.quality = quality;
	}

	/**
	 * Get the number of beds of the RoomQuality
	 * @return<Integer> the number of beds
	 */
	public int getNumberOfBeds() {
		return numberOfBeds;
	}

	/**
	 * Set the number of beds for the Quality
	 * 
	 * @param numberOfBeds
	 *            the number of beds
	 */
	public void setNumberOfBeds(int numberOfBeds) {

	}

	/**
	 * Get the price of the Quality
	 * @return<Integer> the price
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * Set the price of the Quality
	 * 
	 * @param price
	 *            the price
	 */
	public void setPrice(int price) {
		this.price = price;
	}

	/**
	 * Get the hash code of the RoomQuality object
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.getQuality());
	}

	/**
	 * Equals method for comparing two RoomQuality objects
	 * 
	 * @param obj
	 *            the other object to compare against
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (!(obj instanceof RoomQuality)) {
			return false;
		}

		RoomQuality other = (RoomQuality) obj;

		return this.quality.equals(other.quality);
	}

	/**
	 * Get a String represenation of the RoomQuality object
	 */
	@Override
	public String toString() {
		return "Hotel: " + this.hotelName + "\nQuality: " + this.quality + "\nBeds: " + this.numberOfBeds + "\nPrice: "
				+ this.price;
	}

}
