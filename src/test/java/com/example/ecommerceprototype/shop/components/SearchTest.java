package com.example.ecommerceprototype.shop.components;

import com.example.ecommerceprototype.shop.ShopController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SearchTest {

    ShopController controller;

    void setup() throws Exception {
        controller = new ShopController();
        controller.getShopPopulate().populate();
        controller.getShopPopulate().createCategory("TestCategory", "");
        controller.getShopPopulate().createManufacturer("TestManufacturer");
        controller.getShopPopulate().createProduct("TestProduct", "0000", "TestCategory", "TestManufacturer", "500", "short", "long");
    }
    @Test
    void testSearch() throws Exception {
        setup();
        assertEquals(controller.getPIMDriverInstance().getProductByName("TestProduct").getProductUUID(), controller.getSearch().search("Test").get(0).getProductUUID());

    }
}