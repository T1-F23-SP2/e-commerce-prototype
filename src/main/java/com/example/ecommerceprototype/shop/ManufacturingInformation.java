package com.example.ecommerceprototype.shop;

public class ManufacturingInformation {
    private String name;
    private String supportPhoneNumber;
    private String supportMail;

    public String getName() {
        return name;
    }

    public String getSupportMail() {
        return supportMail;
    }

    public String getSupportPhoneNumber() {
        return supportPhoneNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSupportMail(String supportMail) {
        this.supportMail = supportMail;
    }

    public void setSupportPhoneNumber(String supportPhoneNumber) {
        this.supportPhoneNumber = supportPhoneNumber;
    }
}
