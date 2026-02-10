package org.skrprojects.carrental.model;

import java.util.Objects;
import java.util.UUID;


public class User {
    
	private final String id;
    private final String name;
    private final String email;
    private final String phone;
    private final String licenseNumber;
    private final String licenseState;
    
    
	public User(String name, String email, String phone, String licenseNumber, String licenseState) {
		this.id = UUID.randomUUID().toString();
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.licenseNumber = licenseNumber;
		this.licenseState = licenseState;
	}


	public String getId() {
		return id;
	}


	public String getName() {
		return name;
	}


	public String getEmail() {
		return email;
	}


	public String getPhone() {
		return phone;
	}


	public String getLicenseNumber() {
		return licenseNumber;
	}


	public String getLicenseState() {
		return licenseState;
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
		User other = (User) obj;
		return Objects.equals(id, other.id);
	}


	@Override
	public String toString() {
		return "User [name=" + name + "]";
	}
    
    
}
