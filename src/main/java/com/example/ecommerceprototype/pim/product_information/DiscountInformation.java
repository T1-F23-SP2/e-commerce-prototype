package com.example.ecommerceprototype.pim.product_information;

import com.example.ecommerceprototype.pim.sql_helpers.ValueExtractor;

import java.time.Duration;
import java.time.LocalDate;

public class DiscountInformation {
    private String name;
    private LocalDate startingDate;
    private LocalDate expiringDate;

    public DiscountInformation() {

    }

    public DiscountInformation(String name, LocalDate startingDate, LocalDate expiringDate) {
        this.name = name;
        this.startingDate = startingDate;
        this.expiringDate = expiringDate;
    }

    // Returns amount of hours that a given discount is valid, as a Long.
    public long calculateDiscountPeriod() {
        return Duration.between(startingDate, expiringDate).toHours();
    }

    public LocalDate getStartingDate() {
        return this.startingDate;
    }

    public LocalDate getExpiringDate() {
        return this.expiringDate;
    }

    public String getName() {
        return this.name;
    }

    protected DiscountInformation setName(String name) {
        this.name = name;
        return this;
    }

    protected DiscountInformation setStartingDate(LocalDate ld) {
        this.startingDate = ld;
        return this;
    }

    protected DiscountInformation setExpiringDate(LocalDate ld) {
        this.expiringDate = ld;
        return this;
    }

    // TODO: Implement fromDB
    protected DiscountInformation fromDB(ValueExtractor ve) {
        throw new UnsupportedOperationException();
    }
}
