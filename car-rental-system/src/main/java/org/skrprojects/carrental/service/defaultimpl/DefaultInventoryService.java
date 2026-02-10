package org.skrprojects.carrental.service.defaultimpl;

import java.util.List;
import java.util.Optional;

import org.skrprojects.carrental.model.Car;
import org.skrprojects.carrental.model.CarType;
import org.skrprojects.carrental.repository.CarRepository;
import org.skrprojects.carrental.service.InventoryService;


public class DefaultInventoryService implements InventoryService {

	private final CarRepository carRepository;
	
	
	public DefaultInventoryService(CarRepository carRepository) {
		this.carRepository = carRepository;
	}

	
	@Override
	public void addCar(Car car) {
		carRepository.save(car);
	}

	
	@Override
	public List<Car> listAllCars() {
		return this.carRepository.findAll();
	}

	
	@Override
	public List<Car> listCarsByType(CarType type) {
		return carRepository.findByType(type);
	}

	@Override
	public Optional<Car> findById(String carId) {
		return carRepository.findById(carId);
	}

}
