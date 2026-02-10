package org.skrprojects.carrental.service;

import java.util.List;
import java.util.Optional;

import org.skrprojects.carrental.model.Car;
import org.skrprojects.carrental.model.CarType;


public interface InventoryService {
	
    void addCar(Car car);

    List<Car> listAllCars();

    List<Car> listCarsByType(CarType type);

    Optional<Car> findById(String carId);
}
