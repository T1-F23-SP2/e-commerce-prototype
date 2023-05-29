package com.example.ecommerceprototype.shop.components;

import com.example.ecommerceprototype.cms.CMS;
import com.example.ecommerceprototype.pim.product_information.ProductInformation;
import com.example.ecommerceprototype.shop.ShopController;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.Pane;

public class CartItem {

    ShopController controller;
    CMS cms;
    Pane page;
    Pane cartItem;

    public CartItem(ShopController controller) {
        this.controller = controller;
        this.cms = controller.getCMSInstance();
    }

    public void loadCartItem(ProductInformation product) throws Exception{
        Pane item = cms.loadComponent("CartProductView");
        cartItem = item;
        page = controller.getCartPage().getPagePane();
        cms.loadOnto(page, item, "cartProductView_Vbox");

        //Image productImage = new Image(getClass().getResourceAsStream("Placeholder.jpg"));
        //((ImageView) controller.getCMSInstance().findNode(item, "productImage_ImageView")).setImage(productImage);
        setProductName(product.getName());
        setProductPrice((ProductFinder.findProduct(product).getPriceInformation().getPrice()) + "DKK");
        loadSpinner(item, product);

        setButtonOnAction(item, "remove_Button", actionEvent -> {
            controller.getCart().deleteFromCart(product);
            try {
                controller.getCartPage().loadPage(controller.getWindow());
            } catch (Exception e) {
                System.out.println(e);
            }
        });
    }

    public void loadSpinner(Pane item, ProductInformation product) {
        ((Spinner) cms.findNode(item, "amount_Spinner")).setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 99, controller.getCart().getContents().get(product.getProductUUID()), 1));

        ((Spinner) cms.findNode(item, "amount_Spinner")).getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            if (!"".equals(newValue)) {
                if ((Integer) ((Spinner) cms.findNode(item, "amount_Spinner")).getValue() == 0) {
                    controller.getCart().deleteFromCart(product);
                    try {
                        controller.getCartPage().loadPage(controller.getWindow());
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                } else {
                    controller.getCart().getContents().put(product.getProductUUID(), (Integer) ((Spinner) cms.findNode(item, "amount_Spinner")).getValue());
                }
            }
        });

        ((Spinner) cms.findNode(item, "amount_Spinner")).setOnMouseClicked(actionEvent -> {
            try {
                controller.getCartPage().loadPage(controller.getWindow());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
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
