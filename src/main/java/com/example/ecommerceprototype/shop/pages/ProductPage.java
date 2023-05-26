package com.example.ecommerceprototype.shop.pages;
import com.example.ecommerceprototype.cms.CMS;
import com.example.ecommerceprototype.pim.product_information.ProductInformation;
import com.example.ecommerceprototype.shop.ShopController;
import com.example.ecommerceprototype.shop.components.ProductFinder;


import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javafx.event.EventHandler;
import java.util.function.Function;

public class ProductPage {

    ShopController controller;
    CMS cms;
    Pane page;

    public ProductPage(ShopController controller) throws Exception {

        this.controller = controller;
        this.cms = controller.getCMSInstance();
    }
    public void loadPage(Stage window, ProductInformation product) throws Exception {

        Pane plate = cms.loadComponent("ContentTemplate2");
        controller.getTopBanner().loadTopBannerHomeCart(plate);

        Pane productPage = cms.loadComponent("ProductPage");
        this.page = productPage;

        // Image productImage = new Image(ProductPage.class.getResourceAsStream("Placeholder.jpg"));
        // ((ImageView) controller.getCMSInstance().findNode(productPage, "primaryProductImage_ImageView")).setImage(productImage);
        // ((ImageView) controller.getCMSInstance().findNode(productPage, "secondaryProductImage_ImageView")).setImage(productImage);

        setProductName(product.getName());
        setProductPrice((ProductFinder.findProduct(product).getPriceInformation().getPrice()) + "DKK");
        setProductDescription(product.getLongDescription());
        setProductSpecification(product.getShortDescription());

        setButtonOnAction("addToCart_Button", actionEvent -> {
            try {
                controller.getCart().addToCart(product);
                controller.getCartPage().loadPage(controller.getWindow());
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });

        cms.loadOnto(plate, productPage, "contentPlaceholder_Pane");
        controller.setScene(plate);
    }

    public void setLabelText(String fxid, String text) {
        ((Label) cms.findNode(page, fxid)).setText(text);
    }

    public void setTextAreaText(String fxid, String text) {
        ((TextArea) cms.findNode(page, fxid)).setText(text);
    }

    public void setButtonOnAction(String fxid, EventHandler function) {
        ((Button) cms.findNode(page, fxid)).setOnAction(function);
    }
    public void setProductPrice(String productPrice) {
        setLabelText("productPrice_Label", productPrice);
    }
    public void setProductSpecification(String productSpecification) {
        setTextAreaText("productSpecification_TextArea", productSpecification);
    }
    public void setProductDescription(String productDescription) {
        setTextAreaText("productDescription_TextArea", productDescription);
    }
    public void setProductName(String productName) {
        setLabelText("productName_Label", productName);
    }


}
