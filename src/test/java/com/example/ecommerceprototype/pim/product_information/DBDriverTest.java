package com.example.ecommerceprototype.pim.product_information;

import com.example.ecommerceprototype.pim.sql_helpers.SQLConnectionTestInitializerTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBDriverTest {
    static DBDriver dbDriver;
    static Connection connection;

    @BeforeAll
    static void setup() {
        try {
            SQLConnectionTestInitializerTest.setup();
            connection = SQLConnectionTestInitializerTest.getConnection();
            dbDriver = DBDriver.getInstance(connection);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
