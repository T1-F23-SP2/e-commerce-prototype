package com.example.ecommerceprototype.pim.product_information;

import com.example.ecommerceprototype.pim.exceptions.DuplicateEntryException;
import com.example.ecommerceprototype.pim.exceptions.IncompleteProductInformationException;

public class ProductInformationBuilder extends ProductInformationWorker {

    protected ProductInformationBuilder() {
        super(new ProductInformation());
    }

    // TODO: Implement this method.
    @Override
    public ProductInformation submit() throws DuplicateEntryException, IncompleteProductInformationException {
        throw new UnsupportedOperationException();
    }
}