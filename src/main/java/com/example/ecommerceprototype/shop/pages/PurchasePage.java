package com.example.ecommerceprototype.shop.pages;

import com.example.ecommerceprototype.cms.CMS;
import com.example.ecommerceprototype.shop.components.Sidebar;
import com.example.ecommerceprototype.shop.components.TopBanner;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class PurchasePage {

    public static void loadPurchaseComplete(Stage window) throws Exception{
        //Load page template (Template 2 has space for a top banner and some content pane)
        Pane plate = CMS.getInstance().loadComponent("ContentTemplate2");

        TopBanner.loadTopBanner(window, plate);

        Pane paymentPage = CMS.getInstance().loadComponent("PurchaseCompletePage");
        CMS.getInstance().loadOnto(plate, paymentPage, "contentPlaceholder_Pane");

        window.setScene(new Scene(plate, 1920, 1080));
    }
}
