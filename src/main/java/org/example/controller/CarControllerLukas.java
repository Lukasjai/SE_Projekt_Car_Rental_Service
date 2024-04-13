package org.example.controller;


import org.example.database.MariaDB;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
public class CarControllerLukas {


    private MariaDB databaseConnection;

    private String car_brand_name = "FIAT";
    private String car_model_name = "Punto";
    private int number_of_seats = 5;
    private boolean is_booked = false;
    private float prices = 15000;

    private String insertQuery = "INSERT INTO car_inventory (car_brand_name, car_model_name, number_of_seats, is_booked, prices) VALUES (?, ?, ?, ?, ?)";
    private String listQuery = "SELECT * FROM car_inventory";


    public CarControllerLukas(MariaDB databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public void addNewCarToDatabase() {
        try {
            // Get connection from DatabaseConnection class
            Connection connection = databaseConnection.getConnection();

            // Prepare the INSERT statement
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

            // Set values for the placeholders
            preparedStatement.setString(1, car_brand_name);
            preparedStatement.setString(2, car_model_name);
            preparedStatement.setInt(3, number_of_seats);
            preparedStatement.setBoolean(4, is_booked);
            preparedStatement.setFloat(5, prices);

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
        public void listCars() {
            try {
                Connection connection = databaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(listQuery);

                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String carBrandName = resultSet.getString("car_brand_name");
                    String carModelName = resultSet.getString("car_model_name");
                    int numberOfSeats = resultSet.getInt("number_of_seats");
                    boolean isBooked = resultSet.getBoolean("is_booked");
                    float prices = resultSet.getFloat("prices");

                    System.out.println("Car: Brand Name - " + carBrandName + ", Model Name - " + carModelName + ", Seats - " + numberOfSeats + ", Booked - " + isBooked + ", Price - " + prices);
                }

                resultSet.close();
                preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }















    /*
    private DatabaseConnection databaseConnection;

    //Change this parameters to the one inserted in the frontend
    private String car_brand_name = "FIAT";



    String insertQuery = "INSERT INTO car_inventory (car_brand_name, car_model_name, number_of_seats, is_booked, prices) VALUES (?, ?, ?, ?, ?)";
    public CarControllerLukas(DatabaseConnection databaseConnection){
        this.databaseConnection = databaseConnection;
    }


    public void addNewCarToDatabase(){

        databaseConnection = DatabaseConnection.get

        PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

    }


     */
    public static void main(String[] args) {
        MariaDB databaseConnection = new MariaDB();


        CarControllerLukas carController = new CarControllerLukas(databaseConnection);
        System.out.println("connected");

        carController.listCars();
        System.out.println("done");
    }

}
