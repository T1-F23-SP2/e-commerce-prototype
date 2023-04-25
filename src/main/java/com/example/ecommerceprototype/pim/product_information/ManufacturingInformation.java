package com.example.ecommerceprototype.pim.product_information;

import com.example.ecommerceprototype.pim.sql_helpers.ValueExtractor;


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

    protected void setName(String s) {
        this.name = s;
    }

    protected void setSupportPhoneNumber(String s) {
        this.supportPhoneNumber = s;
    }

    protected void setSupportMail(String s) {
        this.supportMail = s;
    }

    protected ManufacturingInformation fromDB(ValueExtractor ve) {
        throw new UnsupportedOperationException();
    }
}
