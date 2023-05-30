package com.example.ecommerceprototype.dam.constants;

public enum CategoryNonProduct
{
    LOGO ("Logo"),
    ARTICLE ("Article");



    private final String value;

    private CategoryNonProduct(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
