package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

public class Hotel {

	private String name;
	private String address;
	private ArrayList<Integer> discounts = new ArrayList<Integer>();
	private ArrayList<RoomQuality> qualities = new ArrayList<RoomQuality>();
	
	public Hotel() {
		
	}
	
	public Hotel(String name, String address) {
		this.name = name;
		this.address = address;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getAddress() {
		return this.address;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public ArrayList<Integer> getDiscounts() {
		return discounts;
	}

	public void setDiscounts(ArrayList<Integer> discounts) {
		this.discounts = discounts;
	}

	public ArrayList<RoomQuality> getQualities() {
		return qualities;
	}

	public void setQualities(ArrayList<RoomQuality> qualities) {
		this.qualities = qualities;
	}

	@Override
	public String toString() {
		return this.name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		
		if (!(obj instanceof Hotel)) {
			return false;
		}
		
		Hotel other = (Hotel) obj;
		
		return this.getName().equals(other.getName());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.name);
	}

}
