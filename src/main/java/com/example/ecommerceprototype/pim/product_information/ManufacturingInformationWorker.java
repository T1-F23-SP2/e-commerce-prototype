package com.example.ecommerceprototype.pim.product_information;

import com.example.ecommerceprototype.pim.exceptions.DuplicateEntryException;
import com.example.ecommerceprototype.pim.exceptions.IncompleteProductCategoryInformation;

public abstract class ManufacturingInformationWorker {
    public void setName(String s) {
        throw new UnsupportedOperationException();
    }

    public void setSupportPhoneNumber(String s) {
        throw new UnsupportedOperationException();
    }

    public void setSupportMail(String s) {
        throw new UnsupportedOperationException();
    }

    public ManufacturingInformation getManufacturingInformation() {
        throw new UnsupportedOperationException();
    }

    public ManufacturingInformation submit() throws DuplicateEntryException, IncompleteProductCategoryInformation {
        throw new UnsupportedOperationException();
    }
}
