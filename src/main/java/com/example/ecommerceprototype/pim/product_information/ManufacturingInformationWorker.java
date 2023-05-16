package com.example.ecommerceprototype.pim.product_information;

import com.example.ecommerceprototype.pim.exceptions.DuplicateEntryException;
import com.example.ecommerceprototype.pim.exceptions.IncompleteManufacturingInformation;

import java.sql.SQLException;

public abstract class ManufacturingInformationWorker {

    private final ManufacturingInformation manufacturingInformation;

    protected ManufacturingInformationWorker(ManufacturingInformation mi) {
        this.manufacturingInformation = mi;
    }

    public ManufacturingInformationWorker setName(String name) {
        this.manufacturingInformation.setName(name);
        return this;
    }

    public ManufacturingInformationWorker setSupportPhoneNumber(String supportPhoneNumber) {
        this.manufacturingInformation.setSupportPhoneNumber(supportPhoneNumber);
        return this;
    }

    public ManufacturingInformationWorker setSupportMail(String supportMail) {
        this.manufacturingInformation.setSupportMail(supportMail);
        return this;
    }

    public ManufacturingInformation getManufacturingInformation() {
        return this.manufacturingInformation;
    }

    public abstract ManufacturingInformation submit() throws SQLException, DuplicateEntryException;
}