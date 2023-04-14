package com.example.ecommerceprototype.pim.sql_helpers;

public interface ObjectReconstructor {
    public default E getObject(){
        throw new UnsupportedOperationException();
    }
}
