package com.example.ecommerceprototype.pim.util;

/*
    A class providing some useful static functions.
 */
public class Functions {
    public static boolean isUUIDv4(String string) {
        return string.toUpperCase().matches("^[0-9ABCDEF]{8}-[0-9ABCDEF]{4}-4[0-9ABCDEF]{3}-[0-9ABCDEF]{4}-[0-9ABCDEF]{12}$");
    }
}