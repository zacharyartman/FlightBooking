package edu.usd.utils;

import edu.usd.models.Administrator;
import edu.usd.models.Passenger;

public class SessionManager {
    private static Administrator currentAdmin;
    private static Passenger currentPassenger;


    public static void setCurrentUser(Passenger passenger) {
        currentPassenger = passenger;
    }

    public static void setCurrentUser(Administrator administrator) {
        currentAdmin = administrator;
    }

    public static Administrator getCurrentAdmin() {
        return currentAdmin;
    }

    public static Passenger getCurrentPassenger() {
        return currentPassenger;
    }
}
