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
    CMS cms;

    public ShopPage(ShopController controller) {
        this.controller = controller;
        this.cms = controller.getCMSInstance();

    }

    public void loadPage(Stage window) throws Exception {

        Pane plate = cms.loadComponent("ContentTemplate1");

        controller.getTopBanner().loadTopBanner(window, plate);
        controller.getProductView().loadProductView(window, plate, controller.getPIMDriverInstance().getAllProducts());
        controller.getSidebar().loadSidebar(window, plate);

        controller.setScene(plate);
    }

    public void reloadProductView(Stage window, ProductList products) throws Exception {

        Pane plate = cms.loadComponent("ContentTemplate1");

        controller.getTopBanner().loadTopBanner(window, plate);
        controller.getProductView().loadProductView(window, plate, products);
        controller.getSidebar().loadSidebar(window, plate);

        controller.setScene(plate);
    }

}
