package edu.usd.controllers;

import edu.usd.models.Passenger;
import edu.usd.utils.DatabaseConnection;
import edu.usd.utils.SessionManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PassengerLoginController {

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameTextField;

    @FXML
    private Button signInButton;

    @FXML
    private Button signUpButton;

    @FXML
    private Button forgotPasswordButton;

    @FXML
    private ImageView backButton;
    @FXML
    private Button passengerButton;
    @FXML
    private Button administratorButton;
    @FXML
    private Label invalidPasswordLabel;


    @FXML
    private void initialize() {
        PassengerController passengerController = new PassengerController();
        invalidPasswordLabel.setVisible(false);
    }

    @FXML
    public void handleSignInAction(ActionEvent event) {
        String username = usernameTextField.getText();
        String password = passwordField.getText();

        String sql = "SELECT * FROM Passengers WHERE username = ? AND password = ?";

        try(Connection conn = new DatabaseConnection().connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next() && rs.getString("password").equals(password)) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/usd/views/passenger_dashboard_page.fxml"));
                    Parent root = loader.load();

                    Stage signInStage = new Stage();
                    signInStage.setTitle("Passenger Dashboard");
                    signInStage.setScene(new Scene(root));
                    signInStage.setResizable(false);
                    signInStage.show();

                    Passenger passenger = new Passenger(rs.getString("fullName"), rs.getString("username"), rs.getString("password"));
                    SessionManager.setCurrentUser(passenger);

                    ((Stage) signInButton.getScene().getWindow()).close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                invalidPasswordLabel.setVisible(true);
                passwordField.setText("");
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleSignupButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/usd/views/signup_page.fxml"));
            Parent root = loader.load();

            Stage signUpStage = new Stage();
            signUpStage.setTitle("Sign Up");
            signUpStage.setScene(new Scene(root));
            signUpStage.setResizable(false);
            signUpStage.show();

             ((Stage) signUpButton.getScene().getWindow()).close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleAdministratorButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/usd/views/admin_signin_page.fxml"));
            Parent root = loader.load();

            Stage signUpStage = new Stage();
            signUpStage.setTitle("Administrator Sign In");
            signUpStage.setScene(new Scene(root));
            signUpStage.setResizable(false);
            signUpStage.show();

            ((Stage) administratorButton.getScene().getWindow()).close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleForgotPassword(ActionEvent event) {
        Alert alert;
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Remember Your Password");
        alert.setContentText("That's too bad :C\nKeep Guessing Bud.");

        alert.getDialogPane().getStylesheets().add(getClass().getResource("/edu/usd/alert.css").toExternalForm());
        Label contentLabel = (Label) alert.getDialogPane().lookup(".content.label");
        if (contentLabel != null) {
            contentLabel.setWrapText(true);
        }
        alert.showAndWait();
    }

    /**
     * sets the username field and focuses on password field. used for after account is created
     * @param username the username to set the text field to.
     */
    public void setUsername(String username) {
        usernameTextField.setText(username);
        Platform.runLater(() -> passwordField.requestFocus());
    }

}

