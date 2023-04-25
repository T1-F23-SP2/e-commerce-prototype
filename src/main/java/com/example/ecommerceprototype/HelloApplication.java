package com.example.ecommerceprototype;

import com.example.ecommerceprototype.cms.CMS;
import com.example.ecommerceprototype.cms.FXMLLoadFailedException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException, FXMLLoadFailedException {
        Pane p = CMS.getInstance().fetchComponent("CategorySidebar");
        Scene scene = new Scene(p, 1640, 750);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}