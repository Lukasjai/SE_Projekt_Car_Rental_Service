package org.example.database;
import java.sql.*;
import java.util.Properties;

public class MariaDB {
    private static final String USER = "root";
    private static final String PASSWORD = "SEProjektPasswort123!";
    private Connection connection;

    public  MariaDB () {
        Properties connConfig = new Properties();
        connConfig.setProperty("user", USER);
        connConfig.setProperty("password", PASSWORD);
        //  connConfig.setProperty("sslMode", "verify-full");
        //  connConfig.setProperty("serverSslCert", "/path/to/ca-bundle.pem");

        // Create Connection to the database
        try {connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/car_rental_service", connConfig);
            System.out.println("Connection established");
            try (Statement stmt = connection.createStatement()) {

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
        // Connection Configuration

    public Connection getConnection() {
        return connection;
    }



}
