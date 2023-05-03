package com.example.ecommerceprototype.pim.sql_helpers;

import com.example.ecommerceprototype.pim.sql_helpers.SQLConnection;
import com.example.ecommerceprototype.pim.sql_helpers.SQLInitializer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public final class TestConnectionWrapper {

    private Connection connection;

    public Connection getConnection() {
        return this.connection;
    }


    public Connection setup(SQLInitializer initializer) throws SQLException, IOException {
        Properties testCredentials = SQLConnection.loadTestProperties();
        /*
            If test database is already present, then drop that database, so it can be recreated to a consistent state.
         */
        if (SQLConnection.isDatabaseInPropertiesPresent(testCredentials)) SQLConnection.dropDatabase(testCredentials, true);

        this.connection = SQLConnection.getTestConnectionInitializeIfNeeded(initializer);

        return this.getConnection();
    }



    public void teardown() throws SQLException {
        /*
            Database is on purpose not dropped on teardown, so that the database can manually be inspected if needed after test run.
            The database is however recreated before each test run. See comment in setup().
         */
        this.connection.close();
    }

}