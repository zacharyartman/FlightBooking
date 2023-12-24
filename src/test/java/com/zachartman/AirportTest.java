package com.zachartman;

import com.zachartman.models.Airport;
import com.zachartman.models.Flight;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class AirportTest {
    private final double[] SANlocation = {32.7332, -117.196053};

    private final Airport SAN = new Airport("San Diego International Airport",
            "SAN",
            SANlocation,
            new ArrayList<>());

    @Test
    @DisplayName("getAirportCode test")
    void getAirportCodeTest() {
        assertEquals("SAN", SAN.getAirportCode());
    }

    @Test
    @DisplayName("getAirportName test")
    void getAirportNameTest() {
        assertEquals("San Diego International Airport", SAN.getAirportName());
    }

    @Test
    @DisplayName("getAirportLocation test")
    void getAirportLocationTest() {
        assertEquals(SANlocation, SAN.getAirportLocation());

    }

    @Test
    @DisplayName("Add/remove flight test")
    void addFlightTest() {
        Flight mockFlight = mock(Flight.class);
        SAN.addFlight(mockFlight);
        assertTrue(SAN.getFlights().contains(mockFlight));
        SAN.removeFlight(mockFlight);
        assertFalse(SAN.getFlights().contains(mockFlight));
    }

    @Test
    @DisplayName("Test getFlight")
    void getFlightTest() {
        Flight mockFlightA = mock(Flight.class);
        Flight mockFlightB = mock(Flight.class);

        SAN.addFlight(mockFlightA);
        SAN.addFlight(mockFlightB);

        List<Flight> expectedFlightList = new ArrayList<>();
        expectedFlightList.add(mockFlightA);
        expectedFlightList.add(mockFlightB);

        assertEquals(expectedFlightList, SAN.getFlights());
    }

}
