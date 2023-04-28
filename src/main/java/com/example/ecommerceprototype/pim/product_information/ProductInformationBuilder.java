package com.example.ecommerceprototype.pim.product_information;

import com.example.ecommerceprototype.pim.exceptions.DuplicateEntryException;
import com.example.ecommerceprototype.pim.exceptions.IncompleteProductCategoryInformation;

public class ProductInformationBuilder extends ProductInformationWorker {
    ProductInformationBuilder productInformationBuilder;

    protected ProductInformationBuilder() {
        productInformationBuilder = new ProductInformationBuilder();
    }

    @Override
    public ProductInformation submit() throws DuplicateEntryException, IncompleteProductCategoryInformation {
        throw new UnsupportedOperationException();
    }
}
