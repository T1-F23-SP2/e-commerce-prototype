package com.example.ecommerceprototype.shop.components;

import com.example.ecommerceprototype.cms.CMS;
import com.example.ecommerceprototype.shop.ShopController;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class TopBanner {

    ShopController controller;

    public TopBanner(ShopController controller) {
        this.controller = controller;
    }

    public void loadTopBanner(Stage window, Pane page) throws Exception {

        Pane topBanner = controller.getCMSInstance().loadComponent("TopBanner");

        ((Button) controller.getCMSInstance().findNode(topBanner, "home_Button")).setOnAction(actionEvent -> {
            try {
                controller.getShopPage().loadPage(window);
            }
            catch (Exception e) {System.out.println(e.getMessage());}
        });

        ((Button) controller.getCMSInstance().findNode(topBanner, "search_Button")).setOnAction(actionEvent -> {
            try {
                controller.getSearch().search(((TextField) controller.getCMSInstance().findNode(topBanner, "search_TextField")).getText());
            }
            catch (Exception e) {System.out.println(e.getMessage());}
        });

        ((Button) controller.getCMSInstance().findNode(topBanner, "cart_Button")).setOnAction(actionEvent -> {
            try {
                controller.getCartPage().loadPage(window);
            }
            catch (Exception e) {System.out.println(e.getMessage());}
        });

        controller.getCMSInstance().loadOnto(page, topBanner, "topBannerPlaceholder_Pane");

    }
}
