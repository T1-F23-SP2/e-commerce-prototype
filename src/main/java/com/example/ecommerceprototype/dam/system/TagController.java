package com.example.ecommerceprototype.dam.system;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;

import java.util.ResourceBundle;

public class TagController implements Initializable {
    private int assetID;

    @FXML
    private Label assetIdLabel;

    @FXML
    private TextField tagNameField;

    @FXML
    private Button applyTagButton;

    @FXML
    private Button removeTagButton;

    @FXML
    private ListView<String> tagListView;

    private ObservableList<String> tagList;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        tagListView.setItems(tagList);
    }


    public void setTagList(ObservableList<String> tagList) {
        ObservableList<String> observableList = FXCollections.observableArrayList(tagList);
        this.tagListView.setItems(observableList);
    }

    public int getAssetID() {
        return assetID;
    }

    public void setAssetID(int assetID) {
        this.assetID = assetID;
    }

    public Label getAssetIdLabel() {
        return assetIdLabel;
    }

    public void setAssetIdLabel(Label assetIdLabel) {
        this.assetIdLabel = assetIdLabel;
    }
}
