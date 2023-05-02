/* SQL DIALECT: PostgreSQL */


/* Creation of Product categories table */
CREATE TABLE Product_categories
(
    id        SERIAL       NOT NULL PRIMARY KEY,
    name      VARCHAR(128) NOT NULL UNIQUE,
    parent_id INT REFERENCES Product_categories (id) DEFAULT NULL
);

/* Creation of Manufactures table */
CREATE TABLE Manufactures
(
    id            SERIAL       NOT NULL PRIMARY KEY,
    name          VARCHAR(256) NOT NULL UNIQUE,
    support_phone VARCHAR(32)  DEFAULT NULL,
    support_mail  VARCHAR(256) DEFAULT NULL
);

/* Creation of Products table */
CREATE TABLE Products
(
    id                    SERIAL       NOT NULL PRIMARY KEY,
    product_UUID          UUID         NOT NULL UNIQUE DEFAULT gen_random_uuid(),
    name                  VARCHAR(256) NOT NULL UNIQUE,
    serial_number         VARCHAR(256) NOT NULL,
    short_description     VARCHAR(512) NOT NULL,
    product_categories_id INT          NOT NULL REFERENCES Product_categories (id),
    manufacture_id        INT          NOT NULL REFERENCES Manufactures (id),
    is_hidden             BOOLEAN      NOT NULL        DEFAULT FALSE,
    long_description      TEXT                         DEFAULT NULL
);

/* Creation of Specification of names table */
CREATE TABLE Specification_names
(
    id   SERIAL       NOT NULL PRIMARY KEY,
    name VARCHAR(256) NOT NULL UNIQUE
);

/* Creation of Specifications table */
CREATE TABLE Specifications
(
    product_id             INT          NOT NULL REFERENCES Products (id),
    specification_names_id INT          NOT NULL REFERENCES Specification_names (id),
    specification_value    VARCHAR(256) NOT NULL,

    PRIMARY KEY (product_id, specification_names_id)
);

/* Creation of Discounts table */
CREATE TABLE Discounts
(
    id         SERIAL       NOT NULL PRIMARY KEY,
    name       VARCHAR(256) NOT NULL UNIQUE,
    start_date TIMESTAMP    NOT NULL,
    end_date   TIMESTAMP    NOT NULL
);

/* Creation of Price history table */
CREATE TABLE Price_history
(
    id               SERIAL    NOT NULL PRIMARY KEY,
    price            NUMERIC   NOT NULL,
    wholesale_price  NUMERIC   NOT NULL,
    time_of_creation TIMESTAMP NOT NULL,
    product_id       INT       NOT NULL REFERENCES Products (id),
    discount_id      INT DEFAULT NULL REFERENCES Discounts (id)
);




/***** INDEXES *****/
CREATE INDEX ON Products(product_UUID);




/***** FUNCTIONS AND PROCEDURES *****/

/* Get Product by UUID */
CREATE OR REPLACE FUNCTION getProductByUUID(argUUID UUID)
    RETURNS TABLE (id INT, product_UUID UUID, name VARCHAR, serial_number VARCHAR, short_description VARCHAR, is_hidden BOOLEAN, long_description TEXT)
AS $$
BEGIN
    RETURN QUERY
        SELECT Products.id,
               Products.product_UUID,
               Products.name,
               Products.serial_number,
               Products.short_description,
               Products.is_hidden,
               Products.long_description
        FROM Products
        WHERE Products.product_UUID = argUUID;
END; $$
LANGUAGE plpgsql;


/* Get Product by Name */
CREATE OR REPLACE FUNCTION getProductByName(argName TEXT)
    RETURNS TABLE (id INT, product_UUID UUID, name VARCHAR, serial_number VARCHAR, short_description VARCHAR, is_hidden BOOLEAN, long_description TEXT)
AS $$
BEGIN
    RETURN QUERY
        SELECT Products.id,
               Products.product_UUID,
               Products.name,
               Products.serial_number,
               Products.short_description,
               Products.is_hidden,
               Products.long_description
        FROM Products
        WHERE Products.name = argName;
