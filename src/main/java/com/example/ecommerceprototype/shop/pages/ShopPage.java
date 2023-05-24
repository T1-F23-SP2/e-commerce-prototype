package com.example.ecommerceprototype.shop.pages;

import com.example.ecommerceprototype.cms.CMS;
import com.example.ecommerceprototype.pim.product_information.PIMDriver;
import com.example.ecommerceprototype.pim.util.ProductList;
import com.example.ecommerceprototype.shop.components.ProductView;
import com.example.ecommerceprototype.shop.components.Sidebar;
import com.example.ecommerceprototype.shop.components.TopBanner;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ShopPage {

    static PIMDriver pimDriverInstance = new PIMDriver();

    public static void loadPage(Stage window) throws Exception {

        Pane plate = CMS.getInstance().loadComponent("ContentTemplate1");

        TopBanner.loadTopBanner(window, plate);
        ProductView.loadProductView(window, plate, pimDriverInstance.getAllProducts());
        Sidebar.loadSidebar(window, plate);

        window.setScene(new Scene(plate, 1920, 1080));
    }

    public static void reloadProductView(Stage window, ProductList products) throws Exception {

        Pane plate = CMS.getInstance().loadComponent("ContentTemplate1");

        TopBanner.loadTopBanner(window, plate);
        ProductView.loadProductView(window, plate, products);
        Sidebar.loadSidebar(window, plate);

        window.setScene(new Scene(plate, 1920, 1080));
    }

}
