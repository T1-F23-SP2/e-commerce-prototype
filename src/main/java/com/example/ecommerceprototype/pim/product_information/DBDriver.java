package com.example.ecommerceprototype.pim.product_information;

import com.example.ecommerceprototype.pim.exceptions.*;
import com.example.ecommerceprototype.pim.sql_helpers.SQLConnection;
import com.example.ecommerceprototype.pim.sql_helpers.SQLValueArguments;
import com.example.ecommerceprototype.pim.util.FilterableArrayList;
import com.example.ecommerceprototype.pim.util.Functions;
import com.example.ecommerceprototype.pim.util.ProductList;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class DBDriver {

    // region DBDriver connection

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
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        }
        return instance;
    }

    // Overwrite the existing instance , for use in tests only
    protected static DBDriver setInstance(DBDriver dbDriver) {
        instance = dbDriver;
        return instance;
    }

    // Overwrite the existing instance , for use in tests only
    protected static DBDriver setInstance(Connection connection) {
        return setInstance(new DBDriver(connection));
    }

    // endregion region DBDriver connection

    // region Helper methods for formatting queries

    private static ProductInformation queryProductInformation(String uniqueIdentifier, PreparedStatement queryStatement, SQLValueArguments sqlValueArguments) throws SQLException {
        sqlValueArguments.setArgument(uniqueIdentifier);

        sqlValueArguments.setArgumentsInStatement(queryStatement);

        queryStatement.execute();
        ResultSet resultSet = queryStatement.getResultSet();
        resultSet.next();

        return getProductInformation(resultSet);
    }

    private static ProductInformation getProductInformation(ResultSet resultSet) throws SQLException {
        ProductInformation productInformation = new ProductInformation();

        productInformation.setUUID(resultSet.getString("product_UUID"))
                .setName(resultSet.getString("name"))
                .setSerialNumber(resultSet.getString("serial_number"))
                .setShortDescription(resultSet.getString("short_description"))
                .setIsHidden(resultSet.getBoolean("is_hidden"))
                .setLongDescription(resultSet.getString("long_description"));
        return productInformation;
    }

    private static ProductList getMultipleProductInformation(PreparedStatement queryStatement) throws SQLException {
        queryStatement.execute();
        ResultSet resultSet = queryStatement.getResultSet();

        ProductList productInformationArrayList = new ProductList();
        while (resultSet.next()) {

            ProductInformation productInformation = getProductInformation(resultSet);

            productInformationArrayList.add(productInformation);
        }
        return productInformationArrayList;
    }

    private static ProductList productsBySerialHelper(String serialNumber, PreparedStatement queryStatement) throws SQLException {
        new SQLValueArguments()
                .setArgument(serialNumber)
                .setArgumentsInStatement(queryStatement);

        return getMultipleProductInformation(queryStatement);
    }

    private ProductCategory queryProductCategory(PreparedStatement queryStatement, SQLValueArguments sqlValueArguments) throws SQLException, CategoryNotFoundException {
        sqlValueArguments.setArgumentsInStatement(queryStatement);

        queryStatement.execute();
        ResultSet resultSet = queryStatement.getResultSet();

        if (!resultSet.next()) return null;

        ProductCategory productCategory = new ProductCategory()
                .setName(resultSet.getString("name"));

        if (resultSet.getInt("parent_id") != 0) {
            return productCategory.setProductCategoryParent(getCategoryByCategoryID(resultSet.getInt("parent_id")));
        } else {
            return productCategory.setProductCategoryParent((ProductCategory) null);
        }
    }

    private ProductCategory getProductCategory(String name, PreparedStatement queryStatement) throws SQLException, CategoryNotFoundException {
        SQLValueArguments sqlValueArguments = new SQLValueArguments();

        sqlValueArguments.setArgument(name);

        return queryProductCategory(queryStatement, sqlValueArguments);
    }

    private ProductCategory getProductCategory(int id, PreparedStatement queryStatement) throws SQLException, CategoryNotFoundException {
        SQLValueArguments sqlValueArguments = new SQLValueArguments();

        sqlValueArguments.setArgument(id);

        return queryProductCategory(queryStatement, sqlValueArguments);
    }

    private static ManufacturingInformation queryManufacturingInformation(String uuid, PreparedStatement queryStatement) throws SQLException {
        new SQLValueArguments()
                .setArgument(uuid)
                .setArgumentsInStatement(queryStatement);

        queryStatement.execute();
        ResultSet resultSet = queryStatement.getResultSet();

        if (!resultSet.next()) return null;

        return new ManufacturingInformation()
                .setName(resultSet.getString("name"))
                .setSupportPhoneNumber(resultSet.getString("support_phone"))
                .setSupportMail(resultSet.getString("support_mail"));
    }

    private static DiscountInformation queryDiscountInformation(String uuid, PreparedStatement queryStatement) throws SQLException {
        new SQLValueArguments()
                .setArgument(uuid)
                .setArgumentsInStatement(queryStatement);

        queryStatement.execute();
        ResultSet resultSet = queryStatement.getResultSet();

        if (!resultSet.next()) return null;


        return new DiscountInformation()
                .setName(resultSet.getString("name"))
                .setStartingDate(resultSet.getTimestamp("start_date").toLocalDateTime().toLocalDate())
                .setExpiringDate(resultSet.getTimestamp("end_date").toLocalDateTime().toLocalDate());
    }

    // Counts amount of rows gotten from the query
    private static int countRowsInQuery(ResultSet resultSet) throws SQLException {
        int size = 0;
        while (resultSet.next()) {
            size++;
        }
        return size;
    }

    // endregion Helper methods for formatting queries

    // region Helper methods for checking if object exists in database

    public boolean productByUUIDExists(String uuid) throws SQLException {
        if (!Functions.isUUIDv4(uuid)) return false;


        PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM getProductByUUID(?)");
        new SQLValueArguments()
                .setArgument(uuid)
                .setArgumentsInStatement(queryStatement);

        ResultSet resultSet = queryStatement.executeQuery();

        return resultSet.next();

    }

    public boolean productByNameExists(String name) {
        try {
            PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM getProductByName(?)");
            new SQLValueArguments()
                    .setArgument(name)
                    .setArgumentsInStatement(queryStatement);

            queryStatement.execute();

            if (countRowsInQuery(queryStatement.getResultSet()) == 0) {
                return false; // There exists none.
            }
        } catch (SQLException e) {
            return false; // Assumes that an error in this step of the execution is a result of the name not being valid. If used w. get-method, the method should throw NotFoundException. Note: Could also be because of server connectivity-issues or any other SQL-related mishaps.
        }

        return true; // If used in an insert-method a DuplicateEntryException should be thrown.
    }

    private boolean productBySerialNumberExists(String serialNumber) {
        try {
            PreparedStatement queryStatement = connection.prepareStatement("SELECT name FROM products WHERE serial_number = ?");
            new SQLValueArguments()
                    .setArgument(serialNumber)
                    .setArgumentsInStatement(queryStatement);

            queryStatement.execute();

            if (countRowsInQuery(queryStatement.getResultSet()) == 0) {
                return false; // There exists none.
            }
        } catch (SQLException e) {
            return false; // Assumes that an error in this step of the execution is a result of the serial not being valid. If used w. get-method, the method should throw NotFoundException. Note: Could also be because of server connectivity-issues or any other SQL-related mishaps.
        }

        return true; // If used in an insert-method a DuplicateEntryException should be thrown.
    }

    public boolean categoryByNameExists(String name) {
        try {
            PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM getCategoryByName(?)");
            new SQLValueArguments()
                    .setArgument(name)
                    .setArgumentsInStatement(queryStatement);

            queryStatement.execute();

            if (countRowsInQuery(queryStatement.getResultSet()) == 0) {
                return false; // There exists none.
            }
        } catch (SQLException e) {
            return false; // If used w. get-method, the method should throw NotFoundException. Note: Could also be caused by server connectivity-issues or any other SQL-related mishaps.
        }

        return true; // If used in an insert-method a DuplicateEntryException should be thrown.
    }

    public boolean categoryByIdExists(int id) {
        try {
            PreparedStatement queryStatement = connection.prepareStatement("SELECT name FROM product_categories WHERE id = ?");
            new SQLValueArguments()
                    .setArgument(id)
                    .setArgumentsInStatement(queryStatement);

            queryStatement.execute();

            if (countRowsInQuery(queryStatement.getResultSet()) == 0) {
                return false; // There exists none.
            }
        } catch (SQLException e) {
            return false; // If used w. get-method, the method should throw NotFoundException. Note: Could also be caused by server connectivity-issues or any other SQL-related mishaps.
        }

        return true; // If used in an insert-method a DuplicateEntryException should be thrown.
    }

    public boolean manufacturerByNameExists(String name) {
        try {
            PreparedStatement queryStatement = connection.prepareStatement("SELECT id FROM manufactures WHERE name = ?");
            new SQLValueArguments()
                    .setArgument(name)
                    .setArgumentsInStatement(queryStatement);

            queryStatement.execute();

            if (countRowsInQuery(queryStatement.getResultSet()) == 0) {
                return false; // There exists none.
            }
        } catch (SQLException e) {
            return false; // If used w. get-method, the method should throw NotFoundException. Note: Could also be caused by server connectivity-issues or any other SQL-related mishaps.
        }

        return true; // If used in an insert-method a DuplicateEntryException should be thrown.
    }

    public boolean discountByNameExists(String name) {
        try {
            PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM getDiscountByName(?)");
            new SQLValueArguments()
                    .setArgument(name)
                    .setArgumentsInStatement(queryStatement);

            queryStatement.execute();

            if (countRowsInQuery(queryStatement.getResultSet()) == 0) {
                return false; // There exists none.
            }
        } catch (SQLException e) {
            return false; // If used w. get-method, the method should throw NotFoundException. Note: Could also be caused by server connectivity-issues or any other SQL-related mishaps.
        }

        return true; // If used in an insert-method a DuplicateEntryException should be thrown.
    }

    // endregion Helper methods for checking if object exists in database


    protected ProductList getAllProducts() throws UUIDNotFoundException, SQLException, CategoryNotFoundException {
        // TODO: Should be replaced by procedure
        PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM getAllProducts()");

        queryStatement.execute();
        ResultSet resultSet = queryStatement.getResultSet();

        ProductList productInformationArrayList = new ProductList();
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
    }

    protected ProductInformation getProductByUUID(String uuid) throws UUIDNotFoundException, SQLException {
        if (!this.productByUUIDExists(uuid)) {
            throw new UUIDNotFoundException();
        }

        PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM getProductByUUID(?)");
        SQLValueArguments sqlValueArguments = new SQLValueArguments();

        return queryProductInformation(uuid, queryStatement, sqlValueArguments);
    }

    protected ProductInformation getProductByName(String name) throws ProductNotFoundException, SQLException {
        if (!productByNameExists(name)) {
            throw new ProductNotFoundException();
        }

        PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM getProductByName(?)");
        SQLValueArguments sqlValueArguments = new SQLValueArguments();

        return queryProductInformation(name, queryStatement, sqlValueArguments);
    }

    protected ProductList getProductsBySerialNumber(String serialNumber) throws ProductNotFoundException, SQLException {
        if (!this.productBySerialNumberExists(serialNumber)) {
            throw new ProductNotFoundException();
        }

        PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM getProductsBySerialNumber(?)");
        return productsBySerialHelper(serialNumber, queryStatement);
    }

    protected ProductList getProductsThatAreHidden() throws SQLException {
        PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM getProductsThatAreHidden()");

        return getMultipleProductInformation(queryStatement);
    }

    protected ProductList getProductsByCategoryName(String categoryName) throws ProductNotFoundException, SQLException {
        if (!this.categoryByNameExists(categoryName)) {
            throw new ProductNotFoundException();
        }

        PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM getProductsByCategoryName(?)");
        return productsBySerialHelper(categoryName, queryStatement);
    }

    protected ProductList getProductsByManufactureName(String manufactureName) throws ProductNotFoundException, SQLException {
        if (!this.manufacturerByNameExists(manufactureName)) {
            throw new ProductNotFoundException();
        }

        PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM getProductsByManufactureName(?)");
        return productsBySerialHelper(manufactureName, queryStatement);
    }

    protected ProductList getProductsByDiscountName(String discountName) throws ProductNotFoundException, SQLException {
        if (!this.discountByNameExists(discountName)) {
            throw new ProductNotFoundException();
        }

        PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM getProductsByDiscountName(?)");
        return productsBySerialHelper(discountName, queryStatement);
    }

    protected FilterableArrayList<ProductCategory> getAllCategories() throws SQLException, CategoryNotFoundException {
        // TODO: Should be replaced by procedure
        PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM getAllCategories()");

        queryStatement.execute();
        ResultSet resultSet = queryStatement.getResultSet();

        FilterableArrayList<ProductCategory> productCategoryArrayList = new FilterableArrayList<>();
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
    }

    protected ProductCategory getCategoryByProductUUID(String uuid) throws UUIDNotFoundException, SQLException, CategoryNotFoundException {
        if (!this.productByUUIDExists(uuid)) {
            throw new UUIDNotFoundException();
        }

        PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM getCategoryByProductUUID(?)");
        return getProductCategory(uuid, queryStatement);

    }

    protected ProductCategory getCategoryByName(String name) throws CategoryNotFoundException, SQLException {
        if (!this.categoryByNameExists(name)) {
            throw new CategoryNotFoundException();
        }

        PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM getCategoryByName(?)");
        return getProductCategory(name, queryStatement);
    }

    protected ProductCategory getCategoryByCategoryID(int categoryId) throws CategoryNotFoundException, SQLException {
        // This method is most relevant for getting the parent category of a category.
        if (!this.categoryByIdExists(categoryId)) {
            throw new CategoryNotFoundException();
        }

        PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM getCategoryByCategoryID(?)");

        return this.getProductCategory(categoryId, queryStatement);
    }

    protected ProductSpecification getSpecificationByProductUUID(String uuid) throws UUIDNotFoundException, SQLException {
        if (!this.productByUUIDExists(uuid)) {
            throw new UUIDNotFoundException();
        }

        PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM getSpecificationByProductUUID(?)");
        new SQLValueArguments()
                .setArgument(uuid)
                .setArgumentsInStatement(queryStatement);

        queryStatement.execute();
        ResultSet resultSet = queryStatement.getResultSet();

        ProductSpecification productSpecification = new ProductSpecification();

        while (resultSet.next()) {
            productSpecification.put(resultSet.getString("name"), resultSet.getString("specification_value"));
        }

        return productSpecification;
    }

    protected FilterableArrayList<ManufacturingInformation> getAllManufactures() throws SQLException {
        // TODO: Should be replaced by procedure

        PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM getallmanufactures()");

        queryStatement.execute();
        ResultSet resultSet = queryStatement.getResultSet();

        FilterableArrayList<ManufacturingInformation> manufacturingInformationArrayList = new FilterableArrayList<>();
        while (resultSet.next()) {
            ManufacturingInformation manufacturingInformation = new ManufacturingInformation();

            manufacturingInformation.setName(resultSet.getString("name"))
                    .setSupportPhoneNumber(resultSet.getString("support_phone"))
                    .setSupportMail(resultSet.getString("support_mail"));

            manufacturingInformationArrayList.add(manufacturingInformation);
        }
        return manufacturingInformationArrayList;
    }


    protected ManufacturingInformation getManufactureByProductUUID(String uuid) throws UUIDNotFoundException, SQLException {
        if (!this.productByUUIDExists(uuid)) {
            throw new UUIDNotFoundException();
        }

        PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM getManufactureByProductUUID(?)");
        return queryManufacturingInformation(uuid, queryStatement);
    }

    protected ManufacturingInformation getManufactureByName(String name) throws ManufactureNotFoundException, SQLException {
        if (!this.manufacturerByNameExists(name)) {
            throw new ManufactureNotFoundException();
        }

        PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM getManufactureByName(?)");
        return queryManufacturingInformation(name, queryStatement);


    }

    protected FilterableArrayList<DiscountInformation> getAllDiscounts() throws SQLException {
        // TODO: Should be replaced by procedure

        PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM getAllDiscounts()");

        queryStatement.execute();
        ResultSet resultSet = queryStatement.getResultSet();

        FilterableArrayList<DiscountInformation> discountInformationArrayList = new FilterableArrayList<>();
        while (resultSet.next()) {
            DiscountInformation discountInformation = new DiscountInformation();

            discountInformation.setName(resultSet.getString("name"))
                    .setStartingDate(resultSet.getTimestamp("start_date").toLocalDateTime().toLocalDate())
                    .setExpiringDate(resultSet.getTimestamp("end_date").toLocalDateTime().toLocalDate());

            discountInformationArrayList.add(discountInformation);
        }
        return discountInformationArrayList;
    }


    protected DiscountInformation getDiscountByProductUUID(String uuid) throws UUIDNotFoundException, SQLException {
        if (!this.productByUUIDExists(uuid)) {
            throw new UUIDNotFoundException();
        }

        PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM getDiscountByProductUUID(?)");
        return queryDiscountInformation(uuid, queryStatement);
    }

    protected DiscountInformation getDiscountByName(String name) throws DiscountNotFoundException, SQLException {
        if (!this.discountByNameExists(name)) {
            throw new DiscountNotFoundException();
        }

        PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM getDiscountByName(?)");
        return queryDiscountInformation(name, queryStatement);

    }

    protected BigDecimal getDiscountPercentageByProductUUID(String uuid) throws UUIDNotFoundException, SQLException {
        // Returns a NUMERIC value of the discount.
        if (!this.productByUUIDExists(uuid)) {
            throw new UUIDNotFoundException();
        }

        PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM getDiscountPercentageByProductUUID(?)");
        new SQLValueArguments()
                .setArgument(uuid)
                .setArgumentsInStatement(queryStatement);

        queryStatement.execute();
        ResultSet resultSet = queryStatement.getResultSet();
        resultSet.next();

        return resultSet.getBigDecimal("percentage");
    }

    protected FilterableArrayList<PriceInformation> getPricesByProductUUID(String uuid) throws UUIDNotFoundException, SQLException {
        if (!this.productByUUIDExists(uuid)) {
            throw new UUIDNotFoundException();
        }

        PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM getPricesByProductUUID(?)");
        new SQLValueArguments()
                .setArgument(uuid)
                .setArgumentsInStatement(queryStatement);

        queryStatement.execute();
        ResultSet resultSet = queryStatement.getResultSet();

        FilterableArrayList<PriceInformation> priceInformationArrayList = new FilterableArrayList<>();
        while (resultSet.next()) {

            PriceInformation priceInformation = new PriceInformation();

            priceInformation.setPrice(resultSet.getBigDecimal("price"));

            priceInformationArrayList.add(priceInformation);
        }
        return priceInformationArrayList;
    }


    protected ProductInformation insertNewProduct(ProductInformation productInformation) throws IncompleteProductInformationException, SQLException, UUIDNotFoundException {
//            if(this.productNameExists(productInformation.getName()) | this.productSerialNumberExists(productInformation.getSerialNumber())) { // Does not check whether UUID exists, as this is only given afterward.
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

        this.insertNewPriceChange(
                UUID,
                productInformation.getPriceInformation().getPrice(),
                productInformation.getPriceInformation().getWholeSalePrice()
        );

        this.insertNewSpecification(
                UUID,
                productInformation.getProductSpecification()
        );

        return productInformation.setUUID(UUID);
    }

    protected void insertNewProductCategory(ProductCategory productCategory) throws SQLException {
        // OBS! The argParentCategoryName should be null if it doesn't have a parent category.
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
    }

    protected void insertNewManufacture(ManufacturingInformation manufacturingInformation) throws DuplicateEntryException, SQLException {
        // Check if the manufacture name already exists:
        if (this.manufacturerByNameExists(manufacturingInformation.getName())) {
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

    protected void insertNewDiscount(DiscountInformation discountInformation) throws SQLException, DuplicateEntryException {
        // Check if discount name already exists:
        if (this.discountByNameExists(discountInformation.getName())) {
            throw new DuplicateEntryException();
        }

        PreparedStatement insertStatement = connection.prepareStatement("CALL insertnewdiscount(?,?,?)");
        new SQLValueArguments()
                .setArgument(discountInformation.getName())
                .setArgument(discountInformation.getStartingDate())
                .setArgument(discountInformation.getExpiringDate())
                .setArgumentsInStatement(insertStatement);

        insertStatement.execute();
    }

    protected void insertNewSpecification(String uuid, ProductSpecification productSpecification) throws SQLException, UUIDNotFoundException {
        if (!this.productByUUIDExists(uuid)) {
            throw new UUIDNotFoundException();
        }

        for (String key : productSpecification.keySet()) { // As productSpecification is a HashMap...
            PreparedStatement insertStatement = connection.prepareStatement("CALL insertNewSpecification(?, ?, ?)");
            new SQLValueArguments()
                    .setArgument(uuid)
                    .setArgument(key)
                    .setArgument(productSpecification.get(key))
                    .setArgumentsInStatement(insertStatement);

            insertStatement.execute();
        }
    }

    protected void insertNewPriceChange(String uuid, BigDecimal price, BigDecimal wholeSalePrice) throws UUIDNotFoundException, SQLException {
        if (!this.productByUUIDExists(uuid)) {
            throw new UUIDNotFoundException();
        }

        PreparedStatement insertStatement = connection.prepareStatement("CALL insertNewPriceChange(?, ?, ?)");
        new SQLValueArguments()
                .setArgument(uuid)
                .setArgument(price)
                .setArgument(wholeSalePrice)
                .setArgumentsInStatement(insertStatement);

        insertStatement.execute();
    }

    protected void insertNewPriceChange(String uuid, BigDecimal price, BigDecimal wholeSalePrice, DiscountInformation discountInformation) throws UUIDNotFoundException, DiscountNotFoundException, SQLException {
        if (!this.productByUUIDExists(uuid)) {
            throw new UUIDNotFoundException();
        }

        if (!this.discountByNameExists(discountInformation.getName())) {
            throw new DiscountNotFoundException();
        }

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

        if (!this.productByUUIDExists(uuid)) {
            throw new UUIDNotFoundException();
        } else if (this.productByNameExists(productInformation.getName()) && !Objects.equals(productInformation.getName(), originalName)) {
            throw new DuplicateEntryException();
        } else if (!categoryByNameExists(productInformation.getProductCategory().getName())) {
            throw new CategoryNotFoundException();
        } else if (!manufacturerByNameExists(productInformation.getManufacturingInformation().getName())) {
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
        PreparedStatement updateStatement = connection.prepareStatement("CALL updateManufactureByName(?,?,?,?)");

        if (this.manufacturerByNameExists(manufacturingInformation.getName()) && !Objects.equals(manufacturingInformation.getName(), originalName)) {
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
        PreparedStatement updateStatement = connection.prepareStatement("CALL updateDiscountByName(?,?,?,?)");

        if (this.discountByNameExists(discountInformation.getName()) && !Objects.equals(discountInformation.getName(), originalName)) {
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

    protected void updateProductCategoryByName(String originalName, ProductCategory productCategory) throws CategoryNotFoundException, DuplicateEntryException, SQLException {
        PreparedStatement updateStatement = connection.prepareStatement("CALL updateProductCategoryByName(?,?,?)");

        if (this.categoryByNameExists(productCategory.getName()) && !Objects.equals(originalName, productCategory.getName())) {
            throw new DuplicateEntryException();
        } else if (!this.categoryByNameExists(productCategory.getProductCategoryParent().getName()) && productCategory.getProductCategoryParent().getName() != null) {
            throw new CategoryNotFoundException();
        } else {
            SQLValueArguments sqlValueArguments = new SQLValueArguments();
            sqlValueArguments.setArgument(originalName)
                    .setArgument(productCategory.getName())
                    .setArgument(productCategory.getProductCategoryParent().getName())
                    .setArgumentsInStatement(updateStatement);

            updateStatement.execute();
        }
    }

//    protected void updateSpecificationByProductUUIDAndKey(String uuid, String originalKey, ProductSpecification productSpecification) throws UUIDNotFoundException, SQLException {
//        PreparedStatement updateStatement = connection.prepareStatement("CALL updateSpecificationByProductUUIDAndKey(?,?,?)");
//
//        if (!this.productByUUIDExists(uuid)) {
//            throw new UUIDNotFoundException();
//        } else {
//            new SQLValueArguments()
//                    .setArgument(uuid)
//                    .setArgument(originalKey)
//                    .setArgumentsInStatement(updateStatement);
//
//            updateStatement.execute();
//        }
//    }


    protected void deleteProductByUUID(String uuid) throws SQLException, UUIDNotFoundException {
        PreparedStatement deleteStatement = connection.prepareStatement("CALL deleteProductByUUID(?)");

        if (!this.productByUUIDExists(uuid)) {
            throw new UUIDNotFoundException();
        } else {
            new SQLValueArguments()
                    .setArgument(uuid)
                    .setArgumentsInStatement(deleteStatement);

            deleteStatement.execute();
        }
    }

    protected void deleteProductCategoryByName(String name) throws CategoryNotFoundException, SQLException {
        PreparedStatement deleteStatement = connection.prepareStatement("CALL deleteProductCategoryByName(?)");

        if (!this.categoryByNameExists(name)) {
            throw new CategoryNotFoundException();
        } else {
            new SQLValueArguments()
                    .setArgument(name)
                    .setArgumentsInStatement(deleteStatement);

            deleteStatement.execute();
        }
    }

    protected void deleteManufactureByName(String name) throws SQLException, ManufactureNotFoundException {
        PreparedStatement deleteStatement = connection.prepareStatement("CALL deleteManufactureByName(?)");

        if (!this.manufacturerByNameExists(name)) {
            throw new ManufactureNotFoundException();
        } else {
            new SQLValueArguments()
                    .setArgument(name)
                    .setArgumentsInStatement(deleteStatement);

            deleteStatement.execute();
        }
    }

    protected void deleteSpecificationByProductUUIDAndKey(String uuid, String key) throws SQLException, UUIDNotFoundException {
        PreparedStatement deleteStatement = connection.prepareStatement("CALL deleteSpecificationByProductUUIDAndKey(?, ?)");

        if (!this.productByUUIDExists(uuid)) {
            throw new UUIDNotFoundException();
        } else {
            new SQLValueArguments()
                    .setArgument(uuid)
                    .setArgument(key)
                    .setArgumentsInStatement(deleteStatement);

            deleteStatement.execute();
        }
    }

    protected void deleteDiscountByName(String name) throws SQLException, DiscountNotFoundException {
        PreparedStatement deleteStatement = connection.prepareStatement("CALL deleteDiscountByName(?)");

        if (!this.discountByNameExists(name)) {
            throw new DiscountNotFoundException();
        } else {
            new SQLValueArguments()
                    .setArgument(name)
                    .setArgumentsInStatement(deleteStatement);

            deleteStatement.execute();
        }
    }

    /* There will not be a method for deleting a price change. This is a design choice. */
}