package com.example.ecommerceprototype.shop.mockShop;

public class ProductCategory {
    public String name;
    public ProductCategory productParent;

    public String getName() {
        return name;
    }

    public ProductCategory getProductParent() {
        return productParent;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProductParent(ProductCategory productParent) {
        this.productParent = productParent;
    }
}
