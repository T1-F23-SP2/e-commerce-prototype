package com.example.ecommerceprototype.pim.sql_helpers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
@FunctionalInterface
public interface SQLInitializer {
    /*
        An interface providing a way to pass initialization code, which will be run after a database is created.
        Should always throw an exception, if initialization fails.
     */
    public void initialize(Connection connection) throws SQLException, IOException;
}