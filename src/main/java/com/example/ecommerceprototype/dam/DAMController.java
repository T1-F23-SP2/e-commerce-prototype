package com.example.ecommerceprototype.dam;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.Node;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DAMController {
    private Parent root;

    private List<File> selectedFiles = new ArrayList<>();

    @FXML
    private Button returnButton;

    @FXML
    private Button viewAllFiles;

    @FXML
    private Button addNewFile;

    @FXML
    private Button editFiles;

    @FXML
    private Button returnToMenu;

    @FXML
    private Button chooseFiles;

    @FXML
    private Button chooseFiles1;

    @FXML
    private Button deleteFileButton;
    @FXML
    private Button addWatermarkButton;

    @FXML
    private Button addSaleLogoButton;

    @FXML
    private Button addCopyrightLogoButton;

    @FXML
    private Button loadFilesButton;

    @FXML
    private Button saveButton;

    @FXML
    private ListView<File> myListView;

    @FXML
    private List<File> fileBuffer = new ArrayList<>();

    @FXML
    private Button openFile;


    public void returnToLoginPage(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

    public void switchToDAM(ActionEvent event) throws IOException {
        // Setting the stage, scene and roots.
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("damMainScene.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

    public void goToViewAllFiles(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("viewAllFiles.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

    public void goToAddNewFiles(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AddNewFiles.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

    public void goToEditFiles(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("editFiles.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }


    public void chooseFiles(ActionEvent event) throws IOException{
        FileChooser fileChooser = new FileChooser();
        List<File> files = fileChooser.showOpenMultipleDialog(null);

        if (files != null){

            for (File file : files){
                System.out.println(file.getAbsolutePath());
                myListView.getItems().add(file);
                FileSaver.getFiles().add(file);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Files Added");
                alert.setHeaderText(null);
                alert.setContentText("The files have been added to the list.");
                alert.showAndWait();

            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Oops!");
            alert.setHeaderText("You didn't select any files! Try Again");
            alert.setContentText("");
            alert.showAndWait();
        }
    }

    public void saveFiles(ActionEvent event) throws IOException {
        List<File> filesToSave = new ArrayList<>(myListView.getItems());
        FileSaver.setFiles(filesToSave);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Files Saved");
        alert.setHeaderText(null);
        alert.setContentText("The files have been saved.");
        alert.showAndWait();
    }

    public void openSelectedFile(ActionEvent event) throws IOException {
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

    public void deleteSelectedFile(ActionEvent event) throws IOException {
        File selectedFile = myListView.getSelectionModel().getSelectedItem();
        if (selectedFile != null) {
            myListView.getItems().remove(selectedFile);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Oops!");
            alert.setHeaderText("You didn't select a file to delete! Try Again");
            alert.setContentText("");
            alert.showAndWait();
        }
    }

    public void LoadFiles(ActionEvent event) throws IOException {
        if (!fileBuffer.isEmpty()) {
            myListView.getItems().addAll(fileBuffer);

            List<File> savedFiles = FileSaver.getFiles();
            ListView<File> savedListView = new ListView<>();
            savedListView.getItems().addAll(savedFiles);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Files Loaded");
            alert.setHeaderText(null);
            alert.setContentText("The files have been loaded from the list.");
            alert.showAndWait();

        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ERROR 404");
            alert.setHeaderText(null);
            alert.setContentText("The aren't any files to load. Add files and try again.");
            alert.showAndWait();
        }
    }

    public void addWatermarkToPicture(ActionEvent event) throws IOException{

    }



}
