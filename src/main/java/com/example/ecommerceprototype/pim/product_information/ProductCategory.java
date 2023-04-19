package com.example.ecommerceprototype.pim.product_information;

import com.example.ecommerceprototype.pim.sql_helpers.ValueExtractor;


public class ProductCategory {
    private String name;
    private ProductCategory productCategoryParent;

    public ProductCategory() {

    }

    public ProductCategory(String name) {
        this.name = name;
    }

    public ProductCategory(String name, ProductCategory productCategoryParent) {
        this.name = name;
        this.productCategoryParent = productCategoryParent;
    }

    public String getName() {
        return this.name;
    }

    public ProductCategory getProductCategoryParent() {
        return this.productCategoryParent;
    }

    protected void setName(String s) {
        this.name = s;
    }

    // TODO: Create below method, by searching for Category by name.
    protected void setProductCategoryParent(String name) {
        throw new UnsupportedOperationException();
    }

    // TODO: Create fromDB
    protected ProductCategory fromDB(ValueExtractor ve) {
        throw new UnsupportedOperationException();
    }
}
