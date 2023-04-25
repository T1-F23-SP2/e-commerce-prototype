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

    public void setName(String name) {
        this.manufacturingInformation.name = name;
    }

    public void setSupportPhoneNumber(String supportPhoneNumber) {
        this.manufacturingInformation.supportPhoneNumber = supportPhoneNumber;
    }

    public void setSupportMail(String supportMail) {
        this.manufacturingInformation.supportMail = supportMail;
    }

    public ManufacturingInformation getManufacturingInformation() {
        return manufacturingInformation;
    }

    public abstract ManufacturingInformation submit() throws DuplicateEntryException, IncompleteProductCategoryInformation;
}
