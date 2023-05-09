package com.example.ecommerceprototype.cms.crud;

import com.example.ecommerceprototype.cms.CMS;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CRUDApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(CMS.getInstance().loadComponent("CRUDText"), 1920, 1080);
        //Scene scene = new Scene(new VBox(), 1920, 1080);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
