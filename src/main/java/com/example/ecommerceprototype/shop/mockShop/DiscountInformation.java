package com.example.ecommerceprototype.shop.mockShop;

import java.time.LocalDate;

public class DiscountInformation {
    private String name;
    private LocalDate startingDate;
    private LocalDate expiringDate;

    public int calculateDiscountPeriod() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public LocalDate getStartingDate() {
        return startingDate;
    }

    public LocalDate getExpiringDate() {
        return expiringDate;
    }

    public String getName() {
        return name;
    }

    public void setExpiringDate(LocalDate expiringDate) {
        this.expiringDate = expiringDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStartingDate(LocalDate startingDate) {
        this.startingDate = startingDate;
    }
}
