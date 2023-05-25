package com.example.ecommerceprototype.shop.components;

import com.example.ecommerceprototype.cms.CMS;
import com.example.ecommerceprototype.pim.product_information.ProductInformation;
import com.example.ecommerceprototype.pim.util.ProductList;
import com.example.ecommerceprototype.shop.ShopController;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ProductView {

    ShopController controller;
    CMS cms;
    Pane productView;

    public ProductView(ShopController controller) {
        this.controller = controller;
        this.cms = controller.getCMSInstance();
    }

    public void loadProductView(Stage window, Pane plate, ProductList products) throws Exception {
        int i = 0;
        for (ProductInformation product : products) {
            if (product.getIsHidden()) {
                continue;
            }

            Pane view = controller.getCMSInstance().loadComponent("ProductView");
            productView = view;

            ((Label) controller.getCMSInstance().findNode(view, "productName_Label")).setText(product.getName());
            if (product.getPriceInformation() == null) {
                ((Label) controller.getCMSInstance().findNode(view, "productPrice_Label")).setText("$" + (ProductFinder.findProduct(product).getPriceInformation().getPrice()));
            } else {
                ((Label) controller.getCMSInstance().findNode(view, "productPrice_Label")).setText("$" + (product.getPriceInformation().getPrice()));
            }
            // ((Label) controller.getCMSInstance().findNode(view, "productStatus_Label")).setText(String.valueOf(StockInterface.getStockValue("12345")));
            // ((Label) controller.getCMSInstance().findNode(view, "productStatus_Label")).setText(StockInterface.getStockValue(products.get(i).getProductUUID()) > 0 ? "In stock" : "Sold out");
            ((TextArea) controller.getCMSInstance().findNode(view, "productDescription_TextArea")).setText(product.getShortDescription());
            //Image productImage = new Image(Objects.requireNonNull(ProductView.class.getResourceAsStream("Placeholder.jpg")));
            //((ImageView) controller.getCMSInstance().findNode(view, "productImage_ImageView")).setImage(productImage);
            ((Button) controller.getCMSInstance().findNode(view, "productImage_Button")).setOnAction(actionEvent -> {
                try {
                    controller.getProductPage().loadPage(window, product);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            });

            ((Button) controller.getCMSInstance().findNode(view, "addToCart_Button")).setOnAction(actionEvent -> {
                try {
                    controller.getCart().addToCart(product);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            });

            GridPane.setColumnIndex(view, i % 3);
            GridPane.setRowIndex(view, (int) Math.floor(i / 3)); // floor(n/3) is the integer sequence for 0, 0, 0, 1, 1, 1, 2, 2, 2... (https://oeis.org/A002264)
            i += 1;

            controller.getCMSInstance().loadOnto(plate, view, "contentPlaceholder_GridPane");
        }
    }

    public void setTextAreaText(String fxid, String text) {
        ((Label) cms.findNode(productView, fxid)).setText(text);
    }

    public void setLabelText(String fxid, String text) {
        ((Label) cms.findNode(productView, fxid)).setText(text);
    }

    public void setButtonOnAction(Pane pane, String fxid, EventHandler function) {
        ((Button) cms.findNode(pane, fxid)).setOnAction(function);
    }

    public void setProductSpecification(String productSpecification) {
        setTextAreaText("productSpecification_TextArea", productSpecification);
    }
    public void setProductDescription(String productDescription) {
        setTextAreaText("productDescription_TextArea", productDescription);
    }

    public void setProductPrice(String productPrice) {
        setLabelText("productPrice_Label", productPrice);
    }
    public void setProductName(String productName) {
        setLabelText("productName_Label", productName);
    }

}
