package com.example.ecommerceprototype.pim.product_information;

import com.example.ecommerceprototype.pim.exceptions.DuplicateEntryException;
import com.example.ecommerceprototype.pim.exceptions.IncompleteProductCategoryInformation;

public abstract class ProductInformationWorker {

    ProductInformation productInformation;

    public ProductInformationWorker() {
        this.productInformation = new ProductInformation()
    }

    public void setProductCategory(ProductCategory pc) {
        this.productInformation.setProductCategory(pc);
    }

    public void setProductSpecification(ProductSpecification ps) {
        this.productInformation.setProductSpecification(ps);
    }

    public void setManufacturingInformation(ManufacturingInformation mi) {
        this.productInformation.setManufacturingInformation(mi);
    }

    public void setPriceInformation(PriceInformation pi) {
        this.productInformation.setPriceInformation(pi);
    }

    public void setName(String name) {
        this.productInformation.setName(name);
    }

    public void setShortDescription(String shortDescription) {
        this.productInformation.setShortDescription(shortDescription);
    }

    public void setLongDescription(String longDescription) {
        this.productInformation.setLongDescription(longDescription);
    }

    public void setIsHidden(boolean isHidden) {
        this.productInformation.setIsHidden(isHidden);
    }

    public ProductInformation getProductInformation() {
        return productInformation;
    }

    public abstract DiscountInformation submit() throws DuplicateEntryException, IncompleteProductCategoryInformation;
}
