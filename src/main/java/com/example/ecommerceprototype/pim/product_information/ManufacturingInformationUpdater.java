package com.example.ecommerceprototype.pim.product_information;

import com.example.ecommerceprototype.pim.exceptions.DuplicateEntryException;
import com.example.ecommerceprototype.pim.exceptions.IncompleteManufacturingInformation;
import com.example.ecommerceprototype.pim.exceptions.NotFoundException;

import java.sql.SQLException;

public class ManufacturingInformationUpdater extends ManufacturingInformationWorker {

    // This value is used when updating a manufactures' information.
    // We need this attribute because we won't know what the original manufactures' name was without it.
    protected String originalName;

    public ManufacturingInformationUpdater(ManufacturingInformation mi) {
        super(mi);
    }

    public String getOriginalName() {
        return this.originalName;
    }

    public ManufacturingInformationUpdater setOriginalName(String originalName) {
        this.originalName = originalName;
        return this;
    }

    @Override
    public ManufacturingInformation submit() throws DuplicateEntryException, SQLException {
        if (originalName == null) {
            originalName = this.getManufacturingInformation().getName();
        }

        DBDriver.getInstance().updateManufactureByName(this.originalName, super.getManufacturingInformation());
        return super.getManufacturingInformation();
    }
}