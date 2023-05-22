package com.example.ecommerceprototype.pim.product_information;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PriceInformation {
    private BigDecimal price;
    private LocalDate priceCreation;
    private DiscountInformation discountInformation;
    private BigDecimal wholeSalePrice;

    public BigDecimal getPrice() {
        return price;
    }

    public LocalDate getPriceCreation() {
        return priceCreation;
    }

    public DiscountInformation getDiscountInformation() {
        return discountInformation;
    }

    public BigDecimal getWholeSalePrice() {
        return this.wholeSalePrice;
    }

    protected PriceInformation setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    protected PriceInformation setWholeSalePrice(BigDecimal wholeSalePrice) {
        this.wholeSalePrice = wholeSalePrice;
        return this;
    }

    protected PriceInformation setDiscountInformation(DiscountInformation di) {
        this.discountInformation = di;
        return this;
    }
}