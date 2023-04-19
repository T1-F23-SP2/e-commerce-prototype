package com.example.ecommerceprototype.pim.product_information;

import com.example.ecommerceprototype.pim.exceptions.DuplicateEntryException;
import com.example.ecommerceprototype.pim.exceptions.IncompleteProductCategoryInformation;

public abstract class ProductCategoryWorker {
    ProductCategory productCategory;
    public ProductCategoryWorker() {
        this.productCategory = new ProductCategory();
    }

    public void setName(String s) {
        this.productCategory.setName(s);
    }

    public void setCategoryParent(String name) {
        this.productCategory.setProductCategoryParent(name);
    }

    public ProductCategory getProductCategory() {
        return this.productCategory;
    }

    public ProductCategory submit() throws DuplicateEntryException, IncompleteProductCategoryInformation {
        throw new UnsupportedOperationException();
    }
}
