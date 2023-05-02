package com.example.ecommerceprototype.pim.product_information;

import com.example.ecommerceprototype.pim.sql_helpers.SQLValueArguments;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DBDriver {

    protected ProductInformation getProductByUUID(SQLValueArguments uuid) {
        // SQL function: getProductByUUID(argUUID UUID)
        // Call by: SELECT * FROM getProductByUUID('some-uuid');
        // Look at the database_initialization.sql file for return types and return values.

        throw new UnsupportedOperationException();
    }

    protected ProductInformation getProductByName(SQLValueArguments name) {
        // SQL function: getProductByName(argName TEXT)
        // Call by: SELECT * FROM getProductByName('some-name');
        // Look at the database_initialization.sql file for return types and return values.

        throw new UnsupportedOperationException();
    }

    protected List<ProductInformation> getProductsBySerialNumber(SQLValueArguments serialNumber) {
        // SQL function: getProductsBySerialNumber(argSerialNumber TEXT)
        // Call by: SELECT * FROM getProductsBySerialNumber('some-serial-number');
        // Look at the database_initialization.sql file for return types and return values.

        throw new UnsupportedOperationException();
    }

    protected List<ProductInformation> getProductsThatAreHidden() {
        // SQL function: getProductsThatAreHidden()
        // Call by: SELECT * FROM getProductsThatAreHidden();
        // Look at the database_initialization.sql file for return types and return values.

        throw new UnsupportedOperationException();
    }

    protected List<ProductInformation> getProductsByCategoryName(SQLValueArguments categoryName) {
        // SQL function: getProductsThatAreHidden(argCategoryName TEXT)
        // Call by: SELECT * FROM getProductsThatAreHidden('someCategoryName');
        // Look at the database_initialization.sql file for return types and return values.

        throw new UnsupportedOperationException();
    }

    protected List<ProductInformation> getProductsByManufactureName(SQLValueArguments manufactureName) {
        // SQL function: getProductsByManufactureName(argManufactureName TEXT)
        // Call by: SELECT * FROM getProductsByManufactureName('someManufactureName');
        // Look at the database_initialization.sql file for return types and return values.

        throw new UnsupportedOperationException();
    }

    protected List<ProductInformation> getProductsByDiscountName(SQLValueArguments discountName) {
        // SQL function: getProductsByDiscountName(argDiscountName TEXT)
        // Call by: SELECT * FROM getProductsByDiscountName('someDiscountName');
        // Look at the database_initialization.sql file for return types and return values.

        throw new UnsupportedOperationException();
    }

    protected ProductCategory getCategoryByProductUUID(SQLValueArguments uuid) {
        // SQL function: getCategoryByProductUUID(argUUID UUID)
        // Call by: SELECT * FROM getCategoryByProductUUID('someUUID');
        // Look at the database_initialization.sql file for return types and return values.

        throw new UnsupportedOperationException();
    }

    protected ProductCategory getCategoryByName(SQLValueArguments categoryName) {
        // SQL function: getCategoryByName(argName TEXT)
        // Call by: SELECT * FROM getCategoryByName('someCategoryName');
        // Look at the database_initialization.sql file for return types and return values.

        throw new UnsupportedOperationException();
    }

    protected ProductCategory getCategoryByCategoryID(SQLValueArguments categoryId) {
        // This method is most relevant for getting the parent category of a category.

        // SQL function: getCategoryByCategoryID(argID INT)
        // Call by: SELECT * FROM getCategoryByCategoryID('someCategoryId');
        // Look at the database_initialization.sql file for return types and return values.

        throw new UnsupportedOperationException();
    }

    protected ProductSpecification getSpecificationByProductUUID(SQLValueArguments uuid) {
        // SQL function: getSpecificationByProductUUID(argUUID UUID)
        // Call by: SELECT * FROM getSpecificationByProductUUID('someUUID');
        // Look at the database_initialization.sql file for return types and return values.

        throw new UnsupportedOperationException();
    }

    protected ManufacturingInformation getManufactureByProductUUID(SQLValueArguments uuid) {
        // SQL function: getManufactureByProductUUID(argUUID UUID)
        // Call by: SELECT * FROM getManufactureByProductUUID('someUUID');
        // Look at the database_initialization.sql file for return types and return values.

        throw new UnsupportedOperationException();
    }

    protected ManufacturingInformation getManufactureByName(SQLValueArguments name) {
        // SQL function: getManufactureByName(argName TEXT)
        // Call by: SELECT * FROM getManufactureByName('someName');
        // Look at the database_initialization.sql file for return types and return values.

        throw new UnsupportedOperationException();
    }

    protected DiscountInformation getDiscountByProductUUID(SQLValueArguments uuid) {
        // SQL function: getDiscountByProductUUID(argUUID UUID)
        // Call by: SELECT * FROM getDiscountByProductUUID('someUUID');
        // Look at the database_initialization.sql file for return types and return values.

        throw new UnsupportedOperationException();
    }

    protected DiscountInformation getDiscountByName(SQLValueArguments name) {
        // SQL function: getDiscountByName(argName TEXT)
        // Call by: SELECT * FROM getDiscountByName('someName');
        // Look at the database_initialization.sql file for return types and return values.

        throw new UnsupportedOperationException();
    }

    protected BigDecimal getDiscountPercentageByProductUUID(SQLValueArguments uuid) {
        // SQL function: getDiscountPercentageByProductUUID(argUUID UUID)
        // Call by: SELECT * FROM getDiscountPercentageByProductUUID('someUUID');
        // Returns a NUMERIC value of the discount.

        throw new UnsupportedOperationException();
    }

    protected List<PriceInformation> getPricesByProductUUID(SQLValueArguments uuid) {
        // SQL function: getPricesByProductUUID(argUUID UUID)
        // Call by: SELECT * FROM getPricesByProductUUID('someUUID');
        // Look at the database_initialization.sql file for return types and return values.

        throw new UnsupportedOperationException();
    }




    protected void insertNewProduct(ProductInformation productInformation) {
        // SQL function: insertNewProduct(argName VARCHAR, argSerialNumber VARCHAR, argShortDescription VARCHAR, argProductCategoryName VARCHAR, argManufactureName VARCHAR, argLongDescription TEXT)
        // Call by: SELECT * FROM insertNewProduct('name', 'serialNumber', 'shortDescription', 'productCategoryName', 'manufactureName', 'longDescription');
        // Look at the database_initialization.sql file for return types and return values.

        throw new UnsupportedOperationException();
    }

    protected void insertNewProductCategory(ProductCategory productCategory) {
        // SQL function: insertNewProduct(argName VARCHAR, argParentCategoryName VARCHAR)
        // Call by: CALL insertNewProductCategory('name', 'parentCategoryName'); // OBS! The argParentCategoryName should be null if it doesn't have a parent category.

        throw new UnsupportedOperationException();
    }

    protected void insertNewManufacture(ManufacturingInformation manufacturingInformation) {
        // SQL function: insertNewManufacture(argName VARCHAR, argSupportPhone VARCHAR(32), argSupportMail VARCHAR)
        // Call by: CALL insertNewManufacture('manufactureName', 'supportPhone', 'supportMail');

        throw new UnsupportedOperationException();
    }

    protected void insertNewDiscount(DiscountInformation discountInformation) {
        // SQL function: insertNewManufacture(argName VARCHAR, argStartDate TIMESTAMP, argEndDate TIMESTAMP)
        // Call by: CALL insertNewDiscount('testDiscount', '01-06-2023', '01-07-2023');

        throw new UnsupportedOperationException();
    }

    protected void insertNewSpecification(ProductSpecification productSpecification) {
        // SQL function: insertNewManufacture(argProductUUID UUID, argKey VARCHAR, argValue VARCHAR)
        // Call by: CALL insertNewSpecification('71bce9bd-ef5f-48c2-af68-9e721cf4f181', 'CPU', 'testValue1243');

        throw new UnsupportedOperationException();
    }

    protected void insertNewPriceChange(SQLValueArguments uuid, SQLValueArguments price, SQLValueArguments wholeSalePrice) {
        // SQL function: insertNewManufacture(argProductUUID UUID, argPrice NUMERIC, argWholesalePrice NUMERIC)
        // Call by: CALL insertNewPriceChange('someUUID', 1234, 1234);

        throw new UnsupportedOperationException();
    }

    protected void insertNewPriceChange(SQLValueArguments uuid, SQLValueArguments price, SQLValueArguments wholeSalePrice, DiscountInformation discountInformation) {
        // SQL function: insertNewManufacture(argProductUUID UUID, argPrice NUMERIC, argWholesalePrice NUMERIC, argDiscountName VARCHAR)
        // Call by: CALL insertNewPriceChange('someUUID', 1234, 1234, 'discountName');

        throw new UnsupportedOperationException();
    }




    protected void updateProductByUUID(SQLValueArguments uuid, ProductInformation productInformation) {
        // SQL function: updateProductByUUID(argUUID UUID, argName VARCHAR, argSerialNumber VARCHAR, argShortDescription VARCHAR, argProductCategoryName VARCHAR, argManufactureName VARCHAR, argIsHidden BOOLEAN, argLongDescription TEXT)
        // Call by: CALL updateProductByUUID('someUUID', 'newName', 'newSerialNumber', 'newShortDescription', 'newCategoryName', 'newManufactureName', false, 'newLongDescription');

        throw new UnsupportedOperationException();
    }

    protected void updateManufactureByName(SQLValueArguments name, ProductCategory productCategory) {
        // SQL function: updateManufactureByName(argName VARCHAR, argNewName VARCHAR, argSupportPhone VARCHAR(32), argSupportMail VARCHAR)
        // Call by: CALL updateManufactureByName('oldName', 'newName', 'newSupportPhone', 'newSupportMail');

        throw new UnsupportedOperationException();
    }

    protected void updateDiscountByName(SQLValueArguments name, ProductCategory productCategory) {
        // SQL function: updateDiscountByName(argName VARCHAR, argNewName VARCHAR, argStartDate TIMESTAMP, argEndDate TIMESTAMP)
        // Call by: CALL updateDiscountByName('oldDiscountName', 'newDiscountName', '01-01-2023', '01-02-2023');

        throw new UnsupportedOperationException();
    }

    protected void updateProductCategoryByName(SQLValueArguments name, ProductCategory productCategory) {
        // SQL function: updateProductCategoryByName(argName VARCHAR, argNewName VARCHAR, argParentCategoryName VARCHAR)
        // Call by: CALL updateProductCategoryByName('oldCategoryName', 'newCategoryName', 'parentCategoryName');

        throw new UnsupportedOperationException();
    }

    protected void updateSpecificationByProductUUIDAndKey(SQLValueArguments uuid, ProductCategory productCategory) {
        // SQL function: updateSpecificationByProductUUIDAndKey(argProductUUID UUID, argKey VARCHAR, argNewKey VARCHAR, argNewValue VARCHAR)
        // Call by: CALL updateSpecificationByProductUUIDAndKey('someUUID', 'oldKeyName', 'newKeyName', 'newValue');

        throw new UnsupportedOperationException();
    }




    protected void deleteProductByUUID(SQLValueArguments uuid) {
        // SQL function: deleteProductByUUID(argUUID UUID)
        // Call by: CALL deleteProductByUUID('someUUID');

        throw new UnsupportedOperationException();
    }

    protected void deleteProductCategoryByName(SQLValueArguments name) {
        // SQL function: deleteProductCategoryByName(argName VARCHAR)
        // Call by: CALL deleteProductCategoryByName('someName');

        throw new UnsupportedOperationException();
    }

    protected void deleteManufactureByName(SQLValueArguments name) {
        // SQL function: deleteManufactureByName(argName VARCHAR)
        // Call by: CALL deleteManufactureByName('someManufactureName');

        throw new UnsupportedOperationException();
    }

    protected void deleteSpecificationByProductUUIDAndKey(SQLValueArguments uuid) {
        // SQL function: deleteSpecificationByProductUUIDAndKey(argProductUUID UUID, argKey VARCHAR)
        // Call by: CALL deleteSpecificationByProductUUIDAndKey('someUUID', 'someSpecificationKey');

        throw new UnsupportedOperationException();
    }

    protected void deleteDiscountByName(SQLValueArguments name) {
        // SQL function: deleteDiscountByName(argName VARCHAR)
        // Call by: CALL deleteDiscountByName('someDiscountName');

        throw new UnsupportedOperationException();
    }

    /* There will not be a method for deleting a price change. This is a design choice. */
}