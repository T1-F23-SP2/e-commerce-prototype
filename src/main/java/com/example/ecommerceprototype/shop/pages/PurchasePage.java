package com.example.ecommerceprototype.shop.pages;

import com.example.ecommerceprototype.cms.CMS;
import com.example.ecommerceprototype.shop.ShopController;
import com.example.ecommerceprototype.shop.components.Sidebar;
import com.example.ecommerceprototype.shop.components.TopBanner;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class PurchasePage {

    ShopController controller;

    public PurchasePage(ShopController controller) {
        this.controller = controller;
    }

    public void loadPurchaseComplete(Stage window) throws Exception{
        Pane plate = CMS.getInstance().loadComponent("ContentTemplate2");

        controller.getTopBanner().loadTopBanner(window, plate);

        Pane paymentPage = CMS.getInstance().loadComponent("PurchaseCompletePage");
        CMS.getInstance().loadOnto(plate, paymentPage, "contentPlaceholder_Pane");

        window.setScene(new Scene(plate, 1920, 1080));
    }
}
