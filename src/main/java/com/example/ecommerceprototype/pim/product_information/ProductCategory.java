package com.example.ecommerceprototype.pim.product_information;

import com.example.ecommerceprototype.pim.sql_helpers.ValueExtractor;


public class ProductCategory {
    private String name;
    private ProductCategory productParent;

    public ProductCategory() {
        throw new UnsupportedOperationException();
    }

    public String getName() {
        throw new UnsupportedOperationException();
    }

    public ProductCategory getProductParent() {
        throw new UnsupportedOperationException();
    }

    protected void setName(String s) {
        throw new UnsupportedOperationException();
    }

    protected void setCategoryParent(String name) {
        throw new UnsupportedOperationException();
    }

    protected ProductCategory fromDB(ValueExtractor ve) {
        throw new UnsupportedOperationException();
    }
}
