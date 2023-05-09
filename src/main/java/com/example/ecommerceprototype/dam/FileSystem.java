package com.example.ecommerceprototype.dam;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class FileSystem {
    private Map<String, List<String>> fileTagMap;
    private TagSystem tagSystem;

    public FileSystem() {
        fileTagMap = new HashMap<>();
        tagSystem = new TagSystem();
    }

    public boolean addFile(String fileName, List<String> tags) {
        fileTagMap.put(fileName, tags);
        tagSystem.addTags(fileName, tags);
        return true;
    }

    public List<String> searchFiles(List<String> tags) {
        return tagSystem.searchTags(tags);
    }
}

    /*public void deleteSelectedFile(ActionEvent event) throws IOException {
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

    public void addSaleLogo(Asset asset) throws Exception {
        // vi starter med at indlæse filen
        File toBeWatermarked = new File(asset.filepath);

        BufferedImage originalImage = null;
        try {
            originalImage = ImageIO.read(toBeWatermarked);
        } catch (Exception e) {
            throw new Exception("File couldn't be read" + e.getMessage());
        }

        // nu opretter vi et BufferedImage object til det billede som vi ønsker at tilføje et vandmærke til
        BufferedImage watermarkedImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);

        // derefter opretter vi et Graphics2D objekt af billedet vi ønsker at vandmærke
        Graphics2D g2d = (Graphics2D) watermarkedImage.getGraphics();

        // vi tilføjer og tegner nu det originale billede ovenpå det nye billede
        g2d.drawImage(originalImage, 0, 0, null);

        BufferedImage logo = null;

        try {
            logo = ImageIO.read(new File("com/example/ecommerceprototype/dam/sale.png"));
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

        String newFilePath = asset.filepath + ".watermarked.jpg";
        try {
            ImageIO.write(watermarkedImage, "jpg", new File(newFilePath));
        } catch (Exception e) {
            throw new Exception("The picture with a watermark couldn't be saved: " + e.getMessage());
        }

        // vi opdaterer nu databasen så det passer
        String sql = "UPDATE files SET name = ? WHERE name = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, asset.filepath);
            pstmt.setInt(2, asset.id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Filepath couldn't be updated in the database: " + e.getMessage());
        }

        // til allersidst opdaterer vi dette assets eksisterende opgave filepath med den nye og vandmærkede fil
        asset.filepath = newFilePath;
    }
     */

