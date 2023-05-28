package com.example.ecommerceprototype.pim.product_information;

import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

/*
    This is a test class dedicated for testing insert methods in DBDriver.

    It inherits from DBDriverAbstractTest, which means that a dedicated dbDriver test instance is
    AUTOMATICALLY created, for use solely by this test class.
    Which means no @BeforeAll setup code is needed.

    Also, the test connection is automatically closed and cleanup after all tests has run within this test class.
    This means no @AfterAll teardown code is needed.

    It also means that any changes to the database made in this file, DOES NOT AFFECT other test classes!
    This means that all test within this test class, is completely independent and isolated from all other test classes.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Tests for all insert methods in DBDriver")
public class DBDriverInsertTest extends DBDriverAbstractTest {
    private static ProductCategory productCategory;
    private static ProductSpecification productSpecification;
    private static PriceInformation priceInformation;
    private static DiscountInformation discountInformation;
    private static ManufacturingInformation manufacturingInformation;


    @Test
    @Order(0)
    void testInsertNewDiscount() {
        final String name = "discount NAME";
        final LocalDate startDate = LocalDate.parse("2023-12-09");
        final LocalDate endDate = LocalDate.now();

        DiscountInformationBuilder builder = new DiscountInformationBuilder();
        builder.setName(name)
                .setStartingDate(startDate)
                .setExpiringDate(endDate);

        AtomicReference<DiscountInformation> diContainer = new AtomicReference<>();

        assertDoesNotThrow(() -> {
            diContainer.set(builder.submit());
        });

        discountInformation = diContainer.get();
    }

    @Test
    @Order(1)
    void testInsertNewPrice() {
        final BigDecimal price = new BigDecimal("9.99");
        final BigDecimal wholesale = new BigDecimal("10.99");

        PriceInformationBuilder builder = new PriceInformationBuilder()
                .setPrice(price)
                .setWholeSalePrice(wholesale)
                .setDiscountInformation(discountInformation);

//        AtomicReference<PriceInformation> priceInfoContainer = new AtomicReference<>();
//
//        assertDoesNotThrow(() -> {
//            priceInfoContainer.set(builder.submit());
//        });
//        PriceInformation priceInfo = priceInfoContainer.get();

        PriceInformation priceInfo = builder.getPriceInformation();
        assertNotNull(priceInfo);
        assertEquals(price, priceInfo.getPrice());
        assertEquals(wholesale, priceInfo.getWholeSalePrice());

        priceInformation = priceInfo;

        assertEquals(discountInformation, priceInfo.getDiscountInformation());
    }

    @Test
    @Order(0)
    void testInsertNewCategory() {
        final String name = "TEst category";

        ProductCategoryWorker builder = new ProductCategoryBuilder()
                .setName(name)
                // TODO: Remove the need to set CategoryParent?
                .setCategoryParent("root");


        AtomicReference<ProductCategory> pcContainer = new AtomicReference<>();

        assertDoesNotThrow(() -> {
            pcContainer.set(builder.submit());
        });
        productCategory = pcContainer.get();

        assertNotNull(productCategory);
        assertEquals(name, productCategory.getName());
    }

    @Test
    @Order(0)
    void testInsertNewManufacturer() {
        final String name = "Manufacturer NAME";
        final String mail = "test@manufacturer.example.com";
        final String phone = "911";


        ManufacturingInformationWorker builder = new ManufacturingInformationBuilder();
        builder.setName(name)
                .setSupportMail(mail)
                .setSupportPhoneNumber(phone);

        AtomicReference<ManufacturingInformation> miContainer = new AtomicReference<>();

        assertDoesNotThrow(() -> {
            miContainer.set(builder.submit());
        });

        manufacturingInformation = miContainer.get();
    }

    @Test
    @Order(0)
    void testInsertNewSpecification() {
        ProductSpecification specs = new ProductSpecification();
        specs.put("RAM", "2 TB");
        productSpecification = specs;
    }


    @Order(Integer.MAX_VALUE) // Make sure this runs last
    @Test
    void testInsertNewProduct() throws SQLException {
        final String name = "TEST NAME";
        final String serial = "HLKJDKLJS8293j";
        final String shortDescription = """
                This is very good product. YOU must indeed have this.
                """;
        final String longDescription = """
                What are you waiting for, just buy it already!
                """;


        ProductInformationBuilder builder = new ProductInformationBuilder();
        builder.setName(name)
                .setSerialNumber(serial)
                .setShortDescription(shortDescription)
                .setLongDescription(longDescription);


        builder.setPriceInformation(priceInformation)
                .setProductCategory(productCategory)
                .setManufacturingInformation(manufacturingInformation)
                .setProductSpecification(productSpecification);


        AtomicReference<ProductInformation> piContainer = new AtomicReference<>();
        assertDoesNotThrow(() -> {
            piContainer.set(builder.submit());
        });

        ProductInformation pi = piContainer.get();


        assertTrue(dbDriver.productByUUIDExists(pi.getProductUUID()));

        assertEquals(name, pi.getName());
        assertEquals(serial, pi.getSerialNumber());
        assertEquals(shortDescription, pi.getShortDescription());
        assertEquals(longDescription, pi.getLongDescription());

        assertEquals(priceInformation, pi.getPriceInformation());
        assertEquals(productCategory, pi.getProductCategory());
        assertEquals(manufacturingInformation, pi.getManufacturingInformation());
        assertEquals(productSpecification, pi.getProductSpecification());
    }
}