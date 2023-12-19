package edu.usd.controllers;

import edu.usd.models.Airport;
import edu.usd.models.Flight;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class SearchForFlightsController {

    @FXML
    private Button backButton;

    @FXML
    private DatePicker dateField;

    @FXML
    private ComboBox<Airport> departureAirportComboBox;

    @FXML
    private ComboBox<Airport> destinationAirportComboBox;

    @FXML
    private VBox flightsContainer;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Button searchForFlightsButton;

    @FXML
    private Button signOutButton;

    private final AdminController adminController = new AdminController();

    @FXML
    public void initialize() {
        departureAirportComboBox.getItems().addAll(adminController.getAirports());
        destinationAirportComboBox.getItems().addAll(adminController.getAirports());
    }

    @FXML
    public void handleBackButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/usd/views/passenger_dashboard_page.fxml"));
            Parent root = loader.load();

            Stage signInStage = new Stage();
            signInStage.setTitle("Passenger Dashboard");
            signInStage.setScene(new Scene(root));
            signInStage.setResizable(false);
            signInStage.show();

            ((Stage) backButton.getScene().getWindow()).close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    void handleSignOutButton(ActionEvent event) {
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
    void handleSearchForFlightsButton(ActionEvent event) {
        flightsContainer.getChildren().clear();
        List<Flight> matchingFlights = findMatchingFlights(departureAirportComboBox.getValue(), destinationAirportComboBox.getValue(), dateField.getValue());
        if(matchingFlights != null) {
            for (Flight flight : matchingFlights) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/usd/views/search_for_flight_pane.fxml"));
                    Pane flightPane = loader.load();

                    SearchForFlightPaneController controller = loader.getController();
                    controller.setFlightData(flight); // Populate pane with flight data

                    flightsContainer.getChildren().add(flightPane);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Used as the search algorithm for finding matching flights
     * @param departureAirport the desired departure airport
     * @param destinationAirport the desired destination airport
     * @param date the desired date
     * @return a list of flight objects matching the criteria
     */
    public List<Flight> findMatchingFlights(Airport departureAirport, Airport destinationAirport, LocalDate date) {
        List<Flight> matchingFlights = adminController.getFlights();

        if (departureAirport != null) {
            matchingFlights.removeIf(flight -> !(flight.getOriginAirport().equals(departureAirport)));
        }
        if (destinationAirport != null) {
            matchingFlights.removeIf(flight -> !(flight.getDestinationAirport().equals(destinationAirport)));
        }
        if (date != null) {
            matchingFlights.removeIf(flight -> !(flight.getDepartureTime().toLocalDate().equals(date)));
        }

        if(matchingFlights.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Flights Found");
            alert.setContentText("No flights found");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/edu/usd/alert.css").toExternalForm());

            alert.showAndWait();
            return null;
        }
        return matchingFlights;
    }

}
