package org.skrprojects.carrental.service;

import java.time.LocalDateTime;
import java.util.List;

import org.skrprojects.carrental.model.Car;
import org.skrprojects.carrental.model.CarType;
import org.skrprojects.carrental.model.Reservation;
import org.skrprojects.carrental.model.User;

public interface ReservationService {

    Reservation reserve( User user, CarType type, LocalDateTime start, int days);

    List<Reservation> findReservationsByUser(String userId);
    
    List<Reservation> findActiveReservationsByUser(String userId);
    
    List<Reservation> findReservationsByCar(Car car);
	
    List<Reservation> findActiveReservationsByCar(Car car);
    
    List<Car> findAvailableCars(CarType type, LocalDateTime start, int days);
    
}
