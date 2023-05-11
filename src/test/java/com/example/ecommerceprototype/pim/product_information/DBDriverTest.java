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

    @Test
    void testGetProductByName() {
        String expectedUUID = "";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM getProductByName(?)");
            preparedStatement.setString(1, "Lenovo Ideapad 5 Pro 14\" QHD touch");
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            resultSet.next();
            expectedUUID = resultSet.getString("product_UUID");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ProductInformation returnedProduct = dbDriver.getProductByName("Lenovo Ideapad 5 Pro 14\" QHD touch");

        assertEquals(expectedUUID, returnedProduct.getProductUUID());
    }

    @Test
    void testGetProductByUUID() {
        String expectedName = "";
        String uuid = "";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM getProductById(?)");
            preparedStatement.setInt(1, 1);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            resultSet.next();
            expectedName = resultSet.getString("name");
            uuid = resultSet.getString("product_uuid");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ProductInformation returnedProduct = dbDriver.getProductByUUID(uuid);

        assertEquals(expectedName, returnedProduct.getName());
    }

    // TODO: Create tests for the rest of the refactored get-methods.

}
