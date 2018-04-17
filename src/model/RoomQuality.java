package model;

import java.util.Objects;

public class RoomQuality {
	
	private String hotelName;
	private String quality;
	private int numberOfBeds;
	private int price;
	
	public RoomQuality() {
		
	}
	
	public RoomQuality(String hotelName, String quality, int price) {
		this.hotelName = hotelName;
		this.quality = quality;
		this.price = price;
	}
	
	public int getNumberOfBeds() {
		return numberOfBeds;
	}

	public void setNumberOfBeds(int numberOfBeds) {
		this.numberOfBeds = numberOfBeds;
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

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
	@Override
	public String toString() {
		return this.quality;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		
		if (!(obj instanceof RoomQuality)) {
			return false;
		}
		
		RoomQuality other = (RoomQuality) obj;
		
		return this.getQuality().equals(other.getQuality());
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.getQuality());
	}
	
}
