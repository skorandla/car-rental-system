package org.skrprojects.carrental.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skrprojects.carrental.model.Car;
import org.skrprojects.carrental.model.CarType;
import org.skrprojects.carrental.repository.CarRepository;
import org.skrprojects.carrental.repository.inmemory.InMemoryCarRepository;
import org.skrprojects.carrental.service.defaultimpl.DefaultInventoryService;

class InventoryServiceTest {

	private InventoryService inventoryService;
	
	
	@BeforeEach
	void setUp() throws Exception {
		CarRepository carRepository = new InMemoryCarRepository();
        inventoryService = new DefaultInventoryService(carRepository);
	}

	
	@AfterEach
	void tearDown() throws Exception {
	}

	
	@Test
	void testAddCarAndFindById() {
		Car car = new Car("Honda", "Accord", 2025, CarType.SEDAN, "257890", "MA");
		inventoryService.addCar(car);
		
        Optional<Car> result = inventoryService.findById(car.getId());

        assertTrue(result.isPresent());
        assertEquals(car.getId(), result.get().getId());
        assertEquals(CarType.SEDAN, result.get().getType());
	}

	
	
	@Test
	void testListAllCars() {
		inventoryService.addCar( new Car("Toyota", "Camry", 2026, CarType.SEDAN, "277890", "MA") );        
		inventoryService.addCar( new Car("Honda", "Pilot", 2026, CarType.SUV, "258890", "NH") );  
		inventoryService.addCar( new Car("Honda", "Odyssey", 2026, CarType.VAN, "259890", "NH") );
		
        List<Car> cars = inventoryService.listAllCars();

        assertEquals(3, cars.size());
	}

	
	@Test
	void testListCarsByType() {
		inventoryService.addCar( new Car("Honda", "Accord", 2025, CarType.SEDAN, "257890", "MA") );    
		inventoryService.addCar( new Car("Toyota", "Camry", 2026, CarType.SEDAN, "277890", "MA") );        
		inventoryService.addCar( new Car("Honda", "Pilot", 2026, CarType.SUV, "258890", "NH") );  
		inventoryService.addCar( new Car("Honda", "Odyssey", 2026, CarType.VAN, "259890", "NH") );
		
		List<Car> sedans = inventoryService.listCarsByType(CarType.SEDAN);

		assertEquals(2, sedans.size());
        assertTrue(sedans.stream().allMatch(c -> c.getType() == CarType.SEDAN));
	}

	
	@Test
	void testFindById() {
		// covered in @testAddCarAndFindById
	}
	
    @Test
    void testFindByIdEmptyWhenCarNotFound() {

        Optional<Car> result = inventoryService.findById("UNKNOWN");

        assertTrue(result.isEmpty());
    }
	
}
