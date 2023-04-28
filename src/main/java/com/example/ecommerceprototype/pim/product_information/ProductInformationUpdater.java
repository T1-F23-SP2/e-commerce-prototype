package com.example.ecommerceprototype.pim.product_information;

import com.example.ecommerceprototype.pim.exceptions.DuplicateEntryException;
import com.example.ecommerceprototype.pim.exceptions.IncompleteProductCategoryInformation;

public class ProductInformationUpdater extends ProductInformationWorker {

    private ProductInformation productInformation;

    public ProductInformationUpdater(ProductInformation pi) {
        this.productInformation = pi;
    }

    public ProductInformationUpdater(String productUUID) {
        //Out commented because it gives error
        //DBDriver.getProductWhereUUIDEqualsX() = productUUID;
    }

    private ProductInformation getProductInformationFromUUID(String productUUID) {
        //Out commented because it gives error
        //DBDriver.getProductWhereUUIDEqualsX() = productUUID;
        throw new UnsupportedOperationException();
    }

    @Override
    public ProductInformation submit() throws DuplicateEntryException, IncompleteProductCategoryInformation {
        throw new UnsupportedOperationException();
    }
}