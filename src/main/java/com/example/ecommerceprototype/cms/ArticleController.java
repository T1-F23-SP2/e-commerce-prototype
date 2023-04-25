package com.example.ecommerceprototype.cms;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ArticleController implements Initializable {

    @FXML
    VBox articleSidebar_VBox;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<String> articleNames = CMS.articles.getArticleNames();
        for (int i = 0; i < articleNames.size(); i++) {
            Button b = new Button(articleNames.get(i));
            articleSidebar_VBox.getChildren().add(b);
        }
    }
}
