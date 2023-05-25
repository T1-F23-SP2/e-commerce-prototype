package com.example.ecommerceprototype.shop.components;

import com.example.ecommerceprototype.cms.CMS;
import com.example.ecommerceprototype.pim.product_information.ProductInformation;
import com.example.ecommerceprototype.pim.util.ProductList;
import com.example.ecommerceprototype.shop.pages.ProductPage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Objects;

public class ProductView {

    public static void loadProductView(Stage window, Pane plate, ProductList products) throws Exception {
        int i = 0;
        for (ProductInformation product : products) {
            if (product.getIsHidden()) {
                continue;
            }

            Pane view = CMS.getInstance().loadComponent("ProductView");

            ((Label) CMS.getInstance().findNode(view, "productName_Label")).setText(product.getName());
            if (product.getPriceInformation() == null) {
                ((Label) CMS.getInstance().findNode(view, "productPrice_Label")).setText("$" + (ProductFinder.findProduct(product).getPriceInformation().getPrice()));
            } else {
                ((Label) CMS.getInstance().findNode(view, "productPrice_Label")).setText("$" + (product.getPriceInformation().getPrice()));
            }
            // ((Label) CMS.getInstance().findNode(view, "productStatus_Label")).setText(String.valueOf(StockInterface.getStockValue("12345")));
            // ((Label) CMS.getInstance().findNode(view, "productStatus_Label")).setText(StockInterface.getStockValue(products.get(i).getProductUUID()) > 0 ? "In stock" : "Sold out");
            ((TextArea) CMS.getInstance().findNode(view, "productDescription_TextArea")).setText(product.getShortDescription());
            //Image productImage = new Image(Objects.requireNonNull(ProductView.class.getResourceAsStream("Placeholder.jpg")));
            //((ImageView) CMS.getInstance().findNode(view, "productImage_ImageView")).setImage(productImage);
            ((Button) CMS.getInstance().findNode(view, "productImage_Button")).setOnAction(actionEvent -> {
                try {
                    ProductPage.loadPage(window, product);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            });

            ((Button) CMS.getInstance().findNode(view, "addToCart_Button")).setOnAction(actionEvent -> {
                try {
                    Cart.addToCart(product);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            });

            GridPane.setColumnIndex(view, i % 3);
            GridPane.setRowIndex(view, (int) Math.floor(i / 3)); // floor(n/3) is the integer sequence for 0, 0, 0, 1, 1, 1, 2, 2, 2... (https://oeis.org/A002264)
            i += 1;

            CMS.getInstance().loadOnto(plate, view, "contentPlaceholder_GridPane");
        }
    }
}
