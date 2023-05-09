package com.example.ecommerceprototype.pim.sql_helpers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class SQLConnectionTest {
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
}