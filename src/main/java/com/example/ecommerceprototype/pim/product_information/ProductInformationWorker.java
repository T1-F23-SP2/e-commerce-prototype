package com.example.ecommerceprototype.pim.product_information;

import com.example.ecommerceprototype.pim.exceptions.DuplicateEntryException;
import com.example.ecommerceprototype.pim.exceptions.IncompleteProductCategoryInformation;

public abstract class ProductInformationWorker {
    public void setProductCategory(ProductCategory pc) {
        throw new UnsupportedOperationException();
    }

    public void setProductSpecification(ProductSpecification ps) {
        throw new UnsupportedOperationException();
    }

    public void setManufacturingInformation(ManufacturingInformation mi) {
        throw new UnsupportedOperationException();
    }

    public void setName(String s) {
        throw new UnsupportedOperationException();
    }

    public void setShortDescription(String s) {
        throw new UnsupportedOperationException();
    }

    public void setLongDescription(String s) {
        throw new UnsupportedOperationException();
    }

    public void setIsHidden(String s) {
        throw new UnsupportedOperationException();
    }

    public ProductInformation getProductInformation() {
        throw new UnsupportedOperationException();
    }

    public DiscountInformation submit() throws DuplicateEntryException, IncompleteProductCategoryInformation {
        throw new UnsupportedOperationException();
    }
}
