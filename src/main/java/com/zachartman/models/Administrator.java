package com.zachartman.models;

import java.util.ArrayList;
import java.util.List;

public class Administrator extends User{
    private List<Airline> airlineNames = new ArrayList<>();

    public Administrator(String fullName, String username, String password, List<Airline> airlineNames) {
        super(fullName, username, password);
        this.airlineNames = airlineNames;
    }

    // constructor to allow for only one airline to be passed through
    public Administrator(String fullName, String username, String password, Airline airlineName) {
        super(fullName, username, password);
        airlineNames.add(airlineName);
    }

    public static class IncorrectPasswordException extends Exception {
        public IncorrectPasswordException(String message) {
            super(message);
        }
    }

    public List<Airline> getAirlineNames() {
        return airlineNames;
    }


    public void setPassword(String oldPassword, String newPassword) throws IncorrectPasswordException {
        if (oldPassword.equals(password))
            password = newPassword;
        else
            throw new IncorrectPasswordException("Incorrect old password.");
    }

}