END; $$
LANGUAGE plpgsql;


/* Get Product by database id */
CREATE OR REPLACE FUNCTION getProductById(argId INT)
    RETURNS TABLE (id INT, product_UUID UUID, name VARCHAR, serial_number VARCHAR, short_description VARCHAR, is_hidden BOOLEAN, long_description TEXT)
AS $$
BEGIN
    RETURN QUERY
        SELECT Products.id,
               Products.product_UUID,
               Products.name,
               Products.serial_number,
               Products.short_description,
               Products.is_hidden,
               Products.long_description
        FROM Products
        WHERE Products.id = argId;
END; $$
LANGUAGE plpgsql;


/* Get Product by serial number */
CREATE OR REPLACE FUNCTION getProductsBySerialNumber(argSerialNumber TEXT)
    RETURNS TABLE (id INT, product_UUID UUID, name VARCHAR, serial_number VARCHAR, short_description VARCHAR, is_hidden BOOLEAN, long_description TEXT)
AS $$
BEGIN
    RETURN QUERY
        SELECT Products.id,
               Products.product_UUID,
               Products.name,
               Products.serial_number,
               Products.short_description,
               Products.is_hidden,
               Products.long_description
        FROM Products
        WHERE Products.serial_number = argSerialNumber;
END; $$
LANGUAGE plpgsql;


/* Get Products that are hidden */
CREATE OR REPLACE FUNCTION getProductsThatAreHidden()
    RETURNS TABLE (id INT, product_UUID UUID, name VARCHAR, serial_number VARCHAR, short_description VARCHAR, is_hidden BOOLEAN, long_description TEXT)
AS $$
BEGIN
    RETURN QUERY
        SELECT Products.id,
               Products.product_UUID,
               Products.name,
               Products.serial_number,
               Products.short_description,
               Products.is_hidden,
               Products.long_description
        FROM Products
        WHERE Products.is_hidden = true;
END; $$
LANGUAGE plpgsql;


/* Get Products by category */
CREATE OR REPLACE FUNCTION getProductsByCategoryName(argCategoryName TEXT)
    RETURNS TABLE (id INT, product_UUID UUID, name VARCHAR, serial_number VARCHAR, short_description VARCHAR, is_hidden BOOLEAN, long_description TEXT)
AS $$
BEGIN
    RETURN QUERY
        SELECT Products.id,
               Products.product_UUID,
               Products.name,
               Products.serial_number,
               Products.short_description,
               Products.is_hidden,
               Products.long_description
        FROM Products
                 INNER JOIN Product_categories ON Products.product_categories_id = Product_categories.id
        WHERE Product_categories.name = argCategoryName;
END; $$
LANGUAGE plpgsql;


/* Get Products by manufacture */
CREATE OR REPLACE FUNCTION getProductsByManufactureName(argManufactureName TEXT)
    RETURNS TABLE (id INT, product_UUID UUID, name VARCHAR, serial_number VARCHAR, short_description VARCHAR, is_hidden BOOLEAN, long_description TEXT)
AS $$
BEGIN
    RETURN QUERY
        SELECT Products.id,
               Products.product_UUID,
               Products.name,
               Products.serial_number,
               Products.short_description,
               Products.is_hidden,
               Products.long_description
        FROM Products
                 INNER JOIN Manufactures ON Products.manufacture_id = Manufactures.id
        WHERE Manufactures.name = argManufactureName;
END; $$
LANGUAGE plpgsql;


/* Get Products by discount */
CREATE OR REPLACE FUNCTION getProductsByDiscountName(argDiscountName TEXT)
    RETURNS TABLE (id INT, product_UUID UUID, name VARCHAR, serial_number VARCHAR, short_description VARCHAR, is_hidden BOOLEAN, long_description TEXT)
