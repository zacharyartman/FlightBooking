package edu.usd.controllers;

import edu.usd.models.Flight;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RemoveFlightController {

    @FXML
    private Button backButton;

    @FXML
    private ComboBox<String> flightNumberField;

    @FXML
    private Button removeFlightButton;

    @FXML
    private Button signOutButton;

    private final AdminController adminController = new AdminController();

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

    @FXML
    public void initialize() {
        List<Flight> flightList = adminController.getFlights();
        List<String> flightNums = new ArrayList<>();
        for(Flight flight: flightList){
            flightNums.add(flight.getFlightNumber());
        }
        flightNumberField.getItems().addAll(flightNums);
    }

    @FXML
    void handleRemoveFlightButton(ActionEvent event) {
        List<Flight> flightList = adminController.getFlights();
        Object flightValue = flightNumberField.getValue();

        boolean validFlight = false;
        Flight curFlight = null;
        Alert alert = null;

        if(flightValue != null){
            String flightNum = flightValue.toString();
            for(Flight flight: flightList){
                if(flightNum.equals(flight.getFlightNumber())){
                    validFlight = true;
                    curFlight = flight;
                    break;
                }
            }
            if (validFlight) {
                ButtonType removeButton = new ButtonType("Remove");
                ButtonType cancelButton = new ButtonType("Cancel");

                Alert removeFlight = new Alert(Alert.AlertType.CONFIRMATION);
                removeFlight.setTitle("Remove Flight?");
                removeFlight.setContentText("Remove " + flightNum + " from " + curFlight.getOriginAirport().getAirportCode() + " to " + curFlight.getDestinationAirport().getAirportCode() + "?");
                removeFlight.getButtonTypes().setAll(removeButton, cancelButton);
                removeFlight.getDialogPane().getStylesheets().add(getClass().getResource("/edu/usd/alert.css").toExternalForm());
                Optional<ButtonType> result = removeFlight.showAndWait();

                if (result.isPresent() && result.get() == removeButton) {
                    adminController.removeFlight(curFlight);
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Flight Removed");
                        alert.setContentText("Flight " + flightNum + " successfully removed.");
                }
            }
            else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Flight Number");
                alert.setContentText("Flight Number + " + flightNum + " is not valid.");
            }
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Flight Not Removed");
            alert.setContentText("Flight unable to be removed.");
        }

        if (alert != null) {
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/edu/usd/alert.css").toExternalForm());
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

}
