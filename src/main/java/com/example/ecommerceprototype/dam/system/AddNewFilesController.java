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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class AddNewFilesController {
    
    @FXML
    private Button AddFilesButton;

    @FXML
    private Button OpenFilesButton;

    @FXML
    private Button LoadExistingFilesButton;

    @FXML
    private Button ReturnToDamButton;


    public void switchToDAM(ActionEvent event) throws IOException {
        // Setting the stage, scene and roots.
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("damMainScene.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }


    public void chooseFiles(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        List<File> files = fileChooser.showOpenMultipleDialog(null);


        if (files != null) {

            for (File file : files) {

                // first we need to load the content of the files into a byte array
                byte[] fileContent = Files.readAllBytes(file.toPath());
                UUID uu_id = UUID.randomUUID();
                String uu_id_string = uu_id.toString();

                try (Connection conn = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USER, Constants.DB_PASSWORD);
                     PreparedStatement stmt = conn.prepareStatement("INSERT INTO files (name, type, data, UUID) VALUES (?, ?, ?, ?)")) {
                    stmt.setString(1, file.getAbsolutePath());
                    stmt.setString(2, Files.probeContentType(file.toPath()));
                    stmt.setBytes(3, fileContent);
                    stmt.setString(4, uu_id_string);
                    stmt.executeUpdate();


                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Files Added");
            alert.setHeaderText(null);
            alert.setContentText("The files have been added to the list.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Oops!");
            alert.setHeaderText("You didn't select any files! Try Again");
            alert.setContentText("");
            alert.showAndWait();
        }
    }


}