AS $$
BEGIN
    RETURN QUERY
        SELECT Products.id,
               Products.product_UUID,
               Products.name,
               Products.serial_number,
               Products.short_description,
               Products.is_hidden,
               Products.long_description
        FROM Products
                 INNER JOIN Price_history ON Products.id = Price_history.product_id
                 INNER JOIN Discounts ON Discounts.id = Price_history.discount_id
        WHERE Discounts.name = argDiscountName;
END; $$
LANGUAGE plpgsql;


/* Get Category by Product UUID */
CREATE OR REPLACE FUNCTION getCategoryByProductUUID(argUUID UUID)
    RETURNS TABLE (id INT, name VARCHAR, parent_id INT)
AS $$
BEGIN
    RETURN QUERY
        SELECT Product_categories.id,
               Product_categories.name,
               Product_categories.parent_id
        FROM Product_categories
                 INNER JOIN Products ON Products.product_categories_id = Product_categories.id
        WHERE Products.product_UUID = argUUID;
END; $$
LANGUAGE plpgsql;


/* Get Category by name */
CREATE OR REPLACE FUNCTION getCategoryByName(argName TEXT)
    RETURNS TABLE (id INT, name VARCHAR, parent_id INT)
AS $$
BEGIN
    RETURN QUERY
        SELECT Product_categories.id,
               Product_categories.name,
               Product_categories.parent_id
        FROM Product_categories
        WHERE Product_categories.name = argName;
END; $$
LANGUAGE plpgsql;


/* Get Category by ID */
CREATE OR REPLACE FUNCTION getCategoryByCategoryID(argID INT)
    RETURNS TABLE (id INT, name VARCHAR, parent_id INT)
AS $$
BEGIN
    RETURN QUERY
        SELECT Product_categories.id,
               Product_categories.name,
               Product_categories.parent_id
        FROM Product_categories
        WHERE Product_categories.id = argID;
END; $$
LANGUAGE plpgsql;


/* Get specification by Product UUID */
CREATE OR REPLACE FUNCTION getSpecificationByProductUUID(argUUID UUID)
    RETURNS TABLE (id INT, product_id INT, name VARCHAR, specification_value VARCHAR)
AS $$
BEGIN
    RETURN QUERY
        SELECT Specifications.product_id,
               Specification_names.name,
               Specifications.specification_value
        FROM Specifications
                 INNER JOIN Specification_names
                            ON Specifications.specification_names_id = Specification_names.id
                 INNER JOIN Products ON Specifications.product_id = Products.id
        WHERE Products.product_UUID = argUUID;
END; $$
LANGUAGE plpgsql;


/* Get Manufacture by Product UUID */
CREATE OR REPLACE FUNCTION getManufactureByProductUUID(argUUID UUID)
    RETURNS TABLE (id INT, name VARCHAR, support_phone VARCHAR, support_mail VARCHAR)
AS $$
BEGIN
    RETURN QUERY
        SELECT Manufactures.id,
               Manufactures.name,
               Manufactures.support_phone,
               Manufactures.support_mail
        FROM Manufactures
                 INNER JOIN Products ON Products.manufacture_id = Manufactures.id
        WHERE Products.product_UUID = argUUID;
END; $$
LANGUAGE plpgsql;


/* Get Manufacture by name */
CREATE OR REPLACE FUNCTION getManufactureByName(argName TEXT)
    RETURNS TABLE (id INT, name VARCHAR, support_phone VARCHAR, support_mail VARCHAR)
AS $$
BEGIN
    RETURN QUERY
        SELECT Manufactures.id,
               Manufactures.name,
               Manufactures.support_phone,
               Manufactures.support_mail
        FROM Manufactures
        WHERE Manufactures.name = argName;
END; $$
LANGUAGE plpgsql;


/* Get Manufacture by database id */
CREATE OR REPLACE FUNCTION getManufactureById(argId int)
    RETURNS TABLE (id INT, name VARCHAR, support_phone VARCHAR, support_mail VARCHAR)
