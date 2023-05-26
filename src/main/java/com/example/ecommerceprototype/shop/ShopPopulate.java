package com.example.ecommerceprototype.shop;

import com.example.ecommerceprototype.pim.exceptions.CategoryNotFoundException;
import com.example.ecommerceprototype.pim.exceptions.DuplicateEntryException;
import com.example.ecommerceprototype.pim.exceptions.NotFoundException;
import com.example.ecommerceprototype.pim.product_information.*;

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
        populateCategories();
        populateManufacturers();
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
