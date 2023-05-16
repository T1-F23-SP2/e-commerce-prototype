package com.example.ecommerceprototype.pim.product_information;

import com.example.ecommerceprototype.pim.exceptions.*;
import com.example.ecommerceprototype.pim.sql_helpers.SQLConnection;
import com.example.ecommerceprototype.pim.sql_helpers.SQLValueArguments;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DBDriver {
    private static DBDriver instance;
    private final Connection connection;

    private DBDriver(Connection connection) {
        this.connection = connection;
    }

    private DBDriver() throws SQLException, IOException {
        this(SQLConnection.getMainConnectionInitializeIfNeeded());
    }

    public static DBDriver getInstance() {
        if (instance == null) {
            try {
                instance = new DBDriver();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return instance;
    }

    // Counts amount of rows gotten from the query
    private int countRowsInQuery(ResultSet resultSet) throws SQLException {
        int size = 0;
        while (resultSet.next()) {
            size++;
        }
        return size;
    }

    public boolean checkIfProductByUUIDExists(String uuid) {
        try {
            PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM getProductByUUID(?)");
            SQLValueArguments sqlValueArguments = new SQLValueArguments();
            sqlValueArguments.setArgument(uuid);
            sqlValueArguments.setArgumentsInStatement(queryStatement);
            queryStatement.execute();

            if (countRowsInQuery(queryStatement.getResultSet()) == 0) {
                return false; // There exists none.
            }
        } catch (SQLException e) {
            return false; // Assumes that an error in this step of the execution is a result of the UUID not being valid. If used w. get-method, the method should throw UUIDNotFoundException. Note: Could also be because of server connectivity-issues or any other SQL-related mishaps.
        }

        return true; // If used in an insert-method a DuplicateEntryException should be thrown.
    }

    public boolean checkIfProductByNameExists(String name) {
        try {
            PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM getProductByName(?)");
            SQLValueArguments sqlValueArguments = new SQLValueArguments();
            sqlValueArguments.setArgument(name);
            sqlValueArguments.setArgumentsInStatement(queryStatement);
            queryStatement.execute();

            if (countRowsInQuery(queryStatement.getResultSet()) == 0) {
                return false; // There exists none.
            }
        } catch (SQLException e) {
            return false; // Assumes that an error in this step of the execution is a result of the name not being valid. If used w. get-method, the method should throw NotFoundException. Note: Could also be because of server connectivity-issues or any other SQL-related mishaps.
        }

        return true; // If used in an insert-method a DuplicateEntryException should be thrown.
    }

//    private boolean productSerialNumberExists(String serialNumber) {
//        try {
//            PreparedStatement queryStatement = connection.prepareStatement("SELECT name FROM products WHERE serial_number = ?");
//            SQLValueArguments sqlValueArguments = new SQLValueArguments();
//            sqlValueArguments.setArgument(serialNumber);
//            sqlValueArguments.setArgumentsInStatement(queryStatement);
//            queryStatement.execute();
//        } catch (SQLException e) {
//            return false; // Assumes that an error in this step of the execution is a result of the serial not being valid. If used w. get-method, the method should throw NotFoundException. Note: Could also be because of server connectivity-issues or any other SQL-related mishaps.
//        }
//
//        return true; // If used in an insert-method a DuplicateEntryException should be thrown.
//    }

    public boolean checkIfCategoryByNameExists(String name) {
        try {
            PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM getCategoryByName(?)");
            SQLValueArguments sqlValueArguments = new SQLValueArguments();
            sqlValueArguments.setArgument(name);
            sqlValueArguments.setArgumentsInStatement(queryStatement);
            queryStatement.execute();

            if (countRowsInQuery(queryStatement.getResultSet()) == 0) {
                return false; // There exists none.
            }
        } catch (SQLException e) {
            return false; // If used w. get-method, the method should throw NotFoundException. Note: Could also be caused by server connectivity-issues or any other SQL-related mishaps.
        }

        return true; // If used in an insert-method a DuplicateEntryException should be thrown.
    }

//    public boolean categoryIdExists(int id) {
//        try {
//            PreparedStatement queryStatement = connection.prepareStatement("SELECT name FROM product_categories WHERE id = ?");
//            SQLValueArguments sqlValueArguments = new SQLValueArguments();
//            sqlValueArguments.setArgument(id);
//            sqlValueArguments.setArgumentsInStatement(queryStatement);
//            queryStatement.execute();
//
//        } catch (SQLException e) {
//            return false; // If used w. get-method, the method should throw NotFoundException. Note: Could also be caused by server connectivity-issues or any other SQL-related mishaps.
//        }
//
//        return true; // If used in an insert-method a DuplicateEntryException should be thrown.
//    }

    public boolean checkIfManufactureByNameExists(String name) {
        try {
            PreparedStatement queryStatement = connection.prepareStatement("SELECT id FROM manufactures WHERE name = ?");
            SQLValueArguments sqlValueArguments = new SQLValueArguments();
            sqlValueArguments.setArgument(name);
            sqlValueArguments.setArgumentsInStatement(queryStatement);
            queryStatement.execute();

            if (countRowsInQuery(queryStatement.getResultSet()) == 0) {
                return false; // There exists none.
            }
        } catch (SQLException e) {
            return false; // If used w. get-method, the method should throw NotFoundException. Note: Could also be caused by server connectivity-issues or any other SQL-related mishaps.
        }

        return true; // If used in an insert-method a DuplicateEntryException should be thrown.
    }

    public boolean checkIfDiscountByNameExists(String name) {
        try {
            PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM getDiscountByName(?)");
            SQLValueArguments sqlValueArguments = new SQLValueArguments();
            sqlValueArguments.setArgument(name);
            sqlValueArguments.setArgumentsInStatement(queryStatement);
            queryStatement.execute();

            if (countRowsInQuery(queryStatement.getResultSet()) == 0) {
                return false; // There exists none.
            }
        } catch (SQLException e) {
            return false; // If used w. get-method, the method should throw NotFoundException. Note: Could also be caused by server connectivity-issues or any other SQL-related mishaps.
        }

        return true; // If used in an insert-method a DuplicateEntryException should be thrown.
    }

    protected List<ProductInformation> getAllProducts() {
        // TODO: Should be replaced by procedure
        try {
            PreparedStatement queryStatement = connection.prepareStatement("SELECT product_uuid, name, serial_number, short_description, is_hidden, long_description FROM Products");

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
                        .setLongDescription(resultSet.getString("long_description"))
                        .setProductCategory(this.getCategoryByProductUUID(resultSet.getString("product_UUID")))
                        .setProductSpecification(this.getSpecificationByProductUUID(resultSet.getString("product_UUID")))
                        .setManufacturingInformation(this.getManufactureByProductUUID(resultSet.getString("product_UUID")))
                        .setPriceInformation(this.getPricesByProductUUID(resultSet.getString("product_UUID")).get(0));

                productInformationArrayList.add(productInformation);
            }
            return productInformationArrayList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
                    .setLongDescription(resultSet.getString("long_description"))
                    .setProductCategory(this.getCategoryByProductUUID(resultSet.getString("product_UUID")))
                    .setProductSpecification(this.getSpecificationByProductUUID(resultSet.getString("product_UUID")))
                    .setManufacturingInformation(this.getManufactureByProductUUID(resultSet.getString("product_UUID")))
                    .setPriceInformation(this.getPricesByProductUUID(resultSet.getString("product_UUID")).get(0));

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
                    .setLongDescription(resultSet.getString("long_description"))
                    .setProductCategory(this.getCategoryByProductUUID(resultSet.getString("product_UUID")))
                    .setProductSpecification(this.getSpecificationByProductUUID(resultSet.getString("product_UUID")))
                    .setManufacturingInformation(this.getManufactureByProductUUID(resultSet.getString("product_UUID")))
                    .setPriceInformation(this.getPricesByProductUUID(resultSet.getString("product_UUID")).get(0));

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
                        .setLongDescription(resultSet.getString("long_description"))
                        .setProductCategory(this.getCategoryByProductUUID(resultSet.getString("product_UUID")))
                        .setProductSpecification(this.getSpecificationByProductUUID(resultSet.getString("product_UUID")))
                        .setManufacturingInformation(this.getManufactureByProductUUID(resultSet.getString("product_UUID")))
                        .setPriceInformation(this.getPricesByProductUUID(resultSet.getString("product_UUID")).get(0));

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
                        .setLongDescription(resultSet.getString("long_description"))
                        .setProductCategory(this.getCategoryByProductUUID(resultSet.getString("product_UUID")))
                        .setProductSpecification(this.getSpecificationByProductUUID(resultSet.getString("product_UUID")))
                        .setManufacturingInformation(this.getManufactureByProductUUID(resultSet.getString("product_UUID")))
                        .setPriceInformation(this.getPricesByProductUUID(resultSet.getString("product_UUID")).get(0));

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
                        .setLongDescription(resultSet.getString("long_description"))
                        .setProductCategory(this.getCategoryByProductUUID(resultSet.getString("product_UUID")))
                        .setProductSpecification(this.getSpecificationByProductUUID(resultSet.getString("product_UUID")))
                        .setManufacturingInformation(this.getManufactureByProductUUID(resultSet.getString("product_UUID")))
                        .setPriceInformation(this.getPricesByProductUUID(resultSet.getString("product_UUID")).get(0));

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
                        .setLongDescription(resultSet.getString("long_description"))
                        .setProductCategory(this.getCategoryByProductUUID(resultSet.getString("product_UUID")))
                        .setProductSpecification(this.getSpecificationByProductUUID(resultSet.getString("product_UUID")))
                        .setManufacturingInformation(this.getManufactureByProductUUID(resultSet.getString("product_UUID")))
                        .setPriceInformation(this.getPricesByProductUUID(resultSet.getString("product_UUID")).get(0));

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
                        .setLongDescription(resultSet.getString("long_description"))
                        .setProductCategory(this.getCategoryByProductUUID(resultSet.getString("product_UUID")))
                        .setProductSpecification(this.getSpecificationByProductUUID(resultSet.getString("product_UUID")))
                        .setManufacturingInformation(this.getManufactureByProductUUID(resultSet.getString("product_UUID")))
                        .setPriceInformation(this.getPricesByProductUUID(resultSet.getString("product_UUID")).get(0));

                productInformationArrayList.add(productInformation);
            }
            return productInformationArrayList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ProductCategory> getAllCategories() {
        // TODO: Should be replaced by procedure
        try {
            PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM Product_categories");

            queryStatement.execute();
            ResultSet resultSet = queryStatement.getResultSet();

            ArrayList<ProductCategory> productCategoryArrayList = new ArrayList<>();
            while (resultSet.next()) {

                ProductCategory productCategory = new ProductCategory();

                productCategory.setName(resultSet.getString("name"));

                if (resultSet.getInt("parent_id") != 0) {
                    ProductCategory parentCategory = getCategoryByCategoryID(resultSet.getInt("parent_id"));
                    productCategory.setProductCategoryParent(parentCategory);
                } else {
                    productCategory.setProductCategoryParent(new ProductCategory());
                }

                productCategoryArrayList.add(productCategory);
            }
            return productCategoryArrayList;

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
            new SQLValueArguments()
                    .setArgument(name)
                    .setArgumentsInStatement(queryStatement);

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

            while (resultSet.next()) {
                productSpecification.put(resultSet.getString("name"), resultSet.getString("specification_value"));
            }

            return productSpecification;

        } catch (SQLException e) {
            System.out.println(e);
            throw new RuntimeException();
        }
    }

    public List<ManufacturingInformation> getAllManufactures() {
        // TODO: Should be replaced by procedure
        try {
            PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM Manufactures");

            queryStatement.execute();
            ResultSet resultSet = queryStatement.getResultSet();

            ArrayList<ManufacturingInformation> manufacturingInformationArrayList = new ArrayList<>();
            while (resultSet.next()) {
                ManufacturingInformation manufacturingInformation = new ManufacturingInformation();

                manufacturingInformation.setName(resultSet.getString("name"))
                        .setSupportPhoneNumber(resultSet.getString("support_phone"))
                        .setSupportMail(resultSet.getString("support_mail"));

                manufacturingInformationArrayList.add(manufacturingInformation);
            }
            return manufacturingInformationArrayList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
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

    public List<DiscountInformation> getAllDiscounts() {
        // TODO: Should be replaced by procedure
        try {
            PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM Discounts");

            queryStatement.execute();
            ResultSet resultSet = queryStatement.getResultSet();

            ArrayList<DiscountInformation> discountInformationArrayList = new ArrayList<>();
            while (resultSet.next()) {
                DiscountInformation discountInformation = new DiscountInformation();

                discountInformation.setName(resultSet.getString("name"))
                        .setStartingDate(resultSet.getTimestamp("start_date").toLocalDateTime().toLocalDate())
                        .setExpiringDate(resultSet.getTimestamp("end_date").toLocalDateTime().toLocalDate());


                discountInformationArrayList.add(discountInformation);
            }
            return discountInformationArrayList;

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


    protected ProductInformation insertNewProduct(ProductInformation productInformation) throws IncompleteProductInformationException, DuplicateEntryException {
        try {
//            if(this.productNameExists(productInformation.getName()) | this.productSerialNumberExists(productInformation.getSerialNumber())) { // Does not check whether UUID exists, as this is only given afterwards.
//                throw new DuplicateEntryException();
//            }
            // The above is done in the GUIController.java
            // For it to be here, there should be thrown a more specific exception

            PreparedStatement insertStatement = connection.prepareStatement("SELECT product_uuid FROM insertnewproduct(?,?,?,?,?,?)");
            // TODO: After getters is implemented, make checks for the existence of the referenced productCategory and ManufacturingInformation.

            new SQLValueArguments()
                    .setArgument(productInformation.getName())
                    .setArgument(productInformation.getSerialNumber())
                    .setArgument(productInformation.getShortDescription())
                    .setArgument(productInformation.getProductCategory().getName())
                    .setArgument(productInformation.getManufacturingInformation().getName())
                    .setArgument(productInformation.getLongDescription())
                    .setArgumentsInStatement(insertStatement);

            insertStatement.execute();

            ResultSet rs = insertStatement.getResultSet();
            rs.next(); // Move "cursor" to first row
            String UUID = rs.getString("product_UUID");

            try {
                this.insertNewPriceChange(
                        UUID,
                        productInformation.getPriceInformation().getPrice(),
                        productInformation.getPriceInformation().getWholeSalePrice()
                );

                this.insertNewSpecification(
                        UUID,
                        productInformation.getProductSpecification()
                );
            } catch (UUIDNotFoundException e) {
                throw new RuntimeException("Database issue: Invalid UUID received when creating product.");
            }

            return new ProductInformation().setUUID(UUID);

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
//            if (this.categoryNameExists(productCategory.getName())) {
//                throw new DuplicateEntryException();
//            }
            // The above is done in the GUIController.java

            PreparedStatement insertStatement;

            if (!Objects.equals(productCategory.getProductCategoryParent().getName(), "")) {
                insertStatement = connection.prepareStatement("CALL insertnewproductcategory(?, ?)");
                new SQLValueArguments()
                        .setArgument(productCategory.getName())
                        .setArgument(productCategory.getProductCategoryParent().getName())
                        .setArgumentsInStatement(insertStatement);
            } else {
                insertStatement = connection.prepareStatement("CALL insertnewproductcategory(?)");
                new SQLValueArguments()
                        .setArgument(productCategory.getName())
                        .setArgumentsInStatement(insertStatement);
            }

            insertStatement.execute();

        } catch (SQLException e) {
            System.out.println(e);
            throw new IncompleteProductCategoryInformation();
        }
    }

    protected void insertNewManufacture(ManufacturingInformation manufacturingInformation) throws DuplicateEntryException, SQLException {
        // Check if the manufacture name already exists:
        if (this.checkIfManufactureByNameExists(manufacturingInformation.getName())) {
            throw new DuplicateEntryException();
        }

        PreparedStatement insertStatement = connection.prepareStatement("CALL insertnewmanufacture(?, ?, ?)");

        new SQLValueArguments()
                .setArgument(manufacturingInformation.getName())
                .setArgument(manufacturingInformation.getSupportPhoneNumber())
                .setArgument(manufacturingInformation.getSupportMail())
                .setArgumentsInStatement(insertStatement);

        insertStatement.execute();
    }

    protected void insertNewDiscount(DiscountInformation discountInformation) throws IncompleteDiscountInformation, DuplicateEntryException {
        try { // Remove this try catch
            // Check if discount name already exists:
//            if(this.discountNameExists(discountInformation.getName())) {
//                throw new DuplicateEntryException();
//            }

            PreparedStatement insertStatement = connection.prepareStatement("CALL insertnewdiscount(?,?,?)");
            new SQLValueArguments()
                    .setArgument(discountInformation.getName())
                    .setArgument(discountInformation.getStartingDate())
                    .setArgument(discountInformation.getExpiringDate())
                    .setArgumentsInStatement(insertStatement);

            insertStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IncompleteDiscountInformation();
        }
    }

    protected void insertNewSpecification(String UUID, ProductSpecification productSpecification) throws UUIDNotFoundException {
        // SQL function: insertNewManufacture(argProductUUID UUID, argKey VARCHAR, argValue VARCHAR)
        // Call by: CALL insertNewSpecification('71bce9bd-ef5f-48c2-af68-9e721cf4f181', 'CPU', 'testValue1243');
        try {
//            if(productSpecification.isEmpty()) { // Avoid SQLException if no specifications are specified.
//                return;
//            }
//
//            if(!this.productUUIDExists(UUID)) {
//                throw new UUIDNotFoundException();
//            }

            for (String key : productSpecification.keySet()) { // As productSpecification is a HashMap...
                PreparedStatement insertStatement = connection.prepareStatement("CALL insertNewSpecification(?, ?, ?)");
                new SQLValueArguments()
                        .setArgument(UUID)
                        .setArgument(key)
                        .setArgument(productSpecification.get(key))
                        .setArgumentsInStatement(insertStatement);

                insertStatement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void insertNewPriceChange(String uuid, BigDecimal price, BigDecimal wholeSalePrice) throws IncompleteProductInformationException, UUIDNotFoundException {
        // SQL function: insertNewManufacture(argProductUUID UUID, argPrice NUMERIC, argWholesalePrice NUMERIC)
        // Call by: CALL insertNewPriceChange('someUUID', 1234, 1234);
        try {
//            if(!this.productUUIDExists(uuid)) {
//                throw new UUIDNotFoundException();
//            }

            PreparedStatement insertStatement = connection.prepareStatement("CALL insertNewPriceChange(?, ?, ?)");
            new SQLValueArguments()
                    .setArgument(uuid)
                    .setArgument(price)
                    .setArgument(wholeSalePrice)
                    .setArgumentsInStatement(insertStatement);

            insertStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IncompleteProductInformationException();
        }
    }

    protected void insertNewPriceChange(String uuid, BigDecimal price, BigDecimal wholeSalePrice, DiscountInformation discountInformation) throws IncompleteProductInformationException, NotFoundException, SQLException {
        // SQL function: insertNewManufacture(argProductUUID UUID, argPrice NUMERIC, argWholesalePrice NUMERIC, argDiscountName VARCHAR)
        // Call by: CALL insertNewPriceChange('someUUID', 1234, 1234, 'discountName');
//            if(!this.productUUIDExists(uuid)) {
//                throw new UUIDNotFoundException();
//            }
//            if(!this.discountNameExists(discountInformation.getName())) {
//                System.out.println("No such Discount name could be found.");
//                throw new NotFoundException();
//            }

            PreparedStatement insertStatement = connection.prepareStatement("CALL insertnewpricechange(?,?,?,?)");

            new SQLValueArguments()
                    .setArgument(uuid)
                    .setArgument(price)
                    .setArgument(wholeSalePrice)
                    .setArgument(discountInformation.getName())
                    .setArgumentsInStatement(insertStatement);

            insertStatement.execute();
    }

    protected void updateProductByUUID(String uuid, String originalName, ProductInformation productInformation) throws SQLException, UUIDNotFoundException, DuplicateEntryException, CategoryNotFoundException, ManufactureNotFoundException {

            PreparedStatement updateStatement = connection.prepareStatement("CALL updateProductByUUID(?,?,?,?,?,?,?,?)");

            if (!this.checkIfProductByUUIDExists(uuid)) {
                throw new UUIDNotFoundException();
            } else if (this.checkIfProductByNameExists(productInformation.getName()) && !Objects.equals(productInformation.getName(), originalName)) {
                throw new DuplicateEntryException();
            } else if (!checkIfCategoryByNameExists(productInformation.getProductCategory().getName())) {
                throw new CategoryNotFoundException();
            } else if (!checkIfManufactureByNameExists(productInformation.getManufacturingInformation().getName())) {
                throw new ManufactureNotFoundException();
            } else {
                new SQLValueArguments().setArgument(uuid)
                        .setArgument(productInformation.getName())
                        .setArgument(productInformation.getSerialNumber())
                        .setArgument(productInformation.getShortDescription())
                        .setArgument(productInformation.getProductCategory().getName())
                        .setArgument(productInformation.getManufacturingInformation().getName())
                        .setArgument(productInformation.getIsHidden())
                        .setArgument(productInformation.getLongDescription())
                        .setArgumentsInStatement(updateStatement);

                updateStatement.execute();
            }
    }

    protected void updateManufactureByName(String originalName, ManufacturingInformation manufacturingInformation) throws DuplicateEntryException, SQLException {
        // SQL function: updateManufactureByName(argName VARCHAR, argNewName VARCHAR, argSupportPhone VARCHAR(32), argSupportMail VARCHAR)
        // Call by: CALL updateManufactureByName('oldName', 'newName', 'newSupportPhone', 'newSupportMail');
        PreparedStatement updateStatement = connection.prepareStatement("CALL updateManufactureByName(?,?,?,?)");

        if (this.checkIfManufactureByNameExists(manufacturingInformation.getName()) && !Objects.equals(manufacturingInformation.getName(), originalName)) {
            throw new DuplicateEntryException();
        } else {
            SQLValueArguments sqlValueArguments = new SQLValueArguments();
            sqlValueArguments.setArgument(originalName)
                    .setArgument(manufacturingInformation.getName())
                    .setArgument(manufacturingInformation.getSupportPhoneNumber())
                    .setArgument(manufacturingInformation.getSupportMail())
                    .setArgumentsInStatement(updateStatement);

            updateStatement.execute();
        }
    }

    protected void updateDiscountByName(String originalName, DiscountInformation discountInformation) throws SQLException, DuplicateEntryException {
        // SQL function: updateDiscountByName(argName VARCHAR, argNewName VARCHAR, argStartDate TIMESTAMP, argEndDate TIMESTAMP)
        // Call by: CALL updateDiscountByName('oldDiscountName', 'newDiscountName', '01-01-2023', '01-02-2023');

        PreparedStatement updateStatement = connection.prepareStatement("CALL updateDiscountByName(?,?,?,?)");

        if (this.checkIfDiscountByNameExists(discountInformation.getName()) && !Objects.equals(discountInformation.getName(), originalName)) {
            throw new DuplicateEntryException();
        } else {

            new SQLValueArguments()
                    .setArgument(originalName)
                    .setArgument(discountInformation.getName())
                    .setArgument(discountInformation.getStartingDate())
                    .setArgument(discountInformation.getExpiringDate())
                    .setArgumentsInStatement(updateStatement);

            updateStatement.execute();
        }
    }

    protected void updateProductCategoryByName(String originalName, ProductCategory productCategory) throws CategoryNotFoundException, DuplicateEntryException {
        try {
            PreparedStatement updateStatement = connection.prepareStatement("CALL updateProductCategoryByName(?,?,?)");

            if (this.checkIfCategoryByNameExists(productCategory.getName()) && !Objects.equals(originalName, productCategory.getName())) {
                throw new DuplicateEntryException();
            } else if (!this.checkIfCategoryByNameExists(productCategory.getProductCategoryParent().getName()) && productCategory.getProductCategoryParent().getName() != null) {
                throw new CategoryNotFoundException();
            } else {
                SQLValueArguments sqlValueArguments = new SQLValueArguments();
                sqlValueArguments.setArgument(originalName)
                        .setArgument(productCategory.getName())
                        .setArgument(productCategory.getProductCategoryParent().getName())
                        .setArgumentsInStatement(updateStatement);

                updateStatement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void updateSpecificationByProductUUIDAndKey(String uuid, ProductSpecification productSpecification) throws UUIDNotFoundException {
        // SQL function: updateSpecificationByProductUUIDAndKey(argProductUUID UUID, argKey VARCHAR, argNewKey VARCHAR, argNewValue VARCHAR)
        // Call by: CALL updateSpecificationByProductUUIDAndKey('someUUID', 'oldKeyName', 'newKeyName', 'newValue');

        try {
            PreparedStatement updateStatement = connection.prepareStatement("CALL updateSpecificationByProductUUIDAndKey(?,?,?)");
            SQLValueArguments sqlValueArguments = new SQLValueArguments();

            if (!this.checkIfProductByUUIDExists(uuid)) {
                throw new UUIDNotFoundException();
            } else {
                //What do you mean with this method???
                sqlValueArguments.setArgument(uuid);

                updateStatement.execute();

            }

        } catch (SQLException e) {
            e.printStackTrace();
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