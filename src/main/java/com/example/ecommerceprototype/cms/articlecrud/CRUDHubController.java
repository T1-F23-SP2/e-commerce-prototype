package com.example.ecommerceprototype.cms.articlecrud;

import com.example.ecommerceprototype.cms.CMS;
import com.example.ecommerceprototype.cms.FXMLLoadFailedException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class CRUDHubController implements Initializable {
    @FXML
    private Button articleManager_Button, genericTextManager_Button;
    private Pane pane;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        articleManager_Button.setOnAction(this::articleCRUDView);
        genericTextManager_Button.setOnAction(this::genericCRUDView);
    }
    private void articleCRUDView(ActionEvent event){
        try {
            // pane = CMS.getInstance().fetchComponent("crud/article/CRUDArticle");
            pane = CMS.getInstance().fetchComponent("articlecrud/CRUDArticle");
            CRUDHubApplication.stage.setTitle("Article Manager Page");
            CRUDHubApplication.setStage(new Scene(pane, 1920, 1032));
        } catch (FXMLLoadFailedException e) {
            throw new RuntimeException(e);
        }
    }
    private void genericCRUDView(ActionEvent event){
        try {
            pane = CMS.getInstance().fetchComponent("crud/generic/CRUDText");
            CRUDHubApplication.stage.setTitle("Text Editor Manager Page");
            CRUDHubApplication.setStage(new Scene(pane, 1920, 1032));
        } catch (FXMLLoadFailedException e) {
            throw new RuntimeException(e);
        }
    }
}
