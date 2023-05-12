package com.example.ecommerceprototype.pim.product_information;

import com.example.ecommerceprototype.pim.exceptions.DuplicateEntryException;
import com.example.ecommerceprototype.pim.exceptions.IncompleteProductInformationException;

import java.math.BigDecimal;

public class PriceInformationBuilder {

    // This value is used when inserting a new price for a product.
    // It references the product which it sets the price for.
    private String productUUID;

    PriceInformation priceInformation;

    public PriceInformationBuilder() {
        this.priceInformation = new PriceInformation();
    }

    public PriceInformationBuilder setPrice(BigDecimal price) {
        this.priceInformation.setPrice(price);
        return this;
    }

    public PriceInformationBuilder setWholeSalePrice(BigDecimal wholeSalePrice) {
        this.priceInformation.setWholeSalePrice(wholeSalePrice);
        return this;
    }

    public PriceInformationBuilder setDiscountInformation(DiscountInformation di) {
        this.priceInformation.setDiscountInformation(di);
        return this;
    }

    public PriceInformationBuilder setProductUUID(String productUUID) {
        this.productUUID = productUUID;
        return this;
    }

    public PriceInformation submit() throws IncompleteProductInformationException {


        try {
            if (priceInformation.getDiscountInformation() == null) {
                DBDriver.getInstance().insertNewPriceChange(this.productUUID, priceInformation.getPrice(), priceInformation.getWholeSalePrice());
            } else {
                DBDriver.getInstance().insertNewPriceChange(this.productUUID, priceInformation.getPrice(), priceInformation.getWholeSalePrice(), priceInformation.getDiscountInformation());
            }
        } catch (IncompleteProductInformationException e) {
            throw new RuntimeException(e);
        }

        return this.priceInformation;
    }
}