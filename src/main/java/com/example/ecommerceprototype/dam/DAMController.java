package com.example.ecommerceprototype.dam;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

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
    private Button openFile;

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/dam";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "Supermand1";


    public void returnToLoginPage(ActionEvent event) throws IOException {
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

    public void goToViewAllFiles(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("viewAllFiles.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

    public void goToAddNewFiles(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AddNewFiles.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

    public void goToEditFiles(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("editFiles.fxml")));
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

                try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dam", "postgres", "Supermand1");
                     PreparedStatement stmt = conn.prepareStatement("INSERT INTO files (name, type, data) VALUES (?, ?, ?)")) {
                    stmt.setString(1, file.getAbsolutePath());
                    stmt.setString(2, Files.probeContentType(file.toPath()));
                    stmt.setBytes(3, fileContent);
                    stmt.executeUpdate();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Files Added");
                    alert.setHeaderText(null);
                    alert.setContentText("The files have been added to the list.");
                    alert.showAndWait();

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
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



    /*public void openSelectedFile(ActionEvent event) throws IOException {
        File selectedFile = myListView.getSelectionModel().getSelectedItem();
        if (selectedFile != null) {
            try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dam", "postgres", "Supermand1");
                 PreparedStatement stmt = conn.prepareStatement("SELECT data FROM files WHERE name = ?")) {
                stmt.setString(1, selectedFile.getName());
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        byte[] fileContent = rs.getBytes("data");
                        File tempFile = File.createTempFile("temp", null);
                        FileOutputStream fos = new FileOutputStream(tempFile);
                        fos.write(fileContent);
                        fos.close();
                        Desktop.getDesktop().open(tempFile);
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Oops!");
            alert.setHeaderText("You didn't select a file to open! Try Again");
            alert.setContentText("");
            alert.showAndWait();
        }
    }

     */

    public void deleteSelectedFile(ActionEvent event) throws IOException {
        File selectedFile = myListView.getSelectionModel().getSelectedItem();
        if (selectedFile != null) {
            myListView.getItems().remove(selectedFile);

            try {
                DriverManager.registerDriver(new org.postgresql.Driver());
                Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

                String sql = "DELETE FROM files WHERE name = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);

                pstmt.setString(1, String.valueOf(selectedFile));

                pstmt.execute();


            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Oops!");
            alert.setHeaderText("You didn't select a file to delete! Try Again");
            alert.setContentText("");
            alert.showAndWait();
        }
    }

    public void LoadFiles() throws IOException {

        myListView.getItems().clear();

        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dam", "postgres", "Supermand1");
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
    public void LoadFiles(ActionEvent event) throws IOException {
        LoadFiles();
    }

}
