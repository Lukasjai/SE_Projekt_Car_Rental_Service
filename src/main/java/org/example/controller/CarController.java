package org.example.controller;

import java.util.List;

import org.example.dto.CarAvailabilityDto;
import org.example.model.Car;
import org.example.service.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping("/availability")
    public ResponseEntity<?> findAvailableCars(@RequestBody CarAvailabilityDto carAvailabilityDto) {
        List<Car> availableCars = carService.findAvailableCars(carAvailabilityDto.getPickupDate(), carAvailabilityDto.getReturnDate());

        return ResponseEntity.status(HttpStatus.OK).body(availableCars);
    }
}