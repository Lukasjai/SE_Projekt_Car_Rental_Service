package org.example;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CarController {

    private static final String template = "Car available: %s";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/available")
    public Car greeting(@RequestParam(value = "name", defaultValue = "Fiat") String name) {
        return new Car(counter.incrementAndGet(), String.format(template, name));
    }
}
