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
import org.skrprojects.carrental.repository.CarRepository;
import org.skrprojects.carrental.repository.ReservationRepository;
import org.skrprojects.carrental.repository.inmemory.InMemoryCarRepository;
import org.skrprojects.carrental.repository.inmemory.InMemoryReservationRepository;
import org.skrprojects.carrental.service.defaultimpl.DefaultInventoryService;
import org.skrprojects.carrental.service.defaultimpl.DefaultReservationService;

class CarRentalServiceTest {

    private CarRentalService carRentalService;

    private InventoryService inventoryService;
    private ReservationService reservationService;

    private User user;
    
    
	@BeforeEach
	void setUp() throws Exception {
        CarRepository carRepository = new InMemoryCarRepository();
        ReservationRepository reservationRepository = new InMemoryReservationRepository();
        inventoryService = new DefaultInventoryService(carRepository);
        reservationService = new DefaultReservationService(
        		reservationRepository, inventoryService);

        carRentalService = new CarRentalService(inventoryService, reservationService);
        
		// initialize inventory
        inventoryService.addCar( new Car("Honda", "Accord", 2025, CarType.SEDAN, "257890", "MA"));
        inventoryService.addCar( new Car("Toyota", "Camry", 2026, CarType.SEDAN, "277890", "MA") );        
        inventoryService.addCar( new Car("Honda", "Pilot", 2026, CarType.SUV, "258891", "NH") );  
        inventoryService.addCar( new Car("Honda", "Odyssey", 2026, CarType.VAN, "259892", "NH") );
        
        user = new User("Alex", "alex@aimail.com", "1126751111", "NHL02578967", "NH");
	}

	
	@AfterEach
	void tearDown() throws Exception {
	}

		
	@Test
	void testAddCar() {
		Car newCar = new Car("Toyota", "Siena", 2026, CarType.VAN, "259893", "NH");
        carRentalService.addCar(newCar);

        List<Car> allCars = inventoryService.listAllCars();

        assertTrue( allCars.stream()
        		.anyMatch(c -> c.equals(newCar))
        );
	}
	

	@Test
	void testListAvailableCarsByType() {
        LocalDateTime start = LocalDateTime.now().plusDays(1);

        List<Car> available =carRentalService.listAvailableCarsByType(CarType.SEDAN, start, 2);

        assertEquals(2, available.size());
	}

	
	@Test
	void testReserveCar() {
        LocalDateTime start = LocalDateTime.now().plusDays(1);

        Reservation reservation =
                carRentalService.reserveCar(user, CarType.SEDAN, start, 2);

        assertNotNull(reservation);
        assertEquals(user.getId(), reservation.getUser().getId());
        assertEquals(CarType.SEDAN, reservation.getCar().getType());
	}

	
    @Test
    void testListAvailableCarsByTypeNotListResrvedCars() {
        LocalDateTime start = LocalDateTime.now().plusDays(1);

        carRentalService.reserveCar(user, CarType.SEDAN, start, 2);

        List<Car> available =
                carRentalService.listAvailableCarsByType(CarType.SEDAN, start, 2);

        assertEquals(1, available.size());
    }
	
}
