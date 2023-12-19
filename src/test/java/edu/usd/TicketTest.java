package edu.usd;

import edu.usd.models.Flight;
import edu.usd.models.Passenger;
import edu.usd.models.Ticket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
public class TicketTest {
    private Ticket ticket;
    private String confirmationCode;
    private String seatClass;
    private Passenger passenger;

    private Flight mockFlight;

    @BeforeEach
    void setUp(){
        confirmationCode = "AB1291";
        seatClass = "First";
        mockFlight = mock(Flight.class);
        passenger = new Passenger("John Appleseed", "jappleseed", "password");
        ticket = new Ticket(confirmationCode, passenger, seatClass, mockFlight);
    }

    @Test
    @DisplayName("Test setConfirmationCode")
    void setConfirmationCodeTest() {
        String expectedConfirmationCode = "DT4454";
        ticket.setConfirmationCode(expectedConfirmationCode);

        assertEquals(expectedConfirmationCode, ticket.getConfirmationCode());
    }

    @Test
    @DisplayName("Test getConfirmationCode")
    void getConfirmationCodeTest() {
        assertEquals(confirmationCode, ticket.getConfirmationCode());
    }

    @Test
    @DisplayName("Test setSeatClass")
    void setSeatClassTest() {
        String expectedSeatClass = "First";
        ticket.setSeatClass(expectedSeatClass);

        assertEquals(expectedSeatClass, ticket.getSeatClass());
    }

    @Test
    @DisplayName("Test getSeatClass")
    void getSeatNumberTest() {
        assertEquals(seatClass, ticket.getSeatClass());
    }

    @Test
    @DisplayName("Test getFlight")
    void getFlightTest() {
        assertEquals(mockFlight, ticket.getFlight());
    }

    @Test
    @DisplayName("Test getPassenger")
    void getPassengerTest() {
        assertEquals(passenger, ticket.getPassenger());
    }
}
