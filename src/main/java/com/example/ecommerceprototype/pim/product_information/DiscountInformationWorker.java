package com.example.ecommerceprototype.pim.product_information;

import com.example.ecommerceprototype.pim.exceptions.DuplicateEntryException;
import com.example.ecommerceprototype.pim.exceptions.IncompleteProductCategoryInformation;

import java.time.LocalDate;

public abstract class DiscountInformationWorker {
    public void setName(String s) {
        throw new UnsupportedOperationException();
    }

    public void setStartingDate(LocalDate ld) {
        throw new UnsupportedOperationException();
    }

    public void setExpiringDate(LocalDate ld) {
        throw new UnsupportedOperationException();
    }

    public DiscountInformation getDiscountInformation() {
        throw new UnsupportedOperationException();
    }

    public DiscountInformation submit() throws DuplicateEntryException, IncompleteProductCategoryInformation {
        throw new UnsupportedOperationException();
    }
}
