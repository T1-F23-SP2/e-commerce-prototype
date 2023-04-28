package com.example.ecommerceprototype.pim.product_information;

import com.example.ecommerceprototype.pim.exceptions.DuplicateEntryException;
import com.example.ecommerceprototype.pim.exceptions.IncompleteProductCategoryInformation;

public class ProductInformationBuilder extends ProductInformationWorker {
    ProductInformation productInformation;

    protected ProductInformationBuilder() {
        productInformation = new ProductInformation();
    }

    @Override
    public ProductInformation submit() throws DuplicateEntryException, IncompleteProductCategoryInformation {
        throw new UnsupportedOperationException();
    }
}
