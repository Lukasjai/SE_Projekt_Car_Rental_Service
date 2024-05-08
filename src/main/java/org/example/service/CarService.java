package org.example.service;

import org.example.currencyConverter.CurrencyConverter;
import org.example.model.Car;
import org.example.repository.CarRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

@Service
public class CarService {

    private static final String DEFAULT_CURRENCY = "USD";
    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> getAvailableCars(LocalDate pickupDate, LocalDate returnDate) {
        checkParameterValidity(pickupDate, returnDate);

        return carRepository.findAvailableCars(pickupDate, returnDate);
    }

    public List<Car> getAvailableCars(LocalDate pickupDate, LocalDate returnDate, String toCurrency) {
        checkParameterValidity(pickupDate, returnDate);

        List<Car> availableCars = carRepository.findAvailableCars(pickupDate, returnDate);

        return convertCarPrices(availableCars, toCurrency);
    }

    private void checkParameterValidity(LocalDate pickupDate, LocalDate returnDate) {
        Assert.notNull(pickupDate, "pickupDate must not be null");
        Assert.notNull(returnDate, "returnDate must not be null");
        Assert.isTrue(pickupDate.isBefore(returnDate) || pickupDate.isEqual(returnDate)
                , "pickupDate must be before return date");
    }

    private List<Car> convertCarPrices(List<Car> cars, String toCurrency) {
        CurrencyConverter currencyConverter = new CurrencyConverter();

        for (Car car : cars) {
            double convertedPrice = currencyConverter.convertCurrency(car.getPrice(), DEFAULT_CURRENCY, toCurrency);
            car.setPrice((float) convertedPrice);
        }
        return cars;
    }
}
