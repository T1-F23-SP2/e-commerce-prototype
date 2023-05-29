package com.example.ecommerceprototype.dam.system;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class DamApp extends Application {


    @Override
    public void start(Stage window) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DamApp.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        window.setTitle("DAM login");
        window.setScene(scene);
        window.show();
    }
    public static void main (){
        launch();
    }
}



