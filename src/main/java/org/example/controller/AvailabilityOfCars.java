package org.example.controller;

import org.example.database.MariaDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AvailabilityOfCars {
    private MariaDB databaseConnection;

    public AvailabilityOfCars(MariaDB database) {
        this.databaseConnection = database;
    }

    public List<String> getAvailableCars(String startDate, String endDate) {
        List<String> availableCars = new ArrayList<>();

        try (Connection connection = databaseConnection.getConnection()) {
            String query = "SELECT car_brand_name, car_model_name FROM car_inventory WHERE car_id NOT IN (SELECT car_id FROM orders WHERE start_date <= ? AND end_date >= ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, endDate);
            preparedStatement.setString(2, startDate);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String carBrandName = resultSet.getString("car_brand_name");
                String carModelName = resultSet.getString("car_model_name");
                availableCars.add(carBrandName + " " + carModelName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return availableCars;
    }

    public static void main(String[] args) {
        MariaDB databaseConnection = new MariaDB();

        AvailabilityOfCars availabilityOfCars = new AvailabilityOfCars(databaseConnection);

        System.out.println("connected");

        availabilityOfCars.getAvailableCars("01.02.2024", "05.05.2024");
        System.out.println("done");
    }




}
