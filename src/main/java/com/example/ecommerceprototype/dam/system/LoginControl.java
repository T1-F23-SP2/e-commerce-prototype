package com.example.ecommerceprototype.dam.system;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Objects;


public class LoginControl {

    Controller dam = new Controller();

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

    private static final String DB_URL = "jdbc:postgresql://damsem2.postgres.database.azure.com:5432/dam";
    private static final String DB_USER = "padmin";
    private static final String DB_PASSWORD = "Dam2.semester";

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
    public void login(javafx.event.ActionEvent actionEvent) throws IOException, SQLException {
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

        if (!UsernameField.getText().isEmpty() && !passwordField.getText().isEmpty()) {

            try {
                DriverManager.registerDriver(new org.postgresql.Driver());
                Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

                String sql = "SELECT * FROM loginpage WHERE username = ? AND password = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);

                pstmt.setString(1, UsernameField.getText());
                pstmt.setString(2, passwordField.getText());

                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    // Login successful
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Login successful!");
                    alert.setHeaderText(null);
                    alert.setContentText("Welcome " + UsernameField.getText() + "!");
                    alert.showAndWait();


                    switchToDamMain(actionEvent);
                } else {
                    // Login failed
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Login failed");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid username or password!");
                    alert.showAndWait();
                }
                conn.close();
            } catch (SQLException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Database error");
                alert.setHeaderText(null);
                alert.setContentText("Database error: " + ex.getMessage());
                alert.showAndWait();
            }
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

    public void switchToDamMain(ActionEvent event) throws IOException
    {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("damMain.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

}
