package com.example.ecommerceprototype.dam.system;


import com.example.ecommerceprototype.dam.dam.DBSystem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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

    private List<String> tagsAdded = new ArrayList<>();

    private List<String> tagsDeleted = new ArrayList<>();

    public List<String> getTagsAdded() {
        return tagsAdded;
    }

    public void setTagsAdded(List<String> tagsAdded) {
        this.tagsAdded = tagsAdded;
    }

    public List<String> getTagsDeleted() {
        return tagsDeleted;
    }

    public void setTagsDeleted(List<String> tagsDeleted) {
        this.tagsDeleted = tagsDeleted;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        tagListView.setItems(tagList);
    }

    @FXML
    private void addTag()
    {
        String tagName = tagNameField.getText();

        boolean tagExists = false;
        for (String tag : tagList) {
            if (tag.equals(tagName)) {
                tagExists = true;
                break;
            }
        }

        if (tagExists) {
            System.out.println("Tag already exists");
        } else {
            tagList.add(tagName);
            if (!tagsDeleted.contains(tagName)) {
                tagsAdded.add(tagName);
            } else {
                tagsDeleted.remove(tagName);
            }
            tagListView.setItems(tagList);
        }
    }

    @FXML
    private void deleteTag()
    {
        String tagName = tagListView.getSelectionModel().getSelectedItem();
        tagList.remove(tagName);

        if (tagsAdded.contains(tagName)) {
            tagsAdded.remove(tagName);
        } else {
            tagsDeleted.add(tagName);
        }

        tagListView.setItems(tagList);

    }




    public ObservableList<String> getTagList() {
        return tagList;
    }

    public void setTagList(ObservableList<String> tagList_in) {
        tagList = FXCollections.observableArrayList(tagList_in);
        this.tagListView.setItems(tagList);
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
