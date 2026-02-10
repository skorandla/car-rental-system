package org.skrprojects.carrental.model;

import java.util.Objects;
import java.util.UUID;



public class Car {

	private final String id;
	private final String make;
	private final String model;
	private final int year;
	private final CarType type;	
	private String plateNumber;
	private String registeredState;
	
	

	public Car(String make, String model, int year, CarType type, String plateNumber,
			String registeredState) {
		
		this.id = UUID.randomUUID().toString();
		this.make = make;
		this.model = model;
		this.year = year;
		this.type = type;
		this.plateNumber = plateNumber;
		this.registeredState = registeredState;
	}


	public String getId() {
		return id;
	}


	public String getMake() {
		return make;
	}


	public String getModel() {
		return model;
	}


	public int getYear() {
		return year;
	}
	

	public CarType getType() {
		return type;
	}


	public String getPlateNumber() {
		return plateNumber;
	}


	public String getRegisteredState() {
		return registeredState;
	}


	public void setRegisteredState(String registeredState) {
		this.registeredState = registeredState;
	}


	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}


	@Override
	public String toString() {
		return "Car [id=" + id + ", make=" + make + ", model=" + model + ", year=" + year + ", type=" + type
				+ ", plateNumber=" + plateNumber + ", registeredState=" + registeredState + "]";
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
		Car other = (Car) obj;
		return Objects.equals(id, other.id);
	}
	
	
	
}

