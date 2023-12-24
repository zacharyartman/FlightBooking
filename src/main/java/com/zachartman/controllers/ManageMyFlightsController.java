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
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManageMyFlightsController {

    @FXML
    private VBox flightsContainer;

    @FXML
    private Button signOutButton;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Button backButton;

    private final AdminController adminController = new AdminController();

    @FXML
    public void initialize() {
        Passenger passenger = SessionManager.getCurrentPassenger();
        List<Ticket> ticketList = adminController.getTicketsByPassenger(passenger);
        HashMap<Flight, Ticket> flightTicketHashMap = new HashMap<>();
        for (Ticket ticket : ticketList) {
            flightTicketHashMap.put(adminController.getFlightByFlightID(ticket.getFlight().getFlightID()), ticket);
        }
        populateFlights(flightTicketHashMap);
    }

    @FXML
    public void handleBackButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/zachartman/views/passenger_dashboard_page.fxml"));
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

    public void populateFlights(HashMap<Flight, Ticket> flightTicketHashMap) {
        flightsContainer.getChildren().clear();
        for (Map.Entry<Flight, Ticket> entry : flightTicketHashMap.entrySet()) {
            try {
                Flight flight = entry.getKey();
                Ticket ticket = entry.getValue();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/zachartman/views/flight_pane.fxml"));
                Pane flightPane = loader.load();

                FlightPaneController controller = loader.getController();
                controller.setFlightData(flight, ticket); // Populate pane with flight data

                flightsContainer.getChildren().add(flightPane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
