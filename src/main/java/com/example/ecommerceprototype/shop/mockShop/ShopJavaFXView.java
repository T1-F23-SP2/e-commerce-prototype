package com.example.ecommerceprototype.shop.mockShop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ShopJavaFXView extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ShopJavaFXView.class.getResource("shop-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 932, 669);
        stage.setResizable(false);
        stage.setTitle("Arne's Electronics");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}