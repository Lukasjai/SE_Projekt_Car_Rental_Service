package org.example.dto;

import java.time.LocalDate;

public class CarAvailabilityDto {

    private LocalDate pickupDate;
    private LocalDate returnDate;

    public CarAvailabilityDto() {
    }

    public CarAvailabilityDto(LocalDate pickupDate, LocalDate returnDate) {
        this.pickupDate = pickupDate;
        this.returnDate = returnDate;
    }

    public LocalDate getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(LocalDate pickupDate) {
        this.pickupDate = pickupDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}
