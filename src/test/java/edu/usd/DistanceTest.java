package edu.usd;

import edu.usd.models.Airport;
import edu.usd.models.Distance;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DistanceTest {
    private final double SANlat = 32.7332;
    private final double SANlon = -117.196053;
    private final double FLLlat = 26.0742;
    private final double FLLlon = -80.1506;

    private final int EXPECTED_DISTANCE = 2264; // from https://www.nhc.noaa.gov/gccalc.shtml
    private final Airport SAN = new Airport("San Diego International Airport",
            "SAN",
            new double[]{SANlat, SANlon},
            new ArrayList<>());
    private final Airport FLL = new Airport("Fort Lauderdale International Airport",
            "SAN",
            new double[]{FLLlat, FLLlon},
            new ArrayList<>());

    @Test
    @DisplayName("Testing latitude/longitude difference using coordinates")
    public void distanceTestCoords() {
        int actualDistance = (int) Distance.getDistance(SANlat, SANlon, FLLlat, FLLlon);
        assertEquals(EXPECTED_DISTANCE, actualDistance);
    }

    @Test
    @DisplayName("Testing latitude/longitude difference using airports")
    public void distanceTestAirport() {
        int actualDistance = (int) Distance.getDistance(SAN, FLL);
        assertEquals(EXPECTED_DISTANCE, actualDistance);
    }

    @Test
    @DisplayName("Testing flight time using coordinates")
    public void flightTimeTestCoords() {
        double expectedFlightTime = (double) EXPECTED_DISTANCE / 500; // assume 500mph
        double actualFlightTime = Distance.getFlightTime(SANlat, SANlon, FLLlat, FLLlon);
        assertEquals(expectedFlightTime, actualFlightTime, .01);
    }

    @Test
    @DisplayName("Testing flight time using coordinates")
    public void flightTimeTestAirport() {
        double expectedFlightTime = (double) EXPECTED_DISTANCE / 500; // assume 500mph
        double actualFlightTime = Distance.getFlightTime(SAN, FLL);
        assertEquals(expectedFlightTime, actualFlightTime, .01);
    }
}
