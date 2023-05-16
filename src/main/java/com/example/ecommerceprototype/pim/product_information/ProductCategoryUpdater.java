package com.example.ecommerceprototype.pim.product_information;

import com.example.ecommerceprototype.pim.exceptions.CategoryNotFoundException;
import com.example.ecommerceprototype.pim.exceptions.DuplicateEntryException;
import com.example.ecommerceprototype.pim.exceptions.IncompleteProductCategoryInformation;

import java.sql.SQLException;

public class ProductCategoryUpdater extends ProductCategoryWorker { // TODO: Implement CategoryUpdater

    // This value is used when updating a categories' information.
    // We need this attribute because we won't know what the original category name was without it.
    private String originalName;

    public ProductCategoryUpdater(ProductCategory pc) {
        super(pc);
    }

    public String getOriginalName() {
        return this.originalName;
    }

    public ProductCategoryUpdater setOriginalName(String originalName) {
        this.originalName = originalName;
        return this;
    }

    @Override
    public ProductCategory submit() throws SQLException, DuplicateEntryException, CategoryNotFoundException {
        if (originalName == null) {
            originalName = this.getProductCategory().getName();
        }

        DBDriver.getInstance().updateProductCategoryByName(this.originalName, super.getProductCategory());
        return super.getProductCategory();
    }
}
