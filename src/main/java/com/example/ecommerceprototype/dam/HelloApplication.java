package com.example.ecommerceprototype.dam;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.print.attribute.standard.Media;
import java.io.File;
import javafx.scene.media.MediaPlayer;
import java.nio.file.Paths;
import java.util.*;

public class HelloApplication extends Application {

    public void music(){
        //kode til media
    }

    private final FileSystem fileSystem = new FileSystem();
    private final Map<String, Asset> fileListAssets = new HashMap<>();

    @Override
    public void start(Stage primaryStage) {


        DAM test2 = new DAM();

        // Create UI components
        Label titleLabel = new Label("DAM");
        titleLabel.setStyle("-fx-font-size: 50px; -fx-font-weight: bold;");
        titleLabel.setFont(Font.font("Arial", 20));

        // File search UI components
        Label fileSearchLabel = new Label("Search for files:");
        TextField fileSearchField = new TextField();
        Button fileSearchButton = new Button("Search");
        ListView<String> fileList = new ListView<>();
        fileList.setPrefHeight(300);

        // File upload UI components
        Label fileUploadLabel = new Label("Select files to add:");
        Button fileSelectButton = new Button("Select files");
        ListView<String> fileListToAdd = new ListView<>();
        fileListToAdd.setPrefHeight(300);
        Label fileUploadStatusLabel = new Label();
        fileUploadStatusLabel.setWrapText(true);
        fileUploadStatusLabel.setMaxWidth(300);
        fileUploadStatusLabel.setPadding(new Insets(0, 0, 0, 10));



        // Layouts
        BorderPane root = new BorderPane();
        HBox topBox = new HBox();
        VBox leftBox = new VBox();
        VBox rightBox = new VBox();

        // Add components to layouts
        topBox.getChildren().add(titleLabel);
        topBox.setAlignment(Pos.CENTER);
        leftBox.getChildren().addAll(fileSearchLabel, fileSearchField, fileSearchButton, fileList);
        leftBox.setSpacing(10);
        leftBox.setPadding(new Insets(10));
        rightBox.getChildren().addAll(fileUploadLabel, fileSelectButton, fileListToAdd, fileUploadStatusLabel);
        rightBox.setSpacing(10);
        rightBox.setPadding(new Insets(10));

        // Set up event handling
        fileSearchButton.setOnAction(event -> {
            List<String> tags = new ArrayList<>(List.of(fileSearchField.getText().split(" ")));
            List<String> searchResults = fileSystem.searchFiles(tags);
            if (searchResults.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("The requested file couldn't be found");
                alert.showAndWait();
            } else {
                // Clear the previous search results
                fileList.getItems().clear();

                // Add the search results to the fileList
                fileList.setItems(FXCollections.observableList(searchResults));

                // Set up event handling for the fileList
                fileList.setOnMouseClicked(mouseEvent -> {
                    if (mouseEvent.getClickCount() >= 1) {
                        String selectedFileName = fileList.getSelectionModel().getSelectedItem();
                        Asset selectedFile = fileListAssets.get(selectedFileName);

                        // Display the selected file
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setContentText("File name: " + selectedFile.getFilename() + "\n" +
                                "File path: " + selectedFile.getFilepath() + "\n" +
                                "File type: " + selectedFile.getFiletype() + "\n" +
                                "File size: " + selectedFile.getFilesize() + " bytes\n");
                        alert.showAndWait();
                    }
                });
            }
        });

        fileSelectButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            List<File> selectedFiles = fileChooser.showOpenMultipleDialog(primaryStage);
            if (selectedFiles != null) {
                List<String> fileNames = new ArrayList<>();
                for (File file : selectedFiles) {
                    TextInputDialog dialog = new TextInputDialog(file.getName());
                    dialog.setTitle("Rename File");
                    dialog.setHeaderText(null);
                    dialog.setContentText("Save in DAM as:");
                    Optional<String> result = dialog.showAndWait();
                    if (result.isPresent()) {
                        String fileName = result.get();
                        String filePath = file.getAbsolutePath();
                        String fileType = getFileType(fileName);
                        int fileSize = (int) file.length();
                        String uuid = java.util.UUID.randomUUID().toString();
                        boolean isWatermarked = false;
                        Date dateAdded = new Date();
                        List<String> tags = new ArrayList<>();

                        try {
                            Asset asset = new Asset(fileName, filePath, fileType, fileSize, uuid, isWatermarked, dateAdded);
                            fileSystem.addFile(String.valueOf(asset), tags);
                            fileNames.add(fileName);
                        } catch (Exception e) {
                            fileUploadStatusLabel.setText("Error uploading file: " + e.getMessage());
                            fileUploadStatusLabel.setTextFill(Color.RED);
                        }
                    }
                }
                if (!fileNames.isEmpty()) {
                    fileListToAdd.setItems(FXCollections.observableList(fileNames));
                }
            }
        });

        // Add layouts to root layout
        root.setTop(topBox);
        root.setLeft(leftBox);
        root.setRight(rightBox);

        // Create and show the scene
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("E-commerce Prototype");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private String getFileType(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex == -1) {
            return "";
        } else {
            return fileName.substring(dotIndex + 1);
        }
    }
}


