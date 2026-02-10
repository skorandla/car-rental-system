package org.skrprojects.carrental.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skrprojects.carrental.model.Car;
import org.skrprojects.carrental.model.CarType;
import org.skrprojects.carrental.model.Reservation;
import org.skrprojects.carrental.model.User;
import org.skrprojects.carrental.repository.ReservationRepository;
import org.skrprojects.carrental.repository.inmemory.InMemoryCarRepository;
import org.skrprojects.carrental.repository.inmemory.InMemoryReservationRepository;
import org.skrprojects.carrental.service.defaultimpl.DefaultInventoryService;
import org.skrprojects.carrental.service.defaultimpl.DefaultReservationService;

class ReservationServiceTest {

    private ReservationRepository reservationRepository;
    private InventoryService inventoryService;
    private ReservationService reservationService;
    
    private User user;
    
    
    
	@BeforeEach
	void setUp() throws Exception {
		reservationRepository = new InMemoryReservationRepository();
        inventoryService = new DefaultInventoryService(new InMemoryCarRepository());
        reservationService = new DefaultReservationService(
        		reservationRepository, inventoryService);
        
		// initialize inventory
        inventoryService.addCar( new Car("Honda", "Accord", 2025, CarType.SEDAN, "257890", "MA"));
        inventoryService.addCar( new Car("Toyota", "Camry", 2026, CarType.SEDAN, "277890", "MA") );        
        inventoryService.addCar( new Car("Honda", "Pilot", 2026, CarType.SUV, "258890", "NH") );  
        inventoryService.addCar( new Car("Honda", "Odyssey", 2026, CarType.VAN, "259890", "NH") );
        
        user = new User("Alex", "alex@aimail.com", "1126751111", "NHL02578967", "NH");
	}

	
	@AfterEach
	void tearDown() throws Exception {
	}

	
	@Test
	void testReserveWhenCarIsAvailable() {
		LocalDateTime start = LocalDateTime.now().plusDays(1);
        Reservation reservation = reservationService.reserve(user, CarType.SEDAN, start, 2);
        
        assertNotNull(reservation);
        assertEquals(user, reservation.getUser());
        assertEquals(CarType.SEDAN, reservation.getCar().getType());
        
        List<Reservation> stored = reservationService.findReservationsByUser(user.getId());
        assertEquals(1, stored.size());
	}

	
	@Test
	void testFindReservationsByUser() {
		// covered in @testReserveWhenCarIsAvailable()
	}

    @Test
    void testThrowExceptionWhenDaysIsZeroOrNegative() {

        LocalDateTime start = LocalDateTime.now().plusDays(1);

        assertThrows(IllegalArgumentException.class,
        		() -> reservationService.reserve(user, CarType.SEDAN, start, 0));

        assertThrows(IllegalArgumentException.class,
                () -> reservationService.reserve(user, CarType.SEDAN, start, -2));
    }

    @Test
    void testThrowExceptionWhenStartDateIsInPast() {

        LocalDateTime past = LocalDateTime.now().minusDays(1);

        assertThrows(IllegalArgumentException.class,
                () -> reservationService.reserve(user, CarType.SEDAN, past, 2));
    }

    
    @Test
    void testThrowExceptionWhenNoCarsAvailable() {

        LocalDateTime start = LocalDateTime.now().plusDays(1);

        // Reserve same car at same time
        reservationService.reserve(user, CarType.SUV, start, 2);
        
        User user2 = new User("Alex2", "alex2@aimail.com", "1126751112", "MDL02578968", "MA");        
        assertThrows(IllegalArgumentException.class,
                () -> reservationService.reserve( user2, CarType.SUV, start, 2 ) );
    }
    
    
    @Test
    void testExcludeCarsWithOverlappingReservation() {

        LocalDateTime start = LocalDateTime.now().plusDays(1);

        Reservation r = reservationService.reserve(user, CarType.SEDAN, start, 2);

        List<Car> available = reservationService.findAvailableCars(CarType.SEDAN, start, 2);

        assertEquals(1, available.size());
        assertFalse(
        		available.stream()
        			.anyMatch(c -> c.getId().equals(r.getCar().getId()))
        );
    }
    
    
	@Test
	void testFindAllReservationsByUser() {
        LocalDateTime start = LocalDateTime.now().plusDays(1);

        reservationService.reserve(user, CarType.SEDAN, start, 2);
        reservationService.reserve(user, CarType.SEDAN, start.plusDays(5), 2);

        List<Reservation> list = reservationService.findReservationsByUser(user.getId());

        assertEquals(2, list.size());
	}

	
	@Test
	void testFindActiveReservationsByUser() {
        LocalDateTime start1 = LocalDateTime.now().minusDays(5);
        LocalDateTime start2 = LocalDateTime.now().plusDays(1);

        List<Car> cars = inventoryService.listCarsByType(CarType.SEDAN);
        
        // mimic old resrvation directly using Repository, 
        // as the service won't allow old reservation creation
        Reservation past = new Reservation(user, cars.get(0), start1, start1.plusDays(1), 1);
        Reservation future = new Reservation(user, cars.get(1), start2, start2.plusDays(2), 2);

        reservationRepository.save(past);
        reservationRepository.save(future);

        List<Reservation> active = reservationService.findActiveReservationsByUser(user.getId());

        assertEquals(1, active.size());
        assertTrue(active.get(0).isActive());
	}
	
	
	@Test
	void testFindReservationsByCar() {
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        Reservation r = reservationService.reserve(user, CarType.SEDAN, start, 2);
        List<Reservation> list = reservationService.findReservationsByCar(r.getCar());

        assertEquals(1, list.size());
	}

	
	@Test
	void testFindActiveReservationsByCar() {
        LocalDateTime start1 = LocalDateTime.now().minusDays(5);
        LocalDateTime start2 = LocalDateTime.now().plusDays(1);

        List<Car> cars = inventoryService.listCarsByType(CarType.SEDAN);
        
        // mimic old resrvation directly using Repository, 
        // as the service won't allow old reservation creation
        // both RESERVATIONS on same car
        Reservation past = new Reservation(user, cars.get(0), start1, start1.plusDays(1), 1);
        Reservation future = new Reservation(user, cars.get(0), start2, start2.plusDays(2), 2);

        reservationRepository.save(past);
        reservationRepository.save(future);

        List<Reservation> list = reservationService.findActiveReservationsByCar(cars.get(0));

        assertEquals(1, list.size());
        assertTrue(list.get(0).isActive());
	}

	
	@Test
	void testFindAvailableCars() {
        LocalDateTime start = LocalDateTime.now().plusDays(1);

        List<Car> available = reservationService.findAvailableCars(CarType.SEDAN, start, 2);

        assertEquals(2, available.size());
	}

}
