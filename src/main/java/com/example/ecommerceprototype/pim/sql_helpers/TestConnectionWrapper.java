package com.example.ecommerceprototype.pim.sql_helpers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public final class TestConnectionWrapper implements AutoCloseable {

    private Connection connection;

    public Connection getConnection() {
        return this.connection;
    }


    public Connection setup(SQLInitializer initializer) throws SQLException, IOException {
        Properties testCredentials = SQLConnection.loadTestProperties();
        /*
            If test database is already present, then drop that database, so it can be recreated to a consistent state.
         */
        if (SQLConnection.isDatabaseInPropertiesPresent(testCredentials))
            SQLConnection.dropDatabase(testCredentials, true);

        this.connection = SQLConnection.getTestConnectionInitializeIfNeeded(initializer);

        return this.getConnection();
    }


    public void teardown() throws SQLException {
        // if Connection is null then do nothing
        if (this.connection == null) return;
        /*
            Database is on purpose not dropped on teardown, so that the database can manually be inspected if needed after test run.
            The database is however recreated before each test run. See comment in setup().
         */


        // If auto commit is not enabled, then
        // commit changes made to test database,
        // So changes to database can be inspected if needed
        if (!this.connection.getAutoCommit()) this.connection.commit();

        this.connection.close();
    }


    // Implements the AutoClosable interface, so that this can be used with the
    // 'try with resource' construct.
    @Override
    public void close() throws Exception {
        this.teardown();
    }
}