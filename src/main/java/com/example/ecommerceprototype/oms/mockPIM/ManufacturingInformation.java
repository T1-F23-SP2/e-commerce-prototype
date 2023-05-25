package com.example.ecommerceprototype.oms.mockPIM;

public class ManufacturingInformation {


    String name;
    String supportPhoneNumber;
    String supportMail;

    public ManufacturingInformation(String name, String supportPhoneNumber, String supportMail) {
        this.name = name;
        this.supportPhoneNumber = supportPhoneNumber;
        this.supportMail = supportMail;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSupportPhoneNumber() {
        return supportPhoneNumber;
    }

    public void setSupportPhoneNumber(String supportPhoneNumber) {
        this.supportPhoneNumber = supportPhoneNumber;
    }

    public String getSupportMail() {
        return supportMail;
    }

    public void setSupportMail(String supportMail) {
        this.supportMail = supportMail;
    }





}
