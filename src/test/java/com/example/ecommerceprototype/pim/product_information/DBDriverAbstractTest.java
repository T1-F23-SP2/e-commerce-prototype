package com.example.ecommerceprototype.pim.product_information;

import com.example.ecommerceprototype.pim.sql_helpers.SQLConnectionTestInitializer;
import com.example.ecommerceprototype.pim.sql_helpers.TestConnectionWrapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;


/*
    This is the root test class for all test which tests the DBDriver.
    The tests for DBDriver have been split into multiple files, in order to better structure the code.
    This is largely because DBDriver is so dense to test, with a very large number of methods.
 */
public abstract class DBDriverAbstractTest {
    protected static DBDriver dbDriver;
    protected static TestConnectionWrapper connectionWrapper;
    protected static Connection connection;

    /*
        Setup code which is run before all tests for each test class which inherits from this class.
        I.e. it is run once for each subclass.
     */
    @BeforeAll
    static void setup() {
        try {
            connectionWrapper = new TestConnectionWrapper();
            connection = connectionWrapper.setup(new SQLConnectionTestInitializer());

            dbDriver = DBDriver.setInstance(connection);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }



    /*
        Teardown code which is run after all tests for each test class which inherits from this class.
        I.e. it is run once for each subclass.
     */

    @AfterAll
    static void teardown() {
        try {
            connectionWrapper.teardown();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}