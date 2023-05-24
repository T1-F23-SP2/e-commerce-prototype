package com.example.ecommerceprototype.shop.components;

import com.example.ecommerceprototype.cms.CMS;
import com.example.ecommerceprototype.shop.pages.CartPage;
import com.example.ecommerceprototype.shop.pages.ShopPage;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class TopBanner {

    public static void loadTopBanner(Stage window, Pane page) throws Exception {

        Pane topBanner = CMS.getInstance().loadComponent("TopBanner");

        ((Button) CMS.getInstance().findNode(topBanner, "home_Button")).setOnAction(actionEvent -> {
            try {
                ShopPage.loadPage(window);
            }
            catch (Exception e) {System.out.println(e.getMessage());}
        });

        ((Button) CMS.getInstance().findNode(topBanner, "search_Button")).setOnAction(actionEvent -> {
            try {
                Search.search(((TextField) CMS.getInstance().findNode(topBanner, "search_TextField")).getText());
            }
            catch (Exception e) {System.out.println(e.getMessage());}
        });

        ((Button) CMS.getInstance().findNode(topBanner, "cart_Button")).setOnAction(actionEvent -> {
            try {
                CartPage.loadPage(window);
            }
            catch (Exception e) {System.out.println(e.getMessage());}
        });

        CMS.getInstance().loadOnto(page, topBanner, "topBannerPlaceholder_Pane");

    }
}
