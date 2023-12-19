package edu.usd.models;

import edu.usd.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Flight {
    private final String flightNumber;
    private final Airline airline;
    private Plane plane;
    private final Airport originAirport;
    private final Airport destinationAirport;

    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private double flightTime;

    private List<Ticket> ticketList = new ArrayList<>();

    // cost per ticket.
    // Format: ("First" : 500)
    private final HashMap<String, Double> ticketPrices;
    // seats per class. Format: ("First" : 6)
    private final HashMap<String, Integer> availableSeats;

    private Integer flightID;

    public Flight(String flightNumber, Airport originAirport, Airport destinationAirport, Airline airline, Plane plane, LocalDateTime departureTime, List<Ticket> ticketList, HashMap<String, Double> ticketPrices, HashMap<String, Integer> availableSeats) {
        this.flightNumber = flightNumber;
        this.originAirport = originAirport;
        this.destinationAirport = destinationAirport;
        this.airline = airline;
        this.plane = plane;
        this.departureTime = departureTime;
        double flightTime = Distance.getFlightTime(originAirport, destinationAirport);
        int hours = (int) flightTime;
        int minutes = (int) ((flightTime - hours) * 60);
        arrivalTime = departureTime.plusHours(hours).plusMinutes(minutes);
        this.ticketList = ticketList;
        this.ticketPrices = ticketPrices;
        this.availableSeats = availableSeats;
    }

    public Flight(String flightNumber, Airport originAirport, Airport destinationAirport, Airline airline, Plane plane, LocalDateTime departureTime, HashMap<String, Double> ticketPrices, HashMap<String, Integer> availableSeats) {
        this.flightNumber = flightNumber;
        this.originAirport = originAirport;
        this.destinationAirport = destinationAirport;
        this.airline = airline;
        this.plane = plane;
        this.departureTime = departureTime;
        double flightTime = getFlightTime();
        int hours = (int) flightTime;
        int minutes = (int) ((flightTime - hours) * 60);
        arrivalTime = departureTime.plusHours(hours).plusMinutes(minutes);
        this.ticketPrices = ticketPrices;
        this.availableSeats = availableSeats;
        System.out.println(arrivalTime);
    }



    public String getFlightNumber() { return flightNumber; }

    public Airport getOriginAirport() {
        return originAirport;
    }

    public Airport getDestinationAirport() {
        return destinationAirport;
    }

    public double getFlightTime() {
        return generateFlightTime();
    }

    public double generateFlightTime() {
        return Distance.getFlightTime(originAirport, destinationAirport);
    }

    public double getFlightDistance() {
        return Distance.getDistance(originAirport, destinationAirport);
    }

    public Airline getAirline() { return airline; }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    public Plane getPlane() { return plane; }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;

        // and then update arrival time
        int hours = (int) flightTime;
        int minutes = (int) ((flightTime - hours) * 60);
        arrivalTime = departureTime.plusHours(hours).plusMinutes(minutes);
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public LocalDateTime getArrivalTime() {
        int hours = (int) getFlightTime();
        int minutes = (int) ((getFlightTime() - hours) * 60);
        return departureTime.plusHours(hours).plusMinutes(minutes);
    }

    public double getTicketPrice(String flightClass) {
        return ticketPrices.get(flightClass);
    }

    public void setTicketPrice(String flightClass, double ticketPrice) {
        ticketPrices.put(flightClass, ticketPrice);
    }

    public int getAvailableSeats(String flightClass) {
        return availableSeats.get(flightClass);
    }

    public void setAvailableSeats(String flightClass, int availableSeats) {
        this.availableSeats.put(flightClass, availableSeats);
    }

    public void removeSeatFromClass(String flightClass) {
        if (availableSeats.containsKey(flightClass)) {
            int currentSeats = availableSeats.get(flightClass);
            if (currentSeats > 0)
                availableSeats.put(flightClass, currentSeats - 1);
            else
                System.err.println("Not enough seats");
        }
        else
            System.err.println("Invalid flight class");
    }

    public void addSeatToClass(String flightClass) {
        if (availableSeats.containsKey(flightClass)) {
            int currentSeats = availableSeats.get(flightClass);
            if (flightClass.equalsIgnoreCase("First")) {
                if (currentSeats < plane.getFirstClassSeats()) {
                    availableSeats.put(flightClass, currentSeats + 1);
                    System.out.println("Added one seat to first class");
                }
            }
            else if (flightClass.equalsIgnoreCase("Economy")) {
                if (currentSeats < plane.getEconomySeats()) {
                    availableSeats.put(flightClass, currentSeats + 1);
                    System.out.println("Added one seat to Economy");
                }
            }
        }
        else
            System.err.println("Invalid flight class");
    }

    public void addTicketToFlight(Ticket ticket) {
        String seatClass = ticket.getSeatClass();
        removeSeatFromClass(seatClass);
        ticketList.add(ticket);
    }

    public void removeTicketFromFlight(Ticket ticket) {
        String seatClass = ticket.getSeatClass();
        addSeatToClass(seatClass);
        ticketList.remove(ticket);
    }

    public HashMap<String, Integer> getAvailableSeatsList() {
        return availableSeats;
    }

    public HashMap<String, Double> getTicketPricesList() {
        return ticketPrices;
    }

    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public String createTicketString() {
        if (ticketList == null) {
            return "";
        }

        List<String> confirmationNumbers = new ArrayList<>();

        for (Ticket ticket : ticketList) {
            confirmationNumbers.add(ticket.getConfirmationCode());
        }

        return confirmationNumbers.toString();
    }

    public String createTicketString(List<Ticket> tickets) {
        if (tickets == null) {
            return "";
        }

        List<String> confirmationNumbers = new ArrayList<>();

        for (Ticket ticket : tickets) {
            confirmationNumbers.add(ticket.getConfirmationCode());
        }

        return confirmationNumbers.toString();
    }


    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/dd/yy HH:mm");

        String formattedDepartureTime = getDepartureTime().format(formatter);
        String formattedArrivalTime = getArrivalTime().format(formatter);

        return getAirline() + " Flight " + getFlightNumber() + " From " + getOriginAirport().getAirportCode() + " to " + getDestinationAirport().getAirportCode() + " " + formattedDepartureTime + " - " + formattedArrivalTime;
    }

    public Integer getFlightID() {
        String sql =  "SELECT * FROM Flights WHERE flightNumber = ? AND originAirport = ?";

        try(Connection conn = new DatabaseConnection().connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, getFlightNumber());
            pstmt.setString(2, originAirport.getAirportCode());

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                this.flightID = rs.getInt("flightID");
            }
            return flightID;
        }

        catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Flight flight = (Flight) obj;
        return (flight.getOriginAirport().equals(getOriginAirport())) &&
                flight.getDestinationAirport().equals(getDestinationAirport()) &&
                flight.getAirline().equals(getAirline()) &&
                flight.getPlane().equals(getPlane()) &&
                flight.getDepartureTime().equals(getDepartureTime())&&
                flight.getArrivalTime().equals(getArrivalTime()) &&
                flight.getTicketList().equals(ticketList) &&
                flight.getTicketPricesList().equals(getTicketPricesList()) &&
                flight.getAvailableSeatsList().equals(getAvailableSeatsList());
    }

}
