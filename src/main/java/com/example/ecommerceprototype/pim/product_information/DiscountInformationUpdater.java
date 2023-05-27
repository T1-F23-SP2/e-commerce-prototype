package com.example.ecommerceprototype.pim.product_information;

import com.example.ecommerceprototype.pim.exceptions.DuplicateEntryException;
import com.example.ecommerceprototype.pim.exceptions.IncompleteProductCategoryInformation;
import com.example.ecommerceprototype.pim.exceptions.NotFoundException;

import java.sql.SQLException;

public class DiscountInformationUpdater extends DiscountInformationWorker {

    private String originalName;

    public DiscountInformationUpdater(DiscountInformation di) {
        super(di);
        this.setOriginalName(di.getName());
    }

    public String getOriginalName() {
        return this.originalName;
    }

    public DiscountInformationUpdater setOriginalName(String originalName) {
        this.originalName = originalName;
        return this;
    }

    @Override // TODO Implement submit for Updating a discount
    public DiscountInformation submit() throws SQLException, DuplicateEntryException {
        DBDriver.getInstance().updateDiscountByName(this.originalName, super.getDiscountInformation());
        return super.getDiscountInformation();
    }
}
