package com.example.ecommerceprototype.pim.product_information;

import com.example.ecommerceprototype.pim.exceptions.DuplicateEntryException;
import com.example.ecommerceprototype.pim.exceptions.IncompleteProductInformationException;

public abstract class ProductInformationWorker {

    protected ProductInformation productInformation;

    protected ProductInformationWorker() {
        this.productInformation = new ProductInformation();
    }

    protected ProductInformationWorker(ProductInformation productInformation) {
        this.productInformation = productInformation;
    }

    public ProductInformationWorker setName(String name) {
        this.productInformation.setName(name);
        return this;
    }

    public ProductInformationWorker setSerialNumber(String serialNumber) {
        this.productInformation.setSerialNumber(serialNumber);
        return this;
    }

    public ProductInformationWorker setShortDescription(String shortDescription) {
        this.productInformation.setShortDescription(shortDescription);
        return this;
    }

    public ProductInformationWorker setLongDescription(String longDescription) {
        this.productInformation.setLongDescription(longDescription);
        return this;
    }

    public ProductInformationWorker setIsHidden(boolean isHidden) {
        this.productInformation.setIsHidden(isHidden);
        return this;
    }

    public ProductInformationWorker setProductCategory(ProductCategory pc) {
        this.productInformation.setProductCategory(pc);
        return this;
    }

    public ProductInformationWorker setProductSpecification(ProductSpecification ps) {
        this.productInformation.setProductSpecification(ps);
        return this;
    }

    public ProductInformationWorker setManufacturingInformation(ManufacturingInformation mi) {
        this.productInformation.setManufacturingInformation(mi);
        return this;
    }

    public ProductInformationWorker setPriceInformation(PriceInformation pi) {
        this.productInformation.setPriceInformation(pi);
        return this;
    }

    public ProductInformation getProductInformation() {
        return productInformation;
    }

    public abstract ProductInformation submit() throws DuplicateEntryException, IncompleteProductInformationException;
}