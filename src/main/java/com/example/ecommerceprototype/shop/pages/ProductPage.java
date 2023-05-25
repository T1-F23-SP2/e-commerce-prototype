package com.example.ecommerceprototype.shop.pages;
import com.example.ecommerceprototype.cms.CMS;
import com.example.ecommerceprototype.pim.product_information.ProductInformation;
import com.example.ecommerceprototype.shop.ShopController;
import com.example.ecommerceprototype.shop.components.ProductFinder;


import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ProductPage {

    ShopController controller;
    CMS cms;

    public ProductPage(ShopController controller) {

        this.controller = controller;
        this.cms = controller.getCMSInstance();
    }
    public void loadPage(Stage window, ProductInformation product) throws Exception {

        Pane plate = cms.loadComponent("ContentTemplate2");

        controller.getTopBanner().loadTopBanner(window, plate);

        Pane productPage = cms.loadComponent("ProductPage");
        // Image productImage = new Image(ProductPage.class.getResourceAsStream("Placeholder.jpg"));
        // ((ImageView) controller.getCMSInstance().findNode(productPage, "primaryProductImage_ImageView")).setImage(productImage);
        // ((ImageView) controller.getCMSInstance().findNode(productPage, "secondaryProductImage_ImageView")).setImage(productImage);

        ((Label) cms.findNode(productPage, "productName_Label")).setText(product.getName());
        if (product.getPriceInformation() == null) {
            ((Label) cms.findNode(productPage, "productPrice_Label")).setText("$" + (ProductFinder.findProduct(product).getPriceInformation().getPrice()));
        } else {
            ((Label) cms.findNode(productPage, "productPrice_Label")).setText("$" + (product.getPriceInformation().getPrice()));
        }
        ((TextArea) cms.findNode(productPage, "productDescription_TextArea")).setText(product.getLongDescription());
        ((TextArea) cms.findNode(productPage, "productSpecification_TextArea")).setText(product.getShortDescription());
        ((Button) cms.findNode(productPage, "addToCart_Button")).setOnAction(actionEvent -> {
            try {
                controller.getCart().addToCart(product);}
            catch (Exception e) {System.out.println(e.getMessage());}
        });

        cms.loadOnto(plate, productPage, "contentPlaceholder_Pane");

        window.setScene(new Scene(plate, 1920, 1080));
    }
}
