package com.example.ecommerceprototype.pim.product_information;

import com.example.ecommerceprototype.pim.exceptions.DuplicateEntryException;
import com.example.ecommerceprototype.pim.exceptions.IncompleteManufacturingInformation;

public abstract class ManufacturingInformationWorker {

    protected ManufacturingInformation manufacturingInformation;

    protected ManufacturingInformationWorker() {
        this.manufacturingInformation = new ManufacturingInformation();
    }

    protected ManufacturingInformationWorker(ManufacturingInformation mi) {
        this.manufacturingInformation = mi;
    }

    public ManufacturingInformationWorker setName(String name) {
        this.manufacturingInformation.name = name;
        return this;
    }

    public ManufacturingInformationWorker setSupportPhoneNumber(String supportPhoneNumber) {
        this.manufacturingInformation.supportPhoneNumber = supportPhoneNumber;
        return this;
    }

    public ManufacturingInformationWorker setSupportMail(String supportMail) {
        this.manufacturingInformation.supportMail = supportMail;
        return this;
    }

    public ManufacturingInformation getManufacturingInformation() {
        return this.manufacturingInformation;
    }

    public abstract ManufacturingInformation submit() throws DuplicateEntryException, IncompleteManufacturingInformation;
}