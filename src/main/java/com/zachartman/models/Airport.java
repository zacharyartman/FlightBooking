package com.zachartman.models;
import java.util.Arrays;
import java.util.List;
public class Airport {
    private final String airportName;
    private final String airportCode;
    // airport latitude, longitude
    private final double[] airportLocation;

    private List<Flight> flights;

    public Airport(String airportName, String airportCode, double[] airportLocation, List<Flight> flights) {
        this.airportName = airportName;
        this.airportCode = airportCode;
        this.airportLocation = airportLocation;
        this.flights = flights;
    }

    public Airport(String airportName, String airportCode, double[] airportLocation) {
        this.airportName = airportName;
        this.airportCode = airportCode;
        this.airportLocation = airportLocation;
    }

    public String getAirportName() { return airportName; }
    public String getAirportCode() {return airportCode; }

    public double[] getAirportLocation() { return airportLocation; }

    public void addFlight(Flight flight) { flights.add(flight); }

    public void removeFlight(Flight flight) { flights.remove(flight); }

    public List<Flight> getFlights() {
        return flights;
    }

    @Override
    public String toString() {
        return airportName + " [" + airportCode + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Airport airport = (Airport) obj;

        return(airport.getAirportName().equals(getAirportName()) &&
                airport.getAirportCode().equals(getAirportCode()) &&
                Arrays.equals(airport.getAirportLocation(), getAirportLocation())
                );

    }
}