AS $$
BEGIN
    RETURN QUERY
        SELECT Manufactures.id,
               Manufactures.name,
               Manufactures.support_phone,
               Manufactures.support_mail
        FROM Manufactures
        WHERE Manufactures.id = argId;
END; $$
LANGUAGE plpgsql;


/* Get Discount by Product UUID */
CREATE OR REPLACE FUNCTION getDiscountByProductUUID(argUUID UUID)
    RETURNS TABLE (id INT, name VARCHAR, start_date TIMESTAMP, end_date TIMESTAMP)
AS $$
BEGIN
    RETURN QUERY
        SELECT Discounts.id,
               Discounts.name,
               Discounts.start_date,
               Discounts.end_date
        FROM Discounts
                 INNER JOIN Price_history ON Discounts.id = Price_history.discount_id
                 INNER JOIN Products ON Price_history.product_id = Products.id
        WHERE Products.product_UUID = argUUID;
END; $$
LANGUAGE plpgsql;


/* Get Discount by name */
CREATE OR REPLACE FUNCTION getDiscountByName(argName TEXT)
    RETURNS TABLE (id INT, name VARCHAR, start_date TIMESTAMP, end_date TIMESTAMP)
AS $$
BEGIN
    RETURN QUERY
        SELECT Discounts.id,
               Discounts.name,
               Discounts.start_date,
               Discounts.end_date
        FROM Discounts
        WHERE Discounts.name = argName;
END; $$
LANGUAGE plpgsql;


/* Get Discount percentage by Product UUID */
CREATE OR REPLACE FUNCTION getDiscountPercentageByProductUUID(argUUID UUID)
    RETURNS TABLE (percentage NUMERIC)
AS $$
DECLARE
    newest_price  NUMERIC;
    earlier_price NUMERIC;
    percentage    NUMERIC;
BEGIN
    SELECT price
    INTO newest_price
    FROM Price_history
             INNER JOIN Products ON Price_history.product_id = Products.id
    WHERE Products.product_UUID = argUUID
    LIMIT 1;

    SELECT price
    INTO earlier_price
    FROM Price_history
             INNER JOIN Products ON Price_history.product_id = Products.id
    WHERE Products.product_UUID = argUUID
    LIMIT 1 OFFSET 1;

    IF newest_price = 0 OR earlier_price = 0 THEN
        RAISE EXCEPTION 'Cannot divide by zero';
    END IF;

    IF newest_price = earlier_price THEN
        RAISE EXCEPTION 'Same price';
    END IF;

    SELECT ((1 - newest_price / earlier_price) * 100) INTO percentage;

    RETURN QUERY SELECT percentage;
END; $$
LANGUAGE plpgsql;


/* Get Discount by database id */
CREATE OR REPLACE FUNCTION getDiscountById(argId int)
    RETURNS TABLE (id INT, name VARCHAR, start_date TIMESTAMP, end_date TIMESTAMP)
AS $$
BEGIN
    RETURN QUERY
        SELECT Discounts.id,
               Discounts.name,
               Discounts.start_date,
               Discounts.end_date
        FROM Discounts
        WHERE Discounts.id = argId;
END; $$
LANGUAGE plpgsql;


/* Get prices by Product UUID */
CREATE OR REPLACE FUNCTION getPricesByProductUUID(argUUID UUID)
    RETURNS TABLE (id INT, price NUMERIC, wholesale_price NUMERIC, time_of_creation TIMESTAMP, product_id INT, discount_id INT)
AS $$
BEGIN
    RETURN QUERY
        SELECT Price_history.id,
               Price_history.price,
               Price_history.wholesale_price,
               Price_history.time_of_creation,
               Price_history.product_id,
               Price_history.discount_id
        FROM Price_history
                 INNER JOIN Products ON Price_history.product_id = Products.id
        WHERE Products.product_UUID = argUUID
        ORDER BY time_of_creation DESC;
END; $$
LANGUAGE plpgsql;


