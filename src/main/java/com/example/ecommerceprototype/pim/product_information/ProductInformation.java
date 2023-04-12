package com.example.ecommerceprototype.pim.product_information;

import com.example.ecommerceprototype.pim.sql_helpers.ValueExtractor;

public class ProductInformation {
    private String productUUID;
    private String name;
    private String serialNumber;
    private String shortDescription;
    private String longDescription;
    private boolean isHidden;
    private ProductSpecification productSpecification;
    private ManufacturingInformation manufacturingInformation;
    private PriceInformation priceInformation;

    public String getProductUUID() {
        throw new UnsupportedOperationException();
    }

    public String getName() {
        throw new UnsupportedOperationException();
    }

    public String getSerialNumber() {
        throw new UnsupportedOperationException();
    }

    public String getShortDescription() {
        throw new UnsupportedOperationException();
    }

    public String getLongDescription() {
        throw new UnsupportedOperationException();
    }

    public boolean getIsHidden() {
        throw new UnsupportedOperationException();
    }

    public ProductCategory getProductCategory() {
        throw new UnsupportedOperationException();
    }

    public ProductSpecification getProductSpecification() {
        throw new UnsupportedOperationException();
    }

    public ManufacturingInformation getManufacturingInformation() {
        throw new UnsupportedOperationException();
    }

    public PriceInformation getPriceInformation() {
        throw new UnsupportedOperationException();
    }

    protected void setUUID(String s) {
        throw new UnsupportedOperationException();
    }

    protected void setName(String s) {
        throw new UnsupportedOperationException();
    }

    protected void setSerialNumber(String s) {
        throw new UnsupportedOperationException();
    }

    protected void setShortDescription(String s) {
        throw new UnsupportedOperationException();
    }

    protected void setLongDescription(String s) {
        throw new UnsupportedOperationException();
    }

    protected void setIsHidden(boolean b) {
        throw new UnsupportedOperationException();
    }

    protected void setProductCategory(ProductCategory pc) {
        throw new UnsupportedOperationException();
    }

    protected void setProductSpecification(ProductSpecification ps) {
        throw new UnsupportedOperationException();
    }

    protected void setManufacturingInformation(ManufacturingInformation mi) {
        throw new UnsupportedOperationException();
    }

    protected void setPriceInformation(PriceInformation pi) {
        throw new UnsupportedOperationException();
    }

    protected ProductInformation fromDB(ValueExtractor ve) {
        throw new UnsupportedOperationException();
    }
}
