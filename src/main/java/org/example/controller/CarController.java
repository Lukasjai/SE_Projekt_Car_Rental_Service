package org.example.controller;

import java.time.LocalDate;
import java.util.List;

import org.example.model.Car;
import org.example.service.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cars")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public ResponseEntity<List<Car>> getAllAvailableCars(
            @RequestParam LocalDate pickupDate,
            @RequestParam LocalDate returnDate,
            @RequestParam(required = false) String toCurrency) {
        List<Car> availableCars;
        if (toCurrency == null) {
            availableCars = carService.getAvailableCars(pickupDate, returnDate);
        } else {
            availableCars = carService.getAvailableCars(pickupDate, returnDate, toCurrency);
        }

        return ResponseEntity.status(HttpStatus.OK).body(availableCars);
    }
}