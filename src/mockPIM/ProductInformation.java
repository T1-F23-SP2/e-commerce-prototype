import java.util.UUID;

public class ProductInformation {

    UUID productUUID;
    String name;
    String serialNumber;
    String shortDescription;
    String longDescription;
    boolean isHidden;
    ProductCategory productCategory;
    ProductSpecification productSpecification;
    ManufacturingInformation manufacturingInformation;
    PriceInformation priceInformation;

    public ProductInformation(UUID productUUID, String name, String serialNumber, String shortDescription, String longDescription, boolean isHidden, ProductCategory productCategory, ProductSpecification productSpecification, ManufacturingInformation manufacturingInformation, PriceInformation priceInformation) {
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


    // Getters
    public UUID getProductUUID() {
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

    public boolean isHidden() {
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


    // Setters


    public void setProductUUID(UUID productUUID) {
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

    public void setHidden(boolean hidden) {
        isHidden = hidden;
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

    public ProductInformation fromDB(ValueExtraxtor ve){
        // Get something from the fucking database
        return null;
    }

}
