package com.example.ecommerceprototype.shop.pages;

import com.example.ecommerceprototype.cms.CMS;
import com.example.ecommerceprototype.pim.product_information.ProductInformation;
import com.example.ecommerceprototype.shop.components.Cart;
import com.example.ecommerceprototype.shop.components.ProductFinder;
import com.example.ecommerceprototype.shop.components.Sidebar;
import com.example.ecommerceprototype.shop.components.TopBanner;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.math.BigDecimal;

public class CartPage {

    boolean cartReloading = false;
    public static void loadPage(Stage window) throws Exception{
        //int change = 0;
        //Load page template (Template 2 has space for a top banner and some content pane)
        Pane plate = CMS.getInstance().loadComponent("ContentTemplate2");

        TopBanner.loadTopBanner(window, plate);

        Pane cartPage = CMS.getInstance().loadComponent("CartPage");
        CMS.getInstance().loadOnto(plate, cartPage, "contentPlaceholder_Pane");

        BigDecimal total = BigDecimal.valueOf(0);
        for (ProductInformation product : Cart.cart.keySet()) {
            total = total.add(ProductFinder.findProduct(product).getPriceInformation().getPrice().multiply(BigDecimal.valueOf(Cart.cart.get(product))));

            Pane item = CMS.getInstance().loadComponent("CartProductView");
            CMS.getInstance().loadOnto(cartPage, item, "cartProductView_Vbox");
            //Image productImage = new Image(getClass().getResourceAsStream("Placeholder.jpg"));
            //((ImageView) CMS.getInstance().findNode(item, "productImage_ImageView")).setImage(productImage);
            ((Label) CMS.getInstance().findNode(item, "productName_Label")).setText(product.getName());
            if (product.getPriceInformation() == null) {
                ((Label) CMS.getInstance().findNode(item, "price_Label")).setText("$" + (ProductFinder.findProduct(product).getPriceInformation().getPrice()));
            } else {
                ((Label) CMS.getInstance().findNode(item, "price_Label")).setText("$" + (product.getPriceInformation().getPrice()));
            }
            ((Spinner) CMS.getInstance().findNode(item, "amount_Spinner")).setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99, Cart.cart.get(product), 1));

            ((Spinner) CMS.getInstance().findNode(item, "amount_Spinner")).getEditor().textProperty().addListener((obs, oldValue, newValue) -> {

                if (!"".equals(newValue)) {
                    Cart.cart.put(product, (Integer) ((Spinner) CMS.getInstance().findNode(item, "amount_Spinner")).getValue());
                }
            });
            ((Spinner) CMS.getInstance().findNode(item, "amount_Spinner")).setOnMouseClicked(actionEvent -> {
                try {
                    loadPage(window);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

            ((Button) CMS.getInstance().findNode(cartPage, "remove_Button")).setOnAction(actionEvent -> {
                try {
                    Cart.deleteFromCart(product);
                }
                catch (Exception e) {System.out.println("!!!" + e.getMessage());}
            });


            updatePrice(cartPage, total);
        }

        ((Button) CMS.getInstance().findNode(cartPage, "pay_Button")).setOnAction(actionEvent -> {
            try {
                PaymentPage.loadPaymentPage(window);
            }
            catch (Exception e) {System.out.println("!!!" + e.getMessage());}
        });

        updatePrice(cartPage, total);
        window.setScene(new Scene(plate, 1920, 1080));
    }

    public static void updatePrice(Pane cartPage, BigDecimal total) {
        ((Label) CMS.getInstance().findNode(cartPage, "priceExclTax_Label")).setText("$" + total);
        ((Label) CMS.getInstance().findNode(cartPage, "priceTax_Label")).setText("$" + total.multiply(BigDecimal.valueOf(0.25)));
        ((Label) CMS.getInstance().findNode(cartPage, "priceTotal_Label")).setText("$" + total.multiply(BigDecimal.valueOf(0.25)).add(total));
    }

}
