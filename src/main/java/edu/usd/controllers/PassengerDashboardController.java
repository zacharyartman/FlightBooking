package edu.usd.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class PassengerDashboardController {

    @FXML
    private Pane searchforflights;

    @FXML
    private Button signOutButton;

    // these 2 functions change the cursor to a hand when hovering over the pane
    @FXML
    private void handleMouseEnter(MouseEvent event) {
        Scene scene = ((Node) event.getSource()).getScene();
        scene.setCursor(Cursor.HAND);
    }

    @FXML
    private void handleMouseExit(MouseEvent event) {
        Scene scene = ((Node) event.getSource()).getScene();
        scene.setCursor(Cursor.DEFAULT);
    }

    @FXML
    void handleManageMyFlights(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/usd/views/manage_my_flights_page.fxml"));
            Parent root = loader.load();

            Stage signInStage = new Stage();
            signInStage.setTitle("Manage My Flights");
            signInStage.setScene(new Scene(root));
            signInStage.setResizable(false);
            signInStage.show();

            ((Stage) searchforflights.getScene().getWindow()).close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleSearchForFlights(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/usd/views/search_for_flights_page.fxml"));
            Parent root = loader.load();

            Stage signInStage = new Stage();
            signInStage.setTitle("Search For Flights");
            signInStage.setScene(new Scene(root));
            signInStage.setResizable(false);
            signInStage.show();

            ((Stage) searchforflights.getScene().getWindow()).close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleSignOutButton(ActionEvent event) {
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
