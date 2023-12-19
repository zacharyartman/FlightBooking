package edu.usd.controllers;

import edu.usd.models.Airport;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;

public class AddAirportController {
    @FXML
    private Button backButton;
    @FXML
    private Button signOutButton;
    @FXML
    private Button addAirportButton;
    @FXML
    private TextField airportCodeField;
    @FXML
    private TextField airportNameField;
    @FXML
    private TextField latitudeField;
    @FXML
    private TextField longitudeField;

    private final AdminController adminController = new AdminController();

    @FXML
    public void handleAddAirportButton(ActionEvent event) {
        List<Airport> airportList = adminController.getAirports();

        String airportName = airportNameField.getText();
        String airportCode = airportCodeField.getText();
        String latitude = latitudeField.getText();
        String longitude = longitudeField.getText();

        Alert alert;

        boolean validAirport = true;

        // ensures that the airport does not already exist in the database and that there are no empty parameters.

        for (Airport airport : airportList) {
            if (airport.getAirportCode().equals(airportCode) || airport.getAirportName().equals(airportName)) {
                validAirport = false;
                break;
            }
        }


        if (validAirport && !airportName.equals("") && !airportCode.equals("") && !latitude.equals("") && !longitude.equals("")) {
            Airport airport = new Airport(
                    airportName,
                    airportCode,
                    new double[]{Double.parseDouble(latitude), Double.parseDouble(longitude)}
            );
            adminController.addAirport(airport);
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Airport Added Successfully");
            alert.setContentText("Successfully added " + airportName + ".");
        }
        else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Airport Not Added");
            alert.setContentText("Airport " + airportName + " not added.");
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

    }
