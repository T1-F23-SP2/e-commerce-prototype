package com.example.ecommerceprototype.pim.product_information;

import com.example.ecommerceprototype.pim.exceptions.DuplicateEntryException;
import com.example.ecommerceprototype.pim.exceptions.IncompleteProductCategoryInformation;

public class ManufacturingInformationBuilder extends ManufacturingInformationWorker {

    private ManufacturingInformation manufacturingInformation;

    protected ManufacturingInformationBuilder() {
        manufacturingInformation = new ManufacturingInformation();
    }

    @Override
    public ManufacturingInformation submit() throws DuplicateEntryException, IncompleteProductCategoryInformation {
        return super.submit();
    }

}
