package com.example.ecommerceprototype.pim.product_information;

import com.example.ecommerceprototype.pim.exceptions.DuplicateEntryException;
import com.example.ecommerceprototype.pim.exceptions.IncompleteProductInformationException;

public class ProductInformationBuilder extends ProductInformationWorker {

    public ProductInformationBuilder() {
        super(new ProductInformation());
    }

    // TODO: Implement this method.
    @Override
    public ProductInformation submit() throws DuplicateEntryException, IncompleteProductInformationException {
        return DBDriver.getInstance().insertNewProduct(super.getProductInformation());
    }
}