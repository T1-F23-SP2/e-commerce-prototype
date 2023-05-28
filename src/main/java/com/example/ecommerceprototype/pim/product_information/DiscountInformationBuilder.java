package com.example.ecommerceprototype.pim.product_information;

import com.example.ecommerceprototype.pim.exceptions.DuplicateEntryException;
import com.example.ecommerceprototype.pim.exceptions.IncompleteDiscountInformation;

import java.sql.SQLException;

public class DiscountInformationBuilder extends DiscountInformationWorker {

    public DiscountInformationBuilder() {
        super(new DiscountInformation());
    }

    @Override
    public DiscountInformation submit() throws SQLException, DuplicateEntryException {
        DBDriver.getInstance().insertNewDiscount(this.getDiscountInformation());
        return this.getDiscountInformation();
    }
}