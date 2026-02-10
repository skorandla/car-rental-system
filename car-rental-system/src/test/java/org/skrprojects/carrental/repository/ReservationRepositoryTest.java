package org.skrprojects.carrental.repository;

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
import org.skrprojects.carrental.repository.inmemory.InMemoryCarRepository;
import org.skrprojects.carrental.repository.inmemory.InMemoryReservationRepository;

class ReservationRepositoryTest {

	private ReservationRepository repository;
	private CarRepository carRepository;
	
	@BeforeEach
	void setUp() throws Exception {
		repository = new InMemoryReservationRepository();
		carRepository = new InMemoryCarRepository();
		
		// initialize inventory
		carRepository.save( new Car("Honda", "Accord", 2025, CarType.SEDAN, "257890", "MA"));
		carRepository.save( new Car("Toyota", "Camry", 2026, CarType.SEDAN, "277890", "MA") );        
		carRepository.save( new Car("Honda", "Pilot", 2026, CarType.SUV, "258890", "NH") );  
		carRepository.save( new Car("Honda", "Odyssey", 2026, CarType.VAN, "259890", "NH") );
	}
	

	@AfterEach
	void tearDown() throws Exception {
	}

	
	@Test
	void testSave() {
		carRepository.save( new Car("Honda", "Accord", 2025, CarType.SEDAN, "257890", "MA"));
		
        User user1 = new User("Alex", "alex@aimail.com", "1126751111", "NHL02578967", "NH");
        
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = start.plusDays(3);
        Reservation res = new Reservation(user1, carRepository.findByType(CarType.SEDAN).get(0), start, end, 3);
        
        repository.save(res);
        
        List<Reservation> resList = repository.findByUserId(user1.getId());

        assertEquals(1, resList.size());
	}

	
	
	@Test
	void testFindAll() {
		//carRepository.save( new Car("Honda", "Accord", 2025, CarType.SEDAN, "257890", "MA"));
		//carRepository.save( new Car("Toyota", "Camry", 2026, CarType.SEDAN, "277890", "MA") ); 
		
        User user1 = new User("Alex", "alex@aimail.com", "1126751111", "NHL02578967", "NH");
        
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = start.plusDays(3);
        Reservation res = new Reservation(user1, carRepository.findByType(CarType.SEDAN).get(0), start, end, 3);
        repository.save(res);
        
        User user2 = new User("Alex2", "alex2@aimail.com", "1126751112", "MDL02578968", "MA");
        
        start = LocalDateTime.now().plusDays(1);
        end = start.plusDays(3);
        Reservation res2 = new Reservation(user2, carRepository.findByType(CarType.SEDAN).get(0), start, end, 3);
        repository.save(res2);
        
		List<Reservation> resList = repository.findAll();
		
		assertEquals(2, resList.size());
	}

	
	@Test
	void testFindByUserId() {
		// covered by @testSave
	}

	
	@Test
	void testFindByCarId() {
        User user1 = new User("Alex", "alex@aimail.com", "1126751111", "NHL02578967", "NH");
        
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = start.plusDays(3);
        Car car = carRepository.findByType(CarType.SEDAN).get(0);
        Reservation res = new Reservation(user1, car, start, end, 3);
        
        repository.save(res);
        
        List<Reservation> resList = repository.findByCarId(car.getId());

        assertEquals(1, resList.size());
	}

}
