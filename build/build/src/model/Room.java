package model;

public class Room {
	
	private int roomNumber;
	private int numberOfBeds;
	private boolean available;
	
	public Room(int roomNumber, int numberOfBeds, boolean available) {
		this.roomNumber = roomNumber;
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
	

}