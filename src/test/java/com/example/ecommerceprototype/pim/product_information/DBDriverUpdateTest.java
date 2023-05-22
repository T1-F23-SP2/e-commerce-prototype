package com.example.ecommerceprototype.pim.product_information;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/*
    This is a test class dedicated for testing update methods in DBDriver.

    It inherits from DBDriverAbstractTest, which means that a dedicated dbDriver test instance is
    AUTOMATICALLY created, for use solely by this test class.
    Which means no @BeforeAll setup code is needed.

    Also, the test connection is automatically closed and cleanup after all tests has run within this test class.
    This means no @AfterAll teardown code is needed.

    It also means that any changes to the database made in this file, DOES NOT AFFECT other test classes!
    This means that all test within this test class, is completely independent and isolated from all other test classes.
 */
@DisplayName("Tests for all update methods in DBDriver")
public class DBDriverUpdateTest extends DBDriverAbstractTest {


    /*
        Example test
        This simply shows how to access relevant objects, needed for testing.
     */
    @Test
    void exampleTest() {
        // TODO: Remove example test once ready
        // Access the dedicated dbDriver test instance like so:
        assertNotNull(dbDriver);

        // Also access the dedicated test connection like so:
        assertNotNull(connection);
    }
}