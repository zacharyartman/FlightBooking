package edu.usd.controllers;

import edu.usd.models.Flight;
import edu.usd.models.Passenger;
import edu.usd.models.Ticket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.HashMap;

public class AddPassengerToFlightController {
    @FXML
    private ComboBox<Flight> flightComboBox;
    @FXML
    private ComboBox<String> seatClassComboBox;
    @FXML
    private TextField passengerUsername;
    @FXML
    private Button signOutButton;
    @FXML
    private Button backButton;


    private final AdminController adminController = new AdminController();
    private final PassengerController passengerController = new PassengerController();

    @FXML
    public void initialize() {
        flightComboBox.getItems().addAll(adminController.getFlights());
        seatClassComboBox.getItems().addAll("First", "Economy");
    }

    @FXML
    public void handleAddPassengerToFlightButton(ActionEvent event) {
        Flight flight = flightComboBox.getValue();
        String seatClass = seatClassComboBox.getValue();
        Passenger passenger = adminController.getPassenger(passengerUsername.getText());
        HashMap<String, String> passengerList = passengerController.getPassengers();

        Alert alert;

        if (passenger != null && seatClass != null && flight != null) {
            if (passengerList.containsKey(passenger.getUsername())) {
                Ticket ticket = new Ticket(adminController.generateConfirmationCode(), passenger, seatClass, flight);
                if (adminController.addTicketToFlight(flight, ticket)) {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Passenger Added Successfully");
                    alert.setContentText(passenger.getFullName() + " successfully added to flight " + flight.getFlightNumber() + " from " + flight.getOriginAirport().getAirportCode() + " to " + flight.getDestinationAirport().getAirportCode() + ".");
                }
                else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Passenger Not Added");
                    alert.setContentText(passenger.getFullName() + " not added. Passenger already on flight " + flight.getFlightNumber() + " from " + flight.getOriginAirport().getAirportCode() + " to " + flight.getDestinationAirport().getAirportCode() + ".");
                }
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Passenger");
                alert.setContentText("Passenger not added. Username [" + passengerUsername + "] not found in database.");
            }
        }
        else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Passenger Not Added");
            alert.setContentText("Passenger not added to flight.");
        }

        alert.getDialogPane().getStylesheets().add(getClass().getResource("/edu/usd/alert.css").toExternalForm());
        // used to set the content text to wrap
        Label contentLabel = (Label) alert.getDialogPane().lookup(".content.label");
        if (contentLabel != null) {
            contentLabel.setWrapText(true);
        }
        alert.showAndWait();

    }

    @FXML
    public void handleSignOutButton(ActionEvent event) {
        // go to sign in page
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/usd/views/passenger_signin_page.fxml"));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/usd/views/admin_dashboard_page.fxml"));
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
