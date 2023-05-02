package com.example.ecommerceprototype.pim.product_information;

import com.example.ecommerceprototype.pim.exceptions.UUIDNotFoundException;
import com.example.ecommerceprototype.pim.sql_helpers.SQLValueArguments;
import com.example.ecommerceprototype.pim.sql_helpers.SQLValueSetter;

import javax.xml.transform.Result;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.ProtectionDomain;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class DBDriver {

    // TODO: Keep DB-login details correct

    private static DBDriver instance;
    private String url = "localhost";
    private int port = 5432;
    private String databaseName = "postgres";
    private String username = "postgres";
    private String password = "";
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

    public static void main(String[] args) {
        DBDriver dbDriver = new DBDriver();

        System.out.println("Product by UUID: " + dbDriver.getProductByUUID("60d13595-434e-4ecf-a791-396a761fb7e9"));
        System.out.println("Product by name: " + dbDriver.getProductByName("Lenovo Ideapad 5 Pro 14\" QHD touch"));
        System.out.println("Products by serial number: " + dbDriver.getProductsBySerialNumber("LenovoIdeapad5Pro-1234"));
        System.out.println("Products that are hidden: " + dbDriver.getProductsThatAreHidden());
        System.out.println("Product by category name: " + dbDriver.getProductsByCategoryName("PC Laptops"));
        System.out.println("Products by manufacture name: " + dbDriver.getProductsByManufactureName("Samsung"));
        System.out.println("Products by discount name: " + dbDriver.getProductsByDiscountName("Spring sale"));

        System.out.println("Category by product UUID: " + dbDriver.getCategoryByProductUUID("60d13595-434e-4ecf-a791-396a761fb7e9"));
        System.out.println("Category by name: " + dbDriver.getCategoryByName("Monitors"));
        System.out.println("Category by id: " + dbDriver.getCategoryByCategoryID(1));

        System.out.println("Specification by product UUID: " + dbDriver.getSpecificationByProductUUID("60d13595-434e-4ecf-a791-396a761fb7e9"));

        System.out.println("Manufacture by product UUID: " + dbDriver.getManufactureByProductUUID("60d13595-434e-4ecf-a791-396a761fb7e9"));
        System.out.println("Manufacture by product name: " + dbDriver.getManufactureByName("Lenovo"));

        System.out.println("Discount by product UUID: " + dbDriver.getDiscountByProductUUID("2710b731-55a9-491d-8d98-a5d488bbe02f"));
        System.out.println("Discount by product name: " + dbDriver.getDiscountByName("Spring sale"));

        System.out.println("Discount percentage by product UUID: " + dbDriver.getDiscountPercentageByProductUUID("2710b731-55a9-491d-8d98-a5d488bbe02f"));

        System.out.println("All prices by product UUID: " + dbDriver.getPricesByProductUUID("60d13595-434e-4ecf-a791-396a761fb7e9"));
    }


    protected ProductInformation getProductByUUID(String uuid) {
        // SQL function: getProductByUUID(argUUID UUID)
        // Call by: SELECT * FROM getProductByUUID('some-uuid');
        // Look at the database_initialization.sql file for return types and return values.

        try {
            PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM getProductByUUID(?)");
            SQLValueArguments sqlValueArguments = new SQLValueArguments();

            sqlValueArguments.setArgument(uuid);

            sqlValueArguments.setArgumentsInStatement(queryStatement);

            queryStatement.execute();
            ResultSet resultSet = queryStatement.getResultSet();
            resultSet.next();

            ProductInformation productInformation = new ProductInformation();

            productInformation.setUUID(resultSet.getString("product_UUID"))
                    .setName(resultSet.getString("name"))
                    .setSerialNumber(resultSet.getString("serial_number"))
                    .setShortDescription(resultSet.getString("short_description"))
                    .setIsHidden(resultSet.getBoolean("is_hidden"))
                    .setLongDescription(resultSet.getString("long_description"));

            return productInformation;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected ProductInformation getProductByName(String name) {
        // SQL function: getProductByName(argName TEXT)
        // Call by: SELECT * FROM getProductByName('some-name');
        // Look at the database_initialization.sql file for return types and return values.

        try {
            PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM getProductByName(?)");
            SQLValueArguments sqlValueArguments = new SQLValueArguments();

            sqlValueArguments.setArgument(name);

            sqlValueArguments.setArgumentsInStatement(queryStatement);

            queryStatement.execute();
            ResultSet resultSet = queryStatement.getResultSet();
            resultSet.next();

            ProductInformation productInformation = new ProductInformation();

            productInformation.setUUID(resultSet.getString("product_UUID"))
                    .setName(resultSet.getString("name"))
                    .setSerialNumber(resultSet.getString("serial_number"))
                    .setShortDescription(resultSet.getString("short_description"))
                    .setIsHidden(resultSet.getBoolean("is_hidden"))
                    .setLongDescription(resultSet.getString("long_description"));

            return productInformation;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected List<ProductInformation> getProductsBySerialNumber(String serialNumber) {
        // SQL function: getProductsBySerialNumber(argSerialNumber TEXT)
        // Call by: SELECT * FROM getProductsBySerialNumber('some-serial-number');
        // Look at the database_initialization.sql file for return types and return values.

        try {
            PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM getProductsBySerialNumber(?)");
            SQLValueArguments sqlValueArguments = new SQLValueArguments();

            sqlValueArguments.setArgument(serialNumber);

            sqlValueArguments.setArgumentsInStatement(queryStatement);

            queryStatement.execute();
            ResultSet resultSet = queryStatement.getResultSet();

            ArrayList<ProductInformation> productInformationArrayList = new ArrayList<>();
            while (resultSet.next()) {

                ProductInformation productInformation = new ProductInformation();

                productInformation.setUUID(resultSet.getString("product_UUID"))
                        .setName(resultSet.getString("name"))
                        .setSerialNumber(resultSet.getString("serial_number"))
                        .setShortDescription(resultSet.getString("short_description"))
                        .setIsHidden(resultSet.getBoolean("is_hidden"))
                        .setLongDescription(resultSet.getString("long_description"));

                productInformationArrayList.add(productInformation);
            }
            return productInformationArrayList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected List<ProductInformation> getProductsThatAreHidden() {
        // SQL function: getProductsThatAreHidden()
        // Call by: SELECT * FROM getProductsThatAreHidden();
        // Look at the database_initialization.sql file for return types and return values.

        try {
            PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM getProductsThatAreHidden()");

            queryStatement.execute();
            ResultSet resultSet = queryStatement.getResultSet();

            ArrayList<ProductInformation> productInformationArrayList = new ArrayList<>();
            while (resultSet.next()) {

                ProductInformation productInformation = new ProductInformation();

                productInformation.setUUID(resultSet.getString("product_UUID"))
                        .setName(resultSet.getString("name"))
                        .setSerialNumber(resultSet.getString("serial_number"))
                        .setShortDescription(resultSet.getString("short_description"))
                        .setIsHidden(resultSet.getBoolean("is_hidden"))
                        .setLongDescription(resultSet.getString("long_description"));

                productInformationArrayList.add(productInformation);
            }
            return productInformationArrayList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected List<ProductInformation> getProductsByCategoryName(String categoryName) {
        // SQL function: getProductsThatAreHidden(argCategoryName TEXT)
        // Call by: SELECT * FROM getProductsThatAreHidden('someCategoryName');
        // Look at the database_initialization.sql file for return types and return values.

        try {
            PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM getProductsByCategoryName(?)");
            SQLValueArguments sqlValueArguments = new SQLValueArguments();

            sqlValueArguments.setArgument(categoryName);

            sqlValueArguments.setArgumentsInStatement(queryStatement);

            queryStatement.execute();
            ResultSet resultSet = queryStatement.getResultSet();

            ArrayList<ProductInformation> productInformationArrayList = new ArrayList<>();
            while (resultSet.next()) {

                ProductInformation productInformation = new ProductInformation();

                productInformation.setUUID(resultSet.getString("product_UUID"))
                        .setName(resultSet.getString("name"))
                        .setSerialNumber(resultSet.getString("serial_number"))
                        .setShortDescription(resultSet.getString("short_description"))
                        .setIsHidden(resultSet.getBoolean("is_hidden"))
                        .setLongDescription(resultSet.getString("long_description"));

                productInformationArrayList.add(productInformation);
            }
            return productInformationArrayList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected List<ProductInformation> getProductsByManufactureName(String manufactureName) {
        // SQL function: getProductsByManufactureName(argManufactureName TEXT)
        // Call by: SELECT * FROM getProductsByManufactureName('someManufactureName');
        // Look at the database_initialization.sql file for return types and return values.

        try {
            PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM getProductsByManufactureName(?)");
            SQLValueArguments sqlValueArguments = new SQLValueArguments();

            sqlValueArguments.setArgument(manufactureName);

            sqlValueArguments.setArgumentsInStatement(queryStatement);

            queryStatement.execute();
            ResultSet resultSet = queryStatement.getResultSet();

            ArrayList<ProductInformation> productInformationArrayList = new ArrayList<>();
            while (resultSet.next()) {

                ProductInformation productInformation = new ProductInformation();

                productInformation.setUUID(resultSet.getString("product_UUID"))
                        .setName(resultSet.getString("name"))
                        .setSerialNumber(resultSet.getString("serial_number"))
                        .setShortDescription(resultSet.getString("short_description"))
                        .setIsHidden(resultSet.getBoolean("is_hidden"))
                        .setLongDescription(resultSet.getString("long_description"));

                productInformationArrayList.add(productInformation);
            }
            return productInformationArrayList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected List<ProductInformation> getProductsByDiscountName(String discountName) {
        // SQL function: getProductsByDiscountName(argDiscountName TEXT)
        // Call by: SELECT * FROM getProductsByDiscountName('someDiscountName');
        // Look at the database_initialization.sql file for return types and return values.

        try {
            PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM getProductsByDiscountName(?)");
            SQLValueArguments sqlValueArguments = new SQLValueArguments();

            sqlValueArguments.setArgument(discountName);

            sqlValueArguments.setArgumentsInStatement(queryStatement);

            queryStatement.execute();
            ResultSet resultSet = queryStatement.getResultSet();

            ArrayList<ProductInformation> productInformationArrayList = new ArrayList<>();
            while (resultSet.next()) {

                ProductInformation productInformation = new ProductInformation();

                productInformation.setUUID(resultSet.getString("product_UUID"))
                        .setName(resultSet.getString("name"))
                        .setSerialNumber(resultSet.getString("serial_number"))
                        .setShortDescription(resultSet.getString("short_description"))
                        .setIsHidden(resultSet.getBoolean("is_hidden"))
                        .setLongDescription(resultSet.getString("long_description"));

                productInformationArrayList.add(productInformation);
            }
            return productInformationArrayList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected ProductCategory getCategoryByProductUUID(String uuid) {
        // SQL function: getCategoryByProductUUID(argUUID UUID)
        // Call by: SELECT * FROM getCategoryByProductUUID('someUUID');
        // Look at the database_initialization.sql file for return types and return values.

        try {
            PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM getCategoryByProductUUID(?)");
            SQLValueArguments sqlValueArguments = new SQLValueArguments();

            sqlValueArguments.setArgument(uuid);

            sqlValueArguments.setArgumentsInStatement(queryStatement);

            queryStatement.execute();
            ResultSet resultSet = queryStatement.getResultSet();
            resultSet.next();

            ProductCategory productCategory = new ProductCategory();

            productCategory.setName(resultSet.getString("name"));

            if (resultSet.getInt("parent_id") != 0) {
                ProductCategory parentCategory = getCategoryByCategoryID(resultSet.getInt("parent_id"));
                productCategory.setProductCategoryParent(parentCategory);
            } else {
                productCategory.setProductCategoryParent((ProductCategory) null);
            }

            return productCategory;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected ProductCategory getCategoryByName(String name) {
        // SQL function: getCategoryByName(argName TEXT)
        // Call by: SELECT * FROM getCategoryByName('someCategoryName');
        // Look at the database_initialization.sql file for return types and return values.

        try {
            PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM getCategoryByName(?)");
            SQLValueArguments sqlValueArguments = new SQLValueArguments();

            sqlValueArguments.setArgument(name);

            sqlValueArguments.setArgumentsInStatement(queryStatement);

            queryStatement.execute();
            ResultSet resultSet = queryStatement.getResultSet();
            resultSet.next();

            ProductCategory productCategory = new ProductCategory();

            productCategory.setName(resultSet.getString("name"));

            if (resultSet.getInt("parent_id") != 0) {
                ProductCategory parentCategory = getCategoryByCategoryID(resultSet.getInt("parent_id"));
                productCategory.setProductCategoryParent(parentCategory);
            } else {
                productCategory.setProductCategoryParent((ProductCategory) null);
            }

            return productCategory;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected ProductCategory getCategoryByCategoryID(int categoryId) {
        // This method is most relevant for getting the parent category of a category.

        // SQL function: getCategoryByCategoryID(argID INT)
        // Call by: SELECT * FROM getCategoryByCategoryID('someCategoryId');
        // Look at the database_initialization.sql file for return types and return values.

        try {
            PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM getCategoryByCategoryID(?)");
            SQLValueArguments sqlValueArguments = new SQLValueArguments();

            sqlValueArguments.setArgument(categoryId);

            sqlValueArguments.setArgumentsInStatement(queryStatement);

            queryStatement.execute();
            ResultSet resultSet = queryStatement.getResultSet();
            resultSet.next();

            ProductCategory productCategory = new ProductCategory();

            productCategory.setName(resultSet.getString("name"));

            if (resultSet.getInt("parent_id") != 0) {
                ProductCategory parentCategory = getCategoryByCategoryID(resultSet.getInt("parent_id"));
                productCategory.setProductCategoryParent(parentCategory);
            } else {
                productCategory.setProductCategoryParent((ProductCategory) null);
            }

            return productCategory;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected ProductSpecification getSpecificationByProductUUID(String uuid) {
        // SQL function: getSpecificationByProductUUID(argUUID UUID)
        // Call by: SELECT * FROM getSpecificationByProductUUID('someUUID');
        // Look at the database_initialization.sql file for return types and return values.

        // T IS HERE...
        try {
            PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM getSpecificationByProductUUID(?)");
            SQLValueArguments sqlValueArguments = new SQLValueArguments();

            sqlValueArguments.setArgument(uuid);

            sqlValueArguments.setArgumentsInStatement(queryStatement);

            queryStatement.execute();
            ResultSet resultSet = queryStatement.getResultSet();

            ProductSpecification productSpecification = new ProductSpecification();

            while(resultSet.next()) {
                productSpecification.put(resultSet.getString("name"), resultSet.getString("specification_value"));
            }

            return productSpecification;

        } catch (SQLException e) {
            System.out.println(e);
            throw new RuntimeException();
        }
    }

    protected ManufacturingInformation getManufactureByProductUUID(String uuid) {
        // SQL function: getManufactureByProductUUID(argUUID UUID)
        // Call by: SELECT * FROM getManufactureByProductUUID('someUUID');
        // Look at the database_initialization.sql file for return types and return values.

        try {
            PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM getManufactureByProductUUID(?)");
            SQLValueArguments sqlValueArguments = new SQLValueArguments();

            sqlValueArguments.setArgument(uuid);

            sqlValueArguments.setArgumentsInStatement(queryStatement);

            queryStatement.execute();
            ResultSet resultSet = queryStatement.getResultSet();
            resultSet.next();

            ManufacturingInformation manufacturingInformation = new ManufacturingInformation();

            manufacturingInformation.setName(resultSet.getString("name"))
                            .setSupportPhoneNumber(resultSet.getString("support_phone"))
                            .setSupportMail(resultSet.getString("support_mail"));

            return manufacturingInformation;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected ManufacturingInformation getManufactureByName(String name) {
        // SQL function: getManufactureByName(argName TEXT)
        // Call by: SELECT * FROM getManufactureByName('someName');
        // Look at the database_initialization.sql file for return types and return values.

        try {
            PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM getManufactureByName(?)");
            SQLValueArguments sqlValueArguments = new SQLValueArguments();

            sqlValueArguments.setArgument(name);

            sqlValueArguments.setArgumentsInStatement(queryStatement);

            queryStatement.execute();
            ResultSet resultSet = queryStatement.getResultSet();
            resultSet.next();

            ManufacturingInformation manufacturingInformation = new ManufacturingInformation();

            manufacturingInformation.setName(resultSet.getString("name"))
                    .setSupportPhoneNumber(resultSet.getString("support_phone"))
                    .setSupportMail(resultSet.getString("support_mail"));

            return manufacturingInformation;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected DiscountInformation getDiscountByProductUUID(String uuid) {
        // SQL function: getDiscountByProductUUID(argUUID UUID)
        // Call by: SELECT * FROM getDiscountByProductUUID('someUUID');
        // Look at the database_initialization.sql file for return types and return values.

        try {
            PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM getDiscountByProductUUID(?)");
            SQLValueArguments sqlValueArguments = new SQLValueArguments();

            sqlValueArguments.setArgument(uuid);

            sqlValueArguments.setArgumentsInStatement(queryStatement);

            queryStatement.execute();
            ResultSet resultSet = queryStatement.getResultSet();
            resultSet.next();

            DiscountInformation discountInformation = new DiscountInformation();

            discountInformation.setName(resultSet.getString("name"))
                            .setStartingDate(resultSet.getTimestamp("start_date").toLocalDateTime().toLocalDate())
                            .setExpiringDate(resultSet.getTimestamp("end_date").toLocalDateTime().toLocalDate());

            return discountInformation;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected DiscountInformation getDiscountByName(String name) {
        // SQL function: getDiscountByName(argName TEXT)
        // Call by: SELECT * FROM getDiscountByName('someName');
        // Look at the database_initialization.sql file for return types and return values.

        try {
            PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM getDiscountByName(?)");
            SQLValueArguments sqlValueArguments = new SQLValueArguments();

            sqlValueArguments.setArgument(name);

            sqlValueArguments.setArgumentsInStatement(queryStatement);

            queryStatement.execute();
            ResultSet resultSet = queryStatement.getResultSet();
            resultSet.next();

            DiscountInformation discountInformation = new DiscountInformation();

            discountInformation.setName(resultSet.getString("name"))
                    .setStartingDate(resultSet.getTimestamp("start_date").toLocalDateTime().toLocalDate())
                    .setExpiringDate(resultSet.getTimestamp("end_date").toLocalDateTime().toLocalDate());

            return discountInformation;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected BigDecimal getDiscountPercentageByProductUUID(String uuid) {
        // SQL function: getDiscountPercentageByProductUUID(argUUID UUID)
        // Call by: SELECT * FROM getDiscountPercentageByProductUUID('someUUID');
        // Returns a NUMERIC value of the discount.

        try {
            PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM getDiscountPercentageByProductUUID(?)");
            SQLValueArguments sqlValueArguments = new SQLValueArguments();

            sqlValueArguments.setArgument(uuid);

            sqlValueArguments.setArgumentsInStatement(queryStatement);

            queryStatement.execute();
            ResultSet resultSet = queryStatement.getResultSet();
            resultSet.next();

            return resultSet.getBigDecimal("percentage");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected List<PriceInformation> getPricesByProductUUID(String uuid) {
        // SQL function: getPricesByProductUUID(argUUID UUID)
        // Call by: SELECT * FROM getPricesByProductUUID('someUUID');
        // Look at the database_initialization.sql file for return types and return values.

        try {
            PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM getPricesByProductUUID(?)");
            SQLValueArguments sqlValueArguments = new SQLValueArguments();

            sqlValueArguments.setArgument(uuid);

            sqlValueArguments.setArgumentsInStatement(queryStatement);

            queryStatement.execute();
            ResultSet resultSet = queryStatement.getResultSet();
            resultSet.next();

            ArrayList<PriceInformation> priceInformationArrayList = new ArrayList<>();
            while (resultSet.next()) {

                PriceInformation priceInformation = new PriceInformation();

                priceInformation.setPrice(resultSet.getBigDecimal("price"));

                priceInformationArrayList.add(priceInformation);
            }
            return priceInformationArrayList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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




    // TODO: The following procedures are missing before they can be implemented:
    // deleteProductByUUID()
    // deleteCategoryByName()
    // deleteSpecificationByProductUUID()
    // deleteManufactureByName()
    // deletePriceChangeByProductUUID()
    // deleteDiscountByName()
}