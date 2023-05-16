package com.example.ecommerceprototype.pim.product_information;

import java.math.BigDecimal;
import java.util.List;

public class PIMDriver {

    private static final DBDriver dbDriverInstance = DBDriver.getInstance();

    public boolean checkIfProductByUUIDExists(String uuid) {
        return dbDriverInstance.checkIfProductByUUIDExists(uuid);
    }

    public boolean checkIfProductByNameExists(String name) {
        return dbDriverInstance.checkIfProductByNameExists(name);
    }

    public boolean checkIfCategoryByNameExists(String name) {
        return dbDriverInstance.checkIfCategoryByNameExists(name);
    }

    public boolean checkIfManufactureByNameExists(String name) {
        return dbDriverInstance.checkIfManufactureByNameExists(name);
    }

    public boolean checkIfDiscountByNameExists(String name) {
        return dbDriverInstance.checkIfDiscountByNameExists(name);
    }


    public List<ProductInformation> getAllProducts() {
        return dbDriverInstance.getAllProducts();
    }

    public ProductInformation getProductByUUID(String uuid) {
        return dbDriverInstance.getProductByUUID(uuid);
    }

    public ProductInformation getProductByName(String name) {
        return dbDriverInstance.getProductByName(name);
    }

    public List<ProductInformation> getProductsBySerialNumber(String serialNumber) {
        return dbDriverInstance.getProductsBySerialNumber(serialNumber);
    }

    public List<ProductInformation> getProductsThatAreHidden() {
        return dbDriverInstance.getProductsThatAreHidden();
    }

    public List<ProductInformation> getProductsByCategoryName(String categoryName) {
        return dbDriverInstance.getProductsByCategoryName(categoryName);
    }

    public List<ProductInformation> getProductsByManufactureName(String manufactureName) {
        return dbDriverInstance.getProductsByManufactureName(manufactureName);
    }

    public List<ProductInformation> getProductsByDiscountName(String discountName) {
        return dbDriverInstance.getProductsByDiscountName(discountName);
    }

    public List<ProductCategory> getAllCategories() {
        return dbDriverInstance.getAllCategories();
    }

    public ProductCategory getCategoryByProductUUID(String uuid) {
        return dbDriverInstance.getCategoryByProductUUID(uuid);
    }

    public ProductCategory getCategoryByName(String name) {
        return dbDriverInstance.getCategoryByName(name);
    }

    public ProductCategory getCategoryByCategoryID(int categoryId) {
        return dbDriverInstance.getCategoryByCategoryID(categoryId);
    }

    public ProductSpecification getSpecificationByProductUUID(String uuid) {
        return dbDriverInstance.getSpecificationByProductUUID(uuid);
    }

    public List<ManufacturingInformation> getAllManufactures() {
        return dbDriverInstance.getAllManufactures();
    }

    public ManufacturingInformation getManufactureByProductUUID(String uuid) {
        return dbDriverInstance.getManufactureByProductUUID(uuid);
    }

    public ManufacturingInformation getManufactureByName(String name) {
        return dbDriverInstance.getManufactureByName(name);
    }

    public List<DiscountInformation> getAllDiscounts() {
        return dbDriverInstance.getAllDiscounts();
    }

    public DiscountInformation getDiscountByProductUUID(String uuid) {
        return dbDriverInstance.getDiscountByProductUUID(uuid);
    }

    public DiscountInformation getDiscountByName(String name) {
        return dbDriverInstance.getDiscountByName(name);
    }

    public BigDecimal getDiscountPercentageByProductUUID(String uuid) {
        return dbDriverInstance.getDiscountPercentageByProductUUID(uuid);
    }

    public List<PriceInformation> getPricesByProductUUID(String uuid) {
        return dbDriverInstance.getPricesByProductUUID(uuid);
    }

    public ProductInformationUpdater prepareProductInformationUpdater(ProductInformation pi) {
        return new ProductInformationUpdater(pi);
    }
}
