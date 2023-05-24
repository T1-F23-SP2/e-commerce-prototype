package com.example.ecommerceprototype.pim.util;

import java.util.regex.Pattern;

/*
    A class providing some useful static functions.
 */
public class Functions {
    public static boolean isUUIDv4(String string) {
        // Mark as case-insensitive, because UUID are case-insensitive
        // https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html#CASE_INSENSITIVE
        return string.matches("(?i)^[0-9ABCDEF]{8}-[0-9ABCDEF]{4}-4[0-9ABCDEF]{3}-[0-9ABCDEF]{4}-[0-9ABCDEF]{12}$");
    }
}
