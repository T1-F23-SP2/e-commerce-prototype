package com.example.ecommerceprototype.pim.product_information;

import com.example.ecommerceprototype.pim.exceptions.*;
import com.example.ecommerceprototype.pim.sql_helpers.SQLConnection;
import com.example.ecommerceprototype.pim.sql_helpers.SQLConnectionMainInitializer;
import com.example.ecommerceprototype.pim.sql_helpers.SQLValueArguments;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBDriver {
    private static DBDriver instance;
    private final Connection connection;

    private DBDriver(Connection connection){
        this.connection = connection;
    }

    private DBDriver() throws SQLException, IOException {
        this(SQLConnection.getMainConnectionInitializeIfNeeded());
    }

    public static DBDriver getInstance() throws SQLException, IOException {
        if (instance == null) {
            instance = new DBDriver();
        }
        return instance;
    }

    private boolean productUUIDExists(String uuid) {
        try {
            PreparedStatement queryStatement = connection.prepareStatement("SELECT name FROM products WHERE product_uuid = ?");
            SQLValueArguments sqlValueArguments = new SQLValueArguments();
            sqlValueArguments.setArgument(uuid);
            sqlValueArguments.setArgumentsInStatement(queryStatement);
            queryStatement.execute();

        } catch (SQLException e) {
            return false; // Assumes that an error in this step of the execution is a result of the UUID not being valid. If used w. get-method, the method should throw UUIDNotFoundException. Note: Could also be because of server connectivity-issues or any other SQL-related mishaps.
        }

        return true; // If used in an insert-method a DuplicateEntryException should be thrown.
    }

    private boolean productNameExists(String name) {
        try {
            PreparedStatement queryStatement = connection.prepareStatement("SELECT product_uuid FROM products WHERE name = ?");
            SQLValueArguments sqlValueArguments = new SQLValueArguments();
            sqlValueArguments.setArgument(name);
            sqlValueArguments.setArgumentsInStatement(queryStatement);
            queryStatement.execute();

        } catch (SQLException e) {
            return false; // Assumes that an error in this step of the execution is a result of the name not being valid. If used w. get-method, the method should throw NotFoundException. Note: Could also be because of server connectivity-issues or any other SQL-related mishaps.
        }

        return true; // If used in an insert-method a DuplicateEntryException should be thrown.
    }

    private boolean productSerialNumberExists(String serialNumber) {
        try {
            PreparedStatement queryStatement = connection.prepareStatement("SELECT name FROM products WHERE serial_number = ?");
            SQLValueArguments sqlValueArguments = new SQLValueArguments();
            sqlValueArguments.setArgument(serialNumber);
            sqlValueArguments.setArgumentsInStatement(queryStatement);
            queryStatement.execute();

        } catch (SQLException e) {
            return false; // Assumes that an error in this step of the execution is a result of the serial not being valid. If used w. get-method, the method should throw NotFoundException. Note: Could also be because of server connectivity-issues or any other SQL-related mishaps.
        }

        return true; // If used in an insert-method a DuplicateEntryException should be thrown.
    }

    private boolean categoryNameExists(String name) {
        try {
            PreparedStatement queryStatement = connection.prepareStatement("SELECT id FROM product_categories WHERE name = ?");
            SQLValueArguments sqlValueArguments = new SQLValueArguments();
            sqlValueArguments.setArgument(name);
            sqlValueArguments.setArgumentsInStatement(queryStatement);
            queryStatement.execute();

        } catch (SQLException e) {
            return false; // If used w. get-method, the method should throw NotFoundException. Note: Could also be caused by server connectivity-issues or any other SQL-related mishaps.
        }

        return true; // If used in an insert-method a DuplicateEntryException should be thrown.
    }

    private boolean categoryIdExists(int id) {
        try {
            PreparedStatement queryStatement = connection.prepareStatement("SELECT name FROM product_categories WHERE id = ?");
            SQLValueArguments sqlValueArguments = new SQLValueArguments();
            sqlValueArguments.setArgument(id);
            sqlValueArguments.setArgumentsInStatement(queryStatement);
            queryStatement.execute();

        } catch (SQLException e) {
            return false; // If used w. get-method, the method should throw NotFoundException. Note: Could also be caused by server connectivity-issues or any other SQL-related mishaps.
        }

        return true; // If used in an insert-method a DuplicateEntryException should be thrown.
    }

    private boolean manufacturerNameExists(String name) {
        try {
            PreparedStatement queryStatement = connection.prepareStatement("SELECT id FROM manufactures WHERE name = ?");
            SQLValueArguments sqlValueArguments = new SQLValueArguments();
            sqlValueArguments.setArgument(name);
            sqlValueArguments.setArgumentsInStatement(queryStatement);
            queryStatement.execute();

        } catch (SQLException e) {
            return false; // If used w. get-method, the method should throw NotFoundException. Note: Could also be caused by server connectivity-issues or any other SQL-related mishaps.
        }

        return true; // If used in an insert-method a DuplicateEntryException should be thrown.
    }

    private boolean discountNameExists(String name) {
        try {
            PreparedStatement queryStatement = connection.prepareStatement("SELECT id FROM discounts WHERE name = ?");
            SQLValueArguments sqlValueArguments = new SQLValueArguments();
            sqlValueArguments.setArgument(name);
            sqlValueArguments.setArgumentsInStatement(queryStatement);
            queryStatement.execute();

        } catch (SQLException e) {
            return false; // If used w. get-method, the method should throw NotFoundException. Note: Could also be caused by server connectivity-issues or any other SQL-related mishaps.
        }

        return true; // If used in an insert-method a DuplicateEntryException should be thrown.
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

    protected String insertNewProduct(ProductInformation productInformation) throws IncompleteProductInformationException, DuplicateEntryException {
        // SQL function: insertNewProduct(argName VARCHAR, argSerialNumber VARCHAR, argShortDescription VARCHAR,
        //                                argProductCategoryName VARCHAR, argManufactureName VARCHAR, argLongDescription TEXT)
        // Call by: SELECT * FROM insertNewProduct('name', 'serialNumber', 'shortDescription',
        //                                          'productCategoryName', 'manufactureName', 'longDescription');
        // Look at the database_initialization.sql file for return types and return values.
        try {
            if(this.productNameExists(productInformation.getName()) | this.productSerialNumberExists(productInformation.getSerialNumber())) { // Does not check whether UUID exists, as this is only given afterwards.
                throw new DuplicateEntryException();
            }

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

            try {
                this.insertNewPriceChange(UUID,
                        productInformation.getPriceInformation().getPrice(),
                        productInformation.getPriceInformation().getWholeSalePrice());

                this.insertNewSpecification(UUID, productInformation.getProductSpecification());
            } catch(UUIDNotFoundException e) {
                throw new RuntimeException("Database issue: Invalid UUID received when creating product.");
            }

            return UUID;

        } catch (SQLException e) {
            System.out.println(e);
            throw new IncompleteProductInformationException();
        }
    }

    protected void insertNewProductCategory(ProductCategory productCategory) throws IncompleteProductCategoryInformation, DuplicateEntryException {
        // SQL function: insertNewProduct(argName VARCHAR, argParentCategoryName VARCHAR)
        // Call by: CALL insertNewProductCategory('name', 'parentCategoryName');
        // OBS! The argParentCategoryName should be null if it doesn't have a parent category.
        try {
            // Check if discount name already exists:
            if(this.categoryNameExists(productCategory.getName())) {
                throw new DuplicateEntryException();
            }

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

    protected void insertNewManufacture(ManufacturingInformation manufacturingInformation) throws IncompleteManufacturingInformation, DuplicateEntryException {
        // SQL function: insertNewManufacture(argName VARCHAR, argSupportPhone VARCHAR(32), argSupportMail VARCHAR)
        // Call by: CALL insertNewManufacture('manufactureName', 'supportPhone', 'supportMail');
        try {
            // Check if discount name already exists:
            if(this.manufacturerNameExists(manufacturingInformation.getName())) {
                throw new DuplicateEntryException();
            }

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

    protected void insertNewDiscount(DiscountInformation discountInformation) throws IncompleteDiscountInformation, DuplicateEntryException {
        // SQL function: insertNewManufacture(argName VARCHAR, argStartDate TIMESTAMP, argEndDate TIMESTAMP)
        // Call by: CALL insertNewDiscount('testDiscount', '01-06-2023', '01-07-2023');

        try {
            // Check if discount name already exists:
            if(this.discountNameExists(discountInformation.getName())) {
                throw new DuplicateEntryException();
            }

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

    protected void insertNewSpecification(String UUID, ProductSpecification productSpecification) throws UUIDNotFoundException {
        // SQL function: insertNewManufacture(argProductUUID UUID, argKey VARCHAR, argValue VARCHAR)
        // Call by: CALL insertNewSpecification('71bce9bd-ef5f-48c2-af68-9e721cf4f181', 'CPU', 'testValue1243');
        try {
            if(productSpecification.isEmpty()) { // Avoid SQLException if no specifications are specified.
                return;
            }

            if(!this.productUUIDExists(UUID)) {
                throw new UUIDNotFoundException();
            }

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

    protected void insertNewPriceChange(String uuid, BigDecimal price, BigDecimal wholeSalePrice) throws IncompleteProductInformationException, UUIDNotFoundException {
        // SQL function: insertNewManufacture(argProductUUID UUID, argPrice NUMERIC, argWholesalePrice NUMERIC)
        // Call by: CALL insertNewPriceChange('someUUID', 1234, 1234);
        try {
            if(!this.productUUIDExists(uuid)) {
                throw new UUIDNotFoundException();
            }

            PreparedStatement insertStatement = connection.prepareStatement("CALL insertNewPriceChange(?, ?, ?)");
            SQLValueArguments sqlValueArguments = new SQLValueArguments()
                    .setArgument(uuid)
                    .setArgument(price)
                    .setArgument(wholeSalePrice);

            sqlValueArguments.setArgumentsInStatement(insertStatement);
            insertStatement.execute();
        } catch (SQLException e) {
            System.out.println(e);
            throw new IncompleteProductInformationException();
        }
    }

    protected void insertNewPriceChange(String uuid, BigDecimal price, BigDecimal wholeSalePrice, DiscountInformation discountInformation) throws IncompleteProductInformationException, NotFoundException, UUIDNotFoundException {
        // SQL function: insertNewManufacture(argProductUUID UUID, argPrice NUMERIC, argWholesalePrice NUMERIC, argDiscountName VARCHAR)
        // Call by: CALL insertNewPriceChange('someUUID', 1234, 1234, 'discountName');
        try {
            if(!this.productUUIDExists(uuid)) {
                throw new UUIDNotFoundException();
            }
            if(!this.discountNameExists(discountInformation.getName())) {
                System.out.println("No such Discount name could be found.");
                throw new NotFoundException();
            }

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
            throw new IncompleteProductInformationException();
        }
    }

    protected void updateProductByUUID(String uuid, ProductInformation productInformation) throws CategoryNotFoundException, NotFoundException  {
        // SQL function: updateProductByUUID(argUUID UUID, argName VARCHAR,
        //                                  argSerialNumber VARCHAR, argShortDescription VARCHAR,
        //                                  argProductCategoryName VARCHAR, argManufactureName VARCHAR,
        //                                  argIsHidden BOOLEAN, argLongDescription TEXT)

        // Call by: CALL updateProductByUUID('someUUID', 'newName', 'newSerialNumber',
        //                                  'newShortDescription', 'newCategoryName',
        //                                  'newManufactureName', false, 'newLongDescription');
        try {
            PreparedStatement updateStatement = connection.prepareStatement("CALL updateProductByUUID(?,?,?,?,?,?,?,?)");
            SQLValueArguments sqlValueArguments = new SQLValueArguments();

            if(!this.productUUIDExists(uuid)) {
                throw new UUIDNotFoundException();
            }else {

                sqlValueArguments.setArgument(uuid);
                sqlValueArguments.setArgument(productInformation.getName());
                sqlValueArguments.setArgument(productInformation.getSerialNumber());
                sqlValueArguments.setArgument(productInformation.getShortDescription());
                sqlValueArguments.setArgument(productInformation.getProductCategory().getName());
                sqlValueArguments.setArgument(productInformation.getManufacturingInformation().getName());
                sqlValueArguments.setArgument(productInformation.getIsHidden());
                sqlValueArguments.setArgument(productInformation.getLongDescription());

                updateStatement.execute();

                if (!categoryNameExists(productInformation.getProductCategory().getName())){
                    throw new CategoryNotFoundException();
                }
                if (!manufacturerNameExists(productInformation.getManufacturingInformation().getName())){
                    throw new NotFoundException();
                }

            }

        }catch (SQLException | UUIDNotFoundException e){
            System.out.println(e);
        }
    }

    protected void updateManufactureByName(String name, ManufacturingInformation manufacturingInformation) throws NotFoundException{
        // SQL function: updateManufactureByName(argName VARCHAR, argNewName VARCHAR, argSupportPhone VARCHAR(32), argSupportMail VARCHAR)
        // Call by: CALL updateManufactureByName('oldName', 'newName', 'newSupportPhone', 'newSupportMail');
        try {
            PreparedStatement updateStatement = connection.prepareStatement("CALL updateManufactureByName(?,?,?,?)");
            SQLValueArguments sqlValueArguments = new SQLValueArguments();

            if(!this.manufacturerNameExists(name)) {
                throw new NotFoundException();
            }else {

                sqlValueArguments.setArgument(name);
                sqlValueArguments.setArgument(manufacturingInformation.getName());
                sqlValueArguments.setArgument(manufacturingInformation.getSupportPhoneNumber());
                sqlValueArguments.setArgument(manufacturingInformation.getSupportMail());

                updateStatement.execute();

            }

        }catch (SQLException e){
            System.out.println(e);
        }
    }

    protected void updateDiscountByName(String name, DiscountInformation discountInformation) throws NotFoundException{
        // SQL function: updateDiscountByName(argName VARCHAR, argNewName VARCHAR, argStartDate TIMESTAMP, argEndDate TIMESTAMP)
        // Call by: CALL updateDiscountByName('oldDiscountName', 'newDiscountName', '01-01-2023', '01-02-2023');

        try {
            PreparedStatement updateStatement = connection.prepareStatement("CALL updateDiscountByName(?,?,?,?)");
            SQLValueArguments sqlValueArguments = new SQLValueArguments();

            if(!this.manufacturerNameExists(name)) {
                throw new NotFoundException();
            }else {

                sqlValueArguments.setArgument(name);
                sqlValueArguments.setArgument(discountInformation.getName());
                sqlValueArguments.setArgument(discountInformation.getStartingDate());
                sqlValueArguments.setArgument(discountInformation.getExpiringDate());

                updateStatement.execute();

            }

        }catch (SQLException e){
            System.out.println(e);
        }
    }

    protected void updateProductCategoryByName(String name, ProductCategory productCategory) throws CategoryNotFoundException {
        // SQL function: updateProductCategoryByName(argName VARCHAR, argNewName VARCHAR, argParentCategoryName VARCHAR)
        // Call by: CALL updateProductCategoryByName('oldCategoryName', 'newCategoryName', 'parentCategoryName');

        try {
            PreparedStatement updateStatement = connection.prepareStatement("CALL updateProductCategoryByName(?,?,?)");
            SQLValueArguments sqlValueArguments = new SQLValueArguments();

            if(!this.categoryNameExists(name)) {
                throw new CategoryNotFoundException();
            }else {

                sqlValueArguments.setArgument(name);
                sqlValueArguments.setArgument(productCategory.getName());
                sqlValueArguments.setArgument(productCategory.getProductCategoryParent().getName());

                updateStatement.execute();

            }
            if (!this.categoryNameExists(productCategory.getProductCategoryParent().getName())) {
                throw new CategoryNotFoundException();
            }

        }catch (SQLException e){
            System.out.println(e);
        }
    }

    protected void updateSpecificationByProductUUIDAndKey(String uuid, ProductSpecification productSpecification) throws UUIDNotFoundException {
        // SQL function: updateSpecificationByProductUUIDAndKey(argProductUUID UUID, argKey VARCHAR, argNewKey VARCHAR, argNewValue VARCHAR)
        // Call by: CALL updateSpecificationByProductUUIDAndKey('someUUID', 'oldKeyName', 'newKeyName', 'newValue');

        try {
            PreparedStatement updateStatement = connection.prepareStatement("CALL updateSpecificationByProductUUIDAndKey(?,?,?)");
            SQLValueArguments sqlValueArguments = new SQLValueArguments();

            if(!this.productUUIDExists(uuid)) {
                throw new UUIDNotFoundException();
            }else {
                //What do you mean with this method???
                sqlValueArguments.setArgument(uuid);

                updateStatement.execute();

            }

        }catch (SQLException e){
            System.out.println(e);
        }
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