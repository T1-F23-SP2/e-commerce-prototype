package com.example.ecommerceprototype.pim.product_information;


public class ManufacturingInformation {
    String name;
    String supportPhoneNumber;
    String supportMail;

    protected ManufacturingInformation(String name, String supportPhoneNumber, String supportMail) {
        this.name = name;
        this.supportPhoneNumber = supportPhoneNumber;
        this.supportMail = supportMail;
    }
    protected ManufacturingInformation() {

    }

    public String getName() {
        return name;
    }

    public String getSupportPhoneNumber() {
        return supportPhoneNumber;
    }

    public String getSupportMail() {
        return supportMail;
    }

    protected ManufacturingInformation setName(String name) {
        this.name = name;
        return this;
    }

    protected ManufacturingInformation setSupportPhoneNumber(String supportPhoneNumber) {
        this.supportPhoneNumber = supportPhoneNumber;
        return this;
    }

    protected ManufacturingInformation setSupportMail(String supportMail) {
        this.supportMail = supportMail;
        return this;
    }
}
