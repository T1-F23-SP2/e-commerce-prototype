package com.example.ecommerceprototype.dam.system;

import com.example.ecommerceprototype.dam.constants.Category;
import com.example.ecommerceprototype.dam.constants.Constants;
import com.example.ecommerceprototype.dam.constants.FileFormat;
import com.example.ecommerceprototype.dam.constants.Type;
import com.example.ecommerceprototype.dam.dam.DAMSystem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.*;
import java.text.Format;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class AddNewFilesController {

    DAMSystem dam = DAMSystem.getInstance();

    
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

                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("newFileContents.fxml"));
                    DialogPane addFilesDialogPane = fxmlLoader.load();
                    NewFileController controller = fxmlLoader.getController();



                    Dialog<ButtonType> dialog = new Dialog<>();
                    dialog.setDialogPane(addFilesDialogPane);
                    dialog.setTitle("Asset Properties");

                    Optional<ButtonType> clickedButton = dialog.showAndWait();
                    if (clickedButton.get() == ButtonType.OK){
                        String name = controller.getName();
                        String type = controller.getType();
                        String category = controller.getCategory();
                        String uuid = controller.getUuid();

                        addAsset(name, type, category, uuid);

                    }

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }

        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Oops!");
            alert.setHeaderText("You did not submit any files! No files has been added.");
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


    private void addAsset(String name_in, String type_in, String cat_in, String uuid_in)
    {
        Type type = null;
        switch (type_in)
        {
            case "Product image":
                type = Type.PRODUCT_IMAGE;
                break;
            case "Product file":
                type = Type.PRODUCT_FILE;
                break;
            case "Image":
                type = Type.IMAGE;
                break;
            case "File":
                type = Type.FILE;
                break;
        }

        Category cat = setCategory(cat_in);


        dam.addAsset(name_in, type, cat,);
    }


    private Category setCategory(String s)
    {
        Category cat = Category.valueOf(s.toUpperCase());
        return cat;
    }

    private FileFormat extractFileFormat(String name_in)
    {
        String formatString = name_in.substring(name_in.lastIndexOf("?") + 1);

        FileFormat format = FileFormat.valueOf(formatString.toUpperCase());
        return format;
    }



}


 /*
        switch (cat_in)
        {
            case "CPU":
                type = Type.PRODUCT_IMAGE;
                break;
            case "RAM":
                type = Type.PRODUCT_FILE;
                break;
            case "GPU":
                type = Type.IMAGE;
                break;
            case "Motherboard":
                type = Type.FILE;
                break;
            case "Harddisk":
                type = Type.PRODUCT_IMAGE;
                break;
            case "SSD":
                type = Type.PRODUCT_FILE;
                break;
            case "Monitor":
                type = Type.IMAGE;
                break;
            case "Laptop":
                type = Type.FILE;
                break;
            case "Desktop":
                type = Type.PRODUCT_FILE;
                break;
            case "Cables":
                type = Type.IMAGE;
                break;
            case "Logo":
                type = Type.FILE;
                break;
            case "Article":
                type = Type.PRODUCT_FILE;
                break;
        }

         */