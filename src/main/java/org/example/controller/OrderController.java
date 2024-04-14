package org.example.controller;

import org.example.database.MariaDB;

import java.sql.*;

public class OrderController {


    private MariaDB databaseConnection;

    private int customer_id = 1;
    private Date order_date = new Date(124, 4, 5);
    private Date pick_up_date = new Date(124, 4, 9);
    private Date bring_back_date = new Date(124, 4, 11);
    private int licencebring_back_date_number = 514587;
    private int car_id = 2;
    private float price = 150;
    private int insertedCustomerID = 1;


    private String insertQuery = "INSERT INTO orders (customer_id, order_date, pick_up_date, bring_back_date, licencebring_back_date_number, car_id, price) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private String listQuery = "SELECT * FROM orders";

    private String listQueryFromCustomerID = "SELECT * FROM orders WHERE customer_id = 'insertedCustomerID'";


    public OrderController(MariaDB databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public void addNewOrder() {
        try {
            // Get connection from DatabaseConnection class
            Connection connection = databaseConnection.getConnection();

            // Prepare the INSERT statement
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

            // Set values for the placeholders
            preparedStatement.setInt(1, customer_id);
            preparedStatement.setDate(2, order_date);
            preparedStatement.setDate(3, pick_up_date);
            preparedStatement.setDate(4, bring_back_date);
            preparedStatement.setInt(5, car_id);
            preparedStatement.setFloat(6, price);


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
    public void listOrders(String Query) {
        try {
            Connection connection = databaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Query);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int order_id = resultSet.getInt("order_id");
                int customer_id = resultSet.getInt("customer_id");
                Date order_date = resultSet.getDate("order_date");
                Date pick_up_date = resultSet.getDate("pick_up_date");
                Date bring_back_date = resultSet.getDate("bring_back_date");
                int car_id = resultSet.getInt("car_id");
                float price = resultSet.getFloat("price");



                System.out.println("Customer: Order ID - " + order_id + ", Customer ID - " + customer_id + ", order date - " + order_date + ", pick up date : - " + pick_up_date + ", bring back date: " + bring_back_date + ", car ID: " + car_id + ", price " + price);
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

        OrderController orderController = new OrderController(databaseConnection);

        System.out.println("connected");

        orderController.listOrders(orderController.listQuery);
        System.out.println("done");
    }


}
