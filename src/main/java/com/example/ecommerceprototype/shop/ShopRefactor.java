package com.example.ecommerceprototype.shop;

import com.example.ecommerceprototype.oms.DB.StockInterface;
import javafx.application.Application;
import javafx.stage.Stage;



public class ShopRefactor extends Application implements StockInterface {


    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        ShopController controller = new ShopController(stage);
    }

}