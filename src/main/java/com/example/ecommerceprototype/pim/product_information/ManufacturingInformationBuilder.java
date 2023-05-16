package com.example.ecommerceprototype.pim.product_information;

import com.example.ecommerceprototype.pim.exceptions.DuplicateEntryException;
import com.example.ecommerceprototype.pim.exceptions.IncompleteManufacturingInformation;

import java.sql.SQLException;

public class ManufacturingInformationBuilder extends ManufacturingInformationWorker {

    public ManufacturingInformationBuilder() {
        super(new ManufacturingInformation());
    }

    @Override
    public ManufacturingInformation submit() throws DuplicateEntryException, IncompleteManufacturingInformation, SQLException {
        DBDriver.getInstance().insertNewManufacture(this.getManufacturingInformation());
        return this.getManufacturingInformation();
    }
}