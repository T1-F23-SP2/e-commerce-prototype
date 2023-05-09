package com.example.ecommerceprototype.cms.articlecrud;

import com.example.ecommerceprototype.cms.CMS;
import javafx.application.Application;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class CRUDHubApplication extends Application {
    private String fileName;
    @Override
    public void start(Stage stage) throws Exception {
        Pane plate = CMS.getInstance().fetchComponent("articlecrud/CRUDArticle");
        Scene scene = new Scene(plate, 1920, 1080);
        stage.setTitle("Arnes Article Manager");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
    public void setFXMLName(String name){
        this.fileName = name;
    }
}
