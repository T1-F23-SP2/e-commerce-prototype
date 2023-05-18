package com.example.ecommerceprototype.pim.product_information;

import com.example.ecommerceprototype.pim.exceptions.NotFoundException;
import com.example.ecommerceprototype.pim.exceptions.UUIDNotFoundException;
import com.example.ecommerceprototype.pim.sql_helpers.SQLConnectionTestInitializer;
import com.example.ecommerceprototype.pim.sql_helpers.TestConnectionWrapper;
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
    static PIMDriver pimDriver;
    private static TestConnectionWrapper connectionWrapper;
    static Connection connection;

    @BeforeAll
    static void setup() {
        try {
            connectionWrapper = new TestConnectionWrapper();
            connection = connectionWrapper.setup(new SQLConnectionTestInitializer());

            dbDriver = DBDriver.getInstance(connection);
            pimDriver = new PIMDriver();
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

        ProductInformation returnedProduct = null;
        try {
            returnedProduct = pimDriver.getProductByName("Lenovo Ideapad 5 Pro 14\" QHD touch");
        } catch (NotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

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

        ProductInformation returnedProduct = null;
        try {
            returnedProduct = pimDriver.getProductByUUID(uuid);
        } catch (UUIDNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

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

        ArrayList<ProductInformation> returnedList = null;
        try {
            returnedList = (ArrayList<ProductInformation>) pimDriver.getProductsBySerialNumber("LenovoIdeapad5Pro-1234");
        } catch (NotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

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

            ArrayList<ProductInformation> returnedList = null;
            try {
                returnedList = (ArrayList<ProductInformation>) pimDriver.getProductsThatAreHidden();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            assertTrue(returnedList.size() == 2 &&
                    productInformationArrayList.get(0).getProductUUID().equals(returnedList.get(0).getProductUUID()) &&
                    productInformationArrayList.get(productInformationArrayList.size() - 1).getProductUUID()
                            .equals(returnedList.get(returnedList.size() - 1).getProductUUID()));

            // Remove inserted, hidden products.
            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = connection.prepareStatement("DELETE FROM products WHERE id=12; DELETE FROM products WHERE id=13");
                preparedStatement.execute();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
    }

    @Test
    void testGetNoProductThatIsHidden() {
        ArrayList<ProductInformation> returnedList = null;
        try {
            returnedList = (ArrayList<ProductInformation>) pimDriver.getProductsThatAreHidden();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        assertTrue(returnedList.isEmpty());
    }

    @Test
    void testGetProductsByCategoryName() {
        ArrayList<ProductInformation> productInformationArrayList = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM getProductsByCategoryName(?)");
            preparedStatement.setString(1, "TV");
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

        ArrayList<ProductInformation> returnedList = null;
        try {
            returnedList = (ArrayList<ProductInformation>) pimDriver.getProductsByCategoryName("TV");
        } catch (NotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

        assertTrue(productInformationArrayList.size() == returnedList.size() &&
                productInformationArrayList.get(0).getProductUUID().equals(returnedList.get(0).getProductUUID()) &&
                productInformationArrayList.get(productInformationArrayList.size()-1).getProductUUID()
                        .equals(returnedList.get(returnedList.size()-1).getProductUUID()));
    }

    @Test
    void testGetNoProductByCategoryName() {
        ArrayList<ProductInformation> returnedList = null;
        try {
            returnedList = (ArrayList<ProductInformation>) pimDriver.getProductsByCategoryName("PC & tablets");
        } catch (NotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

        assertTrue(returnedList.isEmpty());
    }

    @Test
    void testGetProductsByManufactureName() {
        ArrayList<ProductInformation> productInformationArrayList = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM getProductsByMAnufactureName(?)");
            preparedStatement.setString(1, "Lenovo");
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

        ArrayList<ProductInformation> returnedList = null;
        try {
            returnedList = (ArrayList<ProductInformation>) pimDriver.getProductsByManufactureName("Lenovo");
        } catch (NotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

        assertTrue(productInformationArrayList.size() == returnedList.size() &&
                productInformationArrayList.get(0).getProductUUID().equals(returnedList.get(0).getProductUUID()) &&
                productInformationArrayList.get(productInformationArrayList.size()-1).getProductUUID()
                        .equals(returnedList.get(returnedList.size()-1).getProductUUID()));
    }

    @Test
    void testGetNoProductByManufactureName() {
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Manufactures (id, name, support_phone, support_mail) " +
                    "VALUES (10, 'NAN', 21212121, 'NAN@mail.com')");
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e.toString());
        }
        ArrayList<ProductInformation> returnedList = null;
        try {
            returnedList = (ArrayList<ProductInformation>) pimDriver.getProductsByManufactureName("NAN");
        } catch (NotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

        assertTrue(returnedList.isEmpty());
    }

    @Test
    void testGetProductsByDiscountName() {
        ArrayList<ProductInformation> productInformationArrayList = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM getProductsByDiscountName(?)");
            preparedStatement.setString(1, "Spring sale");
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

        ArrayList<ProductInformation> returnedList = null;
        try {
            returnedList = (ArrayList<ProductInformation>) pimDriver.getProductsByDiscountName("Spring sale");
        } catch (NotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

        assertTrue(productInformationArrayList.size() == returnedList.size() &&
                productInformationArrayList.get(0).getProductUUID().equals(returnedList.get(0).getProductUUID()) &&
                productInformationArrayList.get(productInformationArrayList.size()-1).getProductUUID()
                        .equals(returnedList.get(returnedList.size()-1).getProductUUID()));
    }

    @Test
    void testGetCategoryByProductUUID() {
        String uuid = "";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM getProductById(?)");
            preparedStatement.setInt(1, 10);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            resultSet.next();
            uuid = resultSet.getString("product_uuid");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            assertEquals("TV", pimDriver.getCategoryByProductUUID(uuid).getName());
        } catch (UUIDNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetCategoryByCategoryID() {
        try {
            assertEquals("TV", pimDriver.getCategoryByCategoryID(8).getName());
        } catch (NotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetCategoryByName() {
        try {
            assertEquals(pimDriver.getCategoryByCategoryID(8).getName(), pimDriver.getCategoryByName("TV").getName());
        } catch (NotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetSpecificationByProductUUID() {
        String uuid = "";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM getProductById(?)");
            preparedStatement.setInt(1, 1);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            resultSet.next();
            uuid = resultSet.getString("product_uuid");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            assertEquals("AMD Ryzen 5600U", pimDriver.getSpecificationByProductUUID(uuid).get("CPU"));
        } catch (UUIDNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetManufactureByProductUUID() {
        String uuid = "";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM getProductById(?)");
            preparedStatement.setInt(1, 1);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            resultSet.next();
            uuid = resultSet.getString("product_uuid");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            assertEquals("Lenovo", pimDriver.getManufactureByProductUUID(uuid).getName());
        } catch (UUIDNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetManufactureByName() {
        try {
            assertEquals("12345678", pimDriver.getManufactureByName("Lenovo").getSupportPhoneNumber());
        } catch (NotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetDiscountByProductUUID() {
        String uuid = "";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT product_uuid FROM products WHERE id = ?");
            preparedStatement.setInt(1, 1);
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getResultSet();
            resultSet.next();
            uuid = resultSet.getString("product_uuid");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            assertEquals("Spring sale", pimDriver.getDiscountByProductUUID(uuid).getName());
        } catch (UUIDNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetDiscountByName() { // Boring looking test - but then again, if no exception occurs, isn't it technically working?
        try {
            assertEquals("Spring sale", pimDriver.getDiscountByName("Spring sale").getName());
        } catch (NotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetNoDiscountByName() { // Boring looking test - but then again, if no exception occurs, isn't it technically working?
        assertThrows(
                NotFoundException.class,
                () -> pimDriver.getDiscountByName("NotRealDiscount!")
                );
    }

    @AfterAll
    static void end() {
        try {
            connectionWrapper.teardown();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}