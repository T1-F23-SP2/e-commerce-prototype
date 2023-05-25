package com.example.ecommerceprototype.shop.pages;

import com.example.ecommerceprototype.cms.CMS;
import com.example.ecommerceprototype.pim.product_information.ProductInformation;
import com.example.ecommerceprototype.shop.ShopController;
import com.example.ecommerceprototype.shop.components.Cart;
import com.example.ecommerceprototype.shop.components.ProductFinder;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.math.BigDecimal;

public class CartPage {
    ShopController controller;
    CMS cms;

    public CartPage(ShopController controller) {
        this.controller = controller;
        this.cms = controller.getCMSInstance();
    }

    boolean cartReloading = false;
    public void loadPage(Stage window) throws Exception {

        Pane plate = cms.loadComponent("ContentTemplate2");

        controller.getTopBanner().loadTopBanner(window, plate);

        Pane cartPage = cms.loadComponent("CartPage");
        cms.loadOnto(plate, cartPage, "contentPlaceholder_Pane");

        BigDecimal total = BigDecimal.valueOf(0);
        for (ProductInformation product : controller.getCart().getContents().keySet()) {
            total = total.add(ProductFinder.findProduct(product).getPriceInformation().getPrice().multiply(BigDecimal.valueOf(controller.getCart().getContents().get(product))));

            Pane item = cms.loadComponent("CartProductView");
            cms.loadOnto(cartPage, item, "cartProductView_Vbox");
            //Image productImage = new Image(getClass().getResourceAsStream("Placeholder.jpg"));
            //((ImageView) controller.getCMSInstance().findNode(item, "productImage_ImageView")).setImage(productImage);
            ((Label) cms.findNode(item, "productName_Label")).setText(product.getName());
            if (product.getPriceInformation() == null) {
                ((Label) cms.findNode(item, "price_Label")).setText("$" + (ProductFinder.findProduct(product).getPriceInformation().getPrice()));
            } else {
                ((Label) cms.findNode(item, "price_Label")).setText("$" + (product.getPriceInformation().getPrice()));
            }
            ((Spinner) cms.findNode(item, "amount_Spinner")).setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99, controller.getCart().getContents().get(product), 1));

            ((Spinner) cms.findNode(item, "amount_Spinner")).getEditor().textProperty().addListener((obs, oldValue, newValue) -> {

                if (!"".equals(newValue)) {
                    controller.getCart().getContents().put(product, (Integer) ((Spinner) controller.getCMSInstance().findNode(item, "amount_Spinner")).getValue());
                }
            });
            ((Spinner) cms.findNode(item, "amount_Spinner")).setOnMouseClicked(actionEvent -> {
                try {
                    this.loadPage(window);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

            ((Button) cms.findNode(cartPage, "remove_Button")).setOnAction(actionEvent -> {
                try {
                    controller.getCart().deleteFromCart(product);
                }
                catch (Exception e) {System.out.println("!!!" + e.getMessage());}
            });


            updatePrice(cartPage, total);
        }

        ((Button) cms.findNode(cartPage, "pay_Button")).setOnAction(actionEvent -> {
            try {
                controller.getPaymentPage().loadPaymentPage(window);
            }
            catch (Exception e) {System.out.println("!!!" + e.getMessage());}
        });

        updatePrice(cartPage, total);
        controller.setScene(plate);
    }

    public void updatePrice(Pane cartPage, BigDecimal total) {
        ((Label) cms.findNode(cartPage, "priceExclTax_Label")).setText("$" + total);
        ((Label) cms.findNode(cartPage, "priceTax_Label")).setText("$" + total.multiply(BigDecimal.valueOf(0.25)));
        ((Label) cms.findNode(cartPage, "priceTotal_Label")).setText("$" + total.multiply(BigDecimal.valueOf(0.25)).add(total));
    }

}
