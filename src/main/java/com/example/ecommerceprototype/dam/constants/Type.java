package com.example.ecommerceprototype.dam.constants;

public enum Type {

    PRODUCT_IMAGE ("product_image"),
    PRODUCT_FILE ("product_file"),
    IMAGE ("image"),
    FILE ("file");

    private final String value;

    private Type (String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
