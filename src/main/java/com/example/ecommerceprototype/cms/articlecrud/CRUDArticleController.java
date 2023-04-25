package com.example.ecommerceprototype.cms.articlecrud;

import com.example.ecommerceprototype.cms.ArticleData;
import com.example.ecommerceprototype.cms.ArticleManager;
import com.example.ecommerceprototype.cms.CMS;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;

import java.io.File;

public class CRUDArticleController {
    @FXML
    private Button add_Button, delete_Button, save_Button, discardChange_Button;
    @FXML
    private TextField titel_TextField, author_TextField;
    @FXML
    private TextArea articleText_TextArea;
    @FXML
    private ListView<String> articleList_ListView;
    private ObservableList<String> items;
    private ArticleManager articleManager;
    private File file;
    @FXML
    public void initialize(){
        articleManager = CMS.articles;
        items = FXCollections.observableList(articleManager.createArticleDataList());
        articleList_ListView.setItems(items);
        articleList_ListView.setEditable(true);
        articleList_ListView.setCellFactory(TextFieldListCell.forListView());
        add_Button.setOnAction(this::addArticle);
        delete_Button.setOnAction(this::removeArticle);
        discardChange_Button.setOnAction(this::discardChange);
        save_Button.setOnAction(this::saveArticle);
    }
    private void addArticle(ActionEvent add){
        file = new File("cms/Articles/Article"+articleManager.getArticleCount()+".txt");
        add_Button.setDisable(true);
    }
    private void removeArticle(ActionEvent remove){
        File fileSelected = new File(String.valueOf(articleList_ListView.getSelectionModel().getSelectedItem()));
        if (fileSelected.exists()){
            boolean fileDeleted = fileSelected.delete();
            if (fileDeleted){
                items.remove(fileSelected);
                add_Button.setDisable(false);
            }
        }
    }
    private void discardChange(ActionEvent discard){

    }
    private void saveArticle(ActionEvent save){
        if (titel_TextField.getText() != null){
            file.renameTo(new File(titel_TextField.getText()));

        }
        else {
            showErrorAlert("Error", "Failed in renaming file");
        }
        add_Button.setDisable(false);
    }
    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
