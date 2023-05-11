package com.example.ecommerceprototype.pim.product_information;

import com.example.ecommerceprototype.pim.sql_helpers.SQLConnectionTestInitializerTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

    @Test
    void testGetProductsBySerialNumber() {
        ArrayList<ProductInformation> productInformationArrayList = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM getProductsBySerialNumber(?)");
            preparedStatement.setString(1, "LenovoIdeapad5Pro-1234");
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            while (resultSet.next()) {

                ProductInformation productInformation = new ProductInformation();

                productInformation.setUUID(resultSet.getString("product_UUID"))
                        .setName(resultSet.getString("name"))
                        .setSerialNumber(resultSet.getString("serial_number"))
                        .setShortDescription(resultSet.getString("short_description"))
                        .setIsHidden(resultSet.getBoolean("is_hidden"))
                        .setLongDescription(resultSet.getString("long_description"));

                productInformationArrayList.add(productInformation);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ArrayList<ProductInformation> returnedList = (ArrayList<ProductInformation>) dbDriver.getProductsBySerialNumber("LenovoIdeapad5Pro-1234");

        assertTrue(productInformationArrayList.size() == returnedList.size() &&
                productInformationArrayList.get(0).getProductUUID().equals(returnedList.get(0).getProductUUID()) &&
                productInformationArrayList.get(productInformationArrayList.size()-1).getProductUUID()
                        .equals(returnedList.get(returnedList.size()-1).getProductUUID()));
    }

    @Test
    void testGetProductsThatAreHidden() {
        ArrayList<ProductInformation> productInformationArrayList = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO products (id, product_uuid, name, serial_number, short_description, product_categories_id, manufacture_id, is_hidden, long_description) " +
                    "VALUES (12, 'b07f44a9-5963-4499-bcf3-f55007df1b59', 'One', 'OneSerial', 'OneShort', 2, 4, true, 'OneLong')," +
                    "(13, 'b07f44a9-5963-4499-bcf3-f55007df1b58', 'Two', 'TwoSerial', 'TwoShort', 8, 4, true, 'TwoLong')");
            preparedStatement.execute();

            preparedStatement = connection.prepareStatement("SELECT * FROM getProductsThatAreHidden()");
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            while (resultSet.next()) {

                ProductInformation productInformation = new ProductInformation();

                productInformation.setUUID(resultSet.getString("product_UUID"))
                        .setName(resultSet.getString("name"))
                        .setSerialNumber(resultSet.getString("serial_number"))
                        .setShortDescription(resultSet.getString("short_description"))
                        .setIsHidden(resultSet.getBoolean("is_hidden"))
                        .setLongDescription(resultSet.getString("long_description"));

                productInformationArrayList.add(productInformation);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ArrayList<ProductInformation> returnedList = (ArrayList<ProductInformation>) dbDriver.getProductsThatAreHidden();

        assertTrue(returnedList.size() == 2 &&
                productInformationArrayList.get(0).getProductUUID().equals(returnedList.get(0).getProductUUID()) &&
                productInformationArrayList.get(productInformationArrayList.size()-1).getProductUUID()
                        .equals(returnedList.get(returnedList.size()-1).getProductUUID()));
    }

    @Test
    void testNoProductThatIsHidden() {
        ArrayList<ProductInformation> returnedList = (ArrayList<ProductInformation>) dbDriver.getProductsThatAreHidden();

        assertTrue(returnedList.isEmpty());
    }
    // TODO: Create tests for the rest of the refactored get-methods.

    @AfterAll
    static void end() {
        try {
            SQLConnectionTestInitializerTest.tearDown();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
