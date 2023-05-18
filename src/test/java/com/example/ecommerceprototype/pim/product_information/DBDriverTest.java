package com.example.ecommerceprototype.pim.product_information;

import com.example.ecommerceprototype.pim.exceptions.*;
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
    private static TestConnectionWrapper connectionWrapper;
    static Connection connection;

    @BeforeAll
    static void setup() {
        try {
            connectionWrapper = new TestConnectionWrapper();
            connection = connectionWrapper.setup(new SQLConnectionTestInitializer());

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

        ProductInformation returnedProduct = null;
        try {
            returnedProduct = dbDriver.getProductByName("Lenovo Ideapad 5 Pro 14\" QHD touch");
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
            returnedProduct = dbDriver.getProductByUUID(uuid);
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
            returnedList = (ArrayList<ProductInformation>) dbDriver.getProductsBySerialNumber("LenovoIdeapad5Pro-1234");
        } catch (NotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

        assertTrue(productInformationArrayList.size() == returnedList.size() &&
                productInformationArrayList.get(0).getProductUUID().equals(returnedList.get(0).getProductUUID()) &&
                productInformationArrayList.get(productInformationArrayList.size() - 1).getProductUUID()
                        .equals(returnedList.get(returnedList.size() - 1).getProductUUID()));
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
            returnedList = (ArrayList<ProductInformation>) dbDriver.getProductsThatAreHidden();
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
            returnedList = (ArrayList<ProductInformation>) dbDriver.getProductsThatAreHidden();
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
            returnedList = (ArrayList<ProductInformation>) dbDriver.getProductsByCategoryName("TV");
        } catch (NotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

        assertTrue(productInformationArrayList.size() == returnedList.size() &&
                productInformationArrayList.get(0).getProductUUID().equals(returnedList.get(0).getProductUUID()) &&
                productInformationArrayList.get(productInformationArrayList.size() - 1).getProductUUID()
                        .equals(returnedList.get(returnedList.size() - 1).getProductUUID()));
    }

    @Test
    void testGetNoProductByCategoryName() {
        ArrayList<ProductInformation> returnedList = null;
        try {
            returnedList = (ArrayList<ProductInformation>) dbDriver.getProductsByCategoryName("PC & tablets");
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
            returnedList = (ArrayList<ProductInformation>) dbDriver.getProductsByManufactureName("Lenovo");
        } catch (NotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

        assertTrue(productInformationArrayList.size() == returnedList.size() &&
                productInformationArrayList.get(0).getProductUUID().equals(returnedList.get(0).getProductUUID()) &&
                productInformationArrayList.get(productInformationArrayList.size() - 1).getProductUUID()
                        .equals(returnedList.get(returnedList.size() - 1).getProductUUID()));
    }

    @Test
    void testGetNoProductByManufactureName() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Manufactures (id, name, support_phone, support_mail) " +
                    "VALUES (10, 'NAN', 21212121, 'NAN@mail.com')");
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e.toString());
        }
        ArrayList<ProductInformation> returnedList = null;
        try {
            returnedList = (ArrayList<ProductInformation>) dbDriver.getProductsByManufactureName("NAN");
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
            returnedList = (ArrayList<ProductInformation>) dbDriver.getProductsByDiscountName("Spring sale");
        } catch (NotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

        assertTrue(productInformationArrayList.size() == returnedList.size() &&
                productInformationArrayList.get(0).getProductUUID().equals(returnedList.get(0).getProductUUID()) &&
                productInformationArrayList.get(productInformationArrayList.size() - 1).getProductUUID()
                        .equals(returnedList.get(returnedList.size() - 1).getProductUUID()));
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
            assertEquals("TV", dbDriver.getCategoryByProductUUID(uuid).getName());
        } catch (UUIDNotFoundException | SQLException | CategoryNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetCategoryByCategoryID() {
        try {
            assertEquals("TV", dbDriver.getCategoryByCategoryID(8).getName());
        } catch (NotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetCategoryByName() {
        try {
            assertEquals(dbDriver.getCategoryByCategoryID(8).getName(), dbDriver.getCategoryByName("TV").getName());
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
            assertEquals("AMD Ryzen 5600U", dbDriver.getSpecificationByProductUUID(uuid).get("CPU"));
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
            assertEquals("Lenovo", dbDriver.getManufactureByProductUUID(uuid).getName());
        } catch (UUIDNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetManufactureByName() {
        try {
            assertEquals("12345678", dbDriver.getManufactureByName("Lenovo").getSupportPhoneNumber());
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
            assertEquals("Spring sale", dbDriver.getDiscountByProductUUID(uuid).getName());
        } catch (UUIDNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetDiscountByName() { // Boring looking test - but then again, if no exception occurs, isn't it technically working?
        try {
            assertEquals("Spring sale", dbDriver.getDiscountByName("Spring sale").getName());
        } catch (NotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetNoDiscountByName() {
        assertThrows(
                NotFoundException.class,
                () -> dbDriver.getDiscountByName("NotRealDiscount!")
        );
    }

    @Test
    void testDeleteProductByUUID() {
        // Get UUID for a product
        final String uuid;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT product_uuid FROM products WHERE id = ?");
            preparedStatement.setInt(1, 3);
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getResultSet();
            resultSet.next();
            uuid = resultSet.getString("product_uuid");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Delete product
        try {
            dbDriver.deleteProductByUUID(uuid);
        } catch (SQLException | UUIDNotFoundException e) {
            throw new RuntimeException(e);
        }

        assertThrows(
                UUIDNotFoundException.class,
                () -> {
                    try {
                        dbDriver.getProductByUUID(uuid);
                    } catch (SQLException e) {
                        System.out.println("SQL Exception...");
                    }
                }
        );
    }

    @Test
    void testDeleteProductCategoryByName() {
        final String name;
        try {
            ProductCategory cat = dbDriver.getCategoryByCategoryID(1);
            name = cat.getName();
            dbDriver.deleteProductCategoryByName(name);
        } catch (CategoryNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        assertThrows(
                CategoryNotFoundException.class,
                () -> {
                    try {
                        dbDriver.getCategoryByName(name);
                    } catch (SQLException e) {
                        System.out.println("SQL Exception...");
                    }
                }
        );
    }

    @Test
    void testDeleteManufactureByName() {
        final String name;
        try {
            ManufacturingInformation man = dbDriver.getManufactureByName(dbDriver.getAllManufactures().get(1).getName());
            name = man.getName();
            dbDriver.deleteManufactureByName(name);
        } catch (SQLException | ManufactureNotFoundException e) {
            throw new RuntimeException(e);
        }
        assertThrows(
                ManufactureNotFoundException.class,
                () -> {
                    try {
                        dbDriver.getManufactureByName(name);
                    } catch (SQLException e) {
                        System.out.println("SQL Exception...");
                    }
                }
        );
    }

    @Test
    void testDeleteSpecificationByProductUUIDAndKey() {
        // Get UUID for a product
        final String uuid;
        final String key;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT product_uuid FROM products WHERE id = ?");
            preparedStatement.setInt(1, 2);
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getResultSet();
            resultSet.next();
            uuid = resultSet.getString("product_uuid");

            ProductSpecification ps = dbDriver.getSpecificationByProductUUID(uuid);
            key = (String) ps.keySet().toArray()[0];

        } catch (SQLException | UUIDNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Delete specification
        try {
            dbDriver.deleteSpecificationByProductUUIDAndKey(uuid, key);
        } catch (SQLException | UUIDNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Assert the specification name no longer exists for that product.
        try {
            assertFalse(dbDriver.getSpecificationByProductUUID(uuid).containsKey(key));
        } catch (UUIDNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testDeleteDiscountByName() {
        // Create discount to be deleted
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO discounts(name, start_date, end_date) VALUES ('SomeDiscountToBeDeleted', NOW(), NOW())");
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        final String name;
        try {
            DiscountInformation dis = dbDriver.getDiscountByName("SomeDiscountToBeDeleted");
            name = dis.getName();
            dbDriver.deleteDiscountByName(name);
        } catch (SQLException | DiscountNotFoundException e) {
            throw new RuntimeException(e);
        }
        assertThrows(
                DiscountNotFoundException.class,
                () -> {
                    try {
                        dbDriver.getDiscountByName(name);
                    } catch (SQLException e) {
                        System.out.println("SQL Exception...");
                    }
                }
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