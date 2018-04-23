package model;

import java.util.Objects;

public class Reservation {
	
	private int id;
	private String passportNumber;
	private String hotel;
	private int roomNumber;
	private int arrivalDate;
	private int departureDate;
	private Boolean checkedIn;
	private Boolean checkedOut;
	private int price;

	public Reservation(int id, String passportNumber, String hotel, int roomNumber, int arrivalDate, int departureDate, Boolean checkedIn, Boolean checkedOut, int price) {
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
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}
	
	public String getHotel() {
		return hotel;
	}

	public void setHotel(String hotel) {
		this.hotel = hotel;
	}

	public int getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}
	
	public int getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(int arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
	
	public int getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(int departureDate) {
		this.departureDate = departureDate;
	}

	public int getDuration() {
		return this.departureDate - this.arrivalDate;
	}
	
	public Boolean getCheckedIn() {
		return checkedIn;
	}

	public void setCheckedIn(Boolean checkedIn) {
		this.checkedIn = checkedIn;
	}

	public Boolean getCheckedOut() {
		return checkedOut;
	}

	public void setCheckedOut(Boolean checkedOut) {
		this.checkedOut = checkedOut;
	}
	
	
	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String toString() {
		return null;
		// TODO - What should be returned? id+passport+name+duration dates?
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		
		if (!(obj instanceof Guest)) {
			return false;
		}
		
		Reservation other = (Reservation) obj;
		
		return this.getId() == other.getId();
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.getId());
	}
}
