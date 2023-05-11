package com.example.ecommerceprototype.pim.product_information;

import com.example.ecommerceprototype.pim.exceptions.DuplicateEntryException;
import com.example.ecommerceprototype.pim.exceptions.IncompleteManufacturingInformation;

public class ManufacturingInformationBuilder extends ManufacturingInformationWorker {

    protected ManufacturingInformationBuilder() {
        super(new ManufacturingInformation());
    }

    @Override
    public ManufacturingInformation submit() throws DuplicateEntryException, IncompleteManufacturingInformation {
        throw new UnsupportedOperationException();
    }
}