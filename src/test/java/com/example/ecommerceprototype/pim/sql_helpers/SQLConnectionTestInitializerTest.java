package com.example.ecommerceprototype.pim.sql_helpers;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/*
    Tests that the setup script is run properly with setup database.
 */

class SQLConnectionTestInitializerTest {
    private class ExpectedColumns {
        static public ResultSet getTableColumns(Connection connection, String tableName) throws SQLException {
            PreparedStatement statement = connection.prepareStatement("""
                                        SELECT column_name
                                          FROM information_schema.columns
                                         WHERE table_schema = 'public'
                                           AND table_name   = ?
                                        """);
            statement.setString(1, tableName);
            return statement.executeQuery();
        }


        private String[] columns;
        private int counter = 0;

        public ExpectedColumns(String ...columns) {
            this.columns = columns;
        }

        public String next() {
            if (this.isDone()) return null;

            String output = columns[counter];
            counter++;
            return output;
        }

        public Boolean isDone() {
            return counter >= columns.length;
        }

    }

    private static TestConnectionWrapper testConnectionWrapper;
    private static Connection connection;
    @BeforeAll
    static void setup() throws SQLException, IOException {
        testConnectionWrapper = new TestConnectionWrapper();
        connection = testConnectionWrapper.setup(new SQLConnectionTestInitializer());
    }


    @DisplayName("Check 'product' table is good")
    @Test
    void testProductsTable() throws SQLException, IOException {
        // Set expected column names
        ExpectedColumns expectedColumns = new ExpectedColumns("id", "product_uuid", "name", "serial_number", "short_description", "product_categories_id", "manufacture_id", "is_hidden", "long_description");

        // Fetch columns specs from database for table "products"
        ResultSet resultSet = ExpectedColumns.getTableColumns(connection, "products");

        // Iterate through each column and check they match expected names
        while (resultSet.next()) {
            assertEquals(expectedColumns.next(), resultSet.getString("column_name"));
        }

        // Assert that all expected columns have been checked.
        assertTrue(expectedColumns.isDone());
    }

    @DisplayName("Check 'discounts' table is good")
    @Test
    void testDiscountsTable() throws SQLException, IOException {
        // Set expected column names
        ExpectedColumns expectedColumns = new ExpectedColumns("id", "name", "start_date", "end_date");

        // Fetch columns specs from database for table "products"
        ResultSet resultSet = ExpectedColumns.getTableColumns(connection, "discounts");

        // Iterate through each column and check they match expected names
        while (resultSet.next()) {
            assertEquals(expectedColumns.next(), resultSet.getString("column_name"));
        }

        // Assert that all expected columns have been checked.
        assertTrue(expectedColumns.isDone());
    }

    @DisplayName("Check 'manufactures' table is good")
    @Test
    void testManufacturesTable() throws SQLException, IOException {
        // Set expected column names
        ExpectedColumns expectedColumns = new ExpectedColumns("id", "name", "support_phone", "support_mail");

        // Fetch columns specs from database for table "products"
        ResultSet resultSet = ExpectedColumns.getTableColumns(connection, "manufactures");

        // Iterate through each column and check they match expected names
        while (resultSet.next()) {
            assertEquals(expectedColumns.next(), resultSet.getString("column_name"));
        }

        // Assert that all expected columns have been checked.
        assertTrue(expectedColumns.isDone());
    }

    @DisplayName("Check 'price_history' table is good")
    @Test
    void testPrice_historyTable() throws SQLException, IOException {
        // Set expected column names
        ExpectedColumns expectedColumns = new ExpectedColumns("id", "price", "wholesale_price", "time_of_creation", "product_id", "discount_id");

        // Fetch columns specs from database for table "products"
        ResultSet resultSet = ExpectedColumns.getTableColumns(connection, "price_history");

        // Iterate through each column and check they match expected names
        while (resultSet.next()) {
            assertEquals(expectedColumns.next(), resultSet.getString("column_name"));
        }

        // Assert that all expected columns have been checked.
        assertTrue(expectedColumns.isDone());
    }

    @DisplayName("Check 'product_categories' table is good")
    @Test
    void testProduct_categoriesTable() throws SQLException, IOException {
        // Set expected column names
        ExpectedColumns expectedColumns = new ExpectedColumns("id", "name", "parent_id");

        // Fetch columns specs from database for table "products"
        ResultSet resultSet = ExpectedColumns.getTableColumns(connection, "product_categories");

        // Iterate through each column and check they match expected names
        while (resultSet.next()) {
            assertEquals(expectedColumns.next(), resultSet.getString("column_name"));
        }

        // Assert that all expected columns have been checked.
        assertTrue(expectedColumns.isDone());
    }

    @DisplayName("Check 'specification_names' table is good")
    @Test
    void testSpecification_namesTable() throws SQLException, IOException {
        // Set expected column names
        ExpectedColumns expectedColumns = new ExpectedColumns("id", "name");

        // Fetch columns specs from database for table "products"
        ResultSet resultSet = ExpectedColumns.getTableColumns(connection, "specification_names");

        // Iterate through each column and check they match expected names
        while (resultSet.next()) {
            assertEquals(expectedColumns.next(), resultSet.getString("column_name"));
        }

        // Assert that all expected columns have been checked.
        assertTrue(expectedColumns.isDone());
    }

    @DisplayName("Check 'specifications' table is good")
    @Test
    void testSpecificationsTable() throws SQLException, IOException {
        // Set expected column names
        ExpectedColumns expectedColumns = new ExpectedColumns("product_id", "specification_names_id", "specification_value");

        // Fetch columns specs from database for table "products"
        ResultSet resultSet = ExpectedColumns.getTableColumns(connection, "specifications");

        // Iterate through each column and check they match expected names
        while (resultSet.next()) {
            assertEquals(expectedColumns.next(), resultSet.getString("column_name"));
        }

        // Assert that all expected columns have been checked.
        assertTrue(expectedColumns.isDone());
    }




    @AfterAll
    static void tearDown() throws SQLException {
        testConnectionWrapper.teardown();
    }

}