package com.example.ecommerceprototype.dam.system;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ViewAllFilesController {

    public void switchToDAM(ActionEvent event) throws IOException {
        // Setting the stage, scene and roots.
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("damMainScene.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }
}
