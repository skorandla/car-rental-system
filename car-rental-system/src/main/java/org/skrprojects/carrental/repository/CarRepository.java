package org.skrprojects.carrental.repository;

import java.util.List;
import java.util.Optional;

import org.skrprojects.carrental.model.Car;
import org.skrprojects.carrental.model.CarType;


public interface CarRepository {

	void save(Car car);

    List<Car> findAll();

    List<Car> findByType(CarType type);

    Optional<Car> findById(String id);
    
}
