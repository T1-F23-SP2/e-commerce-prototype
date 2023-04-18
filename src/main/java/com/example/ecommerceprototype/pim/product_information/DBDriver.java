package com.example.ecommerceprototype.pim.product_information;

import com.example.ecommerceprototype.pim.sql_helpers.SQLValueArguments;

import java.util.List;

public class DBDriver {
    public DBDriver() {
        throw new UnsupportedOperationException();
    }

    protected ProductInformation getProductWhereUUIDEqualsX(SQLValueArguments x) {
        throw new UnsupportedOperationException();
    }

    protected ProductInformation getProductWhereNameEqualsX(SQLValueArguments x) {
        throw new UnsupportedOperationException();
    }

    protected List<ProductInformation> getProductsWhereCategoryNameEqualsX (String name) {
        throw new UnsupportedOperationException();
    }

    protected List<ProductInformation> getProductsWhereCategoryNameEqualsX (ProductCategory pc) {
        throw new UnsupportedOperationException();
    }

    protected List<ProductInformation> getProductsWhereManufactureNameEqualsX (String name) {
        throw new UnsupportedOperationException();
    }

    protected List<ProductInformation> getProductsWhereManufactureNameEqualsX (ManufacturingInformation mi) {
        throw new UnsupportedOperationException();
    }

    protected List<ProductInformation> getProductsWhereDiscountNameEqualsX (String name) {
        throw new UnsupportedOperationException();
    }

    protected List<ProductInformation> getProductsWhereDiscountNameEqualsX (DiscountInformation di) {
        throw new UnsupportedOperationException();
    }

    protected ProductCategory getCategoryWhereNameEqualsX(String name) {
        throw new UnsupportedOperationException();
    }

    protected ManufacturingInformation getManufactureByName(String name) {
        throw new UnsupportedOperationException();
    }

    protected DiscountInformation getDiscountByName(String name) {
        throw new UnsupportedOperationException();
    }

    protected boolean insertNewEntry(String tableName, SQLValueArguments x) {
        throw new UnsupportedOperationException();
    }

    protected boolean updateEntry(String id, SQLValueArguments x) {
        throw new UnsupportedOperationException();
    }
}