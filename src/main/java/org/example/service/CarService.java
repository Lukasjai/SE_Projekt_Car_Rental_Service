package org.example.service;

import org.example.model.Car;
import org.example.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CarService {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> findAvailableCars(LocalDate pickupDate, LocalDate returnDate) {
        return carRepository.findAvailableCars(pickupDate, returnDate);
    }
}
