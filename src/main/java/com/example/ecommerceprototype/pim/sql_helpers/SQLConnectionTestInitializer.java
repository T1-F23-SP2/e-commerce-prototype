package com.example.ecommerceprototype.pim.sql_helpers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;


/*
    A database initializer which populates a test database with test data from the 'custom_data_for_database.sql' file
 */
public class SQLConnectionTestInitializer extends SQLConnectionMainInitializer{
    @Override
    public void initialize(Connection connection) throws SQLException, IOException {
        // First run initialization code from the parent class.
        super.initialize(connection);

        SQLSetupHelper.setupFromResource(connection, "sql/custom_data_for_database.sql");
    }
}