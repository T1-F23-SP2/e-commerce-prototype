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
    Pane page;


    public ProductPage(ShopController controller) throws Exception {

        this.controller = controller;
        this.cms = controller.getCMSInstance();
    }
    public void loadPage(Stage window, ProductInformation product) throws Exception {

        Pane plate = cms.loadComponent("ContentTemplate2");
        controller.getTopBanner().loadTopBanner(window, plate);

        Pane productPage = cms.loadComponent("ProductPage");
        this.page = productPage;

        // Image productImage = new Image(ProductPage.class.getResourceAsStream("Placeholder.jpg"));
        // ((ImageView) controller.getCMSInstance().findNode(productPage, "primaryProductImage_ImageView")).setImage(productImage);
        // ((ImageView) controller.getCMSInstance().findNode(productPage, "secondaryProductImage_ImageView")).setImage(productImage);

        setLabelText("productName_Label", product.getName());
        if (product.getPriceInformation() == null) {
            setLabelText("productPrice_Label", "$" + (ProductFinder.findProduct(product).getPriceInformation().getPrice()));
        } else {
            setLabelText("productPrice_Label", "$" + (product.getPriceInformation().getPrice()));
        }
        setTextAreaText("productDescription_TextArea", product.getLongDescription());
        setTextAreaText("productSpecification_TextArea", product.getShortDescription());
        ((Button) cms.findNode(productPage, "addToCart_Button")).setOnAction(actionEvent -> {
            try {
                controller.getCart().addToCart(product);}
            catch (Exception e) {System.out.println(e.getMessage());}
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

}
