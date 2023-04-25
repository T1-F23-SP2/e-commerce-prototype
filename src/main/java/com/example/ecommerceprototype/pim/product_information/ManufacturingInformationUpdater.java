package com.example.ecommerceprototype.pim.product_information;

import com.example.ecommerceprototype.pim.exceptions.DuplicateEntryException;
import com.example.ecommerceprototype.pim.exceptions.IncompleteProductCategoryInformation;

public class ManufacturingInformationUpdater extends ManufacturingInformationWorker {
    ManufacturingInformation manufacturingInformation;

    protected ManufacturingInformationUpdater(ManufacturingInformation mi) {
        this.manufacturingInformation = mi;
    }

    protected ManufacturingInformationUpdater(String name) {
        getManufactureByName() = name;
    }

    @Override
    public ManufacturingInformation submit() throws DuplicateEntryException, IncompleteProductCategoryInformation {
        throw new UnsupportedOperationException();
    }
}
