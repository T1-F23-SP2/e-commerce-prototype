package com.example.ecommerceprototype.pim.util;

public interface Filter<E> {
    public static Filter<Nameable> byName(String matchString) {
        return (obj) -> obj.getName().equals(matchString);
    }
    public boolean filter(E element);
}