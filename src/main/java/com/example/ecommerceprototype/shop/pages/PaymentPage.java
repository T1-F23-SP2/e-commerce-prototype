package com.example.ecommerceprototype.shop.pages;

import com.example.ecommerceprototype.cms.CMS;
import com.example.ecommerceprototype.oms.Customers.Customer;
import com.example.ecommerceprototype.oms.DB.StockInterface;
import com.example.ecommerceprototype.oms.MockShop.MockShopObject;
import com.example.ecommerceprototype.oms.OrderStatus.OrderManager;
import com.example.ecommerceprototype.pim.product_information.ProductInformation;
import com.example.ecommerceprototype.shop.ShopController;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.HashMap;

public class PaymentPage {

    ShopController controller;
    CMS cms;

    Pane page;

    public PaymentPage(ShopController controller) {
        this.controller = controller;
        this.cms = controller.getCMSInstance();
    }

    public void loadPaymentPage(Stage window) throws Exception{

        Pane plate = cms.loadComponent("ContentTemplate2");

        controller.getTopBanner().loadTopBannerHomeCart(plate);

        Pane paymentPage = cms.loadComponent("paymentPage");
        page = paymentPage;
        cms.loadOnto(plate, paymentPage, "contentPlaceholder_Pane");

        ((Button) cms.findNode(paymentPage, "finish_Button")).setOnAction(actionEvent -> {

            MockShopObject orderInfo = createShopObject();
            if (orderInfo == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Input");
                alert.setContentText("Please ensure that all input is valid and try again.\n\nZIP Codes, Phone Numbers, Card Numbers and Expiration Month/Year only accept numbers.");
                alert.showAndWait();
                return;
            }

            try {
                controller.getPurchasePage().loadPurchaseComplete(window, orderInfo);
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
            controller.getCart().clearCart();

        });

        controller.setScene(plate);
    }

    public MockShopObject createShopObject() {
        Customer customer = createCustomer();
        if (customer == null)
            return null;
        HashMap<String, Integer> order = createOrder();

        MockShopObject orderInfo = new MockShopObject(order, customer);

        return orderInfo;
    }
    public HashMap<String, Integer> createOrder() {
        HashMap<String, Integer> order = new HashMap<>();

        for (String product : controller.getCart().getContents().keySet()) {
            order.put(product, controller.getCart().getContents().get(product));
        }

        return order;
    }
    public Customer createCustomer() {
        Customer customer;
        try {
        String name = fetchTextFieldText("fullName_TextField");
        String email = fetchTextFieldText("email_TextField");
        int phone = Integer.parseInt(fetchTextFieldText("phoneNumber_TextField"));
        String address = fetchTextFieldText("address_TextField");
        int zipcode = Integer.parseInt(fetchTextFieldText("ZIPCode_TextField"));

        customer = new Customer(name, email, phone, address, zipcode);}
        catch(NumberFormatException e) {
            customer = null;
        }

        return customer;
    }

    public String fetchTextFieldText(String fxid) {
        return ((TextField) cms.findNode(page, fxid)).getText();
    }
}
