package org.example.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.example.Car;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CarController {

    private static final String template = "Car available: %s";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/cars/available")
    public Car available(@RequestParam(value = "name", defaultValue = "Fiat") String name) {
        return new Car(counter.incrementAndGet(), String.format(template, name));
    }

    @GetMapping("/cars/rent")
    public Car rent(@RequestParam(value = "name", defaultValue = "Fiat") String name) {
        return new Car(counter.incrementAndGet(), String.format(template, name));
    }

}
