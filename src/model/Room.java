package model;

import java.util.Objects;

/**
 * Class representing a Room in a Hotel
 * 
 * @author Johan Andersson, Fredrik Norrman, David Larsson
 *
 */
public class Room {

	private int roomNumber;
	private String hotelName;
	private String quality;

	/**
	 * Default constructor
	 * 
	 * @constructor
	 */
	public Room() {

	}

	/**
	 * Constructor, creates a Room object
	 * 
	 * @param roomNumber
	 *            the room number
	 * @param hotelName
	 *            the hotel name
	 * @param quality
	 *            the room quality
	 * @constructor
	 */
	public Room(int roomNumber, String hotelName, String quality) {
		this.roomNumber = roomNumber;
		this.hotelName = hotelName;
		this.quality = quality;
	}

	/**
	 * Get the number of the Room
	 * @return<Integer> the room number
	 */
	public int getRoomNumber() {
		return roomNumber;
	}

	/**
	 * Set the number of the Room
	 * 
	 * @param roomNumber
	 *            the room number
	 */
	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}

	/**
	 * Get the name of the Hotel of the Room
	 * @return<String> the name of the Hotel
	 */
	public String getHotelName() {
		return hotelName;
	}

	/**
	 * Set the name of the Hotel for the Room
	 * 
	 * @param hotelName
	 *            the name of the Hotel
	 */
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	/**
	 * Get the name of the Quality for the Room
	 * @return<String> the Quality of the Room
	 */
	public String getQuality() {
		return quality;
	}

	/**
	 * Set the Quality of the Room
	 * 
	 * @param quality
	 *            the Quality
	 */
	public void setQuality(String quality) {
		this.quality = quality;
	}
	
	/**
	 * Get the hash code of the Room object
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.roomNumber, this.hotelName);
	}

	/**
	 * Equals method for comparing two Room objects
	 * 
	 * @param obj
	 *            the other object to compare against
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (!(obj instanceof Room)) {
			return false;
		}

		Room other = (Room) obj;

		return this.roomNumber == other.roomNumber && this.hotelName.equals(other.hotelName);
	}

	/**
	 * String representation of the Room object
	 */
	@Override
	public String toString() {
		return this.hotelName + " - " + this.roomNumber + " / " + this.quality;
	}

}
