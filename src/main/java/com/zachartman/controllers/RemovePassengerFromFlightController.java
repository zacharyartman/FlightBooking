package com.zachartman.controllers;

import com.zachartman.models.Flight;
import com.zachartman.models.Passenger;
import com.zachartman.models.Ticket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;
import java.util.Optional;

public class RemovePassengerFromFlightController {
    @FXML
    private Button signOutButton;
    @FXML
    private Button backButton;
    @FXML
    private Button removePassengerFromFlightButton;
    @FXML
    private ComboBox<Flight> flightComboBox;
    @FXML
    private TextField passengerUsernameField;

    private final AdminController adminController = new AdminController();
    private final PassengerController passengerController = new PassengerController();

    @FXML
    public void initialize() {
        List<Flight> flightList = adminController.getFlights();
        flightComboBox.getItems().addAll(flightList);
    }
    @FXML
    public void handleRemovePassengerFromFlightButton(ActionEvent event) {
        Flight flight = flightComboBox.getValue();
        Passenger passenger = adminController.getPassenger(passengerUsernameField.getText());
        List<Passenger> passengerList = adminController.getPassengerListByFlight(flight);

        boolean validPassenger = false;

        Alert alert = null;

        if (passenger != null && flight != null) {
            for (Passenger existingPassengers : passengerList) {
                if (passenger.getUsername().equals(existingPassengers.getUsername())) {
                    validPassenger = true;
                    break;
                }
            }
            if (validPassenger) {
                ButtonType removeButton = new ButtonType("Remove");
                ButtonType cancelButton = new ButtonType("Cancel");

                Alert removePassenger = new Alert(Alert.AlertType.CONFIRMATION);
                removePassenger.setTitle("Remove Passenger?");
                removePassenger.setContentText("Remove " + passenger.getFullName() + " from flight " + flight.getFlightNumber() + " from " + flight.getOriginAirport().getAirportCode() + " to " + flight.getDestinationAirport().getAirportCode() + "?");
                removePassenger.getButtonTypes().setAll(removeButton, cancelButton);
                removePassenger.getDialogPane().getStylesheets().add(getClass().getResource("/com/zachartman/alert.css").toExternalForm());
                Optional<ButtonType> result = removePassenger.showAndWait();

                if (result.isPresent() && result.get() == removeButton) {
                    Ticket ticket = adminController.getTicketByPassenger(passenger, flight);
                    if (adminController.removeTicketFromFlight(flight, ticket)) {
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Passenger Removed");
                        alert.setContentText("Passenger " + passenger.getFullName() + " successfully removed from flight.");
                    }
                }
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Passenger");
                alert.setContentText("Passenger " + passenger.getFullName() + " not found on flight.");
            }
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Passenger Not Removed");
            alert.setContentText("Passenger not removed from flight.");
        }

        if (alert != null) {
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/com/zachartman/alert.css").toExternalForm());
            // used to set the content text to wrap
            Label contentLabel = (Label) alert.getDialogPane().lookup(".content.label");
            if (contentLabel != null) {
                contentLabel.setWrapText(true);
            }
            alert.showAndWait();
        }
    }


    @FXML
    public void handleSignOutButton(ActionEvent event) {
        // go to sign in page
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/zachartman/views/passenger_signin_page.fxml"));
            Parent root = loader.load();

            Stage signInStage = new Stage();
            signInStage.setTitle("Sign In");
            signInStage.setScene(new Scene(root));
            signInStage.setResizable(false);
            signInStage.show();

            ((Stage) signOutButton.getScene().getWindow()).close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleBackButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/zachartman/views/admin_dashboard_page.fxml"));
            Parent root = loader.load();

            Stage signInStage = new Stage();
            signInStage.setTitle("Administrator Dashboard");
            signInStage.setScene(new Scene(root));
            signInStage.setResizable(false);
            signInStage.show();

            ((Stage) backButton.getScene().getWindow()).close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
