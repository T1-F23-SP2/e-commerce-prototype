package com.example.ecommerceprototype.pim.product_information;

import com.example.ecommerceprototype.pim.exceptions.DuplicateEntryException;
import com.example.ecommerceprototype.pim.exceptions.IncompleteProductCategoryInformation;

public abstract class ProductCategoryWorker {
    public ProductCategoryWorker() {
        throw new UnsupportedOperationException();
    }

    public void setName(String s) {
        throw new UnsupportedOperationException();
    }

    public void setCategoryParent(String name) {
        throw new UnsupportedOperationException();
    }

    public ProductCategory getProductCategory() {
        throw new UnsupportedOperationException();
    }

    public ProductCategory submit() throws DuplicateEntryException, IncompleteProductCategoryInformation {
        throw new UnsupportedOperationException();
    }
}
