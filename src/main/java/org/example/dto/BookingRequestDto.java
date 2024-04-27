package org.example.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class BookingRequestDto {

    @NotNull(message = "Car ID must not be null")
    private long carId;

    @NotNull(message = "Pickup date must not be null")
    private LocalDate pickupDate;

    @NotNull(message = "Return Date must not be null")
    private LocalDate returnDate;

    @NotNull(message = "Order date must not be null")
    private LocalDate orderDate;

    @NotNull(message = "Price must not be null")
    private float price;

    public BookingRequestDto() {}

    public BookingRequestDto(long carId, LocalDate pickupDate, LocalDate returnDate, LocalDate orderDate, float price) {
        this.carId = carId;
        this.pickupDate = pickupDate;
        this.returnDate = returnDate;
        this.orderDate = orderDate;
        this.price = price;
    }

    public long getCarId() {
        return carId;
    }

    public void setCarId(long carId) {
        this.carId = carId;
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

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
