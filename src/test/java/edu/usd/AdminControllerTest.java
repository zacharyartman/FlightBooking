package edu.usd;

import edu.usd.controllers.AdminController;
import edu.usd.models.*;
import edu.usd.utils.DatabaseTablesCreation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class AdminControllerTest {
    private AdminController adminController;
    private Flight flight;
    private Airport departureAirport;
    private Airport destinationAirport;
    private Airline airline;
    private Plane plane;

    @BeforeEach
    void setUp() {
        DatabaseTablesCreation.recreateDatabase(false);

        adminController = new AdminController();

        // Create objects that will be used by adminController
        double SANirportLongitude = -117.196053;
        double SANAirportLatitude = 32.732346;
        departureAirport = new Airport("San Diego Airport", "SAN", new double[]{SANAirportLatitude, SANirportLongitude});

        double FLLAirportLongitude = -80.1506;
        double FLLAirportLatitude = 26.0742;
        destinationAirport = new Airport("Fort Lauderdale Airport", "FLL", new double[]{FLLAirportLatitude, FLLAirportLongitude});

        airline = new Airline("United Airlines");

        plane = new Plane("Boeing", "737", "100", 100, 10, 100.0);

        LocalDateTime departureTime = LocalDateTime.of(2023, 1, 1, 12, 0);

        HashMap<String, Double> ticketPrices = new HashMap<>();
        ticketPrices.put("First", 500.0);
        ticketPrices.put("Economy", 100.0);

        HashMap<String, Integer> availableSeats = new HashMap<>();
        availableSeats.put("First", 10);
        availableSeats.put("Economy", 100);
      
        String flightNumber = "100";
        flight = new Flight(flightNumber, departureAirport, destinationAirport, airline, plane, departureTime, ticketPrices, availableSeats);
    }

    @Test
    @DisplayName("Tests getting and adding flights")
    void getAndAddFlightTest() {
        // create an empty database and add the flight.
        adminController.addAirport(departureAirport);
        adminController.addAirport(destinationAirport);
        adminController.addAirline(airline);
        adminController.addPlane(plane);
        adminController.addFlight(flight);

        List<Flight> returnedFlights = adminController.getFlights();
        List<Flight> expectedFlights = new ArrayList<>();

        expectedFlights.add(flight);
        assertEquals(expectedFlights.size(), returnedFlights.size());

        // Makes sure that each plane object is equal with the overriden equals function.
        for (int i = 0; i < expectedFlights.size(); i++) {
            assertEquals(expectedFlights.get(i), returnedFlights.get(i));
        }
    }

    @Test
    @DisplayName("Tests adding an airport and then getting it")
    void getAndAddAirportTest() {
        adminController.addAirport(destinationAirport);
        List<Airport> resultAirports = adminController.getAirports();
        List<Airport> expectedAirports = new ArrayList<>(Collections.singletonList(destinationAirport));

        assertIterableEquals(resultAirports, expectedAirports);
    }

    @Test
    @DisplayName("Tests removing a flight from the database")
    void removeFlightTest() {
        adminController.addAirport(departureAirport);
        adminController.addAirport(destinationAirport);
        adminController.addAirline(airline);
        adminController.addPlane(plane);

        adminController.addFlight(flight);
        assertEquals(1, adminController.getFlights().size());

        adminController.removeFlight(flight);
        assertEquals(0, adminController.getFlights().size());
    }

    @Test
    @DisplayName("Tests adding and getting a plane")
    void addAndGetPlaneTest() {
        adminController.addPlane(plane);
        List<Plane> expectedPlanes = new ArrayList<>();

        expectedPlanes.add(plane);
        List<Plane> resultPlanes = adminController.getPlanes();

        assertIterableEquals(expectedPlanes, resultPlanes);
    }

    @Test
    @DisplayName("Tests adding and getting airlines")
    void addAndGetAirlinesTest() {
        adminController.addAirline(airline);
        List<Airline> expectedAirlines = new ArrayList<>();

        expectedAirlines.add(airline);
        List<Airline> resultAirlines = adminController.getAirlines();

        assertIterableEquals(expectedAirlines, resultAirlines);
    }

    @Test
    @DisplayName("Tests getAirlinesByUser")
    void getAirlinesByUserTest() {
        adminController.getAdministrators();
        adminController.addAirport(departureAirport);
        adminController.addAirport(destinationAirport);
        adminController.addAirline(airline);
        adminController.addPlane(plane);
        adminController.addFlight(flight);

        adminController.createAdmin("Larry Jones", "larry_jones", "password", airline);
        List<Airline> airlineList = new ArrayList<>();
        Airline airline2 = new Airline("Southwest Airlines");
        airlineList.add(airline);
        airlineList.add(airline2);

        adminController.createAdmin("Multiple Airlines", "multipleairlines", "password", airlineList);
        List<Airline> expectedAirlineList = new ArrayList<>();
        expectedAirlineList.add(airline);

        assertEquals(expectedAirlineList, adminController.getAirlinesByUser("larry_jones"));

        expectedAirlineList.add(airline2);
        assertEquals(expectedAirlineList, adminController.getAirlinesByUser("multipleairlines"));

    }

    @Test
    @DisplayName("Tests stringToStringList")
    void stringToStringListTest() {
        String listString = "[item1, item2, item3]";
        List<String> resultList = AdminController.stringToStringList(listString);

        assertEquals(3, resultList.size());
        assertTrue(resultList.contains("item1"));
        assertTrue(resultList.contains("item2"));
        assertTrue(resultList.contains("item3"));
    }

    @Test
    @DisplayName("Tests stringToHashMapDouble")
    void stringToHashMapDoubleTest() {
        String mapString = "{key1=1.0, key2=2.0";
        HashMap<String, Double> resultMap = AdminController.stringToHashMapDouble(mapString);

        assertEquals(2, resultMap.size());
        assertTrue(resultMap.containsKey("key1"));
        assertTrue(resultMap.containsKey("key2"));
        assertEquals(1.0, resultMap.get("key1"));
        assertEquals(2.0, resultMap.get("key2"));
    }

    @Test
    @DisplayName("Tests stringToHashMapInteger")
    void stringToHashMapIntegerTest() {
        String mapString = "{key1=10, key2=20}";
        HashMap<String, Integer> resultMap = AdminController.stringToHashMapInteger(mapString);

        assertEquals(2, resultMap.size());
        assertTrue(resultMap.containsKey("key1"));
        assertTrue(resultMap.containsKey("key2"));
        assertEquals(Integer.valueOf(10), resultMap.get("key1"));
        assertEquals(Integer.valueOf(20), resultMap.get("key2"));
    }

    @Test
    @DisplayName("Tests getAirlineByName")
    void getAirlineByNameTest() {
        adminController.addAirline(airline);
        assertEquals(airline, adminController.getAirlineByName("United Airlines"));
    }

    @Test
    @DisplayName("Tests getAdministrators")
    void getAdministratorsTest() {
        HashMap<String, String> admins = new HashMap<>();
        admins.put("larry_jones", "password");

        adminController.createAdmin("Larry Jones", "larry_jones", "password", airline);
        assertEquals(admins, adminController.getAdministrators());
    }

    @Test
    @DisplayName("Tests getPassenger")
    void getPassengerTest() {
        boolean isCreated = adminController.createPassenger("John Smith", "john_smith", "password");
        assertTrue(isCreated);

        Passenger passenger = adminController.getPassenger("john_smith");

        assertNotNull(passenger);
        assertEquals("John Smith", passenger.getFullName());
        assertEquals("john_smith", passenger.getUsername());
        assertEquals("password", passenger.getPassword());
    }

    @Test
    @DisplayName("Tests generating and getting confirmation codes")
    void generateAndGetConfirmationCodeTest() {
        String expectedConfirmationCode = adminController.generateConfirmationCode();
        assertEquals(expectedConfirmationCode.getClass(), String.class);
    }

    @Test
    @DisplayName("Tests addTicketToFlight")
    void addTicketToFlightTest() {
        adminController.addAirport(departureAirport);
        adminController.addAirport(destinationAirport);
        adminController.addAirline(airline);
        adminController.addPlane(plane);
        adminController.addFlight(flight);

        Passenger passenger = new Passenger("John Appleseed", "jappleseed", "password");
        Ticket ticket = new Ticket("AB1291", passenger, "First", flight);

        adminController.addTicketToFlight(flight, ticket);
        Ticket resultTicket = flight.getTicketList().get(0);

        assertEquals(ticket, resultTicket);
    }

    @Test
    @DisplayName("Tests removeTicketFromFlight")
    void removeTicketFromFlightTest() {
        adminController.addAirport(departureAirport);
        adminController.addAirport(destinationAirport);
        adminController.addAirline(airline);
        adminController.addPlane(plane);
        adminController.addFlight(flight);

        Passenger passenger = new Passenger("John Appleseed", "jappleseed", "password");
        Ticket ticket = new Ticket("AB1291", passenger, "First", flight);

        adminController.addTicketToFlight(flight, ticket);
        assertTrue(adminController.removeTicketFromFlight(flight, ticket));
    }

    @Test
    @DisplayName("Tests getFlightByFlightID")
    void getFlightByFlightIDTest() {
        adminController.addFlight(flight);
        adminController.addAirline(airline);
        adminController.addAirport(destinationAirport);
        adminController.addAirport(departureAirport);
        adminController.addPlane(plane);

        Flight resultFlight = adminController.getFlightByFlightID(flight.getFlightID());
        assertEquals(flight, resultFlight);
    }

    @Test
    @DisplayName("Tests getTicketByPassenger")
    void getTicketByPassengerTest() {
        adminController.addAirport(departureAirport);
        adminController.addAirport(destinationAirport);
        adminController.addAirline(airline);
        adminController.addPlane(plane);
        adminController.addFlight(flight);

        Passenger passenger = new Passenger("John Appleseed", "jappleseed", "password");
        Ticket ticket = new Ticket("AB1291", passenger, "First", flight);
        adminController.addTicketToFlight(flight, ticket);

        assertEquals(ticket, adminController.getTicketByPassenger(passenger, flight));
    }

    @Test
    @DisplayName("Tests getPassengerListByFlight")
    void getPassengerListByFlightTest() {
        adminController.addAirport(departureAirport);
        adminController.addAirport(destinationAirport);
        adminController.addAirline(airline);
        adminController.addPlane(plane);
        adminController.addFlight(flight);

        adminController.createPassenger("John Appleseed", "jappleseed", "password");

        Passenger passenger = new Passenger("John Appleseed", "jappleseed", "password");
        Ticket ticket = new Ticket("AB1291", passenger, "First", flight);
        adminController.addTicketToFlight(flight, ticket);

        Passenger resultPassenger = adminController.getPassengerListByFlight(flight).get(0);

        assertEquals(passenger, resultPassenger);
    }

    @Test
    @DisplayName("Tests getTicketsByPassenger")
    void getTicketsByPassengerTest() {
        adminController.addAirport(departureAirport);
        adminController.addAirport(destinationAirport);
        adminController.addAirline(airline);
        adminController.addPlane(plane);
        adminController.addFlight(flight);

        Passenger passenger = new Passenger("John Appleseed", "jappleseed", "password");
        Ticket ticket = new Ticket("AB1291", passenger, "First", flight);
        adminController.addTicketToFlight(flight, ticket);

        Ticket resultTicket = adminController.getTicketsByPassenger(passenger).get(0);

        assertEquals(ticket.getPassenger(), resultTicket.getPassenger());
        assertEquals(ticket.getFlight().toString(), resultTicket.getFlight().toString());
        assertEquals(ticket.getConfirmationCode(), resultTicket.getConfirmationCode());
        assertEquals(ticket.getSeatClass(), resultTicket.getSeatClass());
    }
}