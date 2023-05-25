package com.example.ecommerceprototype.shop;

import javafx.application.Application;
import javafx.stage.Stage;



public class ShopMain extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        ShopController controller = new ShopController(stage);
    }

}