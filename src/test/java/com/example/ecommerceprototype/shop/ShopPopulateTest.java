package com.example.ecommerceprototype.shop;

import com.example.ecommerceprototype.pim.exceptions.DuplicateEntryException;
import com.example.ecommerceprototype.pim.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ShopPopulateTest {

    @Test
    void testPopulateProducts() throws Exception {
        ShopController controller = new ShopController();
        controller.getShopPopulate().populateProducts();
        assertTrue(controller.getPIMDriverInstance().checkIfProductByNameExists("Acer Aspire"));
        assertTrue(controller.getPIMDriverInstance().checkIfProductByNameExists("Phillips monitor"));
        assertTrue(controller.getPIMDriverInstance().checkIfProductByNameExists("AMD Ryzen 7 5700G"));
        assertTrue(controller.getPIMDriverInstance().checkIfProductByNameExists("Corsair Dominator"));
        assertTrue(controller.getPIMDriverInstance().checkIfProductByNameExists("Lenovo Yoga Slim 7 Pro"));
    }

    @Test
    void testPopulateCategories() throws Exception {
        ShopController controller = new ShopController();
        controller.getShopPopulate().populateCategories();
        assertTrue(controller.getPIMDriverInstance().checkIfCategoryByNameExists("Computers"));
        assertTrue(controller.getPIMDriverInstance().checkIfCategoryByNameExists("PC components"));
        assertTrue(controller.getPIMDriverInstance().checkIfCategoryByNameExists("Laptops"));
        assertTrue(controller.getPIMDriverInstance().checkIfCategoryByNameExists("Desktop PCs"));
        assertTrue(controller.getPIMDriverInstance().checkIfCategoryByNameExists("CPUs"));
        assertTrue(controller.getPIMDriverInstance().checkIfCategoryByNameExists("RAM"));
        assertTrue(controller.getPIMDriverInstance().checkIfCategoryByNameExists("Monitors"));
    }

    @Test
    void testPopulateManufacturers() throws Exception {
        ShopController controller = new ShopController();
        controller.getShopPopulate().populateManufacturers();
        assertTrue(controller.getPIMDriverInstance().checkIfManufactureByNameExists("Acer"));
        assertTrue(controller.getPIMDriverInstance().checkIfManufactureByNameExists("Lenovo"));
        assertTrue(controller.getPIMDriverInstance().checkIfManufactureByNameExists("ASUS"));
        assertTrue(controller.getPIMDriverInstance().checkIfManufactureByNameExists("AMD"));
        assertTrue(controller.getPIMDriverInstance().checkIfManufactureByNameExists("Corsair"));
    }

    @Test
    void testCreateProduct() throws Exception {
        ShopController controller = new ShopController();
        controller.getShopPopulate().createProduct("TestProduct", "0000", "TestCategory", "TestManufacturer", "500", "short", "long");
        assertTrue(controller.getPIMDriverInstance().checkIfProductByNameExists("TestProduct"));
    }

    @Test
    void testCreateManufacturer() throws Exception {
        ShopController controller = new ShopController();
        controller.getShopPopulate().createManufacturer("TestManufacturer");
        assertTrue(controller.getPIMDriverInstance().checkIfManufactureByNameExists("TestManufacturer"));
    }

    @Test
    void testCreateCategory() throws Exception {
        ShopController controller = new ShopController();
        controller.getShopPopulate().createCategory("TestCategory", "");
        assertTrue(controller.getPIMDriverInstance().checkIfCategoryByNameExists("TestCategory"));
    }
}