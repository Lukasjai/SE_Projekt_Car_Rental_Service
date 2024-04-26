package org.example.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    private float price;

    @Column(name = "order_date")
    private LocalDate orderDate;

    @Column(name = "pick_up_date")
    private LocalDate pickUpDate;

    @Column(name = "bring_back_date")
    private LocalDate bringBackDate;

    public Order() {
    }

    public Order(Long orderId, Customer customer, Car car, float price, LocalDate orderDate, LocalDate pickUpDate, LocalDate bringBackDate) {
        this.orderId = orderId;
        this.customer = customer;
        this.car = car;
        this.price = price;
        this.orderDate = orderDate;
        this.pickUpDate = pickUpDate;
        this.bringBackDate = bringBackDate;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDate getPickUpDate() {
        return pickUpDate;
    }

    public void setPickUpDate(LocalDate pickUpDate) {
        this.pickUpDate = pickUpDate;
    }

    public LocalDate getBringBackDate() {
        return bringBackDate;
    }

    public void setBringBackDate(LocalDate bringBackDate) {
        this.bringBackDate = bringBackDate;
    }
}