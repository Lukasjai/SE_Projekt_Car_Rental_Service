package org.example.repository;


import org.example.model.Car;
import org.example.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Date;
import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
 //   List<Car> findByAvailabilityDateBetween(Date startDatum, Date endDatum);
}

