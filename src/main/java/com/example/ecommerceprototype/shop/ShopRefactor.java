package com.example.ecommerceprototype.shop;

import com.example.ecommerceprototype.cms.CMS;
import com.example.ecommerceprototype.oms.Customers.Customer;
import com.example.ecommerceprototype.oms.DB.StockInterface;
import com.example.ecommerceprototype.oms.MockShop.MockShopObject;
import com.example.ecommerceprototype.oms.OrderStatus.OrderManager;
import com.example.ecommerceprototype.pim.product_information.ManufacturingInformation;
import com.example.ecommerceprototype.pim.product_information.PIMDriver;
import com.example.ecommerceprototype.pim.product_information.ProductCategory;
import com.example.ecommerceprototype.pim.product_information.ProductInformation;
import com.example.ecommerceprototype.pim.util.FilterableArrayList;
import com.example.ecommerceprototype.pim.util.ProductList;
import com.example.ecommerceprototype.shop.components.Cart;
import com.example.ecommerceprototype.shop.components.ProductFinder;
import com.example.ecommerceprototype.shop.components.Sidebar;
import com.example.ecommerceprototype.shop.components.TopBanner;
import com.example.ecommerceprototype.shop.pages.ShopPage;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;


public class ShopRefactor extends Application implements StockInterface {

    static Stage window;
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {

        window = stage;
        ShopPage.loadPage(window);

        stage.setTitle("Arnes ElectroShop!");
        stage.show();

        stage.setFullScreen(true);
    }

    public static Stage getRootWindow() {
        return window;
    }

}