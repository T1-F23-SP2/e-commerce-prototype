package com.example.ecommerceprototype.shop.components;

import com.example.ecommerceprototype.cms.CMS;
import com.example.ecommerceprototype.shop.ShopController;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class TopBanner {

    ShopController controller;
    CMS cms;
    Pane pane;

    public TopBanner(ShopController controller) {
        this.controller = controller;
        this.cms = controller.getCMSInstance();
    }

    public void loadTopBanner(Stage window, Pane page) throws Exception {

        Pane topBanner = cms.loadComponent("TopBanner");
        pane = topBanner;

        loadHomeButton();
        loadSearchBar();
        loadCartButton();

        cms.loadOnto(page, topBanner, "topBannerPlaceholder_Pane");

    }

    public void loadHomeButton() throws Exception {

        ((Button) cms.findNode(pane, "home_Button")).setOnAction(actionEvent -> {
            try {
                controller.getShopPage().loadPage(controller.getWindow());
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });

    }
    public void loadSearchBar() throws Exception {

        ((Button) cms.findNode(pane, "search_Button")).setOnAction(actionEvent -> {
            try {
                controller.getSearch().search(((TextField) cms.findNode(pane, "search_TextField")).getText());
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });

    }

    public void loadCartButton() throws Exception {

        ((Button) cms.findNode(pane, "cart_Button")).setOnAction(actionEvent -> {
            try {
                controller.getCartPage().loadPage(controller.getWindow());
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }

}
