package model;

import java.util.Objects;

public class RoomQuality {
	
	private String hotelName;
	private String quality;
	private int price;
	
	public RoomQuality() {
		
	}
	
	public RoomQuality(String hotelName, String quality, int price) {
		this.hotelName = hotelName;
		this.quality = quality;
		this.price = price;
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
		
		if (!(obj instanceof Guest)) {
			return false;
		}
		
		RoomQuality other = (RoomQuality) obj;
		
		return this.getHotelName().equals(other.getHotelName()) && this.getQuality().equals(other.getQuality());
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.getHotelName(), this.getQuality());
	}
	
}
