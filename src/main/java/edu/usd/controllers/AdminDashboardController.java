package edu.usd.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AdminDashboardController {

    @FXML
    private Button signOutButton;
    @FXML
    private Button addAirportButton;
    @FXML
    private Button addFlightButton;
    @FXML
    private Button removeFlightButton;
    @FXML
    private Button addPassengerToFlightButton;
    @FXML
    private Button removePassengerFromFlightButton;

    @FXML
    public void handleSignOutButton(ActionEvent event) {
        // go from dashboard page to sign in page
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
    public void handleAddAirportButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/usd/views/add_airport_page.fxml"));
            Parent root = loader.load();

            Stage signInStage = new Stage();
            signInStage.setTitle("Add an Airport");
            signInStage.setScene(new Scene(root));
            signInStage.setResizable(false);
            signInStage.show();

            ((Stage) addAirportButton.getScene().getWindow()).close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleAddFlightButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/usd/views/add_flight_page.fxml"));
            Parent root = loader.load();

            Stage signInStage = new Stage();
            signInStage.setTitle("Add a Flight");
            signInStage.setScene(new Scene(root));
            signInStage.setResizable(false);
            signInStage.show();

            ((Stage) addFlightButton.getScene().getWindow()).close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleRemoveFlightButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/usd/views/remove_flight_page.fxml"));
            Parent root = loader.load();

            Stage signInStage = new Stage();
            signInStage.setTitle("Remove a Flight");
            signInStage.setScene(new Scene(root));
            signInStage.setResizable(false);
            signInStage.show();

            ((Stage) signOutButton.getScene().getWindow()).close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleAddPassengerToFlightButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/usd/views/add_passenger_to_flight_page.fxml"));
            Parent root = loader.load();

            Stage signInStage = new Stage();
            signInStage.setTitle("Add Passenger to a Flight");
            signInStage.setScene(new Scene(root));
            signInStage.setResizable(false);
            signInStage.show();

            ((Stage) addPassengerToFlightButton.getScene().getWindow()).close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleRemovePassengerFromFlightButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/usd/views/remove_passenger_from_flight_page.fxml"));
            Parent root = loader.load();

            Stage signInStage = new Stage();
            signInStage.setTitle("Remove Passenger from a Flight");
            signInStage.setScene(new Scene(root));
            signInStage.setResizable(false);
            signInStage.show();

            ((Stage) removePassengerFromFlightButton.getScene().getWindow()).close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
