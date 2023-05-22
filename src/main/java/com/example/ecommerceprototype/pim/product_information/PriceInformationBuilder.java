package com.example.ecommerceprototype.pim.product_information;

import com.example.ecommerceprototype.pim.exceptions.DuplicateEntryException;
import com.example.ecommerceprototype.pim.exceptions.IncompleteProductInformationException;
import com.example.ecommerceprototype.pim.exceptions.NotFoundException;
import com.example.ecommerceprototype.pim.exceptions.UUIDNotFoundException;

import java.math.BigDecimal;
import java.sql.SQLException;

public class PriceInformationBuilder {

    // This value is used when inserting a new price for a product.
    // It references the product which it sets the price for.
    private String productUUID;

    private final PriceInformation priceInformation;

    public PriceInformationBuilder() {
        this.priceInformation = new PriceInformation();
    }

    public PriceInformation getPriceInformation() {
        return priceInformation;
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
        } catch (UUIDNotFoundException e) {
            throw new RuntimeException(e);
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return this.priceInformation;
    }
}