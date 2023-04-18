package com.example.ecommerceprototype.pim.product_information;

import com.example.ecommerceprototype.pim.exceptions.DuplicateEntryException;
import com.example.ecommerceprototype.pim.exceptions.IncompleteProductCategoryInformation;

public class DiscountInformationUpdater extends DiscountInformationWorker {
    private DiscountInformation discountInformation;

    public DiscountInformationUpdater(DiscountInformation di) {
        throw new UnsupportedOperationException();
    }

    public DiscountInformationUpdater(String name) {
        throw new UnsupportedOperationException();
    }

    @Override // TODO Implement submit for Updating a discount
    public DiscountInformation submit() throws DuplicateEntryException, IncompleteProductCategoryInformation {
        return super.submit();
    }
}
