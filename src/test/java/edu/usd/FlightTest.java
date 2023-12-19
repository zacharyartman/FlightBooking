package edu.usd;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.usd.models.*;
import edu.usd.controllers.AdminController;
import edu.usd.utils.DatabaseTablesCreation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class FlightTest {
    private Flight flight;

    private final Airport SAN = new Airport("San Diego International Airport",
            "SAN",
            new double[]{32.7332, -117.196053},
            new ArrayList<>());
    private final Airport FLL = new Airport("Fort Lauderdale International Airport",
            "SAN",
            new double[]{26.0742, -80.1506},
            new ArrayList<>());
    private String flightNumber;
    private Airline airline;
    private Plane plane;
    private LocalDateTime departureTime;
    private Ticket userATicket;
    private Ticket userBTicket;
    private Ticket userCTicket;
    private final List<Ticket> ticketList = new ArrayList<>();
    private final HashMap<String, Double> ticketPrices = new HashMap<>();
    private final HashMap<String, Integer> availableSeats = new HashMap<>();


    private final List<Flight> flights = new ArrayList<>();
    private final List<Plane> planeFleet = new ArrayList<>();

    @BeforeEach
    void setUp(){
        DatabaseTablesCreation.recreateDatabase(false);

        flightNumber = "QR797";

        airline = new Airline("United Airlines");
        plane = new Plane("Boeing", "747", "11223311", 150, 10, 128.5);

        departureTime = LocalDateTime.of(2023, 9, 13, 15, 56);
        Flight flightMock = mock(Flight.class);
        Passenger passengerMock = mock(Passenger.class);
        userATicket = new Ticket("AB1291", passengerMock, "First", flightMock);
        userBTicket = new Ticket("AB1012", passengerMock, "First", flightMock);
        userCTicket = new Ticket("AB2912", passengerMock, "Economy", flightMock);
        ticketList.add(userATicket);
        ticketList.add(userBTicket);

        ticketPrices.put("First", 200.0);
        ticketPrices.put("Economy", 100.0);

        availableSeats.put("First", 5);
        availableSeats.put("Economy", 130);

        flight = new Flight(flightNumber, SAN, FLL, airline, plane, departureTime, ticketList, ticketPrices, availableSeats);
    }

    @Test
    @DisplayName("Test getFlightNumber")
    void getFlightNumberTest() { assertEquals(flightNumber, flight.getFlightNumber()); }

    @Test
    @DisplayName("Test getAirport")
    void getAirportTest() {
        assertEquals(SAN, flight.getOriginAirport());
        assertEquals(FLL, flight.getDestinationAirport());
    }

    @Test
    @DisplayName("Test getAirline")
    void getAirlineTest() { assertEquals(airline, flight.getAirline()); }

    @Test
    @DisplayName("Test setPlane")
    void setPlaneTest() {
        Plane expectedPlane = new Plane("Airbus", "A380", "11200311", 100, 50, 190.5);
        flight.setPlane(expectedPlane);

        assertEquals(expectedPlane, flight.getPlane());
    }

    @Test
    @DisplayName("Test getPlane")
    void getPlane() { assertEquals(plane, flight.getPlane()); }

    @Test
    @DisplayName("Test setDepartureTime")
    void setDepartureTimeTest() {
        LocalDateTime expectedDepartureTime = LocalDateTime.of(2013, 10, 20, 5, 5);
        flight.setDepartureTime(expectedDepartureTime);

        assertEquals(expectedDepartureTime, flight.getDepartureTime());
    }

    @Test
    @DisplayName("Test getDepartureTime")
    void getDepartureTimeTest() { assertEquals(departureTime, flight.getDepartureTime()); }

    @Test
    @DisplayName("Test getArrivalTime")
    void getArrivalTimeTest() {
        double flightTime = Distance.getFlightTime(SAN, FLL);
        int hours = (int) flightTime;
        int minutes = (int) ((flightTime - hours) * 60);
        LocalDateTime arrivalTime = departureTime.plusHours(hours).plusMinutes(minutes);

        assertEquals(arrivalTime, flight.getArrivalTime()); }

    @Test
    @DisplayName("Test getTicketPrice")
    void getTicketPriceTest() { assertEquals(ticketPrices.get("First"), flight.getTicketPrice("First")); }

    @Test
    @DisplayName("Test setTicketPrice")
    void setTicketPriceTest() {
        flight.setTicketPrice("First", 900.0);
        assertEquals(ticketPrices.get("First"), flight.getTicketPrice("First"));
    }

    @Test
    @DisplayName("Test getAvailableSeats")
    void getAvailableSeats() { assertEquals(availableSeats.get("First"), flight.getAvailableSeats("First")); }

    @Test
    @DisplayName("Test setAvailableSeats")
    void setAvailableSeatsTest() {
        flight.setAvailableSeats("Economy", 200);
        assertEquals(availableSeats.get("Economy"), flight.getAvailableSeats("Economy"));
    }

    @Test
    @DisplayName("Test getFlightTime")
    void getFlightTimeTest() {
        assertEquals(Distance.getFlightTime(SAN, FLL), flight.getFlightTime());
    }

    @Test
    @DisplayName("Test removeSeatFromClass")
    void removeSeatFromClassTest() {
        HashMap<String, Integer> expectedSeats = new HashMap<>();
        expectedSeats.put("First", 4);
        flight.removeSeatFromClass("First");

        assertEquals(expectedSeats.get("First"), flight.getAvailableSeats("First"));
    }

    @Test
    @DisplayName("Test addSeatToClass")
    void addSeatToClassTest() {
        HashMap<String, Integer> expectedSeats = new HashMap<>();
        expectedSeats.put("First", 6);
        flight.addSeatToClass("First");

        assertEquals(expectedSeats.get("First"), flight.getAvailableSeats("First"));
    }

    @Test
    @DisplayName("Test addTicketToFlight")
    void addTicketToFlightTest() {
        List<Ticket> expectedTicketList = new ArrayList<>();
        expectedTicketList.add(userATicket);
        expectedTicketList.add(userBTicket);
        expectedTicketList.add(userCTicket);

        flight.addTicketToFlight(userCTicket);
        assertEquals(expectedTicketList, ticketList);
    }

    @Test
    @DisplayName("Test removeTicketFromFlight")
    void removeTicketFromFlightTest() {
        List<Ticket> expectedTicketList = new ArrayList<>();
        expectedTicketList.add(userATicket);

        flight.removeTicketFromFlight(userBTicket);
        assertEquals(expectedTicketList, ticketList);
    }

    @Test
    @DisplayName("Test getAvailableSeatsList")
    void getAvailableSeatsListTest () {
        HashMap<String, Integer> expectedSeats = new HashMap<>();
        expectedSeats.put("First", 5);
        expectedSeats.put("Economy", 130);

        assertEquals(expectedSeats, flight.getAvailableSeatsList());
    }

    @Test
    @DisplayName("Test getTicketPricesList")
    void getTicketPricesListTest() {
        HashMap<String, Double> expectedTicketPrices = new HashMap<>();
        expectedTicketPrices.put("First", 200.0);
        expectedTicketPrices.put("Economy", 100.0);

        assertEquals(expectedTicketPrices, ticketPrices);
    }

    @Test
    @DisplayName("Test getTicketList")
    void getTicketListTest() {
        List<Ticket> expectedTicketList = new ArrayList<>();
        expectedTicketList.add(userATicket);
        expectedTicketList.add(userBTicket);

        assertEquals(expectedTicketList, ticketList);
    }

    @Test
    @DisplayName("Test createTicketString")
    void createTicketStringTest() {
        assertEquals("[AB1291, AB1012]", flight.createTicketString());
    }

    @Test
    @DisplayName("Test getFlightID")
    void getFlightIDTest() {
        AdminController adminController = new AdminController();
        adminController.addFlight(flight);
        assertEquals(1, flight.getFlightID());
    }
}
