package com.example.ecommerceprototype.pim.product_information;

import com.example.ecommerceprototype.pim.exceptions.NotFoundException;
import com.example.ecommerceprototype.pim.exceptions.UUIDNotFoundException;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

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


    public List<ProductInformation> getAllProducts() throws UUIDNotFoundException, SQLException {
        return dbDriverInstance.getAllProducts();
    }

    public ProductInformation getProductByUUID(String uuid) throws UUIDNotFoundException, SQLException {
        return dbDriverInstance.getProductByUUID(uuid);
    }

    public ProductInformation getProductByName(String name) throws SQLException, NotFoundException {
        return dbDriverInstance.getProductByName(name);
    }

    public List<ProductInformation> getProductsBySerialNumber(String serialNumber) throws SQLException, NotFoundException {
        return dbDriverInstance.getProductsBySerialNumber(serialNumber);
    }

    public List<ProductInformation> getProductsThatAreHidden() throws SQLException {
        return dbDriverInstance.getProductsThatAreHidden();
    }

    public List<ProductInformation> getProductsByCategoryName(String categoryName) throws SQLException, NotFoundException {
        return dbDriverInstance.getProductsByCategoryName(categoryName);
    }

    public List<ProductInformation> getProductsByManufactureName(String manufactureName) throws SQLException, NotFoundException {
        return dbDriverInstance.getProductsByManufactureName(manufactureName);
    }

    public List<ProductInformation> getProductsByDiscountName(String discountName) throws SQLException, NotFoundException {
        return dbDriverInstance.getProductsByDiscountName(discountName);
    }

    public List<ProductCategory> getAllCategories() throws SQLException, NotFoundException {
        return dbDriverInstance.getAllCategories();
    }

    public ProductCategory getCategoryByProductUUID(String uuid) throws UUIDNotFoundException, SQLException {
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

    public List<ManufacturingInformation> getAllManufactures() throws SQLException {
        return dbDriverInstance.getAllManufactures();
    }

    public ManufacturingInformation getManufactureByProductUUID(String uuid) throws UUIDNotFoundException, SQLException {
        return dbDriverInstance.getManufactureByProductUUID(uuid);
    }

    public ManufacturingInformation getManufactureByName(String name) throws SQLException, NotFoundException {
        return dbDriverInstance.getManufactureByName(name);
    }

    public List<DiscountInformation> getAllDiscounts() throws SQLException {
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

    public List<PriceInformation> getPricesByProductUUID(String uuid) throws UUIDNotFoundException, SQLException {
        return dbDriverInstance.getPricesByProductUUID(uuid);
    }

    public ProductInformationUpdater prepareProductInformationUpdater(ProductInformation pi) {
        return new ProductInformationUpdater(pi);
    }
}