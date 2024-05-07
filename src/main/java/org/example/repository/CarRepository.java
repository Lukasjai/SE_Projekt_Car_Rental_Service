package org.example.repository;

import org.example.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    @Query("SELECT c FROM Car c WHERE c.id NOT IN (" +
            "SELECT o.car.id FROM Order o WHERE o.pickUpDate <= :returnDate AND o.bringBackDate >= :pickupDate)")
    List<Car> findAvailableCars(@Param("pickupDate") LocalDate pickupDate, @Param("returnDate") LocalDate returnDate);
}