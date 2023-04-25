package com.example.ecommerceprototype.cms;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.File;

public class CRUDArticleController {
    @FXML
    private Button add_Button, delete_Button, save_Button, discardChange_Button;
    @FXML
    private TextField titel_TextField, author_TextField;
    @FXML
    private TextArea articleText_TextArea;
    @FXML
    private ListView<ArticleData> articleList_ListView;

    private ArticleManager articleManager;
    private File file;
    private String id;
    @FXML
    public void initialize(){
        articleManager = ArticleManager.getInstance();
        ObservableList<ArticleData> items = FXCollections.observableList(articleManager.createArticleDataList());
        articleList_ListView.setItems(items);

        add_Button.setOnAction(this::addArticle);
        delete_Button.setOnAction(this::removeArticle);
        discardChange_Button.setOnAction(this::discardChange);
        save_Button.setOnAction(this::saveArticle);
    }
    private void addArticle(ActionEvent add){
        file = new File("cms/Articles");
    }
    private void removeArticle(ActionEvent remove){

    }
    private void discardChange(ActionEvent discard){

    }
    private void saveArticle(ActionEvent save){

    }
}