/* Inserting a new product */
CREATE OR REPLACE FUNCTION insertNewProduct(argName VARCHAR, argSerialNumber VARCHAR, argShortDescription VARCHAR, argProductCategoryName VARCHAR, argManufactureName VARCHAR, argLongDescription TEXT)
    RETURNS TABLE (pId INT, product_UUID UUID, pName VARCHAR, pSerial_number VARCHAR, pShort_description VARCHAR, pIs_hidden BOOLEAN, pLong_description TEXT)
AS $$
BEGIN
    INSERT INTO Products
    (name, serial_number, short_description, product_categories_id, manufacture_id, long_description)
    VALUES (argName,
            argSerialNumber,
            argShortDescription,
            (SELECT id FROM Product_categories WHERE name = argProductCategoryName),
            (SELECT id FROM Manufactures WHERE name = argManufactureName),
            argLongDescription);

    RETURN QUERY
        SELECT Products.id,
               Products.product_UUID,
               Products.name,
               Products.serial_number,
               Products.short_description,
               Products.is_hidden,
               Products.long_description
        FROM Products
        WHERE name = argName;
END; $$
LANGUAGE plpgsql;


/* Inserting a new product category */
CREATE OR REPLACE PROCEDURE insertNewProductCategory(argName VARCHAR)
AS $$
BEGIN
    INSERT INTO Product_categories (name, parent_id)
    VALUES (argName, null);
END; $$
    LANGUAGE plpgsql;


/* Inserting a new product category */
CREATE OR REPLACE PROCEDURE insertNewProductCategory(argName VARCHAR, argParentCategoryName VARCHAR)
AS $$
BEGIN
    INSERT INTO Product_categories (name, parent_id)
    VALUES (argName, (SELECT id FROM Product_categories WHERE name = argParentCategoryName));
END; $$
LANGUAGE plpgsql;


/* Inserting a new manufacture */
CREATE OR REPLACE PROCEDURE insertNewManufacture(argName VARCHAR, argSupportPhone VARCHAR(32), argSupportMail VARCHAR)
AS $$
BEGIN
    INSERT INTO Manufactures
        (name, support_phone, support_mail)
    VALUES (argName, argSupportPhone, argSupportMail);
END; $$
LANGUAGE plpgsql;


/* Inserting a new discount */
CREATE OR REPLACE PROCEDURE insertNewDiscount(argName VARCHAR, argStartDate TIMESTAMP, argEndDate TIMESTAMP)
AS $$
BEGIN
    INSERT INTO Discounts
        (name, start_date, end_date)
    VALUES (argName, argStartDate, argEndDate);
END; $$
LANGUAGE plpgsql;


/* Inserting a new specification */
CREATE OR REPLACE PROCEDURE insertNewSpecification(argProductUUID VARCHAR, argKey VARCHAR, argValue VARCHAR)
AS $$
BEGIN
    IF (SELECT COUNT(*) FROM Specification_names WHERE name = argKey) < 1 THEN
        INSERT INTO Specification_names
            (name)
        VALUES (argKey);
    END IF;
    INSERT INTO Specifications
        (product_id, specification_names_id, specification_value)
    VALUES ((SELECT id FROM Products WHERE CAST(product_UUID AS VARCHAR) = argProductUUID),
            (SELECT id FROM Specification_names WHERE name = argKey),
            argValue);
END; $$
LANGUAGE plpgsql;

DROP PROCEDURE insertNewSpecification();

/* Inserting a new price change */
CREATE OR REPLACE PROCEDURE insertNewPriceChange(argProductUUID TEXT, argPrice NUMERIC, argWholesalePrice NUMERIC)
AS $$
BEGIN
    INSERT INTO Price_history
        (price, wholesale_price, time_of_creation, product_id)
    VALUES (argPrice,
            argWholesalePrice,
            now(),
            (SELECT id FROM Products WHERE CAST(product_UUID AS TEXT) = argProductUUID));
END; $$
LANGUAGE plpgsql;

