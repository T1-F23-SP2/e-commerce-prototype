package com.example.ecommerceprototype.pim.sql_helpers;

import com.example.ecommerceprototype.pim.exceptions.sql.SQLDatabaseNotFoundException;
import com.example.ecommerceprototype.pim.exceptions.sql.SQLDuplicateDatabaseException;
import com.example.ecommerceprototype.pim.exceptions.sql.SQLInvalidPasswordException;
import com.example.ecommerceprototype.pim.exceptions.sql.SQLRoleNotFoundException;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class SQLExceptionParserTest {

    @Test
    void parseIsDatabaseNotFound() {
        SQLException mockedException = new SQLException("FATAL: database \"non_existing_database\" does not exist");
        assertInstanceOf(SQLDatabaseNotFoundException.class, SQLExceptionParser.parse(mockedException));
    }

    @Test
    void parseIsRoleNotFound() {
        SQLException mockedException = new SQLException("FATAL: role \"non_existing_user\" does not exist");
        assertInstanceOf(SQLRoleNotFoundException.class, SQLExceptionParser.parse(mockedException));
    }

    @Test
    void parseIsInvalidPassword() {
        SQLException mockedException = new SQLException("FATAL: password authentication failed for user \"some_user\"");
        assertInstanceOf(SQLInvalidPasswordException.class, SQLExceptionParser.parse(mockedException));
    }

    @Test
    void parseIsDuplicateDatabase() {
        SQLException mockedException = new SQLException("ERROR: database \"some_database\" already exists");
        assertInstanceOf(SQLDuplicateDatabaseException.class, SQLExceptionParser.parse(mockedException));
    }

    @Test
    void parseUnknownSQLException() {
        SQLException mockedException = new SQLException("Some unknown exception.");
        // Should return the same object.
        assertSame(mockedException, SQLExceptionParser.parse(mockedException));
    }

}