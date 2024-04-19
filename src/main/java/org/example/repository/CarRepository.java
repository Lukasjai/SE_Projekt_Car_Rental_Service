package org.example.repository;


import org.example.model.Car;
import org.example.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
}
