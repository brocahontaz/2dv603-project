package model;

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

}
