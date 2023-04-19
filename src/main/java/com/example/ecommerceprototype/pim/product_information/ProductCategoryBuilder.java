package com.example.ecommerceprototype.pim.product_information;

import com.example.ecommerceprototype.pim.exceptions.DuplicateEntryException;
import com.example.ecommerceprototype.pim.exceptions.IncompleteProductCategoryInformation;

public class ProductCategoryBuilder extends ProductCategoryWorker {
    public ProductCategoryBuilder() {
        super();
    }

    @Override // TODO: Implement CategoryBuilder submit()
    public ProductCategory submit() throws DuplicateEntryException, IncompleteProductCategoryInformation {
        return super.submit();
    }
}
