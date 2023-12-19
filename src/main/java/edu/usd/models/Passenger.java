package edu.usd.models;

public class Passenger extends User{

    public Passenger(String fullName, String username, String password) {
        super(fullName, username, password);
    }

    @Override
    public String toString() {
        return getFullName() + ", " + getUsername() + ", " + getPassword();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Passenger passenger = (Passenger) obj;
        return (passenger.getFullName().equals(getFullName()) &&
                passenger.getPassword().equals(getPassword()) &&
                passenger.getUsername().equals(getUsername())
                );
    }
}
