package edu.usd;

import edu.usd.models.Airline;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AirlineTest{
    private Airline airline;

    @BeforeEach
    void setUp(){
        airline = new Airline("United Airlines");
    }

    @Test
    @DisplayName("Test getAirlineName")
    void getAirlineNameTest() {
        assertEquals("United Airlines", airline.getAirlineName());
    }

    @Test
    @DisplayName("Test setAirlineName")
    void setAirlineNameTest() {
        airline.setAirlineName("Southwest");
        assertEquals("Southwest", airline.getAirlineName());
    }
}
