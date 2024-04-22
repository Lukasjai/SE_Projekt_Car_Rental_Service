package org.example.dto;

public class CarDto {




        private String car_brand_name;
        private String car_model_name;

        private int number_of_seats;
        private float prices;


        public CarDto() {

        }

        public CarDto(String firstName, String lastName, int number_of_seats, float prices) {
            this.car_brand_name = firstName;
            this.car_model_name = lastName;
            this.number_of_seats = number_of_seats;
            this.prices =prices;
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


        public int getNumber_of_seats() {return number_of_seats;
        }

        public void setNumber_of_seats(int licenceNumber) {
            this.number_of_seats = licenceNumber;
        }

        public float getPrice() {
        return prices;
    }

        public void setPrice(int prices) {
        this.prices = prices;
    }

    }




