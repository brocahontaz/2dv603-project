package model;

public class Room {
	
	private int roomNumber;
	private boolean available;
	private String hotelName;
	private String quality;
	
	public Room(int roomNumber, String hotelName, String quality, boolean available) {
		this.roomNumber = roomNumber;
		this.hotelName = hotelName;
		this.quality = quality;
		this.available = available;
	}
	
	public int getRoomNumber() {
		return roomNumber;
	}
	
	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
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
