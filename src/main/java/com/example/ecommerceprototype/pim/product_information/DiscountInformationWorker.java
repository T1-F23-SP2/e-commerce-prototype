package com.example.ecommerceprototype.pim.product_information;

import com.example.ecommerceprototype.pim.exceptions.DuplicateEntryException;
import com.example.ecommerceprototype.pim.exceptions.IncompleteProductCategoryInformation;

import java.time.LocalDate;

public abstract class DiscountInformationWorker {
    DiscountInformation discountInformation;

    public DiscountInformationWorker() {
        this.discountInformation = new DiscountInformation();
    }

    public void setName(String s) {
        this.discountInformation.setName(s);
    }

    public void setStartingDate(LocalDate ld) {
        this.discountInformation.setStartingDate(ld);
    }

    public void setExpiringDate(LocalDate ld) {
        this.discountInformation.setExpiringDate(ld);
    }

    public DiscountInformation getDiscountInformation() {
        return this.discountInformation;
    }

    public DiscountInformation submit() throws DuplicateEntryException, IncompleteProductCategoryInformation {
        throw new UnsupportedOperationException();
    }
}
