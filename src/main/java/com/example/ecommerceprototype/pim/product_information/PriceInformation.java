package com.example.ecommerceprototype.pim.product_information;

import com.example.ecommerceprototype.pim.sql_helpers.ValueExtractor;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PriceInformation {
    private BigDecimal price;
    private LocalDate priceCreation;
    private DiscountInformation discountInformation;

    public BigDecimal getPrice() {
        return price;
    }

    public LocalDate getPriceCreation() {
        return priceCreation;
    }

    public DiscountInformation getDiscountInformation() {
        return discountInformation;
    }

    protected void setPrice(BigDecimal n) {
        this.price = n;
    }

    protected void setDiscountInformation(DiscountInformation di) {
        this.discountInformation = di;
    }

    protected PriceInformation fromDB(ValueExtractor ve) {
        throw new UnsupportedOperationException();
    }
}