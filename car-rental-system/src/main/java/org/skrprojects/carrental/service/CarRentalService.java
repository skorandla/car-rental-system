package org.skrprojects.carrental.service;

import java.time.LocalDateTime;
import java.util.List;

import org.skrprojects.carrental.model.Car;
import org.skrprojects.carrental.model.CarType;
import org.skrprojects.carrental.model.Reservation;
import org.skrprojects.carrental.model.User;



public class CarRentalService {


	private final InventoryService inventoryService;
	private final ReservationService reservationService;
	
	
	public CarRentalService(InventoryService inventoryService,  ReservationService reservationService) {
		this.inventoryService = inventoryService;
		this.reservationService = reservationService;
	}
	
	
	public void addCar(Car car) {
		inventoryService.addCar(car);
	}
		
	
	public List<Car> listAvailableCarsByType(CarType type,  LocalDateTime start, int days) {	
		return reservationService.findAvailableCars(type, start, days);
	}
	
	
	public synchronized Reservation reserveCar(User user, CarType type, LocalDateTime start, int days ) {
		return reservationService.reserve(user, type, start, days);
	}
	
}

