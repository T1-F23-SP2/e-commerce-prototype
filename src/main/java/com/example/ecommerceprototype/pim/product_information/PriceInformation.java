package com.example.ecommerceprototype.pim.product_information;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PriceInformation {
    private BigDecimal price;
    private LocalDate priceCreation;
    private DiscountInformation discountInformation;

    public BigDecimal getPrice() {
        throw new UnsupportedOperationException();
    }

    public LocalDate getPriceCreation() {
        throw new UnsupportedOperationException();
    }

    public DiscountInformation getDiscountInformation() {
        throw new UnsupportedOperationException();
    }

    protected void setPrice(BigDecimal n) {
        throw new UnsupportedOperationException();
    }

    protected void setDiscountInformation(DiscountInformation di) {
        throw new UnsupportedOperationException();
    }

    protected PriceInformation fromDB(ValueExtractor ve) {
        throw new UnsupportedOperationException();
    }
}
