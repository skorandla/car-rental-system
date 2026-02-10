package org.skrprojects.carrental.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skrprojects.carrental.model.Car;
import org.skrprojects.carrental.model.CarType;
import org.skrprojects.carrental.repository.inmemory.InMemoryCarRepository;

class CarRepositoryTest {

	private CarRepository carRepository;
	
	
	@BeforeEach
	void setUp() throws Exception {
		carRepository = new InMemoryCarRepository();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	
	@Test
	void testSaveAndFindById() {
		Car car = new Car("Honda", "Accord", 2025, CarType.SEDAN, "257890", "MA");
        carRepository.save(car);

        Optional<Car> resCar = carRepository.findById(car.getId());
        
        assertTrue(resCar.isPresent());
        assertEquals(car.getId(), resCar.get().getId());
        assertEquals(CarType.SEDAN, resCar.get().getType());
	}

	@Test
	void testReturnEmptyWhenCarNotFound() {
        Optional<Car> result = carRepository.findById("UNKNOWN");

        assertTrue(result.isEmpty());
	}
	
	
	@Test
	void testFindAll() {
		carRepository.save( new Car("Toyota", "Camry", 2026, CarType.SEDAN, "277890", "MA") );        
		carRepository.save( new Car("Honda", "Pilot", 2026, CarType.SUV, "258890", "NH") );  
		carRepository.save( new Car("Honda", "Odyssey", 2026, CarType.VAN, "259890", "NH") );

        List<Car> allCars = carRepository.findAll();

        assertEquals(3, allCars.size());
	}

	
	@Test
	void testFindByType() {
		carRepository.save( new Car("Honda", "Accord", 2025, CarType.SEDAN, "257890", "MA"));
		carRepository.save( new Car("Toyota", "Camry", 2026, CarType.SEDAN, "277890", "MA") );        
		carRepository.save( new Car("Honda", "Pilot", 2026, CarType.SUV, "258890", "NH") );  
		carRepository.save( new Car("Honda", "Odyssey", 2026, CarType.VAN, "259890", "NH") );

        List<Car> sedans = carRepository.findByType(CarType.SEDAN);

        assertEquals(2, sedans.size());
        assertTrue(sedans.stream().allMatch(c -> c.getType() == CarType.SEDAN));
	}

	
	@Test
	void testFindByTypeWhenEmpty() {
		carRepository.save( new Car("Honda", "Accord", 2025, CarType.SEDAN, "257890", "MA"));
		
        List<Car> vans = carRepository.findByType(CarType.VAN);

        assertNotNull(vans);
        assertTrue(vans.isEmpty());
	}

}
