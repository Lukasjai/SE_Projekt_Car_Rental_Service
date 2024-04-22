package org.example.controller;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import jakarta.servlet.http.HttpSession;
import org.example.Car;
import org.example.repository.CarRepository;
import org.example.service.CarService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cars")
public class CarController {

    private final CarRepository carRepository;
    private final CarService carService;

    public CarController(CarRepository carRepository, CarService carService) {
        this.carRepository = carRepository;
        this.carService = carService;
    }

    private static final String template = "Car available: %s";
    private final AtomicLong counter = new AtomicLong();

    @PostMapping("/verfuegbar")
    public ResponseEntity<List<org.example.model.Car>> findAvailableCars(@RequestParam("pick_up_date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                                                         @RequestParam("bring_back_date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        List<org.example.model.Car> availableCars = carRepository.findByAvailabilityDateBetween(startDate, endDate);
        return ResponseEntity.ok(availableCars);
    }

    public ResponseEntity<List<org.example.model.Car>> getAllCars() {
        List<org.example.model.Car> cars = carRepository.findAll();
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/carinfo")
    public ResponseEntity<?> getUserInfo(HttpSession session) {
        Long carId = (Long) session.getAttribute("carId");
        return ResponseEntity.ok(carId);
    }

    @GetMapping("/rent")
    public Car rent(@RequestParam(value = "name", defaultValue = "Fiat") String name) {
        return new Car(counter.incrementAndGet(), String.format(template, name));
    }

    }



    /*
    @PostMapping("/available")
    public ResponseEntity<?> carsAvailable(@RequestBody DateWrapper dateWrapper) {
        Optional<Car> carOptional = Optional.ofNullable(carRepository.findCarById(car));
        if (carOptional.isPresent()) {
            return ResponseEntity.ok(carOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }


        System.out.println("todo");
        return ResponseEntity.notFound().build();
    }
  */


