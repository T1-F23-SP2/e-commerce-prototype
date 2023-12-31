package com.example.ecommerceprototype.pim.product_information;

import com.example.ecommerceprototype.pim.exceptions.*;
import com.example.ecommerceprototype.pim.util.ProductList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/*
    This is a test class dedicated for testing update methods in DBDriver.

    It inherits from DBDriverAbstractTest, which means that a dedicated dbDriver test instance is
    AUTOMATICALLY created, for use solely by this test class.
    Which means no @BeforeAll setup code is needed.

    Also, the test connection is automatically closed and cleanup after all tests has run within this test class.
    This means no @AfterAll teardown code is needed.

    It also means that any changes to the database made in this file, DOES NOT AFFECT other test classes!
    This means that all test within this test class, is completely independent and isolated from all other test classes.
 */
@DisplayName("Tests for all update methods in DBDriver")
public class DBDriverUpdateTest extends DBDriverAbstractTest {
    @Test
    void testUpdateProductByUUID() {
        // Get list of products
        ProductList productList = new ProductList();
        try {
            productList.addAll(dbDriver.getAllProducts());
        } catch (NotFoundException| SQLException e) {
            throw new RuntimeException(e);
        }

        // Get specific productInfo
        ProductInformation productInformation = productList.get(0);

        // Try update
        ProductInformationUpdater productInformationUpdater = pimDriver.prepareProductInformationUpdater(productInformation);
        productInformationUpdater.setName("NewProductName");
        String uuid = productInformationUpdater.getProductInformation().getProductUUID();

        try {
            productInformationUpdater.submit();
        } catch (SQLException | UUIDNotFoundException | DuplicateEntryException | CategoryNotFoundException |
                 ManufactureNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Assert
        try {
            assertEquals(productInformation.getName() /*"NewProductName"*/, dbDriver.getProductByUUID(uuid).getName());
        } catch (NotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testUpdateManufactureByName() {
        // Get list of manufacturers
        List<ManufacturingInformation> manufacturingInformationList;
        try {
            manufacturingInformationList = new ArrayList<>(dbDriver.getAllManufactures());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Get specific info
        ManufacturingInformation manufacturingInformation = manufacturingInformationList.get(0);

        // Try update
        ManufacturingInformationUpdater manufacturingInformationUpdater = new ManufacturingInformationUpdater(manufacturingInformation);
        String name = manufacturingInformationUpdater.getManufacturingInformation().getName();
        manufacturingInformationUpdater.setSupportPhoneNumber("NotANumber");

        try {
            manufacturingInformationUpdater.submit();
        } catch (SQLException | DuplicateEntryException e) {
            throw new RuntimeException(e);
        }

        // Assert
        try {
            assertEquals("NotANumber", dbDriver.getManufactureByName(name).getSupportPhoneNumber());
        } catch (SQLException | ManufactureNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testUpdateDiscountByName() {
        // Get list of manufacturers
        List<DiscountInformation> DiscountInformationList;
        try {
            DiscountInformationList = new ArrayList<>(dbDriver.getAllDiscounts());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Get specific info
        DiscountInformation discountInformation = DiscountInformationList.get(0);
        LocalDate startDate = discountInformation.getStartingDate();

        // Try update
        DiscountInformationUpdater discountInformationUpdater = new DiscountInformationUpdater(discountInformation);
        String name = discountInformationUpdater.getOriginalName();
        discountInformationUpdater.setStartingDate(startDate.minusDays(1));
        try {
            discountInformationUpdater.submit();
        } catch (SQLException | DuplicateEntryException e) {
            throw new RuntimeException(e);
        }

        // Assert
        try {
            assertEquals(startDate.minusDays(1), dbDriver.getDiscountByName(name).getStartingDate());
        } catch (SQLException | DiscountNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testUpdateProductCategoryByName() {
        // Get list of manufacturers
        List<ProductCategory> productCategoryList;
        try {
            productCategoryList = new ArrayList<>(dbDriver.getAllCategories());
        } catch (SQLException | CategoryNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Get specific info
        ProductCategory productCategory = productCategoryList.get(0);

        // Try update
        ProductCategoryUpdater productCategoryUpdater = new ProductCategoryUpdater(productCategory);
        String name = productCategoryUpdater.getOriginalName();
        productCategoryUpdater.setName("TestCategoryName");
        try {
            productCategoryUpdater.submit();
        } catch (SQLException | DuplicateEntryException | CategoryNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Assert
        assertDoesNotThrow(
                () -> dbDriver.getCategoryByName("TestCategoryName")
        );
    }

//    @Test
//    void testUpdateSpecificationByProductUUIDAndKey() {
//        // Get list of manufacturers
//        ProductSpecification specs;
//        ProductInformation pi;
//        try {
//            pi = dbDriver.getAllProducts().get(0);
//            specs = pi.getProductSpecification();
//        } catch (SQLException | CategoryNotFoundException | UUIDNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//
//        // Get specific info
//        Map.Entry<String,String> entry = specs.entrySet().iterator().next();
//        String key = entry.getKey();
//        String value = entry.getValue();
//
//        // Try update
//        specs.put(key, "NotARealValue");
//        ProductInformationUpdater piu = new ProductInformationUpdater(pi);
//        piu.setProductSpecification(specs);
//        try {
//            dbDriver.updateSpecificationByProductUUIDAndKey(pi.getProductUUID(), key, specs);
//        } catch (UUIDNotFoundException | SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//        // Assert
//        try {
//            assertEquals("NotARealValue", dbDriver.getProductByUUID(pi.getProductUUID()).getProductSpecification().get(key));
//        } catch (UUIDNotFoundException | SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

    @Test
    void testUpdatePrice() throws NotFoundException, SQLException {
        ProductInformation pi = pimDriver.getAllProducts().get(0);
        BigDecimal oldPrice = pi.getPriceInformation().getPrice();
        BigDecimal newPrice = new BigDecimal("39.99");
        String discountName = "test_new_discount";

        DiscountInformationBuilder discountInformationBuilder = new DiscountInformationBuilder();
        discountInformationBuilder.setName(discountName)
                .setStartingDate(LocalDate.parse("2023-01-01"))
                .setExpiringDate(LocalDate.now());

        assertDoesNotThrow(() -> {
            discountInformationBuilder.submit();
        });

        DiscountInformation discount = discountInformationBuilder.getDiscountInformation();

        PriceInformationBuilder builder = new PriceInformationBuilder();
        builder.setPrice(newPrice)
                .setWholeSalePrice(new BigDecimal("23.00"))
                .setDiscountInformation(discount)
                .setProductUUID(pi.getProductUUID());

        assertDoesNotThrow(() -> {
            builder.submit();
        });

        ProductInformation updatedPi = pimDriver.getProductByUUID(pi.getProductUUID());

        assertNotEquals(oldPrice, updatedPi.getPriceInformation().getPrice());

        assertEquals(newPrice, updatedPi.getPriceInformation().getPrice());
    }
}