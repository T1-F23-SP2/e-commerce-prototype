package com.example.ecommerceprototype.shop;

public class ProductInformation {
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

    public ManufacturingInformation getManufcaturingInformation() {
        return manufacturingInformation;
    }

    public PriceInformation getPriceInformation() {
        return priceInformation;
    }

    public void setUUID(String productUUID) {
        this.productUUID = productUUID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public void setIsHidden(boolean isHidden) {
        this.isHidden = isHidden;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public void setProductSpecification(ProductSpecification productSpecification) {
        this.productSpecification = productSpecification;
    }

    public void setManufacturingInformation(ManufacturingInformation manufacturingInformation) {
        this.manufacturingInformation = manufacturingInformation;
    }

    public void setPriceInformation(PriceInformation priceInformation) {
        this.priceInformation = priceInformation;
    }

    // fromDB will be left unimplemeneted in our mock

    public ProductInformation(String productUUID, String name, String serialNumber, String shortDescription, String longDescription, boolean isHidden, ProductCategory productCategory, ProductSpecification productSpecification, ManufacturingInformation manufacturingInformation, PriceInformation priceInformation) {
        this.productUUID = productUUID;
        this.name = name;
        this.serialNumber = serialNumber;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.isHidden = isHidden;
        this.productCategory = productCategory;
        this.productSpecification = productSpecification;
        this.manufacturingInformation = manufacturingInformation;
        this.priceInformation = priceInformation;
    }

}
