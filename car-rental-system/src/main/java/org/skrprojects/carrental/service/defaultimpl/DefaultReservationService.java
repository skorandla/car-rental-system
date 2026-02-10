package org.skrprojects.carrental.service.defaultimpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.skrprojects.carrental.model.Car;
import org.skrprojects.carrental.model.CarType;
import org.skrprojects.carrental.model.Reservation;
import org.skrprojects.carrental.model.User;
import org.skrprojects.carrental.repository.ReservationRepository;
import org.skrprojects.carrental.service.InventoryService;
import org.skrprojects.carrental.service.ReservationService;



public class DefaultReservationService implements ReservationService {

	
	private final ReservationRepository reservationRepository;
	private final InventoryService inventoryService;
	
	
	public DefaultReservationService(ReservationRepository reservationRepository, InventoryService inventoryService) {
		this.reservationRepository = reservationRepository;
		this.inventoryService = inventoryService;
	}

	
	@Override
	public Reservation reserve(User user, CarType type, LocalDateTime start, int days) {
		if (days <=0) 
			throw new IllegalArgumentException("Invalid number of days");

		if (start.isBefore(LocalDateTime.now()))
			throw new IllegalArgumentException("Invalid start date, date in the past");
		
		List<Car> carList =  findAvailableCars(type, start, days);
		
		if(carList.size() == 0)
			throw new IllegalArgumentException("No car available for the Type " + type + ",  the start date and number of days");
		

		LocalDateTime end = start.plusDays(days);
		Reservation res = new Reservation(user, carList.get(0), start, end, days); 
		reservationRepository.save(res);
		return res;
	}

	
	@Override
	public List<Reservation> findReservationsByUser(String userId) {
		return reservationRepository.findByUserId(userId);
	}


	@Override
	public List<Reservation> findActiveReservationsByUser(String userId) {
		return reservationRepository.findByUserId(userId)
				.stream()
				.filter(res -> res.isActive())
				.collect(Collectors.toList());
	}

	@Override
	public List<Reservation> findReservationsByCar(Car car) {
		return reservationRepository.findByCarId(car.getId());
	}
	

	@Override
	public List<Reservation> findActiveReservationsByCar(Car car) {
		return reservationRepository.findByCarId(car.getId())
				.stream()
				.filter( res -> res.isActive())
				.collect(Collectors.toList());
	}
	

	@Override
	public List<Car> findAvailableCars(CarType type, LocalDateTime start, int days) {
		LocalDateTime end = start.plusDays(days);
		
		return inventoryService.listCarsByType(type)
				.stream()
				.filter( car -> isCarAvailable(car, start, end))
				.collect(Collectors.toList());
	}

	
	/** Checks the given car is available between the start and end dates 
	 * 
	 * @param car  the car to check 
	 * @param start start date and time
	 * @param end  end date and time
	 * 
	 * @return	true if the car is available between the given start and and dates, otherwise false.
	 */
	private boolean isCarAvailable(Car car, LocalDateTime start, LocalDateTime end) {
		return reservationRepository.findByCarId(car.getId())
				.stream()
				.noneMatch( res -> res.overlaps(start, end));
	}
}
