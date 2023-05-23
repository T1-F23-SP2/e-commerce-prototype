package com.example.ecommerceprototype.pim.product_information;

import com.example.ecommerceprototype.pim.sql_helpers.SQLConnectionTestInitializer;
import com.example.ecommerceprototype.pim.sql_helpers.TestConnectionWrapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;


/*
    This is the root test class for all tests which tests the DBDriver.

    The tests for DBDriver have been split into multiple files, in order to better structure the code.
    This is largely because DBDriver is so dense to test, with a very large number of methods.

    Splitting testing into smaller tests files, based on tests which tests similar aspects, allows for easier reading
    and makes it easier to find relevant tests for each method.
 */
public abstract class DBDriverAbstractTest {
    private static TestConnectionWrapper connectionWrapper;
    protected static Connection connection;
    protected static DBDriver dbDriver;

    protected static PIMDriver pimDriver;

    /*
        Setup code which is run before all tests for each test class which inherits from this class.

        I.e. it is run once for each subclass.

        I.e. a new test connection and a new DBDriver instance is created for each subclass, before any tests
        within that test class is run.
     */
    @BeforeAll
    static void setup() throws SQLException, IOException {
        connectionWrapper = new TestConnectionWrapper();

        // Create a test connection with full database setup, with example data.
        connection = connectionWrapper.setup(new SQLConnectionTestInitializer());

        // Set the DBDriver instance with the newly created test connection.
        dbDriver = DBDriver.setInstance(connection);

        pimDriver = new PIMDriver(dbDriver);
    }


    /*
        Teardown code which is run after all tests for each test class which inherits from this class.
        I.e. it is run once for each subclass.

        I.e. the test connection is automatically closed and cleanup, after each test subclass has executed.
     */
    @AfterAll
    static void teardown() throws SQLException {
        connectionWrapper.teardown();
    }
}