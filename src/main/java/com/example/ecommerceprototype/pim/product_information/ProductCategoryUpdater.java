package com.example.ecommerceprototype.pim.product_information;

import com.example.ecommerceprototype.pim.exceptions.DuplicateEntryException;
import com.example.ecommerceprototype.pim.exceptions.IncompleteProductCategoryInformation;

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
    public ProductCategory submit() throws DuplicateEntryException, IncompleteProductCategoryInformation {
        if (originalName == null) {
            throw new IncompleteProductCategoryInformation();
        }

        DBDriver.getInstance().updateProductCategoryByName(this.originalName, super.getProductCategory());
        return super.productCategory;
    }
}
