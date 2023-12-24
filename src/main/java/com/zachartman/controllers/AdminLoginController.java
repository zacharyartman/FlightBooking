package com.zachartman.controllers;

import com.zachartman.models.Administrator;
import com.zachartman.models.Airline;
import com.zachartman.utils.DatabaseConnection;
import com.zachartman.utils.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AdminLoginController {

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameTextField;

    @FXML
    private Button signInButton;

    @FXML
    private Button passengerButton;

    @FXML
    private Button forgotPasswordButton;

    @FXML
    private Button administratorButton;

    @FXML
    private Label invalidPasswordLabel;

    private AdminController adminController;

    @FXML
    private void initialize() {
        adminController = new AdminController();
        invalidPasswordLabel.setVisible(false);
    }

    @FXML
    public void handleSignInAction(ActionEvent event) {
        String username = usernameTextField.getText();
        String password = passwordField.getText();

        String sql = "SELECT * FROM Administrators WHERE username = ? AND password = ?";

        try(Connection conn = new DatabaseConnection().connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next() && rs.getString("password").equals(password)) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/zachartman/views/admin_dashboard_page.fxml"));
                    Parent root = loader.load();

                    Stage signInStage = new Stage();
                    signInStage.setTitle("Administrator Dashboard");
                    signInStage.setScene(new Scene(root));
                    signInStage.setResizable(false);
                    signInStage.show();

                    List<Airline> airlines = adminController.getAirlinesByUser(rs.getString("username"));
                    Administrator administrator = new Administrator(rs.getString("fullName"), rs.getString("username"), rs.getString("password"), airlines);
                    SessionManager.setCurrentUser(administrator);

                    ((Stage) signInButton.getScene().getWindow()).close();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            else {
                System.out.println("Incorrect Password");
                invalidPasswordLabel.setVisible(true);
                passwordField.setText("");
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void handlePassengerButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/zachartman/views/passenger_signin_page.fxml"));
            Parent root = loader.load();

            Stage signUpStage = new Stage();
            signUpStage.setTitle("Passenger Sign In");
            signUpStage.setScene(new Scene(root));
            signUpStage.setResizable(false);
            signUpStage.show();

            ((Stage) passengerButton.getScene().getWindow()).close();
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

        alert.getDialogPane().getStylesheets().add(getClass().getResource("/com/zachartman/alert.css").toExternalForm());
        Label contentLabel = (Label) alert.getDialogPane().lookup(".content.label");
        if (contentLabel != null) {
            contentLabel.setWrapText(true);
        }
        alert.showAndWait();
    }

}

