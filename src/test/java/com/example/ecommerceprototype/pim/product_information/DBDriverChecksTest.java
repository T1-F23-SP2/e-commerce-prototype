package com.example.ecommerceprototype.pim.product_information;

import com.example.ecommerceprototype.pim.exceptions.CategoryNotFoundException;
import com.example.ecommerceprototype.pim.exceptions.UUIDNotFoundException;
import com.example.ecommerceprototype.pim.util.ProductList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests for the various check/exists methods")
public class DBDriverChecksTest extends DBDriverAbstractTest{


    @Test
    void testCheckIfProductByUUIDExists() throws UUIDNotFoundException, SQLException, CategoryNotFoundException {
        assertFalse(pimDriver.checkIfProductByUUIDExists("lkdjsf"));

        ProductList productList = pimDriver.getAllProducts();
        ProductInformation pi = productList.get(0);
        String uuid = pi.getProductUUID();


        assertTrue(pimDriver.checkIfProductByUUIDExists(uuid));
    }
}