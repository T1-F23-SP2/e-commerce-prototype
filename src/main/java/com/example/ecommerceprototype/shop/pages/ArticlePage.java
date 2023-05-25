package com.example.ecommerceprototype.shop.pages;

import com.example.ecommerceprototype.cms.CMS;
import com.example.ecommerceprototype.shop.ShopController;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ArticlePage {

    ShopController controller;
    CMS cms;

    public ArticlePage(ShopController controller) {
        this.controller = controller;
        this.cms = controller.getCMSInstance();
    }

    public void loadPage(Stage window) throws Exception {

        Pane plate = cms.loadComponent("ContentTemplate2");

        cms.loadOnto(plate, controller.getCMSInstance().loadComponent("ArticlePage"), "contentPlaceholder_Pane");
        controller.getTopBanner().loadTopBanner(window, plate);

        window.setScene(new Scene(plate, 1920, 1080));
    }

}
