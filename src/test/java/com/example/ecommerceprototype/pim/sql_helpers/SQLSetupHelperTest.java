package com.example.ecommerceprototype.pim.sql_helpers;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class SQLSetupHelperTest {
    private static TestConnectionWrapper testConnectionWrapper;
    private static Connection connection;

    @BeforeAll
    static void setup() throws SQLException, IOException {
        testConnectionWrapper = new TestConnectionWrapper();
        connection = testConnectionWrapper.setup(connection -> {/* nothing */});
    }


    @DisplayName("Test resource not found exception")
    @Test
    void setupFromResourceWithInvalid() {
        assertThrows(IOException.class, () -> SQLSetupHelper.setupFromResource(connection, "non/existing/resource"));
    }


    @AfterAll
    static void teardown() throws SQLException {
        testConnectionWrapper.teardown();
    }
}