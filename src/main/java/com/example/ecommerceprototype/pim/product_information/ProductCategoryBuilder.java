package com.example.ecommerceprototype.pim.product_information;

import com.example.ecommerceprototype.pim.exceptions.DuplicateEntryException;
import com.example.ecommerceprototype.pim.exceptions.IncompleteProductCategoryInformation;

import java.sql.SQLException;

public class ProductCategoryBuilder extends ProductCategoryWorker {

    public ProductCategoryBuilder() {
        super(new ProductCategory());
    }

    @Override
    public ProductCategory submit() throws SQLException {
        DBDriver.getInstance().insertNewProductCategory(this.getProductCategory());
        return this.getProductCategory();
    }
}