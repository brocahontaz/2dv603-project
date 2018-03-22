package model;

public class Room {
	
	private int roomNumber;
	private int numberOfBeds;
	private boolean smoking;
	private boolean available;
	
	public Room(int roomNumber, int numberOfBeds, boolean smoking, boolean available) {
		this.roomNumber = roomNumber;
		this.numberOfBeds = numberOfBeds;
		this.smoking = smoking;
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
	
	public boolean isSmoking() {
		return smoking;
	}
	
	public void setSmoking(boolean smoking) {
		this.smoking = smoking;
	}
	
	public boolean isAvailable() {
		return available;
	}
	
	public void setAvailable(boolean available) {
		this.available = available;
	}
	

}
