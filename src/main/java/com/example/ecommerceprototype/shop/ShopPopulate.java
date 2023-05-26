package com.example.ecommerceprototype.shop;

import com.example.ecommerceprototype.pim.exceptions.*;
import com.example.ecommerceprototype.pim.product_information.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class ShopPopulate {
    ShopController controller;
    PIMDriver pim;

    public ShopPopulate(ShopController controller) {
        this.controller = controller;
        this.pim = controller.getPIMDriverInstance();
    }

    public void populate() throws SQLException, NotFoundException, DuplicateEntryException {

        for (ProductInformation product: pim.getAllProducts()) {
            pim.deleteProductByUUID(product.getProductUUID());
        }

        populateCategories();
        populateManufacturers();
        createProduct("Product", "1111", "Computers", "Lenovo", "1222", "short", "long");
    }

    public void populateCategories() throws SQLException, NotFoundException, DuplicateEntryException {

        for (ProductCategory category : pim.getAllCategories()) {
            pim.deleteProductCategoryByName(category.getName());
        }

        String[] parentCategories = {"Computers", "PC components"};
        for (String category : parentCategories) {
            createCategory(category, "");
        }

        String[] parentCategoryComputers = {"Laptops", "Desktop PCs"};
        for (String category : parentCategoryComputers) {
            createCategory(category, "Computers");
        }

        String[] parentCategoryPCComponents = {"CPUs", "RAM"};
        for (String category : parentCategoryPCComponents) {
            createCategory(category, "PC components");
        }

        createCategory("Monitors", "");

    }

    public void populateManufacturers() throws SQLException, NotFoundException, DuplicateEntryException {

        for (ManufacturingInformation manufacturer : pim.getAllManufactures()) {
            pim.deleteManufactureByName(manufacturer.getName());
        }
        createManufacturer("Acer");
        createManufacturer("Lenovo");
        createManufacturer("ASUS");
        createManufacturer("AMD");

    }

    public void createProduct(String name, String serialNumber, String category, String manufacturer, String price, String shortDescription, String longDescription) throws SQLException, DuplicateEntryException {
        // Creates a category object for the product object
        ProductCategoryBuilder categoryBuilder = (ProductCategoryBuilder) new ProductCategoryBuilder()
                .setName(category);

        // Creates a manufacture object for the product object
        ManufacturingInformationBuilder manufactureBuilder = (ManufacturingInformationBuilder) new ManufacturingInformationBuilder()
                .setName(manufacturer);

        // Creates a price object for the product object
        PriceInformationBuilder priceInformationBuilder = new PriceInformationBuilder()
                .setPrice(new BigDecimal(price))
                .setWholeSalePrice(new BigDecimal(0));

        // Inserts the new product
        try {
            new ProductInformationBuilder()
                    .setName(name)
                    .setSerialNumber(serialNumber)
                    .setProductCategory(categoryBuilder.getProductCategory())
                    .setManufacturingInformation(manufactureBuilder.getManufacturingInformation())
                    .setPriceInformation(priceInformationBuilder.getPriceInformation())
                    .setShortDescription(shortDescription)
                    .setLongDescription(longDescription)
                    .setProductSpecification(new ProductSpecification())
                    .submit();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void createManufacturer(String name) throws SQLException, DuplicateEntryException {
        new ManufacturingInformationBuilder()
                .setName(name)
                .setSupportPhoneNumber("12 23 34 45")
                .setSupportMail(name.toLowerCase() + "@company.com")
                .submit();
    }

    public void createCategory(String name, String parent) throws SQLException, CategoryNotFoundException, DuplicateEntryException {
        new ProductCategoryBuilder()
                .setName(name)
                .setCategoryParent(parent)
                .submit();
    }

}
