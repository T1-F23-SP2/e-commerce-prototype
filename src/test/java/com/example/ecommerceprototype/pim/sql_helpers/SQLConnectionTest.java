package com.example.ecommerceprototype.pim.sql_helpers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class SQLConnectionTest {

    @Test
    void testLoadProperties() throws IOException {
        Properties exampleCredentials = SQLConnection.loadProperties("example.credentials");

        assertNotNull(exampleCredentials);
        assertEquals("jdbc:postgresql://localhost:5432/", exampleCredentials.getProperty("host"));
        assertEquals("e_commerce_pim_db", exampleCredentials.getProperty("database"));
        assertEquals("my_user", exampleCredentials.getProperty("username"));
        assertEquals("My strong password", exampleCredentials.getProperty("password"));
    }

    @Test
    void testNotFoundPropertiesFile() {
        Exception exception = assertThrows(IOException.class, () -> {
            SQLConnection.loadProperties("non-existing.credentials");
        });

        String expectedMsg = """
                Could not find file: src/main/resources/com/example/ecommerceprototype/pim/non-existing.credentials
                                
                Look in src/main/resources/com/example/ecommerceprototype/pim/credentials/README.md, for how to setup credentials.
                
                """;

        assertEquals(expectedMsg, exception.getMessage());
    }


    @DisplayName("Test creating and dropping database")
    @Test
    void testDropAndCreateDatabase() throws IOException, SQLException {
        Properties credentials = SQLConnection.loadTestProperties();

        // Try to create a database
        // Sets the database to a name which is unlikely to already be present in system
        credentials.setProperty("database", "pim_test_930c507f_1902_4127_bad6_6dbb40a6ec3b");
        assertDoesNotThrow(() -> SQLConnection.createDatabase(credentials));

        // Assert that the created database is in the server
        assertTrue(SQLConnection.isDatabaseInPropertiesPresent(credentials));

        assertDoesNotThrow(() -> SQLConnection.dropDatabase(credentials, false));

        // Assert that the dropped database is no longer in the server
        assertFalse(SQLConnection.isDatabaseInPropertiesPresent(credentials));
    }



    /*
        This is the only test allowed to interact with the main system.
        This test simple assures that it can successfully connect to the main system.
     */
    @Test
    void getMainConnectionInitializeIfNeeded() {
        assertDoesNotThrow(() -> {
            Connection connection = SQLConnection.getMainConnectionInitializeIfNeeded();

            // Close connection after test.
            connection.close();
        });
    }

    @Test
    void getTestConnectionInitializeIfNeeded() {
        assertDoesNotThrow(() -> {
            Connection connection = SQLConnection.getTestConnectionInitializeIfNeeded();

            // Close connection after test.
            connection.close();
        });
    }
}