package com.example.ecommerceprototype.pim.sql_helpers;

import com.example.ecommerceprototype.pim.util.Functions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class SQLExampleTest {

    static Connection connection;
    static TestConnectionWrapper testConnectionWrapper;

    @BeforeAll
    static void setup() throws SQLException, IOException {
        /*
            *** IMPORTANT ***
            Remember to do setup in a static @BeforeAll method.
            AND REMEMBER to do a static @AfterAll method.
         */

        // Create a test connection wrapper. This facilitates the creation of a test connection
        // And proper setup of a newly created database each time.
        testConnectionWrapper = new TestConnectionWrapper();

        // Setup database
        // This will recreate a test database
        connection = testConnectionWrapper.setup(conn -> {
            // In this lambda function can you run any setup needed to set up the database.
            // `conn` input variable is the connection to the database.
            // This can be running a sql script.


            // Executing the sql script in the connection `conn` variable.
            SQLSetupHelper.setupFromResource(conn, "sql/tests/test_connection_table_setup.sql");
            /*
                The path provided is relative to pim resource folder.
                However, an InputStream can also be provided.
             */

            // Note that multiple sql scripts can be executed if needed.
        });
        // The return connection object is persistent across all tests within a single file.
    }


    @Test
    void testValue() throws SQLException {
        String expected = "Hello world!";

        // Can now freely use the connection to interact with the test database.
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT value FROM test_connection_table WHERE value = ?");
        preparedStatement.setString(1, expected);

        ResultSet resultSet = preparedStatement.executeQuery();

        // Asserts that the returned result set contains at least 1 row.
        assertTrue(resultSet.next());


        assertEquals(expected, resultSet.getString("value"));
    }


    @Test
    void testUUID() throws SQLException {
        String expected = "Hello world!";
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT uuid FROM test_connection_table WHERE value = ?");
        preparedStatement.setString(1, expected);
        ResultSet resultSet = preparedStatement.executeQuery();
        assertTrue(resultSet.next());

        assertTrue(Functions.isUUIDv4(resultSet.getString("uuid")));
    }




    @AfterAll
    static void teardown() throws SQLException {
        testConnectionWrapper.teardown();
    }
}