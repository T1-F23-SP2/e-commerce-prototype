package com.example.ecommerceprototype.pim.sql_helpers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;


/*
    Initializer which setups the database with the specified tables and such as defined in 'database_initialization.sql'
 */

public class SQLConnectionMainInitializer implements SQLInitializer{
    @Override
    public void initialize(Connection connection) throws SQLException, IOException {
        SQLSetupHelper.setupFromResource(connection, "sql/database_initialization.sql");
    }
}