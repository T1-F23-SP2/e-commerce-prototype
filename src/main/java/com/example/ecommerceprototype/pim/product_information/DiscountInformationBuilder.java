package com.example.ecommerceprototype.pim.product_information;

import com.example.ecommerceprototype.pim.exceptions.DuplicateEntryException;
import com.example.ecommerceprototype.pim.exceptions.IncompleteProductCategoryInformation;

public class DiscountInformationBuilder extends DiscountInformationWorker {
    private DiscountInformation discountInformation;
    public DiscountInformationBuilder() {
        super();
    }

    @Override // TODO Implement submit for Creating a discount
    public DiscountInformation submit() throws DuplicateEntryException, IncompleteProductCategoryInformation {
        return super.submit();
    }
}
