package edu.usd.models;

public class Airline {
    private String airlineName;

    public Airline(String airlineName) {
        this.airlineName = airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }


    public String getAirlineName() {
        return airlineName;
    }


    @Override
    public String toString() {
        return airlineName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Airline airline = (Airline) obj;
        return (airline.getAirlineName().equals(getAirlineName()));
    }

}
