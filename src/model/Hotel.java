package model;

import java.util.ArrayList;

public class Hotel {
	
	private String name;
	private String address;
	private ArrayList<String> qualities;
	
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
	
	@Override
	public String toString() {
		return this.name;
	}

}
