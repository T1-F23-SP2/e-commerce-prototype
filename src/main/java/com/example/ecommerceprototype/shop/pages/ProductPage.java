package com.example.ecommerceprototype.shop.pages;
import com.example.ecommerceprototype.cms.CMS;
import com.example.ecommerceprototype.pim.product_information.ProductInformation;
import com.example.ecommerceprototype.shop.ShopController;
import com.example.ecommerceprototype.shop.components.Cart;
import com.example.ecommerceprototype.shop.components.ProductFinder;
import com.example.ecommerceprototype.shop.components.Sidebar;
import com.example.ecommerceprototype.shop.components.TopBanner;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ProductPage {

    ShopController controller;

    public ProductPage(ShopController controller) {
        this.controller = controller;
    }
    public void loadPage(Stage window, ProductInformation product) throws Exception {
        //Load page template (Template 2 has space for a top banner and some content pane)
        Pane plate = CMS.getInstance().loadComponent("ContentTemplate2");

        controller.getTopBanner().loadTopBanner(window, plate);

        Pane productPage = CMS.getInstance().loadComponent("ProductPage");
        // Image productImage = new Image(ProductPage.class.getResourceAsStream("Placeholder.jpg"));
        // ((ImageView) CMS.getInstance().findNode(productPage, "primaryProductImage_ImageView")).setImage(productImage);
        // ((ImageView) CMS.getInstance().findNode(productPage, "secondaryProductImage_ImageView")).setImage(productImage);

        ((Label) CMS.getInstance().findNode(productPage, "productName_Label")).setText(product.getName());
        if (product.getPriceInformation() == null) {
            ((Label) CMS.getInstance().findNode(productPage, "productPrice_Label")).setText("$" + (ProductFinder.findProduct(product).getPriceInformation().getPrice()));
        } else {
            ((Label) CMS.getInstance().findNode(productPage, "productPrice_Label")).setText("$" + (product.getPriceInformation().getPrice()));
        }
        ((TextArea) CMS.getInstance().findNode(productPage, "productDescription_TextArea")).setText(product.getLongDescription());
        ((TextArea) CMS.getInstance().findNode(productPage, "productSpecification_TextArea")).setText(product.getShortDescription());
        ((Button) CMS.getInstance().findNode(productPage, "addToCart_Button")).setOnAction(actionEvent -> {
            try {
                controller.getCart().addToCart(product);}
            catch (Exception e) {System.out.println(e.getMessage());}
        });

        CMS.getInstance().loadOnto(plate, productPage, "contentPlaceholder_Pane");

        window.setScene(new Scene(plate, 1920, 1080));
    }
}
