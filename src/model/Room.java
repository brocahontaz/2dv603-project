package model;

public class Room {
	
	private int roomNumber;
	private String hotelName;
	private String quality;
	
	public Room() {
		
	}
	
	public Room(int roomNumber, String hotelName, String quality) {
		this.roomNumber = roomNumber;
		this.hotelName = hotelName;
		this.quality = quality;
	}
	
	public int getRoomNumber() {
		return roomNumber;
	}
	
	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
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
	
	@Override
	public String toString() {
		return hotelName + " - " + roomNumber + " / " + quality;
	}

}
