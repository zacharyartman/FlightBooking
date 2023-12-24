package com.zachartman.controllers;

import com.zachartman.models.Flight;
import com.zachartman.models.Passenger;
import com.zachartman.models.Ticket;
import com.zachartman.utils.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class FlightPaneController {

    @FXML
    private Label arrivalAirport;

    @FXML
    private Label arrivalTime;

    @FXML
    private Label confirmationCode;

    @FXML
    private Label departureAirport;

    @FXML
    private Label departureTime;

    @FXML
    private Label flightDate;

    @FXML
    private Label flightOriginToDestinationName;

    @FXML
    private Label flightTime;

    @FXML
    private Label planeNameAndModel;

    @FXML
    private Label seatClass;

    private final AdminController adminController = new AdminController();

    public void setFlightData(Flight flight, Ticket ticket) {
        arrivalAirport.setText(flight.getDestinationAirport().getAirportCode());
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("MMM d yyyy");

        arrivalTime.setText(flight.getArrivalTime().format(timeFormatter));
        confirmationCode.setText(ticket.getConfirmationCode());
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
        seatClass.setText(ticket.getSeatClass());
    }

    @FXML
    void cancelFlightButton(ActionEvent event) {
        Passenger passenger = SessionManager.getCurrentPassenger();
        List<Ticket> ticketList = adminController.getTicketsByPassenger(passenger);
        for (Ticket ticket : ticketList) {
            if (ticket.getConfirmationCode().equalsIgnoreCase(confirmationCode.getText())) {
                Flight flight = adminController.getFlightByFlightID(ticket.getFlight().getFlightID());
                ButtonType removeButton = new ButtonType("Yes");
                ButtonType cancelButton = new ButtonType("No");

                Alert removePassenger = new Alert(Alert.AlertType.CONFIRMATION);
                removePassenger.setTitle("Cancel Flight?");
                removePassenger.setContentText("Are you sure you want to cancel flight " +  flight.getFlightNumber() + " from " + flight.getOriginAirport().getAirportCode() + " to " + flight.getDestinationAirport().getAirportCode() + "?");
                removePassenger.getButtonTypes().setAll(removeButton, cancelButton);
                removePassenger.getDialogPane().getStylesheets().add(getClass().getResource("/com/zachartman/alert.css").toExternalForm());
                Optional<ButtonType> result = removePassenger.showAndWait();

                if (result.isPresent() && result.get() == removeButton) {
                    if (adminController.removeTicketFromFlight(flight, ticket)) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Your flight has been cancelled.");
                        alert.setContentText("Your flight was successfully cancelled.");
                        alert.getDialogPane().getStylesheets().add(getClass().getResource("/com/zachartman/alert.css").toExternalForm());
                        // used to set the content text to wrap
                        Label contentLabel = (Label) alert.getDialogPane().lookup(".content.label");
                        if (contentLabel != null) {
                            contentLabel.setWrapText(true);
                        }
                        alert.showAndWait();

                        // reload the page to remove the canceled flight.
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/zachartman/views/manage_my_flights_page.fxml"));
                            Parent root = loader.load();

                            Stage signInStage = new Stage();
                            signInStage.setTitle("Manage My Flights");
                            signInStage.setScene(new Scene(root));
                            signInStage.setResizable(false);
                            signInStage.show();

                            ((Stage) departureTime.getScene().getWindow()).close();
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                break;
                }

            }
        }
    }

    @FXML
    void modifyFlightButton(ActionEvent event) {
        // TODO: POSSIBLY ADD ABILITY TO MODIFY FLIGHT, MAYBE JUST UPGRADE TO FIRST CLASS OPTION
    }

}
