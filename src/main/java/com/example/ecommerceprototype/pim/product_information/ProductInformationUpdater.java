package com.example.ecommerceprototype.pim.product_information;

import com.example.ecommerceprototype.pim.exceptions.*;

import java.sql.SQLException;

public class ProductInformationUpdater extends ProductInformationWorker {

    private String originalName;

    public ProductInformationUpdater(ProductInformation pi) {
        super(pi);
        this.setOriginalName(pi.getName());
    }

    public String getOriginalName() {
        return this.originalName;
    }

    public ProductInformationUpdater setOriginalName(String originalName) {
        this.originalName = originalName;
        return this;
    }

    @Override
    public ProductInformation submit() throws UUIDNotFoundException, SQLException, ManufactureNotFoundException, CategoryNotFoundException, DuplicateEntryException {
        DBDriver.getInstance().updateProductByUUID(super.getProductInformation().getProductUUID(), this.originalName, super.getProductInformation());

        return super.getProductInformation();
    }
}