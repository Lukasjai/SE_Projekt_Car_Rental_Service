package org.example.model;

import jakarta.persistence.*;

@Entity
@Table(name = "car_inventory")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    private Long id;

    @Column(name = "car_brand_name", nullable = false, columnDefinition = "CHAR")
    private String brandName;

    @Column(name = "car_model_name", nullable = false, columnDefinition = "CHAR")
    private String modelName;

    @Column(name = "number_of_seats")
    private int numberOfSeats;

    @Column(name = "prices", nullable = false)
    private float price;

    public Car() {}

    public Car(String brandName, String modelName, int numberOfSeats, float price) {
        this.brandName = brandName;
        this.modelName = modelName;
        this.numberOfSeats = numberOfSeats;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}