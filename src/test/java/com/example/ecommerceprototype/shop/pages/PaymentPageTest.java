package com.example.ecommerceprototype.shop.pages;

import com.example.ecommerceprototype.oms.Customers.Customer;
import com.example.ecommerceprototype.oms.MockShop.MockShopObject;
import com.example.ecommerceprototype.shop.ShopController;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class PaymentPageTest {

    ShopController controller;

    @Test
    void testCreateShopObject() throws Exception {
        controller = new ShopController();
        controller.getShopPopulate().createCategory("TestCategory", "");
        controller.getShopPopulate().createManufacturer("TestManufacturer");
        controller.getShopPopulate().createProduct("TestProduct", "0000", "TestCategory", "TestManufacturer", "500", "short", "long");
        controller.getCart().addToCart(controller.getPIMDriverInstance().getProductByName("TestProduct"));

        Customer customer = new Customer("Name", "Email", 0000, "Address", 0000);
        HashMap<String, Integer> order = controller.getPaymentPage().createOrder();
        MockShopObject orderInfo = new MockShopObject(order, customer);

        assertEquals(order, orderInfo.getMap());
    }

    @Test
    void testCreateOrder() throws Exception {
        controller = new ShopController();
        controller.getShopPopulate().createCategory("TestCategory", "");
        controller.getShopPopulate().createManufacturer("TestManufacturer");
        controller.getShopPopulate().createProduct("TestProduct", "0000", "TestCategory", "TestManufacturer", "500", "short", "long");
        controller.getCart().addToCart(controller.getPIMDriverInstance().getProductByName("TestProduct"));
        assertEquals(1, controller.getPaymentPage().createOrder().get(controller.getPIMDriverInstance().getProductByName("TestProduct").getProductUUID()));
    }

    @Test
    void testCreateCustomer() {
        Customer customer = new Customer("Name", "Email", 0000, "Address", 0000);
        assertEquals("Name", customer.getName());
        assertEquals("Email", customer.getEmail());
        assertEquals(0000, customer.getPhone());
        assertEquals("Address", customer.getAddress());
        assertEquals(0000, customer.getZipcode());
    }
}