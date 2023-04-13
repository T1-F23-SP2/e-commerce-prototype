package com.example.ecommerceprototype.pim.product_information;

import com.example.ecommerceprototype.pim.sql_helpers.ValueExtractor;

import java.time.LocalDate;

public class DiscountInformation {
    private String name;
    private LocalDate startingDate;
    private LocalDate expiringDate;

    public DiscountInformation() {
        throw new UnsupportedOperationException();
    }

    public int calculateDiscountPeriod() {
        throw new UnsupportedOperationException();
    }

    public LocalDate getStartingDate() {
        throw new UnsupportedOperationException();
    }

    public LocalDate getExpiringDate() {
        throw new UnsupportedOperationException();
    }

    public String getName() {
        throw new UnsupportedOperationException();
    }

    protected void setName(String s) {
        throw new UnsupportedOperationException();
    }

    protected void setStartingDate(LocalDate ld) {
        throw new UnsupportedOperationException();
    }

    protected void setExpiringDate(LocalDate ld) {
        throw new UnsupportedOperationException();
    }

    protected DiscountInformation fromDB(ValueExtractor ve) {
        throw new UnsupportedOperationException();
    }
}
