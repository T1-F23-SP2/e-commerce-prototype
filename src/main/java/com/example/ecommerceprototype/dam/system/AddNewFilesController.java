package com.example.ecommerceprototype.dam.system;

import com.example.ecommerceprototype.dam.constants.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class AddNewFilesController {
    
    @FXML
    private Button addFilesButton;

    @FXML
    private Button openFilesButton;

    @FXML
    private Button loadFilesButton;

    @FXML
    private Button returnToDamButton;

    @FXML
    private ListView<File> myListView;


    public void switchToDAM(ActionEvent event) throws IOException {
        // Setting the stage, scene and roots.
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("damMainScene.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }


    public void addFiles(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        List<File> files = fileChooser.showOpenMultipleDialog(null);


        if (files != null) {

            for (File file : files) {

            }

        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Oops!");
            alert.setHeaderText("You didn't select any files! Try Again");
            alert.setContentText("");
            alert.showAndWait();
        }
    }


    public void openFile(ActionEvent event) throws IOException {
        File selectedFile = myListView.getSelectionModel().getSelectedItem();
        if (selectedFile != null) {
            Desktop.getDesktop().open(selectedFile);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Oops!");
            alert.setHeaderText("You didn't select a file to open! Try Again");
            alert.setContentText("");
            alert.showAndWait();
        }
    }


    public void loadFiles() throws IOException {

        myListView.getItems().clear();

        try (Connection conn = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USER, Constants.DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT name, type FROM files")) {
            while (rs.next()) {
                String name = rs.getString("name");
                String type = rs.getString("type");
                myListView.getItems().add(new File(name));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
