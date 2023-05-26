package com.example.ecommerceprototype.shop.components;

import com.example.ecommerceprototype.shop.ShopController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductFinderTest {

    ShopController controller;

    @Test
    void findProduct() throws Exception {
        controller = new ShopController();
        controller.getShopPopulate().createCategory("TestCategory", "");
        controller.getShopPopulate().createManufacturer("TestManufacturer");
        controller.getShopPopulate().createProduct("TestProduct", "0000", "TestCategory", "TestManufacturer", "500", "short", "long");

        assertEquals(controller.getPIMDriverInstance().getProductByName("TestCategory"), ProductFinder.findProduct(controller.getPIMDriverInstance().getProductByName("TestCategory")));
    }
}