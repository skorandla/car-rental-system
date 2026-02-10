package org.skrprojects.carrental.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;


public class Reservation {

    private final String id;
    private final User user;
    private final Car car;
    private final LocalDateTime start;
    private final LocalDateTime end;
    private final int numOfDays;
    
    
	public Reservation(User user, Car car, LocalDateTime start, LocalDateTime end, int numOfDays) {
		this.id = UUID.randomUUID().toString();
		this.user = user;
		this.car = car;
		this.start = start;
		this.end = end;
		this.numOfDays = numOfDays;
	}


	public String getId() {
		return id;
	}


	public User getUser() {
		return user;
	}


	public Car getCar() {
		return car;
	}


	public LocalDateTime getStart() {
		return start;
	}


	public LocalDateTime getEnd() {
		return end;
	}


	public int getNumOfDays() {
		return numOfDays;
	}
    
    public boolean overlaps(LocalDateTime s, LocalDateTime e) {
        return start.isBefore(e) && s.isBefore(end);
    }
    
    public boolean isActive() {
    	var now = LocalDateTime.now(); 
        return start.isAfter(now) || now.isBefore(end);
    }


	@Override
	public int hashCode() {
		return Objects.hash(id);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reservation other = (Reservation) obj;
		return Objects.equals(id, other.id);
	}


	@Override
	public String toString() {
		return "Reservation [id=" + id + ", user=" + user + ", car=" + car + ", start=" + start + ", end=" + end
				+ ", numOfDays=" + numOfDays + "]";
	}
    
    
}
