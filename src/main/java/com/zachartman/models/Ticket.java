package com.zachartman.models;

public class Ticket {
    private String confirmationCode;
    private final Flight flight;
    private String seatClass;
    private final Passenger passenger;

    public Ticket(String confirmationCode, Passenger passenger, String seatClass, Flight flight) {
        this.confirmationCode = confirmationCode;
        this.passenger = passenger;
        this.seatClass = seatClass;
        this.flight = flight;
    }

    public void setConfirmationCode(String confirmationCode) { this.confirmationCode = confirmationCode; }
    public String getConfirmationCode() { return confirmationCode; }

    public void setSeatClass(String seatClass) {
        this.seatClass = seatClass;
        flight.removeSeatFromClass(seatClass);
        flight.addSeatToClass(seatClass);
    }
    public String getSeatClass() { return seatClass; }

    public Flight getFlight() { return flight; }

    public Passenger getPassenger() {
        return passenger;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Ticket ticket = (Ticket) obj;
        return(ticket.getConfirmationCode().equals(getConfirmationCode()) &&
                ticket.getPassenger().equals(getPassenger()) &&
                ticket.getFlight().equals(getFlight()) &&
                ticket.getSeatClass().equals(getSeatClass())
                );
    }
}