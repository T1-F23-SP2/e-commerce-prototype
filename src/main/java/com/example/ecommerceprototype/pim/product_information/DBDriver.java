package com.example.ecommerceprototype.pim.product_information;

import com.example.ecommerceprototype.pim.exceptions.*;
import com.example.ecommerceprototype.pim.sql_helpers.SQLValueArguments;
import com.example.ecommerceprototype.pim.sql_helpers.SQLValueSetter;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DBDriver {

    // TODO: Keep DB-login details correct

    private static DBDriver instance;
    private String url = "localhost";
    private int port = 5432;
    private String databaseName = "postgres";
    private String username = "postgres";
    private String password = "password";
    private Connection connection = null;

    private DBDriver(){
        initializePostgresqlDatabase();
    }
    public static DBDriver getInstance(){
        if (instance == null) {
            instance = new DBDriver();
        }
        return instance;
    }

    private void initializePostgresqlDatabase() {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
            connection = DriverManager.getConnection("jdbc:postgresql://" + url + ":" + port + "/" + databaseName, username, password);
        } catch (SQLException | IllegalArgumentException ex) {
            ex.printStackTrace(System.err);
        } finally {
            if (connection == null) System.exit(-1);
        }
    }

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

    protected String insertNewProduct(ProductInformation productInformation) throws IncompleteProductInformationException {
        // SQL function: insertNewProduct(argName VARCHAR, argSerialNumber VARCHAR, argShortDescription VARCHAR,
        //                                argProductCategoryName VARCHAR, argManufactureName VARCHAR, argLongDescription TEXT)
        // Call by: SELECT * FROM insertNewProduct('name', 'serialNumber', 'shortDescription',
        //                                          'productCategoryName', 'manufactureName', 'longDescription');
        // Look at the database_initialization.sql file for return types and return values.
        try {
            PreparedStatement insertStatement = connection.prepareStatement("SELECT product_uuid FROM insertnewproduct(?,?,?,?,?,?)");
            // TODO: After getters is implemented, make checks for the existence of the referenced productCategory and ManufacturingInformation.
            SQLValueArguments sqlValueArguments = new SQLValueArguments();
            sqlValueArguments.setArgument(productInformation.getName());
            sqlValueArguments.setArgument(productInformation.getSerialNumber());
            sqlValueArguments.setArgument(productInformation.getShortDescription());
            sqlValueArguments.setArgument(productInformation.getProductCategory().getName());
            sqlValueArguments.setArgument(productInformation.getManufacturingInformation().getName());
            sqlValueArguments.setArgument(productInformation.getLongDescription());

            sqlValueArguments.setArgumentsInStatement(insertStatement);
            insertStatement.execute();

            ResultSet rs = insertStatement.getResultSet();
            rs.next(); // Move "cursor" to first row
            String UUID = rs.getString("product_UUID");

            this.insertNewPriceChange(UUID,
                    productInformation.getPriceInformation().getPrice(),
                    productInformation.getPriceInformation().getWholeSalePrice());

            this.insertNewSpecification(UUID, productInformation.getProductSpecification());

            return UUID;

        } catch (SQLException e) {
            System.out.println(e);
            throw new IncompleteProductInformationException();
        }
    }

    protected void insertNewProductCategory(ProductCategory productCategory) throws IncompleteProductCategoryInformation {
        // SQL function: insertNewProduct(argName VARCHAR, argParentCategoryName VARCHAR)
        // Call by: CALL insertNewProductCategory('name', 'parentCategoryName');
        // OBS! The argParentCategoryName should be null if it doesn't have a parent category.
        try {
            PreparedStatement insertStatement;
            SQLValueArguments sqlValueArguments;

            if(productCategory.getProductCategoryParent() != null) {
                insertStatement = connection.prepareStatement("CALL insertnewproductcategory(?, ?)");
                sqlValueArguments = new SQLValueArguments();
                sqlValueArguments.setArgument(productCategory.getName());
                sqlValueArguments.setArgument(productCategory.getProductCategoryParent().getName());
            } else {
                insertStatement = connection.prepareStatement("CALL insertnewproductcategory(?)");
                sqlValueArguments = new SQLValueArguments();
                sqlValueArguments.setArgument(productCategory.getName());
            }

            sqlValueArguments.setArgumentsInStatement(insertStatement);
            insertStatement.execute();

        } catch (SQLException e) {
            System.out.println(e);
            throw new IncompleteProductCategoryInformation();
        }
    }

    protected void insertNewManufacture(ManufacturingInformation manufacturingInformation) throws IncompleteManufacturingInformation {
        // SQL function: insertNewManufacture(argName VARCHAR, argSupportPhone VARCHAR(32), argSupportMail VARCHAR)
        // Call by: CALL insertNewManufacture('manufactureName', 'supportPhone', 'supportMail');
        try {
            PreparedStatement insertStatement = connection.prepareStatement("CALL insertnewmanufacture(?, ?, ?)");
            SQLValueArguments sqlValueArguments = new SQLValueArguments();
            sqlValueArguments.setArgument(manufacturingInformation.getName());
            sqlValueArguments.setArgument(manufacturingInformation.getSupportPhoneNumber());
            sqlValueArguments.setArgument(manufacturingInformation.getSupportMail());

            sqlValueArguments.setArgumentsInStatement(insertStatement);
            insertStatement.execute();
        } catch (SQLException e) {
            System.out.println(e);
            throw new IncompleteManufacturingInformation();
        }
    }

    protected void insertNewDiscount(DiscountInformation discountInformation) throws IncompleteDiscountInformation {
        // SQL function: insertNewManufacture(argName VARCHAR, argStartDate TIMESTAMP, argEndDate TIMESTAMP)
        // Call by: CALL insertNewDiscount('testDiscount', '01-06-2023', '01-07-2023');

        try {

            PreparedStatement insertStatement = connection.prepareStatement("CALL insertnewdiscount(?,?,?)");
            SQLValueArguments sqlValueArguments = new SQLValueArguments();
            sqlValueArguments.setArgument(discountInformation.getName());
            sqlValueArguments.setArgument(discountInformation.getStartingDate());
            sqlValueArguments.setArgument(discountInformation.getExpiringDate());

            sqlValueArguments.setArgumentsInStatement(insertStatement);
            insertStatement.execute();
        } catch (SQLException e){
            System.out.println(e);
            throw new IncompleteDiscountInformation();
        }
    }

    protected void insertNewSpecification(String UUID, ProductSpecification productSpecification) {
        // SQL function: insertNewManufacture(argProductUUID UUID, argKey VARCHAR, argValue VARCHAR)
        // Call by: CALL insertNewSpecification('71bce9bd-ef5f-48c2-af68-9e721cf4f181', 'CPU', 'testValue1243');
        try {
            for(String key : productSpecification.keySet()) { // As productSpecification is a HashMap...
                PreparedStatement insertStatement = connection.prepareStatement("CALL insertNewSpecification(?, ?, ?)");
                SQLValueArguments sqlValueArguments = new SQLValueArguments()
                        .setArgument(UUID)
                        .setArgument(key)
                        .setArgument(productSpecification.get(key));

                sqlValueArguments.setArgumentsInStatement(insertStatement);
                insertStatement.execute();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    protected void insertNewPriceChange(String uuid, BigDecimal price, BigDecimal wholeSalePrice) {
        // SQL function: insertNewManufacture(argProductUUID UUID, argPrice NUMERIC, argWholesalePrice NUMERIC)
        // Call by: CALL insertNewPriceChange('someUUID', 1234, 1234);
        try {
            PreparedStatement insertStatement = connection.prepareStatement("CALL insertNewPriceChange(?, ?, ?)");
            SQLValueArguments sqlValueArguments = new SQLValueArguments()
                    .setArgument(uuid)
                    .setArgument(price)
                    .setArgument(wholeSalePrice);

            sqlValueArguments.setArgumentsInStatement(insertStatement);
            insertStatement.execute();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    protected void insertNewPriceChange(String uuid, BigDecimal price, BigDecimal wholeSalePrice, DiscountInformation discountInformation) {
        // SQL function: insertNewManufacture(argProductUUID UUID, argPrice NUMERIC, argWholesalePrice NUMERIC, argDiscountName VARCHAR)
        // Call by: CALL insertNewPriceChange('someUUID', 1234, 1234, 'discountName');
        try {
            PreparedStatement insertStatement = connection.prepareStatement("CALL insertnewpricechange(?,?,?,?)");
            SQLValueArguments sqlValueArguments = new SQLValueArguments();

            sqlValueArguments.setArgument(uuid);
            sqlValueArguments.setArgument(price);
            sqlValueArguments.setArgument(wholeSalePrice);
            sqlValueArguments.setArgument(discountInformation.getName());

            sqlValueArguments.setArgumentsInStatement(insertStatement);
            insertStatement.execute();
        } catch (SQLException e){
            System.out.println(e);
        }
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




    // TODO: The following procedures are missing before they can be implemented:
    // deleteProductByUUID()
    // deleteCategoryByName()
    // deleteSpecificationByProductUUID()
    // deleteManufactureByName()
    // deletePriceChangeByProductUUID()
    // deleteDiscountByName()
}