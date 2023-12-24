package com.zachartman.utils;

import com.zachartman.models.Administrator;
import com.zachartman.models.Passenger;

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
