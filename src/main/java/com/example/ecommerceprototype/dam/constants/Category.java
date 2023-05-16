package com.example.ecommerceprototype.dam.constants;

public enum Category
{
    CPU ("CPU"),
    RAM ("RAM"),
    GPU ("GPU"),
    MOTHERBOARD ("Motherboard"),
    HDD ("Harddisk"),
    SSD ("SSD"),
    MONITOR ("Monitor"),
    LAPTOP ("Laptop"),
    DESKTOP ("Desktop"),
    CABLES ("Cables");

    private final String value;

    private Category(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
