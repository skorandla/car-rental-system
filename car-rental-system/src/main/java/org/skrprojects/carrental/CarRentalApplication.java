package org.skrprojects.carrental;

import java.time.LocalDateTime;

import org.skrprojects.carrental.model.Car;
import org.skrprojects.carrental.model.CarType;
import org.skrprojects.carrental.model.Reservation;
import org.skrprojects.carrental.model.User;
import org.skrprojects.carrental.repository.CarRepository;
import org.skrprojects.carrental.repository.ReservationRepository;
import org.skrprojects.carrental.repository.inmemory.InMemoryCarRepository;
import org.skrprojects.carrental.repository.inmemory.InMemoryReservationRepository;
import org.skrprojects.carrental.service.CarRentalService;
import org.skrprojects.carrental.service.InventoryService;
import org.skrprojects.carrental.service.ReservationService;
import org.skrprojects.carrental.service.defaultimpl.DefaultInventoryService;
import org.skrprojects.carrental.service.defaultimpl.DefaultReservationService;

/** Car Rental application 
 * 
 */
public class CarRentalApplication {

	
	CarRentalService rentalService;
	
	CarRentalApplication() {
		initApplication();
	}

	private void initApplication() {
		ReservationRepository reservationRepository = new InMemoryReservationRepository();
		CarRepository carRepository = new InMemoryCarRepository();
		
		InventoryService inventoryService = new DefaultInventoryService(carRepository);
        ReservationService reservationService = new DefaultReservationService(reservationRepository, inventoryService);
        
        rentalService = new CarRentalService(inventoryService, reservationService);
        
	}

	
	public static void main(String[] args) {
        System.out.println("Starting the Car Rental Application...");
        
        CarRentalApplication app = new CarRentalApplication();
        
        // String make, String model, int year, CarType type, String plateNumber, String registeredState
        //Car car = new Car("Honda", "Accord", 2025, CarType.SEDAN, "257890", "MA");
        // invenory seed 
        app.rentalService.addCar( new Car("Honda", "Accord", 2025, CarType.SEDAN, "257890", "MA") );
        app.rentalService.addCar( new Car("Toyota", "Camry", 2026, CarType.SEDAN, "277890", "MA") );        
        app.rentalService.addCar( new Car("Honda", "Pilot", 2026, CarType.SUV, "258890", "NH") );  
        app.rentalService.addCar( new Car("Honda", "Odyssey", 2026, CarType.VAN, "259890", "NH") );
        
        //String name, String email, String phone, String licenseNumber, String licenseState
        User user1 = new User("Alex", "alex@aimail.com", "1126751111", "NHL02578967", "NH");
        
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        
        Reservation r = app.rentalService.reserveCar(user1, CarType.SEDAN, start, 2);

        System.out.println("\nReservation created:");
        System.out.println(r);

        System.out.println("\nAvailable sedans after reservation:");
        app.rentalService.listAvailableCarsByType(CarType.SEDAN, start, 2)
                .forEach(System.out::println);
        
        
    }
	
	
}

