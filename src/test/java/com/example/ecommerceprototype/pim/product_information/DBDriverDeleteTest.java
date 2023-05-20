package com.example.ecommerceprototype.pim.product_information;

import com.example.ecommerceprototype.pim.exceptions.CategoryNotFoundException;
import com.example.ecommerceprototype.pim.exceptions.DiscountNotFoundException;
import com.example.ecommerceprototype.pim.exceptions.ManufactureNotFoundException;
import com.example.ecommerceprototype.pim.exceptions.UUIDNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Tests for all delete methods in DBDriver")
public class DBDriverDeleteTest extends DBDriverAbstractTest {
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
}