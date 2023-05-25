package com.example.ecommerceprototype.oms.mockPIM;

public class ProductCategory {
    String name;
    ProductCategory productParent;

    public ProductCategory(String name, ProductCategory productParent) {
        this.name = name;
        this.productParent = productParent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductCategory getProductParent() {
        return productParent;
    }

    public void setProductParent(ProductCategory productParent) {
        this.productParent = productParent;
    }



}
