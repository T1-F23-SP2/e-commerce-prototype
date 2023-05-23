package com.example.ecommerceprototype.dam.system;

import com.example.ecommerceprototype.dam.constants.Category;
import com.example.ecommerceprototype.dam.constants.Type;
import com.example.ecommerceprototype.dam.dam.DAMSystem;
import com.example.ecommerceprototype.dam.dam.searchModel;
import javafx.beans.value.ObservableValue;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Dialog;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.*;
import java.util.List;

public class DamController implements Initializable {
    DAMSystem dam = DAMSystem.getInstance();

    Connection conn = dam.getConn();

    @FXML
    private Button downloadFileButton;

    @FXML
    private Button uploadFileButton;

    @FXML
    private Button updateFileButton;

    @FXML
    private Button addTagsButton;

    @FXML
    private Button watermarkButton;

    @FXML
    private Button resizeButton;

    @FXML
    private Button deleteButton;

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
        showTable();
    }


    public void showTable() {
        searchModelObservableList.clear();

        try {
            Statement statement = conn.createStatement();
            ResultSet queryOutput = statement.executeQuery("SELECT * FROM get_all_assets()");
            while (queryOutput.next()) {
                int queryAssetID = queryOutput.getInt("asset_id");
                String queryName = queryOutput.getString("asset_name");
                String queryFormat = queryOutput.getString("asset_format");
                String queryCategory = queryOutput.getString("asset_category");
                String queryType = queryOutput.getString("asset_type");
                String queryUUID = queryOutput.getString("asset_uuid");
                String queryTags = queryOutput.getString("asset_tags");
                String queryPath = queryOutput.getString("asset_path");

                searchModelObservableList.add(new searchModel(queryAssetID, queryName, queryFormat, queryCategory, queryType, queryUUID, queryTags, queryPath));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        // Showing all data from query
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

                if (String.valueOf(searchModel.getId()).toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if (searchModel.getName().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if (searchModel.getFormat().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if (searchModel.getCategory().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if (searchModel.getType().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if (searchModel.getUuid().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if (searchModel.getPath().toLowerCase().contains(searchKeyword)) {
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

        downloadFileButton.disableProperty().bind(tableView.getSelectionModel().selectedItemProperty().isNull());

        updateFileButton.disableProperty().bind(tableView.getSelectionModel().selectedItemProperty().isNull());

        addTagsButton.disableProperty().bind(tableView.getSelectionModel().selectedItemProperty().isNull());

        watermarkButton.disableProperty().bind(tableView.getSelectionModel().selectedItemProperty().isNull());

        resizeButton.disableProperty().bind(tableView.getSelectionModel().selectedItemProperty().isNull());

        deleteButton.disableProperty().bind(tableView.getSelectionModel().selectedItemProperty().isNull());
    }


    public void downloadFile() {

        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();

        int id = assetIdTableColumn.getCellObservableValue(selectedIndex).getValue();
        String filename = nameTableColumn.getCellObservableValue(selectedIndex).getValue();
        String type = typeTableColumn.getCellObservableValue(selectedIndex).getValue();
        String cat = categoryTableColumn.getCellObservableValue(selectedIndex).getValue();
        String uuid = uuidTableColumn.getCellObservableValue(selectedIndex).getValue();

        dam.downloadAsset(filename, type, cat, uuid);

    }


    public void uploadFiles(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        List<File> files = fileChooser.showOpenMultipleDialog(null);


        if (files != null) {

            for (File file : files) {

                try {
                    String oriFileName = file.getName();
                    String oriFilePath = file.getAbsolutePath();
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("newFileContents.fxml"));
                    DialogPane addFilesDialogPane = fxmlLoader.load();
                    NewFileController controller = fxmlLoader.getController();


                    Dialog<ButtonType> dialog = new Dialog<>();
                    dialog.setDialogPane(addFilesDialogPane);
                    dialog.setTitle("Asset Properties");

                    Optional<ButtonType> clickedButton = dialog.showAndWait();
                    if (clickedButton.get() == ButtonType.OK) {
                        controller.updateUUIDLabel(event);
                        controller.updateNameLabel(event);


                        String name = controller.getName();
                        String type = controller.getType();
                        String category = controller.getCategory();
                        String uuid = controller.getUuid();

                        System.out.println(name + type + category + uuid);
                        addAsset(name, type, category, uuid, oriFileName, oriFilePath);
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
        showTable();
    }


    //MANGLER ADD OG DELETE TAGS
    public void addTags() throws IOException {
        tableView.setEditable(true);
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        int assetID = assetIdTableColumn.getCellObservableValue(selectedIndex).getValue();

        List<String> tagListArray = dam.getTagsForAssetID(assetID);
        ObservableList<String> tagList = FXCollections.observableArrayList(tagListArray);


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("tags.fxml"));
        DialogPane tagPane = fxmlLoader.load();
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(tagPane);
        dialog.setTitle("tag Properties");


        TagController controller = fxmlLoader.getController();
        controller.setAssetID(assetID);
        controller.setTagList(tagList);


        Optional<ButtonType> clickedButton = dialog.showAndWait();

        if (clickedButton.get() == ButtonType.OK) {

            List<String> newTagList = controller.getTagsAdded();
            for (String tag : newTagList) {
                dam.tagAssignment(assetID, tag);
                System.out.println("add " + tag);
            }


            List<String> tagsDeleted = controller.getTagsDeleted();
            for (String tag : tagsDeleted) {
                dam.deleteTagAssignment(assetID, tag);

            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Oops!");
            alert.setHeaderText("You did not submit anything! No tags has been added or deleted.");
            alert.setContentText("");
            alert.showAndWait();
        }

        showTable();
    }


    public void watermark() throws IOException {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();

        int id = assetIdTableColumn.getCellObservableValue(selectedIndex).getValue();
        String filename = nameTableColumn.getCellObservableValue(selectedIndex).getValue();
        String type = typeTableColumn.getCellObservableValue(selectedIndex).getValue();
        String cat = categoryTableColumn.getCellObservableValue(selectedIndex).getValue();
        String uuid = uuidTableColumn.getCellObservableValue(selectedIndex).getValue();


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("watermarkFiles.fxml"));
        DialogPane tagPane = fxmlLoader.load();
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(tagPane);
        dialog.setTitle("Watermark");

        WatermarkController controller = fxmlLoader.getController();
        controller.setId(id);
        controller.setFilename(filename);
        controller.setType(type);
        controller.setCategory(cat);
        controller.setUuid(uuid);

        Optional<ButtonType> clickedButton = dialog.showAndWait();
        if (clickedButton.get() == ButtonType.OK) {
            showTable();
        }
    }


    public void resize() throws IOException {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();

        int id = assetIdTableColumn.getCellObservableValue(selectedIndex).getValue();
        String filename = nameTableColumn.getCellObservableValue(selectedIndex).getValue();
        String type = typeTableColumn.getCellObservableValue(selectedIndex).getValue();
        String cat = categoryTableColumn.getCellObservableValue(selectedIndex).getValue();
        String uuid = uuidTableColumn.getCellObservableValue(selectedIndex).getValue();


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("resizeImages.fxml"));
        DialogPane tagPane = fxmlLoader.load();
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(tagPane);
        dialog.setTitle("Resize");

        ResizeImagesController controller = fxmlLoader.getController();
        controller.setId(id);
        controller.setFilename(filename);
        controller.setType(type);
        controller.setCategory(cat);
        controller.setUuid(uuid);

        Optional<ButtonType> clickedButton = dialog.showAndWait();
        if (clickedButton.get() == ButtonType.OK) {
            showTable();
        } else {
            System.out.println("222");
        }
    }


    public void deleteAsset() {
        tableView.setEditable(true);
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();

        int id = assetIdTableColumn.getCellObservableValue(selectedIndex).getValue();
        String filename = nameTableColumn.getCellObservableValue(selectedIndex).getValue();
        String type = typeTableColumn.getCellObservableValue(selectedIndex).getValue();
        String cat = categoryTableColumn.getCellObservableValue(selectedIndex).getValue();
        String uuid = uuidTableColumn.getCellObservableValue(selectedIndex).getValue();

        searchModelObservableList.remove(selectedIndex);

        dam.deleteAsset(id, filename, type, cat, uuid);

    }


    public void switchToDAM(ActionEvent event) throws IOException {
        // Setting the stage, scene and roots.
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("dam.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }


    private void addAsset(String name_in, String type_in, String cat_in, String uuid_in, String oriname_in, String oripath_in) {
        Type type = switch (type_in) {
            case "Product image" -> Type.PRODUCT_IMAGE;
            case "Product file" -> Type.PRODUCT_FILE;
            case "Image" -> Type.IMAGE;
            case "File" -> Type.FILE;
            default -> null;
        };

        Category cat = extractCategory(cat_in);

        String fileFormat = extractFileFormat(oriname_in);

        String name = name_in + "." + fileFormat;

        dam.addAsset(name, type, cat, fileFormat, uuid_in, oripath_in);
    }


    private Category extractCategory(String cat_in) {
        Category cat = Category.valueOf(cat_in.toUpperCase());
        return cat;
    }

    private String extractFileFormat(String name_in) {
        String formatString = name_in.substring(name_in.lastIndexOf(".") + 1);

        return formatString;
    }

}
