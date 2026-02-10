package org.skrprojects.carrental.repository.inmemory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.skrprojects.carrental.model.Car;
import org.skrprojects.carrental.model.CarType;
import org.skrprojects.carrental.repository.CarRepository;

public class InMemoryCarRepository implements CarRepository {

	private final Map<String, Car> store = new HashMap<>();
	
	
	@Override
	public void save(Car car) {
		store.put(car.getId(), car);
	}

	
	@Override
	public List<Car> findAll() {
		return new ArrayList<>(store.values());
	}

	
	@Override
	public List<Car> findByType(CarType type) {
        return store.values()
                .stream()
                .filter(c -> c.getType() == type)
                .collect(Collectors.toList());
	}

	
	@Override
	public Optional<Car> findById(String id) {
		return Optional.ofNullable(store.get(id));
	}

}
