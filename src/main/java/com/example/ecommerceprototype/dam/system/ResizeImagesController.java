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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.Objects;

public class ResizeImagesController {

    @FXML
    private Button returnButton;

    @FXML
    private Button loadAllFilesButton;

    @FXML
    private Button openFileButton;

    @FXML
    private Button deletefileButton;

    @FXML
    private Button resizeImageButton;

    @FXML
    private TextField targetWidthField;

    @FXML
    private TextField targetHeightField;

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


    public void deleteFile(ActionEvent event) throws IOException {
        File selectedFile = myListView.getSelectionModel().getSelectedItem();
        if (selectedFile != null) {
            myListView.getItems().remove(selectedFile);

            try {
                DriverManager.registerDriver(new org.postgresql.Driver());
                Connection conn = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USER, Constants.DB_PASSWORD);

                String sql = "DELETE FROM files WHERE name = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);

                pstmt.setString(1, String.valueOf(selectedFile));

                pstmt.execute();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Files Deleted");
                alert.setHeaderText(null);
                alert.setContentText("The Chosen File Has Been Deleted");
                alert.showAndWait();


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


    public void resizeImage(ActionEvent event) throws Exception {

        int targetWidth = Integer.parseInt(targetWidthField.getText());
        int targetHeight = Integer.parseInt(targetWidthField.getText());

        File selectedFile = myListView.getSelectionModel().getSelectedItem();

        File toBeResized = new File(String.valueOf(selectedFile));
        BufferedImage originalImage = null;

        if (selectedFile != null) {

            try {
                originalImage = ImageIO.read(toBeResized);
            } catch (Exception e) {
                throw new Exception("Image couldn't be loaded" + e.getMessage());
            }

            BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);

            Graphics2D graphics2D = resizedImage.createGraphics();

            graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
            graphics2D.dispose();

            // vi gemmer nu det nye billede som en fil

            String newFilePath = String.valueOf(selectedFile) + ".resized.jpg";
            try {
                ImageIO.write(resizedImage, "jpg", new File(newFilePath));
            } catch (Exception e) {
                throw new Exception("The resized picture couldn't be saved: " + e.getMessage());
            }

            try {
                DriverManager.registerDriver(new org.postgresql.Driver());
                Connection conn = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USER, Constants.DB_PASSWORD);

                String sql = "UPDATE files SET name = ? WHERE name = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);

                pstmt.setString(1, newFilePath);
                pstmt.setString(2, String.valueOf(selectedFile));

                pstmt.execute();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("File resized");
                alert.setHeaderText(null);
                alert.setContentText("The Chosen File Has Been Resized. Reload the files to view the result");
                alert.showAndWait();


            }
            catch (SQLException e) {
                throw new Exception("The Filepath couldn't be saved in the database" + e.getMessage());
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Oops!");
            alert.setHeaderText("You didn't select a file to resize! Try Again");
            alert.setContentText("");
            alert.showAndWait();
        }

    }



}
