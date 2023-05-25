package com.example.ecommerceprototype.shop.pages;

import com.example.ecommerceprototype.cms.CMS;
import com.example.ecommerceprototype.shop.ShopController;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class PurchasePage {

    ShopController controller;
    CMS cms;

    public PurchasePage(ShopController controller) {

        this.controller = controller;
        this.cms = controller.getCMSInstance();
    }

    public void loadPurchaseComplete(Stage window) throws Exception{
        Pane plate = cms.loadComponent("ContentTemplate2");

        controller.getTopBanner().loadTopBanner(window, plate);

        Pane paymentPage = cms.loadComponent("PurchaseCompletePage");
        cms.loadOnto(plate, paymentPage, "contentPlaceholder_Pane");

        window.setScene(new Scene(plate, 1920, 1080));
    }
}
