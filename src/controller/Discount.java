package controller;

public class Discount {
	
	private String hotelName;
	private int discountPercentage;
	
	public Discount() {
		
	}
	
	public Discount(String hotelName, int discountPercentage) {
		this.hotelName = hotelName;
		this.discountPercentage = discountPercentage;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public int getDiscountPercentage() {
		return discountPercentage;
	}

	public void setDiscountPercentage(int discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

}
