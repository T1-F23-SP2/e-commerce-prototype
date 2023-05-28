package com.example.ecommerceprototype.pim.product_information;

import com.example.ecommerceprototype.pim.exceptions.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertFalse;

@DisplayName("Tests for helper methods in DBDriver")
public class DBDriverHelperTest extends DBDriverAbstractTest {
    @Test
    void testCheckIfProductByUUIDNotExists() throws SQLException, NotFoundException {
        assertFalse(pimDriver.checkIfProductByUUIDExists("NotARealUUID!"));
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