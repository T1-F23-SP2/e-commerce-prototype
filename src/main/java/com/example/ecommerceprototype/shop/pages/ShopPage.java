package com.example.ecommerceprototype.shop.pages;

import com.example.ecommerceprototype.cms.CMS;
import com.example.ecommerceprototype.pim.product_information.PIMDriver;
import com.example.ecommerceprototype.pim.util.ProductList;
import com.example.ecommerceprototype.shop.ShopController;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ShopPage {

    ShopController controller;

    public ShopPage(ShopController controller) {
        this.controller = controller;
    }

    public void loadPage(Stage window) throws Exception {

        Pane plate = CMS.getInstance().loadComponent("ContentTemplate1");

        controller.getTopBanner().loadTopBanner(window, plate);
        controller.getProductView().loadProductView(window, plate, controller.getPIMDriverInstance().getAllProducts());
        controller.getSidebar().loadSidebar(window, plate);

        window.setScene(new Scene(plate, 1920, 1080));
    }

    public void reloadProductView(Stage window, ProductList products) throws Exception {

        Pane plate = CMS.getInstance().loadComponent("ContentTemplate1");

        controller.getTopBanner().loadTopBanner(window, plate);
        controller.getProductView().loadProductView(window, plate, products);
        controller.getSidebar().loadSidebar(window, plate);

        window.setScene(new Scene(plate, 1920, 1080));
    }

}
