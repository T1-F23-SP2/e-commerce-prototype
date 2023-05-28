package com.example.ecommerceprototype.pim.product_information;

import com.example.ecommerceprototype.pim.exceptions.CategoryNotFoundException;
import com.example.ecommerceprototype.pim.exceptions.UUIDNotFoundException;
import com.example.ecommerceprototype.pim.util.ProductList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Tests for helper methods in DBDriver")
public class DBDriverHelperTest extends DBDriverAbstractTest {
    @Test
    void testCheckIfProductByUUIDNotExists() throws SQLException, UUIDNotFoundException, CategoryNotFoundException {
        assertFalse(pimDriver.checkIfProductByUUIDExists("NotARealUUID!"));

        ProductList productList = pimDriver.getAllProducts();
        ProductInformation pi = productList.get(0);
        String uuid = pi.getProductUUID();


        assertTrue(pimDriver.checkIfProductByUUIDExists(uuid));
    }

    @Test
    void testCheckIfCategoryByNameNotExists() {
        assertFalse(pimDriver.checkIfCategoryByNameExists("NotARealCategoryName!"));
    }

    @Test
    void testCheckIfManufactureByNameNotExists() {
        assertFalse(pimDriver.checkIfManufactureByNameExists("NotARealManufacturerName!"));
    }

    @Test
    void testCheckIfDiscountByNameNotExists() {
        assertFalse(pimDriver.checkIfDiscountByNameExists("NotARealDiscountName!"));
    }

    @Test
    void testCheckIfProductBySerialNotExists() {
        assertFalse(pimDriver.checkIfDiscountByNameExists("NotARealDiscountName!"));
    }

    @Test
    void testCheckIfCategoryByIDNotExists() {
        assertFalse(dbDriver.categoryByIdExists(Integer.MIN_VALUE));
    }
}