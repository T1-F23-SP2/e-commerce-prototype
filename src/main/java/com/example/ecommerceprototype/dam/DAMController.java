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

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.UUID;
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
    private Button goToResizefiles;

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
    private Button UUIDbutton;

    @FXML
    private Button addSaleLogoButton;

    @FXML
    private Button addCopyrightLogoButton;

    @FXML
    private Button loadFilesButton;

    @FXML
    private Button saveButton;

    @FXML
    private Button addTextWatermarkButton;

    @FXML
    private TextField getWatermarkText;

    @FXML
    private ListView<File> myListView;

    @FXML
    private Button openFile;

    @FXML
    private TextField targetWidthField;

    @FXML
    private TextField targetHeightField;

    @FXML
    private Button resizeImageButton;

    private static final String DB_URL = "jdbc:postgresql://damsem2.postgres.database.azure.com:5432/dam";
    private static final String DB_USER = "padmin";
    private static final String DB_PASSWORD = "Dam2.semester";


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

    public void goToResizeFiles(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("resizeAndCrop.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
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

            try {
                DriverManager.registerDriver(new org.postgresql.Driver());
                Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

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

    public void LoadFiles() throws IOException {

        myListView.getItems().clear();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
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

    public void addWatermark(ActionEvent event) throws Exception {

        File selectedFile = myListView.getSelectionModel().getSelectedItem();
        File toBeWatermarked = new File(String.valueOf(selectedFile));
        BufferedImage originalImage = null;

        if (selectedFile != null) {

            try {
                originalImage = ImageIO.read(toBeWatermarked);
            } catch (Exception e) {
                throw new Exception("File couldn't be read" + e.getMessage());
            }

            // nu opretter vi et BufferedImage object til det billede som vi ønsker at tilføje et vandmærke
            BufferedImage watermarkedImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);

            // derefter opretter vi et Graphics2D objekt af billedet vi ønsker at vandmærke
            Graphics2D g2d = (Graphics2D) watermarkedImage.getGraphics();

            // vi tilføjer og tegner nu det originale billede ovenpå det nye billede
            g2d.drawImage(originalImage, 0, 0, null);

            BufferedImage logo = null;

            try {
                logo = ImageIO.read(new File("src/main/resources/sdu_logo.png"));
            } catch (Exception e){
                throw new Exception("logo couldn't be loaded: " + e.getMessage());
            }

            // vi bliver nu nødt til at tegne logoet ovenpå det nye billede

            int logoWidth = logo.getWidth();
            int logoHeight = logo.getHeight();
            int logoMargin = 12;
            int logoX = watermarkedImage.getWidth() - logoWidth - logoMargin;
            int logoY = watermarkedImage.getHeight() - logoHeight - logoMargin;
            g2d.drawImage(logo, logoX, logoY, null);

            // vi gemmer nu det nye billede som en fil

            String newFilePath = String.valueOf(selectedFile) + ".watermarked.jpg";
            try {
                ImageIO.write(watermarkedImage, "jpg", new File(newFilePath));
            } catch (Exception e) {
                throw new Exception("The picture with a watermark couldn't be saved: " + e.getMessage());
            }

            try {
                DriverManager.registerDriver(new org.postgresql.Driver());
                Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

                String sql = "UPDATE files SET name = ? WHERE name = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);

                pstmt.setString(1, newFilePath);
                pstmt.setString(2, String.valueOf(selectedFile));

                pstmt.execute();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Files Deleted");
                alert.setHeaderText(null);
                alert.setContentText("The Chosen File Has Been Watermarked. Reload the files");
                alert.showAndWait();


            }
            catch (SQLException e) {
                throw new Exception("The Filepath couldn't be saved in the database" + e.getMessage());
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Oops!");
            alert.setHeaderText("You didn't select a file to watermark! Try Again");
            alert.setContentText("");
            alert.showAndWait();
        }

    }

    public void addSaleLogo(ActionEvent event) throws Exception {

        File selectedFile = myListView.getSelectionModel().getSelectedItem();
        File toBeWatermarked = new File(String.valueOf(selectedFile));
        BufferedImage originalImage = null;

        if (selectedFile != null) {

            try {
                originalImage = ImageIO.read(toBeWatermarked);
            } catch (Exception e) {
                throw new Exception("File couldn't be read" + e.getMessage());
            }

            // nu opretter vi et BufferedImage object til det billede som vi ønsker at tilføje et vandmærke
            BufferedImage watermarkedImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);

            // derefter opretter vi et Graphics2D objekt af billedet vi ønsker at vandmærke
            Graphics2D g2d = (Graphics2D) watermarkedImage.getGraphics();

            // vi tilføjer og tegner nu det originale billede ovenpå det nye billede
            g2d.drawImage(originalImage, 0, 0, null);

            BufferedImage logo = null;

            try {
                logo = ImageIO.read(new File("src/main/resources/sale.png"));
            } catch (Exception e){
                throw new Exception("logo couldn't be loaded: " + e.getMessage());
            }

            // vi bliver nu nødt til at tegne logoet ovenpå det nye billede

            int logoWidth = logo.getWidth();
            int logoHeight = logo.getHeight();
            int logoMargin = 12;
            int logoX = watermarkedImage.getWidth() - logoWidth - logoMargin;
            int logoY = watermarkedImage.getHeight() - logoHeight - logoMargin;
            g2d.drawImage(logo, logoX, logoY, null);

            // vi gemmer nu det nye billede som en fil

            String newFilePath = String.valueOf(selectedFile) + ".watermarked.jpg";
            try {
                ImageIO.write(watermarkedImage, "jpg", new File(newFilePath));
            } catch (Exception e) {
                throw new Exception("The picture with a watermark couldn't be saved: " + e.getMessage());
            }

            try {
                DriverManager.registerDriver(new org.postgresql.Driver());
                Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

                String sql = "UPDATE files SET name = ? WHERE name = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);

                pstmt.setString(1, newFilePath);
                pstmt.setString(2, String.valueOf(selectedFile));

                pstmt.execute();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Files Deleted");
                alert.setHeaderText(null);
                alert.setContentText("The Chosen File Has Been Watermarked. Reload the files");
                alert.showAndWait();


            }
            catch (SQLException e) {
                throw new Exception("The Filepath couldn't be saved in the database" + e.getMessage());
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Oops!");
            alert.setHeaderText("You didn't select a file to watermark! Try Again");
            alert.setContentText("");
            alert.showAndWait();
        }

    }

    public void addCopyrightLogo(ActionEvent event) throws Exception {

        File selectedFile = myListView.getSelectionModel().getSelectedItem();
        File toBeWatermarked = new File(String.valueOf(selectedFile));
        BufferedImage originalImage = null;

        if (selectedFile != null) {

            try {
                originalImage = ImageIO.read(toBeWatermarked);
            } catch (Exception e) {
                throw new Exception("File couldn't be read" + e.getMessage());
            }

            // nu opretter vi et BufferedImage object til det billede som vi ønsker at tilføje et vandmærke
            BufferedImage watermarkedImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);

            // derefter opretter vi et Graphics2D objekt af billedet vi ønsker at vandmærke
            Graphics2D g2d = (Graphics2D) watermarkedImage.getGraphics();

            // vi tilføjer og tegner nu det originale billede ovenpå det nye billede
            g2d.drawImage(originalImage, 0, 0, null);

            BufferedImage logo = null;

            try {
                logo = ImageIO.read(new File("src/main/resources/copyrightbillede.png"));
            } catch (Exception e){
                throw new Exception("logo couldn't be loaded: " + e.getMessage());
            }

            // vi bliver nu nødt til at tegne logoet ovenpå det nye billede

            int logoWidth = logo.getWidth();
            int logoHeight = logo.getHeight();
            int logoMargin = 12;
            int logoX = watermarkedImage.getWidth() - logoWidth - logoMargin;
            int logoY = watermarkedImage.getHeight() - logoHeight - logoMargin;
            g2d.drawImage(logo, logoX, logoY, null);

            // vi gemmer nu det nye billede som en fil

            String newFilePath = String.valueOf(selectedFile) + ".watermarked.jpg";
            try {
                ImageIO.write(watermarkedImage, "jpg", new File(newFilePath));
            } catch (Exception e) {
                throw new Exception("The picture with a watermark couldn't be saved: " + e.getMessage());
            }

            try {
                DriverManager.registerDriver(new org.postgresql.Driver());
                Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

                PreparedStatement pstmt = conn.prepareStatement("INSERT INTO files (name, type, data, UUID) VALUES (?, ?, ?, ?)");

                String sql = "UPDATE files SET name = ? WHERE name = ?";

                pstmt.setString(1, newFilePath);
                pstmt.setString(2, String.valueOf(selectedFile));

                pstmt.execute();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Files Deleted");
                alert.setHeaderText(null);
                alert.setContentText("The Chosen File Has Been Watermarked. Reload the files");
                alert.showAndWait();


            }
            catch (SQLException e) {
                throw new Exception("The Filepath couldn't be saved in the database" + e.getMessage());
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Oops!");
            alert.setHeaderText("You didn't select a file to watermark! Try Again");
            alert.setContentText("");
            alert.showAndWait();
        }

    }

    public void addTextmark(ActionEvent event) throws Exception {

        String mark = getWatermarkText.getText();

        File selectedFile = myListView.getSelectionModel().getSelectedItem();
        File toBeWatermarked = new File(String.valueOf(selectedFile));
        BufferedImage originalImage = null;

        if (selectedFile != null) {

            try {
                originalImage = ImageIO.read(toBeWatermarked);
            } catch (Exception e) {
                throw new Exception("File couldn't be read" + e.getMessage());
            }

            // nu opretter vi et BufferedImage object til det billede som vi ønsker at tilføje et vandmærke
            BufferedImage watermarkedImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);

            // derefter opretter vi et Graphics2D objekt af billedet vi ønsker at vandmærke
            Graphics2D g2d = (Graphics2D) watermarkedImage.getGraphics();

            // vi tilføjer og tegner nu det originale billede ovenpå det nye billede
            g2d.drawImage(originalImage, 0, 0, null);

            Font font = new Font("Purisa", Font.PLAIN, 48);
            Color color = Color.WHITE;
            g2d.setFont(font);
            g2d.setColor(color);
            g2d.drawString(mark, 10, 50);

            // vi gemmer nu det nye billede som en fil

            String newFilePath = String.valueOf(selectedFile) + ".watermarked.jpg";
            try {
                ImageIO.write(watermarkedImage, "jpg", new File(newFilePath));
            } catch (Exception e) {
                throw new Exception("The picture with a watermark couldn't be saved: " + e.getMessage());
            }

            try {
                DriverManager.registerDriver(new org.postgresql.Driver());
                Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

                String sql = "UPDATE files SET name = ? WHERE name = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);

                pstmt.setString(1, newFilePath);
                pstmt.setString(2, String.valueOf(selectedFile));

                pstmt.execute();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Files Deleted");
                alert.setHeaderText(null);
                alert.setContentText("The Chosen File Has Been Watermarked. Reload the files");
                alert.showAndWait();


            }
            catch (SQLException e) {
                throw new Exception("The Filepath couldn't be saved in the database" + e.getMessage());
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Oops!");
            alert.setHeaderText("You didn't select a file to watermark! Try Again");
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
                Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

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
