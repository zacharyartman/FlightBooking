package com.zachartman.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    final String url = "jdbc:mysql://localhost:3306/flightBooking"; // Replace with your database URL
    final String username = "root";
    final String password = "password";

    public Connection connect() {
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }
}

