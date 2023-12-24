package com.zachartman.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;


public class PassengerSignupController {

    @FXML
    private TextField fullNameField;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private final PassengerController passengerController = new PassengerController();

    @FXML
    private Button signUpButton;

    @FXML
    private Button backButton;

    @FXML
    private void handleSignUpAction(ActionEvent event) {
        String fullName = fullNameField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        AdminController adminController = new AdminController();
        Alert alert;
        if (adminController.createPassenger(fullName, username, password)) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Account created Successfully");
            alert.setContentText("Account creation was successful.");

            alert.getDialogPane().getStylesheets().add(getClass().getResource("/com/zachartman/alert.css").toExternalForm());
            Label contentLabel = (Label) alert.getDialogPane().lookup(".content.label");
            if (contentLabel != null) {
                contentLabel.setWrapText(true);
            }
            alert.showAndWait();
            accountCreated();


        }
        else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Account already exists.");
            alert.setContentText("Account creation was unsuccessful.\nAccount already exists.");

            alert.getDialogPane().getStylesheets().add(getClass().getResource("/com/zachartman/alert.css").toExternalForm());
            Label contentLabel = (Label) alert.getDialogPane().lookup(".content.label");
            if (contentLabel != null) {
                contentLabel.setWrapText(true);
            }
            alert.showAndWait();
        }

    }
    @FXML
    private void handleBackButton(ActionEvent event) {
        // go from sign up page to sign in page
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/zachartman/views/passenger_signin_page.fxml"));
            Parent root = loader.load();

            Stage signInStage = new Stage();
            signInStage.setTitle("Sign In");
            signInStage.setScene(new Scene(root));
            signInStage.setResizable(false);
            signInStage.show();

            ((Stage) backButton.getScene().getWindow()).close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * goes from the sign up page to the sign in page
     */
    private void accountCreated() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/zachartman/views/passenger_signin_page.fxml"));
            Parent root = loader.load();

            PassengerLoginController passengerLoginController = loader.getController();
            passengerLoginController.setUsername(usernameField.getText());

            Stage signInStage = new Stage();
            signInStage.setTitle("Sign In");
            signInStage.setScene(new Scene(root));
            signInStage.setResizable(false);
            signInStage.show();


            ((Stage) signUpButton.getScene().getWindow()).close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
