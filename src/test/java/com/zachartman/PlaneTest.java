package com.zachartman;

import com.zachartman.models.Plane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlaneTest {
    private Plane plane;
    private final String planeMake = "Boeing";
    private final String planeModel = "747";
    private final String registrationNumber = "11223311";
    private final int economySeats = 50;
    private final int firstClassSeats = 10;
    private final double storageCapacity = 128.5;

    @BeforeEach
    void setUp(){
        plane = new Plane(planeMake, planeModel, registrationNumber, economySeats, firstClassSeats, storageCapacity);
    }

    @Test
    @DisplayName("Testing getPlaneMake")
    void getPlaneMakeTest(){
        String actualPlaneMake = plane.getPlaneMake();

        assertEquals(planeMake, actualPlaneMake);
    }

    @Test
    @DisplayName("Testing getPlaneModel")
    void getPlaneModelTest(){
        String actualPlaneModel = plane.getPlaneModel();

        assertEquals(planeModel, actualPlaneModel);
    }

    @Test
    @DisplayName("Testing getRegistrationNumber")
    void getRegistrationNumberTest(){
        String actualRegistrationNumber = plane.getRegistrationNumber();

        assertEquals(registrationNumber, actualRegistrationNumber);
    }
    @Test
    @DisplayName("Testing setRegistrationNumber")
    void setRegistrationNumberTest(){
        String expectedRegistrationNumber = "77665544";

        plane.setRegistrationNumber(expectedRegistrationNumber);
        String actualRegistrationNumber = plane.getRegistrationNumber();

        assertEquals(expectedRegistrationNumber, actualRegistrationNumber);
    }

    @Test
    @DisplayName("Testing getEconomySeats")
    void getEconomySeatsTest(){
        int actualEconomySeats = plane.getEconomySeats();

        assertEquals(economySeats, actualEconomySeats);
    }

    @Test
    @DisplayName("Testing setEconomySeats")
    void setEconomySeatsTest(){
        int expectedEconomySeats = 33;

        plane.setEconomySeats(expectedEconomySeats);
        int actualEconomySeats = plane.getEconomySeats();

        assertEquals(expectedEconomySeats, actualEconomySeats);
    }

    @Test
    @DisplayName("Testing getFirstClassSeats")
    void getFirstClassSeatsTest(){
        int actualFirstClassSeats = plane.getFirstClassSeats();

        assertEquals(firstClassSeats, actualFirstClassSeats);
    }

    @Test
    @DisplayName("Testing setFirstClassSeats")
    void setFirstClassSeatsTest(){
        int expectedFirstClassSeats = 3;

        plane.setFirstClassSeats(expectedFirstClassSeats);
        int actualFirstClassSeats = plane.getFirstClassSeats();

        assertEquals(expectedFirstClassSeats, actualFirstClassSeats);
    }

    @Test
    @DisplayName("Testing getStorageCapacity")
    void getStorageCapacityTest(){
        double actualStorageCapacity = plane.getStorageCapacity();

        assertEquals(storageCapacity, actualStorageCapacity);
    }

    @Test
    @DisplayName("Testing setStorageCapacity")
    void setStorageCapacityTest(){
        double expectedStorageCapacity = 333.33;

        plane.setStorageCapacity(expectedStorageCapacity);
        double actualStorageCapacty = plane.getStorageCapacity();

        assertEquals(expectedStorageCapacity, actualStorageCapacty);
    }
}
