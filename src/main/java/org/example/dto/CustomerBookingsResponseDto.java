package org.example.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class CustomerBookingsResponseDto {

    @NotEmpty
    private String carBrand;

    @NotEmpty
    private String carModel;

    @NotNull
    @Min(1)
    private int carSeats;

    @NotNull
    private float carPrice;

    @NotNull
    private LocalDate returnDate;

    @NotNull
    private LocalDate orderDate;

    @NotNull
    private LocalDate pickupDate;

    public CustomerBookingsResponseDto() {}

    public CustomerBookingsResponseDto(
            String carBrand,
            String carModel,
            int carSeats,
            float carPrice,
            LocalDate returnDate,
            LocalDate orderDate,
            LocalDate pickupDate
    ) {
        this.carBrand = carBrand;
        this.carModel = carModel;
        this.carSeats = carSeats;
        this.carPrice = carPrice;
        this.returnDate = returnDate;
        this.orderDate = orderDate;
        this.pickupDate = pickupDate;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public String getCarModel() {
        return carModel;
    }

    public int getCarSeats() {
        return carSeats;
    }

    public float getCarPrice() {
        return carPrice;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public LocalDate getPickupDate() {
        return pickupDate;
    }
}