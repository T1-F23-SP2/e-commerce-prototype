package com.example.ecommerceprototype.shop.pages;

import com.example.ecommerceprototype.cms.CMS;
import com.example.ecommerceprototype.pim.product_information.PIMDriver;
import com.example.ecommerceprototype.pim.product_information.ProductInformation;
import com.example.ecommerceprototype.shop.ShopController;
import com.example.ecommerceprototype.shop.components.Cart;
import com.example.ecommerceprototype.shop.components.ProductFinder;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.math.BigDecimal;

public class CartPage {
    ShopController controller;
    CMS cms;
    Pane page;
    Pane cartItem;
    PIMDriver pim;

    public CartPage(ShopController controller) {
        this.controller = controller;
        this.cms = controller.getCMSInstance();
        this.pim = controller.getPIMDriverInstance();
    }

    boolean cartReloading = false;
    public void loadPage(Stage window) throws Exception {

        Pane plate = cms.loadComponent("ContentTemplate2");

        controller.getTopBanner().loadTopBannerHome(plate);

        Pane cartPage = cms.loadComponent("CartPage");
        page = cartPage;
        cms.loadOnto(plate, cartPage, "contentPlaceholder_Pane");

        BigDecimal total = BigDecimal.valueOf(0);

        for (String product : controller.getCart().getContents().keySet()) {
            total = total.add(ProductFinder.findProduct(pim.getProductByUUID(product)).getPriceInformation().getPrice().multiply(BigDecimal.valueOf(controller.getCart().getContents().get(product))));

            controller.getCartItem().loadCartItem(pim.getProductByUUID(product));

            updatePrice(total);
        }

        setButtonOnAction(page, "pay_Button", actionEvent -> {
            try {
                controller.getPaymentPage().loadPaymentPage(window);
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });

        updatePrice(total);
        controller.setScene(plate);
    }

    public void updatePrice(BigDecimal total) {
        setLabelText(page, "priceExclTax_Label", total + "DKK");
        setLabelText(page, "priceTax_Label", total.multiply(BigDecimal.valueOf(0.25)) + "DKK");
        setLabelText(page, "priceTotal_Label", total.multiply(BigDecimal.valueOf(0.25)).add(total) + "DKK");
    }
    public void setLabelText(Pane pane, String fxid, String text) {
        ((Label) cms.findNode(pane, fxid)).setText(text);
    }

    public void setButtonOnAction(Pane pane, String fxid, EventHandler function) {
        ((Button) cms.findNode(pane, fxid)).setOnAction(function);
    }
    public Pane getPagePane() {
        return page;
    }
}
