package org.skrprojects.carrental.repository.inmemory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.skrprojects.carrental.model.Reservation;
import org.skrprojects.carrental.repository.ReservationRepository;

public class InMemoryReservationRepository implements ReservationRepository {

	
    private final List<Reservation> reservations = new ArrayList<>();
    
    
	@Override
	public void save(Reservation reservation) {
		reservations.add(reservation);
	}

	@Override
	public List<Reservation> findAll() {
		return List.copyOf(reservations);
	}

	@Override
	public List<Reservation> findByUserId(String userId) {
        return reservations.stream()
                .filter(r -> r.getUser().getId().equals(userId))
                .collect(Collectors.toList());
	}

	@Override
	public List<Reservation> findByCarId(String carId) {
        return reservations.stream()
                .filter(r -> r.getCar().getId().equals(carId))
                .collect(Collectors.toList());
	}

}
