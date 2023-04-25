package com.example.ecommerceprototype.pim.product_information;

import com.example.ecommerceprototype.pim.exceptions.DuplicateEntryException;
import com.example.ecommerceprototype.pim.exceptions.IncompleteProductCategoryInformation;

public abstract class ManufacturingInformationWorker {

    ManufacturingInformation manufacturingInformation;

    protected ManufacturingInformationWorker(ManufacturingInformation mi){
        this.manufacturingInformation = mi;
    }

    protected ManufacturingInformationWorker(){

    }

    public void setName(String s) {
        this.manufacturingInformation.name = s;
    }

    public void setSupportPhoneNumber(String s) {
        this.manufacturingInformation.supportPhoneNumber = s;
    }

    public void setSupportMail(String s) {
        this.manufacturingInformation.supportMail = s;
    }

    public ManufacturingInformation getManufacturingInformation() {
        return manufacturingInformation;
    }

    public ManufacturingInformation submit() throws DuplicateEntryException, IncompleteProductCategoryInformation {
        throw new UnsupportedOperationException();
    }
}
