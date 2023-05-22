package com.example.ecommerceprototype.pim.product_information;

import com.example.ecommerceprototype.pim.exceptions.*;
import com.example.ecommerceprototype.pim.util.FilterableArrayList;
import com.example.ecommerceprototype.pim.util.ProductList;

import java.math.BigDecimal;
import java.sql.SQLException;

public class PIMDriver {

    private static final DBDriver dbDriverInstance = DBDriver.getInstance();

    public boolean checkIfProductByUUIDExists(String uuid) {
        return dbDriverInstance.productByUUIDExists(uuid);
    }

    public boolean checkIfProductByNameExists(String name) {
        return dbDriverInstance.productByNameExists(name);
    }

    public boolean checkIfCategoryByNameExists(String name) {
        return dbDriverInstance.categoryByNameExists(name);
    }

    public boolean checkIfManufactureByNameExists(String name) {
        return dbDriverInstance.manufacturerByNameExists(name);
    }

    public boolean checkIfDiscountByNameExists(String name) {
        return dbDriverInstance.discountByNameExists(name);
    }


    public ProductList getAllProducts() throws UUIDNotFoundException, SQLException, CategoryNotFoundException {
        return dbDriverInstance.getAllProducts();
    }

    public ProductInformation getProductByUUID(String uuid) throws UUIDNotFoundException, SQLException {
        return dbDriverInstance.getProductByUUID(uuid);
    }

    public ProductInformation getProductByName(String name) throws SQLException, NotFoundException {
        return dbDriverInstance.getProductByName(name);
    }

    public ProductList getProductsBySerialNumber(String serialNumber) throws SQLException, NotFoundException {
        return dbDriverInstance.getProductsBySerialNumber(serialNumber);
    }

    public ProductList getProductsThatAreHidden() throws SQLException {
        return dbDriverInstance.getProductsThatAreHidden();
    }

    public ProductList getProductsByCategoryName(String categoryName) throws SQLException, NotFoundException {
        return dbDriverInstance.getProductsByCategoryName(categoryName);
    }

    public ProductList getProductsByManufactureName(String manufactureName) throws SQLException, NotFoundException {
        return dbDriverInstance.getProductsByManufactureName(manufactureName);
    }

    public ProductList getProductsByDiscountName(String discountName) throws SQLException, NotFoundException {
        return dbDriverInstance.getProductsByDiscountName(discountName);
    }

    public FilterableArrayList<ProductCategory> getAllCategories() throws SQLException, NotFoundException {
        return dbDriverInstance.getAllCategories();
    }

    public ProductCategory getCategoryByProductUUID(String uuid) throws UUIDNotFoundException, SQLException, CategoryNotFoundException {
        return dbDriverInstance.getCategoryByProductUUID(uuid);
    }

    public ProductCategory getCategoryByName(String name) throws SQLException, NotFoundException {
        return dbDriverInstance.getCategoryByName(name);
    }

    public ProductCategory getCategoryByCategoryID(int categoryId) throws SQLException, NotFoundException {
        return dbDriverInstance.getCategoryByCategoryID(categoryId);
    }

    public ProductSpecification getSpecificationByProductUUID(String uuid) throws UUIDNotFoundException, SQLException {
        return dbDriverInstance.getSpecificationByProductUUID(uuid);
    }

    public FilterableArrayList<ManufacturingInformation> getAllManufactures() throws SQLException {
        return dbDriverInstance.getAllManufactures();
    }

    public ManufacturingInformation getManufactureByProductUUID(String uuid) throws UUIDNotFoundException, SQLException {
        return dbDriverInstance.getManufactureByProductUUID(uuid);
    }

    public ManufacturingInformation getManufactureByName(String name) throws SQLException, NotFoundException {
        return dbDriverInstance.getManufactureByName(name);
    }

    public FilterableArrayList<DiscountInformation> getAllDiscounts() throws SQLException {
        return dbDriverInstance.getAllDiscounts();
    }

    public DiscountInformation getDiscountByProductUUID(String uuid) throws UUIDNotFoundException, SQLException {
        return dbDriverInstance.getDiscountByProductUUID(uuid);
    }

    public DiscountInformation getDiscountByName(String name) throws SQLException, NotFoundException {
        return dbDriverInstance.getDiscountByName(name);
    }

    public BigDecimal getDiscountPercentageByProductUUID(String uuid) throws UUIDNotFoundException, SQLException {
        return dbDriverInstance.getDiscountPercentageByProductUUID(uuid);
    }

    public FilterableArrayList<PriceInformation> getPricesByProductUUID(String uuid) throws UUIDNotFoundException, SQLException {
        return dbDriverInstance.getPricesByProductUUID(uuid);
    }

    public ProductInformationUpdater prepareProductInformationUpdater(ProductInformation pi) {
        return new ProductInformationUpdater(pi);
    }

    public void deleteProductByUUID(String uuid) throws UUIDNotFoundException, SQLException {
        dbDriverInstance.deleteProductByUUID(uuid);
    }

    public void deleteProductCategoryByName(String name) throws SQLException, CategoryNotFoundException {
        dbDriverInstance.deleteProductCategoryByName(name);
    }

    public void deleteManufactureByName(String name) throws SQLException, ManufactureNotFoundException {
        dbDriverInstance.deleteManufactureByName(name);
    }

    public void deleteSpecificationByProductUUIDAndKey(String uuid, String key) throws UUIDNotFoundException, SQLException {
        dbDriverInstance.deleteSpecificationByProductUUIDAndKey(uuid, key);
    }

    public void deleteDiscountByName(String name) throws SQLException, DiscountNotFoundException {
        dbDriverInstance.deleteDiscountByName(name);
    }
}