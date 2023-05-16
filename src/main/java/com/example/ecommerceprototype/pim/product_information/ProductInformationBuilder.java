package com.example.ecommerceprototype.pim.product_information;

import com.example.ecommerceprototype.pim.exceptions.DuplicateEntryException;
import com.example.ecommerceprototype.pim.exceptions.IncompleteProductInformationException;
import com.example.ecommerceprototype.pim.exceptions.UUIDNotFoundException;

import java.sql.SQLException;

public class ProductInformationBuilder extends ProductInformationWorker {

    public ProductInformationBuilder() {
        super(new ProductInformation());
    }

    @Override
    public ProductInformation submit() throws UUIDNotFoundException, SQLException, IncompleteProductInformationException {
        return DBDriver.getInstance().insertNewProduct(super.getProductInformation());
    }
}