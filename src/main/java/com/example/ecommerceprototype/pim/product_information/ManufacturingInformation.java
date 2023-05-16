package com.example.ecommerceprototype.pim.product_information;

import com.example.ecommerceprototype.pim.util.Nameable;


public class ManufacturingInformation implements Nameable {
    private String name;
    private String supportPhoneNumber;
    private String supportMail;

    // Should the empty constructor be removed?
    public ManufacturingInformation() {}

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
