package com.example.ecommerceprototype.shop.mockShop;

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

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setDiscountInformation(DiscountInformation discountInformation) {
        this.discountInformation = discountInformation;
    }

    public PriceInformation(BigDecimal price, LocalDate priceCreation, DiscountInformation discountInformation) {
        this.discountInformation = discountInformation;
        this.price = price;
        this.priceCreation = priceCreation;

    }
}
