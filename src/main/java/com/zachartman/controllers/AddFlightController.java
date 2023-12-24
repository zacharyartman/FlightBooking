package com.zachartman.controllers;

import com.zachartman.models.Airline;
import com.zachartman.models.Airport;
import com.zachartman.models.Flight;
import com.zachartman.models.Plane;
import com.zachartman.utils.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;

public class AddFlightController {

    @FXML
    private ComboBox<Airline> airlineComboBox;
    @FXML
    private TextField flightNumberField;
    @FXML
    private ComboBox<Airport> departureAirportComboBox;
    @FXML
    private ComboBox<Airport> arrivalAirportComboBox;
    @FXML
    private DatePicker departureDatePicker;
    @FXML
    private TextField departureTimeField;
    @FXML
    private ComboBox<Plane> planeComboBox;
    @FXML
    private TextField firstClassSeatPriceField;
    @FXML
    private TextField economySeatPriceField;
    @FXML
    private Button addFlightButton;
    @FXML
    private Button backButton;
    @FXML
    private Button signOutButton;

    private final AdminController adminController = new AdminController();

    @FXML
    public void initialize() {
        planeComboBox.getItems().addAll(adminController.getPlanes());
        departureAirportComboBox.getItems().addAll(adminController.getAirports());
        arrivalAirportComboBox.getItems().addAll(adminController.getAirports());
        airlineComboBox.getItems().addAll(adminController.getAirlinesByUser(SessionManager.getCurrentAdmin().getUsername()));
    }

    @FXML
    public void handleAddFlightButton(ActionEvent event) {

        Airline airline = airlineComboBox.getValue();
        Airport departureAirport = departureAirportComboBox.getValue();
        Airport arrivalAirport = arrivalAirportComboBox.getValue();
        Plane plane = planeComboBox.getValue();
        LocalDate departureDate = departureDatePicker.getValue();

        Alert alert;

        if (airline != null && departureAirport != null && arrivalAirport != null &&
                plane != null && !flightNumberField.getText().isEmpty() && !departureTimeField.getText().isEmpty()
                && (!economySeatPriceField.getText().isEmpty()) && (!firstClassSeatPriceField.getText().isEmpty()) && departureDate != null) {

            double firstClassSeatPrice = Double.parseDouble(firstClassSeatPriceField.getText());
            double economySeatPrice = Double.parseDouble(economySeatPriceField.getText());
            String flightNumber = flightNumberField.getText();


            // extracts the time from the date picker and time string
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime time = LocalTime.parse(departureTimeField.getText(), timeFormatter);
            LocalDateTime departureTimeValue = LocalDateTime.of(departureDatePicker.getValue(), time);

            // creates ticket prices
            HashMap<String, Double> ticketPrices = new HashMap<>();
            ticketPrices.put("First", firstClassSeatPrice);
            ticketPrices.put("Economy", economySeatPrice);

            //creates available seats hashmap
            HashMap<String, Integer> availableSeats = new HashMap<>();
            availableSeats.put("First", planeComboBox.getValue().getFirstClassSeats());
            availableSeats.put("Economy", planeComboBox.getValue().getEconomySeats());

            Flight flight = new Flight(flightNumber, departureAirport, arrivalAirport,
                    airline, plane, departureTimeValue, Collections.emptyList(), ticketPrices, availableSeats);

            adminController.addFlight(flight);
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Flight Added Successfully");
            alert.setContentText("Flight " + flightNumber + " from " + departureAirport.getAirportCode() + " to " + arrivalAirport.getAirportCode() + " successfully created.");
        }
        else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Flight Not Added");
            alert.setContentText("Flight was not created. Make sure that all fields are complete.");
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
    public void handleSignOutButton(ActionEvent event) {
        // go from dashboard page to sign in page
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
