package com.example.ecommerceprototype.pim.product_information;

import com.example.ecommerceprototype.pim.util.Nameable;


public class ProductCategory implements Nameable {
    private String name;
    private ProductCategory productCategoryParent;

    public String getName() {
        return this.name;
    }

    public ProductCategory getProductCategoryParent() {
        if (this.productCategoryParent == null) {
            return null;
        }
        return this.productCategoryParent;
    }

    protected ProductCategory setName(String name) {
        this.name = name;
        return this;
    }

    protected ProductCategory setProductCategoryParent(ProductCategory productCategoryParent) {
        this.productCategoryParent = productCategoryParent;
        return this;
    }

    protected ProductCategory setProductCategoryParent(String name) {
        this.productCategoryParent = new ProductCategory().setName(name);
        return this;
    }
}
