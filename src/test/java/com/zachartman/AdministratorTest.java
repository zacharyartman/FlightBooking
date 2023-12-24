package com.zachartman;

import com.zachartman.models.Administrator;
import com.zachartman.models.Airline;
import com.zachartman.models.Plane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AdministratorTest {
    private Administrator administrator;
    private String administratorID;
    private String fullName;
    private String password;
    private Airline airline;

    private final List<Integer> approvedAdministrators = new ArrayList<>();
    private final List<Plane> planeFleet = new ArrayList<>();

    @BeforeEach
    void setUp(){
        administratorID = "123456";
        fullName = "Test Admin";
        password = "password";

        airline = new Airline("United");
        administrator = new Administrator(fullName, administratorID, password, airline);
    }

    @Test
    @DisplayName("Testing getAdministratorName")
    void getAdministratorNameTest() {
        String expectedAdministratorName = fullName;
        String actualAdministratorName = administrator.getFullName();

        assertEquals(expectedAdministratorName, actualAdministratorName);
    }

    @Test
    @DisplayName("Testing setPassword")
    void setPasswordTest() {
        // sets password to newPassword, so the old password should throw an exception
        assertDoesNotThrow(() -> {
            administrator.setPassword(password, "newPassword");
        });

        assertThrows(Administrator.IncorrectPasswordException.class, () -> {
            administrator.setPassword(password, "password");
        });
    }

    @Test
    @DisplayName("Testing getAirline")
    void getAirlineTest() {
        assertEquals(airline, administrator.getAirlineNames().get(0));
    }

    @Test
    @DisplayName("Testing getUsername")
    void getUsernameTest() {
        assertEquals(administratorID, administrator.getUsername());
    }
}
