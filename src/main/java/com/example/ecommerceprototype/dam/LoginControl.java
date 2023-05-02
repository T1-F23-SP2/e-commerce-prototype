package com.example.ecommerceprototype.dam;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Window;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class LoginControl {

    DAMController dam = new DAMController();

    @FXML
    private TextField UsernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button quitButton;

    @FXML
    private Hyperlink forgotPass;

    @FXML
    public void forgotPassClicked(javafx.event.ActionEvent event){
        showAlert(Alert.AlertType.INFORMATION, forgotPass.getScene().getWindow(), "Forgot Password",
                "skill issue");
    }

    @FXML
    public void quitButtonClicked(javafx.event.ActionEvent quitEvent){
        Platform.exit();
    }

    @FXML
    public void login(javafx.event.ActionEvent actionEvent) throws IOException {
        Window owner = loginButton.getScene().getWindow();

        System.out.println(UsernameField.getText());
        System.out.println(passwordField.getText());

        if (UsernameField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Error!",
                    "Please enter your username");
            return;
        }
        if (passwordField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Error!",
                    "Please enter a password");
            return;
        }

        if (UsernameField.getText().equals("admin") &&
                passwordField.getText().contains("banan")) {

            // her tjekker den om credentials er rigtige

            infoBox("Login successful!","Success","Login");
            dam.switchToDAM(actionEvent);

            // tilføj kode således at vi kan move til next stage
            // Stage stage = osv...
            // stage.setScene(new Scene(root))

        } else {
            showAlert(Alert.AlertType.ERROR, owner, "Error!","Invalid username or password" );
        }
    }

    private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

    public static void infoBox(String infoMessage, String headerText, String title) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }
}