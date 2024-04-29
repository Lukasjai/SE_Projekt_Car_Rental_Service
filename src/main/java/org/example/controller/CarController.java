package org.example.controller;

import java.util.List;

import org.example.dto.CarAvailabilityDto;
import org.example.model.Car;
import org.example.service.CarService;
import org.example.currencyConverter.CurrencyConverter;
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

    @PostMapping("/findAvailable")
    public ResponseEntity<?> findAvailableCars(@RequestBody CarAvailabilityDto carAvailabilityDto) {
        List<Car> availableCars = carService.findAvailableCars(carAvailabilityDto.getPickupDate(), carAvailabilityDto.getReturnDate());

        return ResponseEntity.status(HttpStatus.OK).body(availableCars);
    }

    @PostMapping("/updatePrice")
    public ResponseEntity<?> updatePriceOfAvailableCars(@RequestBody CarAvailabilityDto carAvailabilityDto, @RequestParam String currency) {
        List<Car> availableCars = carService.findAvailableCars(carAvailabilityDto.getPickupDate(), carAvailabilityDto.getReturnDate());
        CurrencyConverter currencyConverter = new CurrencyConverter();
        for (Car car : availableCars) {
            System.out.println("update PRice");
            double convertedPrice = currencyConverter.convertCurrency(car.getPrices(), "USD", currency);
            car.setPrices((float) convertedPrice);

        }
        return ResponseEntity.status(HttpStatus.OK).body(availableCars);
    }

}