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
        private String car_brand_name;
        @Column(name = "car_model_name", nullable = false, columnDefinition = "CHAR")
        private String car_model_name;

        @Column(name = "number_of_seats")
        private int number_of_seats;
        @Column(name = "prices", nullable = false)
        private float prices;


        public Car() {}

        public Car(String car_brand_name, String car_model_name, int number_of_seats, float prices) {
            this.car_brand_name = car_brand_name;
            this.car_model_name = car_model_name;
            this.number_of_seats = number_of_seats;
            this.prices = prices;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getCar_brand_name() {
            return car_brand_name;
        }

        public void setCar_brand_name(String car_brand_name) {
            this.car_brand_name = car_brand_name;
        }

        public String getCar_model_name() {
            return car_model_name;
        }

        public void setCar_model_name(String car_model_name) {this.car_model_name = car_model_name;
        }

        public int getNumber_of_seats() {
            return number_of_seats;
        }

        public void setNumber_of_seats(int number_of_seats) {
            this.number_of_seats = number_of_seats;
        }

        public float getPrices() {
            return prices;
        }

        public void setPrices(float prices) {
            this.prices = prices;
        }


    }