DROP PROCEDURE insertNewPriceChange(argProductUUID VARCHAR, argPrice NUMERIC, argWholesalePrice NUMERIC);

/* Inserting a new price change with discount */
CREATE OR REPLACE PROCEDURE insertNewPriceChange(argProductUUID UUID, argPrice NUMERIC, argWholesalePrice NUMERIC, argDiscountName VARCHAR)
AS $$
BEGIN
    INSERT INTO Price_history
        (price, wholesale_price, time_of_creation, product_id, discount_id)
    VALUES (argPrice,
            argWholesalePrice,
            now(),
            (SELECT id FROM Products WHERE product_UUID = argProductUUID),
            (SELECT id FROM Discounts WHERE name = argDiscountName));
END; $$
LANGUAGE plpgsql;


/* Update product by UUID */
CREATE OR REPLACE PROCEDURE updateProductByUUID(argUUID UUID, argName VARCHAR, argSerialNumber VARCHAR, argShortDescription VARCHAR, argProductCategoryName VARCHAR, argManufactureName VARCHAR, argIsHidden BOOLEAN, argLongDescription TEXT)
AS $$
BEGIN
    UPDATE Products
    SET name                  = argName,
        serial_number         = argSerialNumber,
        short_description     = argShortDescription,
        product_categories_id = (SELECT id FROM Product_categories WHERE name = argProductCategoryName),
        manufacture_id        = (SELECT id FROM Manufactures WHERE name = argManufactureName),
        is_hidden             = argIsHidden,
        long_description      = argLongDescription
    WHERE product_UUID = argUUID;
END; $$
LANGUAGE plpgsql;


/* Update product category by name */
CREATE OR REPLACE PROCEDURE updateProductCategoryByName(argName VARCHAR, argNewName VARCHAR, argParentCategoryName VARCHAR)
AS $$
BEGIN
    UPDATE Product_categories
    SET name      = argNewName,
        parent_id = (SELECT id FROM Product_categories WHERE name = argParentCategoryName)
    WHERE name = argName;
END; $$
LANGUAGE plpgsql;


/* Update manufacture by name */
CREATE OR REPLACE PROCEDURE updateManufactureByName(argName VARCHAR, argNewName VARCHAR, argSupportPhone VARCHAR(32), argSupportMail VARCHAR)
AS $$
BEGIN
    UPDATE Manufactures
    SET name          = argNewName,
        support_phone = argSupportPhone,
        support_mail  = argSupportMail
    WHERE name = argName;
END; $$
LANGUAGE plpgsql;


/* Update discount by name */
CREATE OR REPLACE PROCEDURE updateDiscountByName(argName VARCHAR, argNewName VARCHAR, argStartDate TIMESTAMP, argEndDate TIMESTAMP)
AS $$
BEGIN
    UPDATE Discounts
    SET name       = argNewName,
        start_date = argStartDate,
        end_date   = argEndDate
    WHERE name = argName;
END; $$
LANGUAGE plpgsql;


/* Update specification by product UUID and specification key */
CREATE OR REPLACE PROCEDURE updateSpecificationByProductUUIDAndKey(argProductUUID UUID, argKey VARCHAR, argNewKey VARCHAR, argNewValue VARCHAR)
AS $$
BEGIN
    IF (SELECT COUNT(*) FROM Specification_names WHERE name = argNewKey) < 1 THEN
        INSERT INTO Specification_names
            (name)
        VALUES (argNewKey);
    END IF;

    UPDATE Specifications
    SET specification_names_id = (SELECT id FROM Specification_names WHERE name = argNewKey),
        specification_value    = argNewValue
    WHERE product_id = (SELECT id FROM Products WHERE product_UUID = argProductUUID)
      AND specification_names_id = (SELECT id FROM Specification_names WHERE name = argKey);
END; $$
LANGUAGE plpgsql;




-- TODO:
-- Delete product by UUID
-- Delete product category by name
-- Delete specification by name
-- Delete manufacture by name
