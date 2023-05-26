package com.example.ecommerceprototype.shop.components;

import com.example.ecommerceprototype.pim.exceptions.CategoryNotFoundException;
import com.example.ecommerceprototype.pim.exceptions.DuplicateEntryException;
import com.example.ecommerceprototype.pim.exceptions.NotFoundException;
import com.example.ecommerceprototype.pim.product_information.ProductCategoryBuilder;
import com.example.ecommerceprototype.pim.product_information.ProductInformationBuilder;
import com.example.ecommerceprototype.pim.product_information.ProductSpecification;
import com.example.ecommerceprototype.shop.ShopController;
import com.example.ecommerceprototype.shop.ShopPopulate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CartTest {


    static ShopController controller;

    @BeforeAll
    static void setup() throws Exception {
        controller = new ShopController();
        // controller.getShopPopulate().populate();
        controller.getShopPopulate().createCategory("TestCategory", "");
        controller.getShopPopulate().createManufacturer("TestManufacturer");
        controller.getShopPopulate().createProduct("TestProduct", "0000", "TestCategory", "TestManufacturer", "500", "short", "long");
    }
    @Test
    void testAddToCart() throws SQLException, NotFoundException {
        controller.getCart().addToCart(controller.getPIMDriverInstance().getProductByName("TestProduct"));
        assertEquals(true, controller.getCart().getContents().containsKey(controller.getPIMDriverInstance().getProductByName("TestProduct")));
        assertEquals(2, controller.getCart().getContents().get(controller.getPIMDriverInstance().getProductByName("TestProduct")));
    }

    @Test
    void testDeleteFromCart() {
    }

    @Test
    void testClearCart() {
    }

    @Test
    void testGetContents() {
    }
}