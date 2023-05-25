package com.example.ecommerceprototype.shop.pages;

import com.example.ecommerceprototype.cms.CMS;
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

    public CartPage(ShopController controller) {
        this.controller = controller;
        this.cms = controller.getCMSInstance();
    }

    boolean cartReloading = false;
    public void loadPage(Stage window) throws Exception {

        Pane plate = cms.loadComponent("ContentTemplate2");

        controller.getTopBanner().loadTopBanner(window, plate);

        Pane cartPage = cms.loadComponent("CartPage");
        page = cartPage;
        cms.loadOnto(plate, cartPage, "contentPlaceholder_Pane");

        BigDecimal total = BigDecimal.valueOf(0);

        for (ProductInformation product : controller.getCart().getContents().keySet()) {
            total = total.add(ProductFinder.findProduct(product).getPriceInformation().getPrice().multiply(BigDecimal.valueOf(controller.getCart().getContents().get(product))));

            loadCartItem(product);

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

    public void loadCartItem(ProductInformation product) throws Exception{
        Pane item = cms.loadComponent("CartProductView");
        cartItem = item;
        cms.loadOnto(page, item, "cartProductView_Vbox");

        //Image productImage = new Image(getClass().getResourceAsStream("Placeholder.jpg"));
        //((ImageView) controller.getCMSInstance().findNode(item, "productImage_ImageView")).setImage(productImage);
        setProductName(product.getName());
        setProductPrice((ProductFinder.findProduct(product).getPriceInformation().getPrice()) + "DKK");
        loadSpinner(item, product);

        setButtonOnAction(item, "remove_Button", actionEvent -> {
            controller.getCart().deleteFromCart(product);}
        );
    }

    public void loadSpinner(Pane item, ProductInformation product) {
        ((Spinner) cms.findNode(item, "amount_Spinner")).setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99, controller.getCart().getContents().get(product), 1));

        ((Spinner) cms.findNode(item, "amount_Spinner")).getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            if (!"".equals(newValue)) {
                controller.getCart().getContents().put(product, (Integer) ((Spinner) controller.getCMSInstance().findNode(item, "amount_Spinner")).getValue());
            }
        });

        ((Spinner) cms.findNode(item, "amount_Spinner")).setOnMouseClicked(actionEvent -> {
            try {
                this.loadPage(controller.getWindow());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void updatePrice(BigDecimal total) {
        setLabelText(page, "priceExclTax_Label", total + "DKK");
        setLabelText(page, "priceTax_Label", total.multiply(BigDecimal.valueOf(0.25)) + "DKK");
        setLabelText(page, "priceTotal_Label", total.multiply(BigDecimal.valueOf(0.25)).add(total) + "DKK");
    }

    public void setProductName(String productName) {
        setLabelText(cartItem, "productName_Label", productName);
    }
    public void setProductPrice(String productPrice) {
        setLabelText(cartItem, "price_Label", productPrice);
    }
    public void setLabelText(Pane pane, String fxid, String text) {
        ((Label) cms.findNode(pane, fxid)).setText(text);
    }

    public void setButtonOnAction(Pane pane, String fxid, EventHandler function) {
        ((Button) cms.findNode(pane, fxid)).setOnAction(function);
    }

}
