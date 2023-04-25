package com.example.ecommerceprototype.pim.product_information;

import com.example.ecommerceprototype.pim.exceptions.DuplicateEntryException;
import com.example.ecommerceprototype.pim.exceptions.IncompleteProductCategoryInformation;

import java.time.LocalDate;

public abstract class DiscountInformationWorker {
    DiscountInformation discountInformation;

    public DiscountInformationWorker() {
        this.discountInformation = new DiscountInformation();
    }

    public DiscountInformationWorker setName(String name) {
        this.discountInformation.setName(name);
        return this;
    }

    public DiscountInformationWorker setStartingDate(LocalDate ld) {
        this.discountInformation.setStartingDate(ld);
        return this;
    }

    public DiscountInformationWorker setExpiringDate(LocalDate ld) {
        this.discountInformation.setExpiringDate(ld);
        return this;
    }

    public DiscountInformation getDiscountInformation() {
        return this.discountInformation;
    }

    public abstract DiscountInformation submit() throws DuplicateEntryException, IncompleteProductCategoryInformation;
}
