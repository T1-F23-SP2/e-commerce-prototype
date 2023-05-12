package com.example.ecommerceprototype.pim.product_information;

import com.example.ecommerceprototype.pim.exceptions.DuplicateEntryException;
import com.example.ecommerceprototype.pim.exceptions.IncompleteProductInformationException;

public class ProductInformationUpdater extends ProductInformationWorker {

    public ProductInformationUpdater(ProductInformation pi) {
        super(pi);
    }

    @Override
    public ProductInformation submit() throws DuplicateEntryException, IncompleteProductInformationException {
        DBDriver.getInstance().updateProductByUUID(super.getProductInformation().getProductUUID(), super.getProductInformation());
        return super.getProductInformation();
    }
}