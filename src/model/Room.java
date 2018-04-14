package model;

public class Room {
	
	private int roomNumber;
	private int numberOfBeds;
	private boolean available;
	private String hotelName;
	private String quality;
	
	public Room(int roomNumber, String hotelName, String quality, int numberOfBeds, boolean available) {
		this.roomNumber = roomNumber;
		this.hotelName = hotelName;
		this.quality = quality;
		this.numberOfBeds = numberOfBeds;
		this.available = available;
	}
	
	public int getRoomNumber() {
		return roomNumber;
	}
	
	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}
	
	public int getNumberOfBeds() {
		return numberOfBeds;
	}
	
	public void setNumberOfBeds(int numberOfBeds) {
		this.numberOfBeds = numberOfBeds;
	}
	
	public boolean isAvailable() {
		return available;
	}
	
	public void setAvailable(boolean available) {
		this.available = available;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public String getQuality() {
		return quality;
	}

	public void setQuality(String quality) {
		this.quality = quality;
	}
	

}
