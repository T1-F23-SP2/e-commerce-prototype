package com.example.ecommerceprototype.shop.pages;

import com.example.ecommerceprototype.cms.CMS;
import com.example.ecommerceprototype.shop.components.TopBanner;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ArticlePage {

    public static void loadPage(Stage window) throws Exception {

        Pane plate = CMS.getInstance().loadComponent("ContentTemplate2");

        CMS.getInstance().loadOnto(plate, CMS.getInstance().loadComponent("ArticlePage"), "contentPlaceholder_Pane");
        TopBanner.loadTopBanner(window, plate);

        window.setScene(new Scene(plate, 1920, 1080));
    }

}
