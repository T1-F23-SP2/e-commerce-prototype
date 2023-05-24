package com.example.ecommerceprototype.pim.product_information;

import com.example.ecommerceprototype.pim.util.Nameable;

public class ProductInformation implements Nameable {
    private String productUUID;
    private String name;
    private String serialNumber;
    private String shortDescription;
    private String longDescription;
    private boolean isHidden;
    private ProductCategory productCategory;
    private ProductSpecification productSpecification;
    private ManufacturingInformation manufacturingInformation;
    private PriceInformation priceInformation;

    public String getProductUUID() {
        return productUUID;
    }

    public String getName() {
        return name;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public boolean getIsHidden() {
        return isHidden;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public ProductSpecification getProductSpecification() {
        return productSpecification;
    }

    public ManufacturingInformation getManufacturingInformation() {
        return manufacturingInformation;
    }

    public PriceInformation getPriceInformation() {
        return priceInformation;
    }

    protected ProductInformation setUUID(String productUUID) {
        this.productUUID = productUUID;
        return this;
    }

    protected ProductInformation setName(String name) {
        this.name = name;
        return this;
    }

    protected ProductInformation setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    protected ProductInformation setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
        return this;
    }

    protected ProductInformation setLongDescription(String longDescription) {
        this.longDescription = longDescription;
        return this;
    }

    protected ProductInformation setIsHidden(boolean isHidden) {
        this.isHidden = isHidden;
        return this;
    }

    protected ProductInformation setProductCategory(ProductCategory pc) {
        this.productCategory = pc;
        return this;
    }

    protected ProductInformation setProductSpecification(ProductSpecification ps) {
        this.productSpecification = ps;
        return this;
    }

    protected ProductInformation setManufacturingInformation(ManufacturingInformation mi) {
        this.manufacturingInformation = mi;
        return this;
    }

    protected ProductInformation setPriceInformation(PriceInformation pi) {
        this.priceInformation = pi;
        return this;
    }
}
