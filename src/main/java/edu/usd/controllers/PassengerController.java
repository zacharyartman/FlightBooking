package edu.usd.controllers;

import edu.usd.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class PassengerController {

    /**
     * gets all passenger users
     * @return a hashmap containing Username, Password
     */
    public HashMap<String, String> getPassengers() {
        HashMap<String, String> usernamePassword = new HashMap<>();
        String sql = "SELECT * from Passengers";

        try (Connection conn = new DatabaseConnection().connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                usernamePassword.put(username, password);
            }
        }

        catch(SQLException e) {
            e.printStackTrace();
        }
        return usernamePassword;
    }

}
