package edu.usd.controllers;
import edu.usd.models.*;
import edu.usd.utils.DatabaseConnection;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
// This class is used for most of the controller classes and is what gets results from the database and inserts values
// into the database.
public class AdminController {


    /**
     * Gets all flights from the database
     * @return a list of flights
     */
    public List<Flight> getFlights() {
        List<Flight> flights = new ArrayList<>();
        String sql = "SELECT * from Flights";

        try (Connection conn = new DatabaseConnection().connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String originAirportCode = rs.getString("originAirport");
                List<Airport> airports = getAirports();
                Airport originAirport = findAirportByCode(airports, originAirportCode);

                String destinationAirportCode = rs.getString("destinationAirport");
                Airport destinationAirport = findAirportByCode(airports, destinationAirportCode);

                String airlineName = rs.getString("airline");
                Airline airline = getAirlineByName(airlineName);


                String planeRegistrationNumber = rs.getString("planeRegistrationNumber");
                List<Plane> planes = getPlanes();
                Plane plane = findPlaneByRegistrationNumber(planes, planeRegistrationNumber);

                String departureTime = rs.getString("departureTime");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime departureTimeObject = LocalDateTime.parse(departureTime, formatter);

                String ticketPrices = rs.getString("ticketPrices");
                HashMap<String, Double> ticketPricesObject = stringToHashMapDouble(ticketPrices);

                String availableSeats = rs.getString("availableSeats");
                HashMap<String, Integer> availableSeatsObject = stringToHashMapInteger(availableSeats);


                Flight flight = new Flight(
                    rs.getString("flightNumber"),
                    originAirport,
                    destinationAirport,
                    airline,
                    plane,
                    departureTimeObject,
                    ticketPricesObject,
                    availableSeatsObject
                );

                flights.add(flight);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return flights;
    }

    /**
     * Adds a passenger to the database
     * @param fullName user's full name
     * @param username user's username
     * @param password user's password
     * @return true if passenger was created with no errors else false
     */
     public boolean createPassenger(String fullName, String username, String password) {
        PassengerController passengerController = new PassengerController();
        HashMap<String, String> existingUsers = passengerController.getPassengers();

        if (!existingUsers.containsKey(username) && !username.equals("") && !password.equals("") && !fullName.equals("")) {
            String sql = "INSERT INTO Passengers VALUES (?, ?, ?);";

            try (Connection conn = new DatabaseConnection().connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, fullName);
                pstmt.setString(2, username);
                pstmt.setString(3, password);

                pstmt.executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Adds an administrator to the database
     * @param fullName the admin's full name
     * @param username the admin's username
     * @param password the admin's password
     * @param airline the airlines that the admin has access to
     * @return true if the administrator was created successfully else false
     */
    public boolean createAdmin(String fullName, String username, String password, Airline airline) {
        AdminController adminController = new AdminController();
        HashMap<String, String> existingUsers = adminController.getAdministrators();

        if (!existingUsers.containsKey(username) && !username.equals("") && !password.equals("") && !fullName.equals("")) {
            String sql = "INSERT INTO Administrators VALUES (?, ?, ?, ?);";

            try (Connection conn = new DatabaseConnection().connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, fullName);
                pstmt.setString(2, username);
                pstmt.setString(3, password);
                pstmt.setString(4, airline.getAirlineName());

                pstmt.executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Adds an administrator to the database
     * @param fullName the admin's full name
     * @param username the admin's username
     * @param password the admin's password
     * @param airlineList a list of airlines that the administrator can access
     * @return true if the administrator was created successfully else false
     */

    public boolean createAdmin(String fullName, String username, String password, List<Airline> airlineList) {
        AdminController adminController = new AdminController();
        HashMap<String, String> existingUsers = adminController.getAdministrators();

        if (!existingUsers.containsKey(username) && !username.equals("") && !password.equals("") && !fullName.equals("")) {
            String sql = "INSERT INTO Administrators VALUES (?, ?, ?, ?);";

            try (Connection conn = new DatabaseConnection().connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, fullName);
                pstmt.setString(2, username);
                pstmt.setString(3, password);
                pstmt.setString(4, airlineList.toString());

                pstmt.executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * gets all the tickets for a flight
     * @param flightID ID of the flight, can use flight.getFlightID() to get
     * @return a list of tickets for a flight
     */

    private List<Ticket> getTicketListByFlightID(Integer flightID) {
        String sql = "SELECT * FROM tickets WHERE flightID = ?";
        List<Ticket> ticketList = new ArrayList<>();

        Flight flight = getFlightByFlightID(flightID);

        try (Connection conn = new DatabaseConnection().connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, flightID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Passenger passenger = getPassenger(stringToStringList(rs.getString("passenger")).get(1));
                Ticket ticket = new Ticket(rs.getString("confirmationCode"), passenger, rs.getString("seatClass"), flight);
                ticketList.add(ticket);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ticketList;
    }

    /**
     * gets an airport object by the airport code
     * @param airports a list of airports
     * @param code the airport code
     * @return an airport object
     */
    private Airport findAirportByCode(List<Airport> airports, String code) {
        for (Airport airport : airports) {
            if (airport.getAirportCode().equals(code)) {
                return airport;
            }
        }
        return null; // no airport found
    }


    /**
     * adds a flight to the database
     * @param flight the flight object to be added to the database
     */
    public void addFlight(Flight flight) {

        String sql = "INSERT INTO Flights (flightNumber, originAirport, destinationAirport," +
                "airline, plane, planeRegistrationNumber, departureTime, arrivalTime, ticketList, ticketPrices, availableSeats)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String formattedDepartureTime = flight.getDepartureTime().format(formatter);
        String formattedArrivalTime = flight.getArrivalTime().format(formatter);


        try (Connection conn = new DatabaseConnection().connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, flight.getFlightNumber());
            pstmt.setString(2, flight.getOriginAirport().getAirportCode());
            pstmt.setString(3, flight.getDestinationAirport().getAirportCode());
            pstmt.setString(4, flight.getAirline().getAirlineName());
            pstmt.setString(5, flight.getPlane().getPlaneMake() + " " + flight.getPlane().getPlaneModel());
            pstmt.setString(6, flight.getPlane().getRegistrationNumber());
            pstmt.setString(7, formattedDepartureTime);
            pstmt.setString(8, formattedArrivalTime);
            pstmt.setString(9, flight.createTicketString());
            pstmt.setString(10, flight.getTicketPricesList().toString());
            pstmt.setString(11, flight.getAvailableSeatsList().toString());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * adds an airport to the database
     * @param airport the airport object to be added
     */
    public void addAirport(Airport airport) {
        String sql = "INSERT INTO Airports (airportName, airportCode, airportLatitude, airportLongitude) VALUES (?, ?, ?, ?)";

        try(Connection conn = new DatabaseConnection().connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, airport.getAirportName());
            pstmt.setString(2, airport.getAirportCode());
            pstmt.setDouble(3, airport.getAirportLocation()[0]);
            pstmt.setDouble(4, airport.getAirportLocation()[1]);

            pstmt.executeUpdate();
        }

        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * gets all airports in the database
     * @return a list of airports
     */

    public List<Airport> getAirports() {

        List<Airport> airportList = new ArrayList<>();

        String sql = "SELECT * from Airports";

        try (Connection conn = new DatabaseConnection().connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Airport airport = new Airport(
                        rs.getString("airportName"),
                        rs.getString("airportCode"),
                        new double[]{rs.getDouble("airportLatitude"), rs.getDouble("airportLongitude")});
                airportList.add(airport);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return airportList;
    }

    /**
     * removes a flight from the database and the tickets associated with it
     * @param flight the flight to be removed
     */
    public void removeFlight(Flight flight) {

        // delete tickets
        String deleteTicketsSql = "DELETE FROM tickets WHERE flightID = ?";
        try (Connection conn = new DatabaseConnection().connect();
             PreparedStatement pstmt = conn.prepareStatement(deleteTicketsSql)) {

            pstmt.setInt(1, flight.getFlightID());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        // Then, delete the flight
        String deleteFlightSql = "DELETE FROM Flights WHERE flightID = ?";
        try (Connection conn = new DatabaseConnection().connect();
             PreparedStatement pstmt = conn.prepareStatement(deleteFlightSql)) {

            pstmt.setInt(1, flight.getFlightID());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    /**
     * adds a plane to the database
     * @param plane the plane to be added
     */
    public void addPlane(Plane plane) {
        String sql = "INSERT INTO Planes (planeMake, planeModel, registrationNumber, economySeats, firstClassSeats, storageCapacity) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try(Connection conn = new DatabaseConnection().connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, plane.getPlaneMake());
            pstmt.setString(2, plane.getPlaneModel());
            pstmt.setString(3, plane.getRegistrationNumber());
            pstmt.setInt(4, plane.getEconomySeats());
            pstmt.setInt(5, plane.getFirstClassSeats());
            pstmt.setDouble(6, plane.getStorageCapacity());

            pstmt.executeUpdate();

        }

        catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * gets all planes in the database
     * @return a list of planes
     */
    public List<Plane> getPlanes() {
        List<Plane> planeList = new ArrayList<>();

        String sql = "SELECT * from Planes";

        try (Connection conn = new DatabaseConnection().connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Plane plane = new Plane(
                    rs.getString("planeMake"),
                    rs.getString("planeModel"),
                    rs.getString("registrationNumber"),
                    rs.getInt("economySeats"),
                    rs.getInt("firstClassSeats"),
                    rs.getDouble("storageCapacity"));

                planeList.add(plane);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return planeList;
    }

    /**
     * finds a plane object by its registration number
     * @param planes a list of all the planes
     * @param registrationNumber the registration number of the desired plane
     * @return a plane object
     */
    private Plane findPlaneByRegistrationNumber(List<Plane> planes, String registrationNumber) {
        for (Plane plane : planes) {
            if (plane.getRegistrationNumber().equals(registrationNumber)) {
                return plane;
            }
        }
        return null; // no matching plane found
    }

    /**
     * Adds an airline to the database
     * @param airline the airline to be added
     */
    public void addAirline(Airline airline) {
        String sql = "INSERT INTO Airlines (airlineName) VALUES (?)";

        try(Connection conn = new DatabaseConnection().connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, airline.getAirlineName());

            pstmt.executeUpdate();
        }

        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * gets all the airlines in the database
     * @return a list of airlines
     */
    public List<Airline> getAirlines() {
        List<Airline> airlineList = new ArrayList<>();

        String sql = "SELECT * from Airlines";

        try (Connection conn = new DatabaseConnection().connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                Airline airline = new Airline(
                        rs.getString("airlineName")
                );

                airlineList.add(airline);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return airlineList;
    }

    /**
     * returns all airlines that a user can access
     * @param username the username of the administrator
     * @return a list of airlines
     */
    public List<Airline> getAirlinesByUser(String username) {
        List<Airline> airlineList = new ArrayList<>();

        String sql = "SELECT * FROM administrators WHERE username LIKE ?";

        try(Connection conn = new DatabaseConnection().connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
                String airlineNameString = rs.getString("airlineName");
                List<String> stringAirlineList = stringToStringList(airlineNameString);
                for (String airlineName : stringAirlineList) {
                    Airline airline = new Airline(airlineName);
                    airlineList.add(airline);
                    // adds airline to database if it isn't there.
                    if (!getAirlines().contains(airline)) {
                        addAirline(airline);
                    }
                }
            }
        }

        catch (SQLException e) {
            e.printStackTrace();
        }

        return airlineList;
    }

    /**
     * gets all the administrators
     * @return a HashMap in the format <Username: Password>
     */
    public HashMap<String, String> getAdministrators() {
        HashMap<String, String> usernamePassword = new HashMap<>();
        String sql = "SELECT * from Administrators";

        try (Connection conn = new DatabaseConnection().connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                usernamePassword.put(username, password);
            }
        }

        catch(SQLException e) {
            e.printStackTrace();
        }
        return usernamePassword;
    }

    /**
     * converts a string like [1, 2] into an actual list object
     * @param listString the string of the list
     * @return a list of the string
     */
    public static List<String> stringToStringList(String listString) {
        if (listString == null || listString.isEmpty()) {
            return Collections.emptyList();
        }

        // remove brackets if list otherwise return list of one item.
        if (listString.startsWith("[") && listString.endsWith("]")) {
            listString = listString.substring(1, listString.length() - 1);
        }
        return Arrays.stream(listString.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
    }

    /**
     * converts a string of a hashmap to an actual hashmap object
     * @param mapString a hashmap in the form of a string
     * @return a hashmap
     */
    public static HashMap<String, Double> stringToHashMapDouble(String mapString) {
        HashMap<String, Double> map = new HashMap<>();

        if (mapString == null || mapString.isEmpty()) {
            return map;
        }

        mapString = mapString.substring(1, mapString.length() - 1);

        String[] entries = mapString.split(",");
        for (String entry : entries) {
            String[] keyValue = entry.split("=");
            String key = keyValue[0].trim();
            Double value = Double.parseDouble(keyValue[1].trim());
            map.put(key, value);
        }
        return map;
    }

    /**
     * converts a string of a hashmap to an actual hashmap object
     * @param mapString a hashmap in the form of a string
     * @return a hashmap
     */
    public static HashMap<String, Integer> stringToHashMapInteger(String mapString) {
        HashMap<String, Integer> map = new HashMap<>();

        if (mapString == null || mapString.isEmpty()) {
            return map;
        }

        mapString = mapString.substring(1, mapString.length() - 1);

        String[] entries = mapString.split(",");
        for (String entry : entries) {
            String[] keyValue = entry.split("=");
            String key = keyValue[0].trim();
            Integer value = Integer.parseInt(keyValue[1].trim());
            map.put(key, value);
        }
        return map;
    }

    /**
     * gets the airline object by the airline name
     * @param airlineName name of the airline
     * @return airline object
     */
    public Airline getAirlineByName(String airlineName) {
        List<Airline> airlines = getAirlines();

        for (Airline airline : airlines) {
            if (airline.getAirlineName().equalsIgnoreCase(airlineName))
                return airline;
        }

        return null;
    }

    /**
     * gets the passenger object by username
     * @param username passenger's username
     * @return passenger object
     */
    public Passenger getPassenger(String username) {
        String sql = "SELECT * FROM passengers WHERE username LIKE ?";

        try(Connection conn = new DatabaseConnection().connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                return new Passenger(rs.getString("fullName"), rs.getString("username"), rs.getString("password"));
            }
        }

        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * creates a unique 6 digit confirmation code
     * @return 6 digit confirmation code
     */
    public String generateConfirmationCode() {
        List<String> confirmationCodes = getConfirmationCodes();
        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        Random random = new SecureRandom();

        String confirmationCode;

        do {
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < 6; i++) {
                sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
            }
            confirmationCode = sb.toString();
        } while(confirmationCodes.contains(confirmationCode));

        return confirmationCode;
    }

    /**
     * Adds a ticket to the flight
     * @param flight the flight to add the ticket to
     * @param ticket the ticket
     * @return true if the ticket was added successfully else false
     */
    public boolean addTicketToFlight(Flight flight, Ticket ticket) {
        List<Ticket> ticketList = getTicketListByFlightID(flight.getFlightID());
        // if the passenger is already on the flight
        for (Ticket existingTicket : ticketList) {
            if (existingTicket.getPassenger().getUsername().equals(ticket.getPassenger().getUsername())) {
                return false;
            }
        }

        String sql = "INSERT INTO tickets (confirmationCode, passenger, seatClass, flightID) VALUES (?, ?, ?, ?)";

        try(Connection conn = new DatabaseConnection().connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, ticket.getConfirmationCode());
            pstmt.setString(2, ticket.getPassenger().toString());
            pstmt.setString(3, ticket.getSeatClass());
            pstmt.setInt(4, flight.getFlightID());

            pstmt.executeUpdate();

            flight.addTicketToFlight(ticket);
            ticketList.add(ticket);
            String newTicketList = flight.createTicketString(ticketList);


            // change the ticket list of the flight
            updateFlightTicketList(flight.getFlightID(), newTicketList, flight.getAvailableSeatsList().toString());

            return true;
        }

        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * removes a ticket from a flight
     * @param flight the flight to remove the ticket from
     * @param ticket the ticket
     * @return true if the ticket was successfully removed
     */
    public boolean removeTicketFromFlight(Flight flight, Ticket ticket) {
        String sql = "DELETE FROM tickets WHERE confirmationCode = ?";
            try(Connection conn = new DatabaseConnection().connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, ticket.getConfirmationCode());

                pstmt.executeUpdate();

                flight.removeTicketFromFlight(ticket);

                List<Ticket> ticketList = getTicketListByFlightID(flight.getFlightID());
                ticketList.remove(ticket);
                String newTicketList = flight.createTicketString(ticketList);

                // change the ticket list of the flight
                updateFlightTicketList(flight.getFlightID(), newTicketList, flight.getAvailableSeatsList().toString());

                return true;
            }

            catch (SQLException e) {
                e.printStackTrace();
            }
        return false;
    }

    /**
     * gets a flight object by its flight id
     * @param flightID the flight's id, from .getFlightID()
     * @return the flight object
     */
    public Flight getFlightByFlightID (Integer flightID) {
        String sql = "SELECT * from Flights WHERE flightID = ?";

        try (Connection conn = new DatabaseConnection().connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, flightID);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String originAirportCode = rs.getString("originAirport");
                List<Airport> airports = getAirports();
                Airport originAirport = findAirportByCode(airports, originAirportCode);

                String destinationAirportCode = rs.getString("destinationAirport");
                Airport destinationAirport = findAirportByCode(airports, destinationAirportCode);

                String airlineName = rs.getString("airline");
                Airline airline = getAirlineByName(airlineName);

                String planeRegistrationNumber = rs.getString("planeRegistrationNumber");
                List<Plane> planes = getPlanes();
                Plane plane = findPlaneByRegistrationNumber(planes, planeRegistrationNumber);

                String departureTime = rs.getString("departureTime");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime departureTimeObject = LocalDateTime.parse(departureTime, formatter);

                String ticketPrices = rs.getString("ticketPrices");
                HashMap<String, Double> ticketPricesObject = stringToHashMapDouble(ticketPrices);

                String availableSeats = rs.getString("availableSeats");
                HashMap<String, Integer> availableSeatsObject = stringToHashMapInteger(availableSeats);

                return new Flight(
                        rs.getString("flightNumber"),
                        originAirport,
                        destinationAirport,
                        airline,
                        plane,
                        departureTimeObject,
                        ticketPricesObject,
                        availableSeatsObject
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    /**
     * updates the ticket list for a flight with a new string of ticket lists and available seats
     * @param flightID the flight's ID, from .getFlightID()
     * @param newTicketList the ticket list, converted to a string
     * @param availableSeats the available seats list
     */
    public void updateFlightTicketList(Integer flightID, String newTicketList, String availableSeats) {
        String sql = "UPDATE Flights SET ticketList = ?, availableSeats = ? WHERE flightID = ?";

        try (Connection conn = new DatabaseConnection().connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newTicketList);
            pstmt.setString(2, availableSeats);
            pstmt.setInt(3, flightID);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * gets the ticket for a specified passenger
     * @param passenger the passenger
     * @param flight the flight the ticket is on
     * @return the Ticket if there is one else null
     */
    public Ticket getTicketByPassenger(Passenger passenger, Flight flight) {
        String sql = "SELECT * FROM tickets WHERE passenger = ? AND flightID = ?";

        try (Connection conn = new DatabaseConnection().connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, passenger.toString());
            pstmt.setInt(2, flight.getFlightID());

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String confirmationCode = rs.getString("confirmationCode");
                String seatClass = rs.getString("seatClass");
                return new Ticket(confirmationCode, passenger, seatClass, flight);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * gets a list of passengers by flight
     * @param flight the flight
     * @return a list of passenger objects
     */
    public List<Passenger> getPassengerListByFlight(Flight flight) {
        if (flight != null) {
            List<Passenger> passengerList = new ArrayList<>();
            List<Ticket> ticketList = getTicketListByFlightID(flight.getFlightID());
            for (Ticket ticket : ticketList) {
                Passenger passenger = ticket.getPassenger();
                passengerList.add(passenger);
            }
            return passengerList;
        }
        return null;
    }

    /**
     * gets all tickets for a passenger
     * @param passenger the passenger
     * @return a list of ticket objects
     */
    public List<Ticket> getTicketsByPassenger(Passenger passenger) {

        List<Ticket> ticketList = new ArrayList<>();

        String sql = "SELECT * FROM tickets WHERE passenger = ?";

        try (Connection conn = new DatabaseConnection().connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, passenger.toString());

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String confirmationCode = rs.getString("confirmationCode");
                String seatClass = rs.getString("seatClass");
                Flight flight = getFlightByFlightID(rs.getInt("flightID"));
                ticketList.add(new Ticket(confirmationCode, passenger, seatClass, flight));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ticketList;
    }

    /**
     * gets all confirmation codes, used to make sure new confirmation codes are unique
     * @return a list of confirmation code strings
     */
    public List<String> getConfirmationCodes() {
        List<String> confirmationCodes = new ArrayList<>();

        String sql = "SELECT * FROM tickets";

        try (Connection conn = new DatabaseConnection().connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String confirmationCode = rs.getString("confirmationCode");
                confirmationCodes.add(confirmationCode);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return confirmationCodes;
    }
}