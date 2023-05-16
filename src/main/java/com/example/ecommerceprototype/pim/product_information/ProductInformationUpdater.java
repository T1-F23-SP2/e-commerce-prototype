package com.example.ecommerceprototype.pim.product_information;

import com.example.ecommerceprototype.pim.exceptions.*;

import java.sql.SQLException;

public class ProductInformationUpdater extends ProductInformationWorker {

    private String originalName;

    public ProductInformationUpdater(ProductInformation pi) {
        super(pi);
    }

    public String getOriginalName() {
        return this.originalName;
    }

    public ProductInformationUpdater setOriginalName(String originalName) {
        this.originalName = originalName;
        return this;
    }

    @Override
    public ProductInformation submit() throws SQLException, DuplicateEntryException, UUIDNotFoundException, ManufactureNotFoundException, CategoryNotFoundException {
        if (originalName == null) {
            originalName = this.getProductInformation().getName();
        }

        DBDriver.getInstance().updateProductByUUID(super.getProductInformation().getProductUUID(), this.originalName, super.getProductInformation());

        return super.getProductInformation();
    }
}