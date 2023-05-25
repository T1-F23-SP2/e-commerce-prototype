package com.example.ecommerceprototype.shop.pages;

import com.example.ecommerceprototype.cms.CMS;
import com.example.ecommerceprototype.oms.Customers.Customer;
import com.example.ecommerceprototype.oms.DB.StockInterface;
import com.example.ecommerceprototype.oms.MockShop.MockShopObject;
import com.example.ecommerceprototype.oms.OrderStatus.OrderManager;
import com.example.ecommerceprototype.pim.product_information.ProductInformation;
import com.example.ecommerceprototype.shop.components.Cart;
import com.example.ecommerceprototype.shop.components.Sidebar;
import com.example.ecommerceprototype.shop.components.TopBanner;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.HashMap;

public class PaymentPage {

    public static void loadPaymentPage(Stage window) throws Exception{
        //Load page template (Template 2 has space for a top banner and some content pane)
        Pane plate = CMS.getInstance().loadComponent("ContentTemplate2");

        TopBanner.loadTopBanner(window, plate);

        Pane paymentPage = CMS.getInstance().loadComponent("paymentPage");
        CMS.getInstance().loadOnto(plate, paymentPage, "contentPlaceholder_Pane");

        ((Button) CMS.getInstance().findNode(paymentPage, "finish_Button")).setOnAction(actionEvent -> {
            String name = ((TextField) CMS.getInstance().findNode(paymentPage, "fullName_TextField")).getText();
            String email = ((TextField) CMS.getInstance().findNode(paymentPage, "email_TextField")).getText();
            int phone = Integer.parseInt(((TextField) CMS.getInstance().findNode(paymentPage, "phoneNumber_TextField")).getText());
            String address = ((TextField) CMS.getInstance().findNode(paymentPage, "address_TextField")).getText();
            int zipcode = Integer.parseInt(((TextField) CMS.getInstance().findNode(paymentPage, "ZIPCode_TextField")).getText());

            HashMap<String, Integer> order = new HashMap<>();

            for (ProductInformation product : Cart.cart.keySet()) {
                order.put(product.getProductUUID(), Cart.cart.get(product));
            }

            Customer customer = new Customer(name, email, phone, address, zipcode);
            MockShopObject orderInfo = new MockShopObject(order, customer);
            StockInterface.sendOrderOMSNew(orderInfo);
            OrderManager.sendOrder(orderInfo);


            try {
                Cart.clearCart();
                PurchasePage.loadPurchaseComplete(window);
            }
            catch (Exception e) {System.out.println(e.getMessage());}
        });

        window.setScene(new Scene(plate, 1920, 1080));
    }
}
