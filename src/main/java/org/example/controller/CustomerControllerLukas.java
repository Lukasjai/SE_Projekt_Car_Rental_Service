package org.example.controller;

import org.example.database.MariaDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerControllerLukas {


    private MariaDB databaseConnection;

    private String customer_firstname = "Lukas";
    private String customer_lastname = "Mustermann";
    private String customer_telephone_number = "+43660843580";

    private int licence_number = 514587;

    private String insertQuery = "INSERT INTO customers (customer_firstname, customer_lastname, customer_telephone_number, licence_number) VALUES (?, ?, ?, ?)";
    private String listQuery = "SELECT * FROM customers";


    public CustomerControllerLukas(MariaDB databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public void addNewCustomer() {
        try {
            // Get connection from DatabaseConnection class
            Connection connection = databaseConnection.getConnection();

            // Prepare the INSERT statement
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

            // Set values for the placeholders
            preparedStatement.setString(1, customer_firstname);
            preparedStatement.setString(2, customer_lastname);
            preparedStatement.setString(3, customer_telephone_number);
            preparedStatement.setInt(4, licence_number);

            // Execute the INSERT statement
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted.");

            // Close resources
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void listCustomer() {
        try {
            Connection connection = databaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(listQuery);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int customer_id = resultSet.getInt("customer_id");
                String customer_firstname = resultSet.getString("customer_firstname");
                String customer_lastname = resultSet.getString("customer_lastname");
                String customerTelephoneNumber = resultSet.getString("customer_telephone_number");
                int licenceNumber = resultSet.getInt("licence_number");


                System.out.println("Customer ID: " + customer_id + ", Customer: First Name - " + customer_firstname + ", Last Name - " + customer_lastname + ", telephone number - " + customer_telephone_number + ", licence number: - " + licence_number);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        MariaDB databaseConnection = new MariaDB();

        CustomerControllerLukas customerControllerLukas = new CustomerControllerLukas(databaseConnection);

        System.out.println("connected");

        customerControllerLukas.listCustomer();
        System.out.println("done");
    }



}
