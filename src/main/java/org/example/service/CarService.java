package org.example.service;

import org.example.dto.CarDto;
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

    public Car callCars(CarDto carDto) {
        Car car = new Car();
        car.setCar_brand_name(carDto.getCar_brand_name());
        car.setCar_model_name(carDto.getCar_model_name());
        car.setNumber_of_seats(carDto.getNumber_of_seats());
        car.setPrices(carDto.getPrice());
        return carRepository.save(car);
    }

    public List<Car> findAvailableCars(LocalDate pickupDate, LocalDate returnDate) {
        return carRepository.findAvailableCars(pickupDate, returnDate);
    }
}