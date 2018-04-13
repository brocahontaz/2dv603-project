package model;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + discountPercentage;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Discount other = (Discount) obj;
		if (discountPercentage != other.discountPercentage)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return Integer.toString(getDiscountPercentage());
	}

}
