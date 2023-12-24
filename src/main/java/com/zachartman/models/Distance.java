package com.zachartman.models;

public class Distance {

    // returns distance in miles
    // based on https://stackoverflow.com/questions/7426710/how-to-find-the-distance-between-two-zipcodes-using-java-code
    public static double getDistance(double originLatitude, double originLongitude, double destinationLatitude, double destinationLongitude) {
        final int R = 6371; // Radius of the earth in kilometers
        double latDistance = Math.toRadians(destinationLatitude - originLatitude);
        double lonDistance = Math.toRadians(destinationLongitude - originLongitude);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(originLatitude)) * Math.cos(Math.toRadians(destinationLatitude))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c; // convert to kilometers
        distance = distance * 0.621371; // convert to miles
        return distance;
    }

    public static double getDistance(Airport originAirport, Airport destinationAirport) {
        double originLatitude = originAirport.getAirportLocation()[0];
        double originLongitude = originAirport.getAirportLocation()[1];
        double destinationLatitude = destinationAirport.getAirportLocation()[0];
        double destinationLongitude = destinationAirport.getAirportLocation()[1];

        return getDistance(originLatitude, originLongitude, destinationLatitude, destinationLongitude);
    }

    public static double getFlightTime(double originLatitude, double originLongitude, double destinationLatitude, double destinationLongitude) {
        double distance = getDistance(originLatitude, originLongitude, destinationLatitude, destinationLongitude);
        return distance / 500; // assume 500mph average
    }

    public static double getFlightTime(Airport originAirport, Airport destinationAirport) {
        double distance = getDistance(originAirport, destinationAirport);
        return distance / 500; // assume 500mph average
    }
}
