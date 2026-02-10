package org.skrprojects.carrental.repository;

import java.util.List;

import org.skrprojects.carrental.model.Reservation;


public interface ReservationRepository {

    void save(Reservation reservation);

    List<Reservation> findAll();

    List<Reservation> findByUserId(String userId);

    List<Reservation> findByCarId(String carId);
    
}
