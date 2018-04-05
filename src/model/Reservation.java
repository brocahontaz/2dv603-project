package model;

import java.util.Date;
import java.util.Objects;

public class Reservation {
	
	private int id;
	private Guest guest;
	private Room room;
	private Date startDate;
	private Date endDate;
	private int daysDuration;

	public Reservation(int id, Guest guest, Room room, Date startDate, Date endDate, int daysDuration) {
		this.id = id;
		this.guest = guest;
		this.room = room;
		this.startDate = startDate;
		this.endDate = endDate;
		this.daysDuration = daysDuration;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public Guest getGuest() {
		return guest;
	}

	public void setGuest(Guest guest) {
		this.guest = guest;
	}
	
	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}
	
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getDuration() {
		return daysDuration;
	}

	public void setDuration(int daysDuration) {
		this.daysDuration = daysDuration;
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
