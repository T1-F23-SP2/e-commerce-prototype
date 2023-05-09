package com.example.ecommerceprototype.cms.crud;

import com.example.ecommerceprototype.cms.CMS;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class CRUDHubApplication extends Application {
    protected static Stage stage;
    @Override
    public void start(Stage stage) throws Exception {
        CRUDHubApplication.stage = stage;
        Pane plate = CMS.getInstance().loadComponent("CRUDHub", true);
        Scene scene = new Scene(plate, 600, 400);
        stage.setTitle("Arnes Manager");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
    public static void setStage(Scene scene){
        CRUDHubApplication.stage.setScene(scene);
    }
}
