package com.zachartman.controllers;

import com.zachartman.models.Flight;
import com.zachartman.models.Passenger;
import com.zachartman.models.Ticket;
import com.zachartman.utils.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class SearchForFlightPaneController {

    @FXML
    private Label arrivalAirport;

    @FXML
    private Label arrivalTime;

    @FXML
    private Button bookEconomyButton;

    @FXML
    private Button bookFirstClassButton;

    @FXML
    private Label departureAirport;

    @FXML
    private Label departureTime;

    @FXML
    private Label economyPrice;

    @FXML
    private Label firstClassPrice;

    @FXML
    private Label flightDate;

    @FXML
    private Label flightOriginToDestinationName;

    @FXML
    private Label flightTime;

    @FXML
    private Label planeNameAndModel;

    private Flight flight;

    private final AdminController adminController = new AdminController();

    @FXML
    void handleBookEconomyButton(ActionEvent event) {
        Passenger passenger = SessionManager.getCurrentPassenger();
        Ticket ticket = new Ticket(adminController.generateConfirmationCode(), passenger, "Economy", flight);
        Alert alert;
        if (adminController.addTicketToFlight(flight, ticket)) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Flight Booked Successfully");
            alert.setContentText("Successfully booked 1 economy seat from " + flight.getOriginAirport().getAirportCode() + " to \n" + flight.getDestinationAirport().getAirportCode());
        }
        else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Flight Not Booked");
            alert.setContentText("Error booking flight.");
        }

        alert.getDialogPane().getStylesheets().add(getClass().getResource("/com/zachartman/alert.css").toExternalForm());
        // used to set the content text to wrap
        Label contentLabel = (Label) alert.getDialogPane().lookup(".content.label");
        if (contentLabel != null) {
            contentLabel.setWrapText(true);
        }
        alert.showAndWait();
    }

    @FXML
    void handleBookFirstClassButton(ActionEvent event) {
        Passenger passenger = SessionManager.getCurrentPassenger();
        Ticket ticket = new Ticket(adminController.generateConfirmationCode(), passenger, "First", flight);
        Alert alert;
        if (adminController.addTicketToFlight(flight, ticket)) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Flight Booked Successfully");
            alert.setContentText("Successfully booked 1 first class seat from " + flight.getOriginAirport().getAirportCode() + " to \n" + flight.getDestinationAirport().getAirportCode());
        }
        else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Flight Not Booked");
            alert.setContentText("Error booking flight.");
        }

        alert.getDialogPane().getStylesheets().add(getClass().getResource("/com/zachartman/alert.css").toExternalForm());
        // used to set the content text to wrap
        Label contentLabel = (Label) alert.getDialogPane().lookup(".content.label");
        if (contentLabel != null) {
            contentLabel.setWrapText(true);
        }
        alert.showAndWait();
    }


    /**
     * Populates the flight pane to have all the proper information
     * @param flight the flight to set the pane to
     */
    public void setFlightData(Flight flight) {
        arrivalAirport.setText(flight.getDestinationAirport().getAirportCode());
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("MMM d yyyy");

        arrivalTime.setText(flight.getArrivalTime().format(timeFormatter));
        departureAirport.setText(flight.getOriginAirport().getAirportCode());
        departureTime.setText(flight.getDepartureTime().format(timeFormatter));
        flightDate.setText(flight.getDepartureTime().format(dayFormatter));
        flightOriginToDestinationName.setText(flight.getOriginAirport().getAirportName() + " to " + flight.getDestinationAirport().getAirportName());
        DateTimeFormatter flightTimeFormatter = DateTimeFormatter.ofPattern("HH'h' mm'm'");

        // creating time from the double flightTime
        int hours = (int) flight.getFlightTime();
        int minutes = (int) ((flight.getFlightTime() - hours) * 60);
        LocalTime flightTimeObject = LocalTime.of(hours, minutes);
        flightTime.setText(flightTimeObject.format(flightTimeFormatter));
        System.out.println(flight.getFlightTime());
        planeNameAndModel.setText(flight.getPlane().getPlaneMake() + " " + flight.getPlane().getPlaneModel());
        firstClassPrice.setText("$" + flight.getTicketPrice("First"));
        economyPrice.setText("$" + flight.getTicketPrice("Economy"));

        this.flight = flight;
    }


}
