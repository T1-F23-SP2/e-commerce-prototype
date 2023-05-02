package com.example.ecommerceprototype.pim.product_information;

import com.example.ecommerceprototype.pim.exceptions.DuplicateEntryException;
import com.example.ecommerceprototype.pim.exceptions.IncompleteProductCategoryInformation;

public abstract class ProductCategoryWorker {
    ProductCategory productCategory;
    public ProductCategoryWorker() {
        this.productCategory = new ProductCategory();
    }

    public ProductCategoryWorker setName(String name) {
        this.productCategory.setName(name);
        return this;
    }

    public ProductCategoryWorker setCategoryParent(String name) {
        this.productCategory.setProductCategoryParent(name);
        return this;
    }

    public ProductCategory getProductCategory() {
        return this.productCategory;
    }

    public abstract ProductCategory submit() throws DuplicateEntryException, IncompleteProductCategoryInformation;
}