package edu.usd.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.DriverManager;

public class DatabaseTablesCreation {

    // run the main function if it is your first time.
    // the recreateDatabase function is used for testing purposes, to get a fresh database.



    public static void main(String[] args) {
        // Creates the database and tables
        // and creates administrator with username: zartman and password: password with access to United Airlines
        // and creates passenger with username: testpassenger and password: password
        // and creates Airports SAN and FLL
        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_RESET = "\u001B[0m";

        if (recreateDatabase(true)) {
            System.out.println(ANSI_GREEN + "Database Successfully Created!\nGo to Main.java to run the program." + ANSI_RESET);
        }
        else {
            System.err.println("Database not created successfully.");
        }
    }

    public static boolean recreateDatabase(boolean sampleObjects) {

        String url = "jdbc:mysql://localhost:3306/mysql"; // default database
        String username = "root";
        String password = "password";

        String[] sqlStatements = {"CREATE TABLE flights ( flightId INT AUTO_INCREMENT PRIMARY KEY, flightNumber VARCHAR(255) NOT NULL, originAirport VARCHAR(10), destinationAirport VARCHAR(10), airline VARCHAR(255), plane VARCHAR(255), planeRegistrationNumber VARCHAR(255), departureTime DATETIME, arrivalTime DATETIME, ticketList TEXT, ticketPrices TEXT, availableSeats TEXT );",
                "CREATE TABLE airports ( airportName VARCHAR(255) NOT NULL, airportCode VARCHAR(10), airportLatitude VARCHAR(255), airportLongitude VARCHAR(255) );",
                "CREATE TABLE planes ( planeMake VARCHAR(255) NOT NULL, planeModel VARCHAR(255), registrationNumber VARCHAR(255), economySeats INT, firstClassSeats INT, storageCapacity DOUBLE );",
                "CREATE TABLE airlines ( airlineName VARCHAR(255) NOT NULL );",
                "CREATE TABLE administrators ( fullName VARCHAR(255), username VARCHAR(255), password VARCHAR(255), airlineName VARCHAR(255) );",
                "CREATE TABLE passengers ( fullName VARCHAR(255), username VARCHAR(255), password VARCHAR(255) );",
                "CREATE TABLE tickets ( confirmationCode VARCHAR(255), passenger VARCHAR(255), seatClass VARCHAR(255), flightID INT, FOREIGN KEY (flightId) REFERENCES flights(flightId)\n );"};

        String[] sqlStatementsDefaults = {"INSERT INTO planes VALUES (\"Airbus\", \"A380\", 594, 50, 10, 500);",
                "INSERT INTO airlines VALUES (\"United Airlines\");",
                "INSERT INTO Administrators VALUES (\"Zachary Artman\", \"zartman\", \"password\", \"United Airlines\");",
                "INSERT INTO Passengers VALUES(\"Test Passenger\", \"testpassenger\", \"password\");",
                "INSERT INTO Airports VALUES (\"San Diego International Airport\", \"SAN\", 32.732346, -117.196053);",
                "INSERT INTO Airports VALUES (\"Fort Lauderdale International Airport\", \"FLL\", 26.0742, -80.1506);"};


        String sqlDropDB = "DROP DATABASE IF EXISTS flightBooking";
        String sqlCreateDB = "CREATE DATABASE IF NOT EXISTS flightBooking";

        try {
            Connection conn = DriverManager.getConnection(url, username, password);

            // Drop the old database
            PreparedStatement pstmt = conn.prepareStatement(sqlDropDB);
            pstmt.executeUpdate();

            // Create the new database
            pstmt = conn.prepareStatement(sqlCreateDB);
            pstmt.executeUpdate();

            // Reconnect to the flight booking database
            url = "jdbc:mysql://localhost:3306/flightBooking";
            conn = DriverManager.getConnection(url, username, password);

            for (String sql : sqlStatements) {
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.executeUpdate();
                }
            }

            if (sampleObjects) {
                for (String sql : sqlStatementsDefaults) {
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.executeUpdate();
                    }
                }
            }

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
