package com.example.ecommerceprototype.pim.sql_helpers;

import com.example.ecommerceprototype.pim.exceptions.sql.SQLDuplicateDatabaseException;
import com.example.ecommerceprototype.pim.exceptions.sql.SQLInvalidPasswordException;
import com.example.ecommerceprototype.pim.exceptions.sql.SQLRoleNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
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

        String expectedMsg = (String.format("""
        Could not find file: src/main/resources/com%sexample%secommerceprototype%spim%snon-existing.credentials
        
        Look in src/main/resources/com/example/ecommerceprototype/pim/credentials/README.md, for how to setup credentials.
        
        """, File.separatorChar, File.separatorChar, File.separatorChar, File.separatorChar));

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


//    @Test
//    void testInvalidUser() throws IOException {
//        Properties credentials = SQLConnection.loadTestProperties();
//        credentials.setProperty("username", "non_existing_user_b652c7e4");
//        assertThrows(SQLRoleNotFoundException.class, () -> {
//           SQLConnection.getConnectionFromProperties(credentials);
//        });
//    }


    @Test
    void testInvalidPassword() throws IOException {
        Properties credentials = SQLConnection.loadTestProperties();
        credentials.setProperty("password", "wrong password ddd342a805e8");
        assertThrows(SQLInvalidPasswordException.class, () -> {
            SQLConnection.getConnectionFromProperties(credentials);
        });
    }


    @Test
    void testCreateDuplicateDatabase() throws IOException, SQLException {
        Properties credentials = SQLConnection.loadTestProperties();

        // Try to create a database
        // Sets the database to a name which is unlikely to already be present in system
        credentials.setProperty("database", "pim_test_930c507f_1902_4127_bad6_c51c15ee6244");
        assertDoesNotThrow(() -> SQLConnection.createDatabase(credentials));

        assertThrows(SQLDuplicateDatabaseException.class, () -> SQLConnection.createDatabase(credentials));

        // Then drop the database again
        assertDoesNotThrow(() -> SQLConnection.dropDatabase(credentials, false));
    }
}