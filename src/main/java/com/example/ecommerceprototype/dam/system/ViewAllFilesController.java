package com.example.ecommerceprototype.dam.system;

import com.example.ecommerceprototype.dam.constants.Constants;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

public class ViewAllFilesController implements Initializable {

    @FXML
    private Button returnButton;

    @FXML
    private Button loadFilesButton;

    @FXML
    private Button openFileButton;

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


    public void openFile() throws IOException {
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

    @FXML
    private TextField searchField;
    @FXML
    private TableView<searchModel> tableView;
    @FXML
    private TableColumn<searchModel, Integer> assetIdTableColumn;
    @FXML
    private TableColumn<searchModel, String> nameTableColumn;
    @FXML
    private TableColumn<searchModel, String> formatTableColumn;
    @FXML
    private TableColumn<searchModel, String> categoryTableColumn;
    @FXML
    private TableColumn<searchModel, String> typeTableColumn;
    @FXML
    private TableColumn<searchModel, String> uuidTableColumn;
    @FXML
    private TableColumn<searchModel, String> tagsTableColumn;
    @FXML
    private TableColumn<searchModel, String> pathTableColumn;


    ObservableList<searchModel> searchModelObservableList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DBconnection connect = new DBconnection();

        Connection conn = connect.getConn();

        try {
            Statement statement = conn.createStatement();
            ResultSet queryOutput = statement.executeQuery("SELECT * FROM get_all_assets()");

            while (queryOutput.next()){

                Integer queryAssetID = queryOutput.getInt("asset_id");
                String queryName = queryOutput.getString("asset_name");
                String queryFormat = queryOutput.getString("asset_format");
                String queryCategory = queryOutput.getString("asset_category");
                String queryType = queryOutput.getString("asset_type");
                String queryUUID = queryOutput.getString("asset_uuid");
                String queryTags = queryOutput.getString("asset_tags");
                String queryPath = queryOutput.getString("asset_path");

                searchModelObservableList.add(new searchModel(queryAssetID,queryName,queryFormat,queryCategory,queryType,queryUUID,queryTags,queryPath));
            }

            assetIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            formatTableColumn.setCellValueFactory(new PropertyValueFactory<>("format"));
            categoryTableColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
            typeTableColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
            uuidTableColumn.setCellValueFactory(new PropertyValueFactory<>("uuid"));
            tagsTableColumn.setCellValueFactory(new PropertyValueFactory<>("tags"));
            pathTableColumn.setCellValueFactory(new PropertyValueFactory<>("path"));

            tableView.setItems(searchModelObservableList);

            FilteredList<searchModel> filteredData = new FilteredList<>(searchModelObservableList, b -> true);


            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(searchModel -> {

                    if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                        return true;
                    }

                    String searchKeyword = newValue.toLowerCase();

                    if (searchModel.getName().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    } else if (searchModel.getFormat().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    }else if (searchModel.getCategory().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    } else if (searchModel.getType().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    } else if (searchModel.getUuid().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    } else if (searchModel.getPath().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    } else {
                        // Add null check for tags property
                        String tags = searchModel.getTags();
                        if (tags != null && tags.toLowerCase().contains(searchKeyword)) {
                            return true;
                        }
                    }

                    return false;


                });
            });

            SortedList<searchModel> sortedData = new SortedList<>(filteredData);

            sortedData.comparatorProperty().bind(tableView.comparatorProperty());

            tableView.setItems(sortedData);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }








}
