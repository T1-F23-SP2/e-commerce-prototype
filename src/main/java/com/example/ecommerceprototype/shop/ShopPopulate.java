package com.example.ecommerceprototype.shop;

import com.example.ecommerceprototype.dam.constants.Category;
import com.example.ecommerceprototype.dam.constants.Type;
import com.example.ecommerceprototype.dam.dam.DAMSystem;
import com.example.ecommerceprototype.dam.system.DamController;
import com.example.ecommerceprototype.pim.exceptions.*;
import com.example.ecommerceprototype.pim.product_information.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class ShopPopulate {
    ShopController controller;
    PIMDriver pim;
    DAMSystem dam;

    public ShopPopulate(ShopController controller) {
        this.controller = controller;
        this.pim = controller.getPIMDriverInstance();
        this.dam = controller.getDAMSystemInstance();
    }

    public void populate() throws SQLException, NotFoundException, DuplicateEntryException {

        populateCategories();
        populateManufacturers();
        populateProducts();
        populateImages();

    }

    public void populateProducts() throws SQLException, DuplicateEntryException, NotFoundException {

        for (ProductInformation product: pim.getAllProducts()) {
            pim.deleteProductByUUID(product.getProductUUID());
        }

        createProduct("Acer Aspire", "XC-1760 i7-12/16/256", "Computers", "Acer", "6499", "    Intel® Core™ i7-12700-processor\n" +
                "    16 GB DDR4 RAM\n" +
                "    256 GB SSD", "Denne Acer Aspire XC-1760 stationære computer har en 12. Gen Intel®-processor, der vil optimere din daglige computerbrug. Den klarer multitasking med absolut lethed og kommer i et elegant sort kabinet, der sikrer et stilrent udtryk.");
        createProduct("Phillips monitor", "242S1AE/00 23,8\" ", "Monitors", "Philips", "1519", "    23,8\" Full HD 1080p IPS panel\n" +
                "    75 Hz opdatering, 1000:1 kontrast\n" +
                "    250 nit lysstyrke, flimrefri", "Philips 242S1AE/00 23,8\" skærmen lader dig nyde skarp FHD 1080p billedkvalitet. EasyRead og Easy comfort teknologien reducerer belastningen af dine øjne, når du arbejder på skærmen.");
        createProduct("AMD Ryzen 7 5700G", "5700G", "CPUs", "AMD", "1579", "    8-core/16-tråde, 3,8-4,6 GHz\n" +
                "    AM4 kontakt, 20 MB total cache\n" +
                "    65 W TDP, inkl. blæser", "AMD Ryzen™ 7 5700G processoren er en kraftfuld byggesten til dit næste gaming setup. Den har 8 cores, 16 tråde og genererer en kraftfuld ydeevne og en endnu hurtigere turbo-tilstand. Den har integreret GPU for en samlet computerløsning.");
        createProduct("Corsair Dominator Platinum RGB DDR5-5200", "CMT32GX5M2B5200C40", "RAM", "Corsair", "1209", "Corsair Dominator CMT32GX5M2B5200C40, 32 GB, 2 x 16 GB, DDR5, 5200 Mhz, 288-pin DIMM, Sort", "Corsair Dominator CMT32GX5M2B5200C40. Komponent til: PC/server, Intern hukommelse: 32 GB, Hukommelseslayout (moduler x størrelse): 2 x 16 GB, Intern hukommelsestype: DDR5, Hukommelsesur hastighed: 5200 Mhz, Hukommelse form faktor: 288-pin DIMM, Maksimal blænderåbning: 40, Produktfarve: Sort");
        createProduct("Lenovo Yoga Slim 7 Pro", "Lenovo Yoga Slim 7 Pro", "Laptops", "Lenovo", "6999", "    Intel® Core™ i7-12700H-processor\n" +
                "    14\" QWQXGA+ IPS-skærm\n" +
                "    16 GB LPDDR5 RAM, 512 GB SSD", "Denne Lenovo Yoga Slim 7 Pro 14\" bærbar computer giver dig mulighed for at skabe kraftfulde kreationer, mens du beholder en ultratynd formfaktor. Den lynhurtige 12. Gen Intel®-processor klarer ubesværet krævende opgaver og giver altid en jævn ydeevne.");
    }

    public void populateImages() throws SQLException, NotFoundException {
        dam.addAsset("acer-aspire.jpg", Type.PRODUCT_IMAGE, extractCategory("Desktop"), ".jpg", pim.getProductByName("Acer Aspire").getProductUUID(), "src/main/resources/com/example/ecommerceprototype/shop/images/acer-aspire.jpg");
        dam.addAsset("phillips-skarm.jpg", Type.PRODUCT_IMAGE, extractCategory("Monitor"), ".jpg", pim.getProductByName("Phillips monitor").getProductUUID(), "src/main/resources/com/example/ecommerceprototype/shop/images/phillips-skarm.jpg");
        dam.addAsset("amd-ryzen-7.png", Type.PRODUCT_IMAGE, extractCategory("CPU"), ".png", pim.getProductByName("AMD Ryzen 7 5700G").getProductUUID(), "src/main/resources/com/example/ecommerceprototype/shop/images/amd-ryzen-7.png");
        dam.addAsset("corsair-dominator", Type.PRODUCT_IMAGE, extractCategory("RAM"), ".jpg", pim.getProductByName("Corsair Dominator Platinum RGB DDR5-5200").getProductUUID(), "src/main/resources/com/example/ecommerceprototype/shop/images/corsair-dominator.jpg");
        dam.addAsset("lenovo-yoga-slim-7-pro.jpg", Type.PRODUCT_IMAGE, extractCategory("Laptop"), ".avif", pim.getProductByName("Lenovo Yoga Slim 7 Pro").getProductUUID(), "src/main/resources/com/example/ecommerceprototype/shop/images/lenovo-yoga-slim-7-pro.jpg");
    }

    private Category extractCategory(String cat_in) {
        Category cat = Category.valueOf(cat_in.toUpperCase());
        return cat;
    }

    private String extractFileFormat(String name_in) {
        String formatString = name_in.substring(name_in.lastIndexOf(".") + 1);

        return formatString;
    }

    public void populateCategories() throws SQLException, NotFoundException, DuplicateEntryException {

        for (ProductCategory category : pim.getAllCategories()) {
            pim.deleteProductCategoryByName(category.getName());
        }

        String[] parentCategories = {"Computers", "PC components"};
        for (String category : parentCategories) {
            createCategory(category, "");
        }

        String[] parentCategoryComputers = {"Laptops", "Desktop PCs"};
        for (String category : parentCategoryComputers) {
            createCategory(category, "Computers");
        }

        String[] parentCategoryPCComponents = {"CPUs", "RAM"};
        for (String category : parentCategoryPCComponents) {
            createCategory(category, "PC components");
        }

        createCategory("Monitors", "");

    }

    public void populateManufacturers() throws SQLException, NotFoundException, DuplicateEntryException {

        for (ManufacturingInformation manufacturer : pim.getAllManufactures()) {
            pim.deleteManufactureByName(manufacturer.getName());
        }
        createManufacturer("Acer");
        createManufacturer("Lenovo");
        createManufacturer("ASUS");
        createManufacturer("AMD");
        createManufacturer("Phillips");

    }

    public void createProduct(String name, String serialNumber, String category, String manufacturer, String price, String shortDescription, String longDescription) throws SQLException, DuplicateEntryException {

        ProductCategoryBuilder categoryBuilder = (ProductCategoryBuilder) new ProductCategoryBuilder()
                .setName(category);


        ManufacturingInformationBuilder manufactureBuilder = (ManufacturingInformationBuilder) new ManufacturingInformationBuilder()
                .setName(manufacturer);


        PriceInformationBuilder priceInformationBuilder = new PriceInformationBuilder()
                .setPrice(new BigDecimal(price))
                .setWholeSalePrice(new BigDecimal(0));


        try {
            new ProductInformationBuilder()
                    .setName(name)
                    .setSerialNumber(serialNumber)
                    .setProductCategory(categoryBuilder.getProductCategory())
                    .setManufacturingInformation(manufactureBuilder.getManufacturingInformation())
                    .setPriceInformation(priceInformationBuilder.getPriceInformation())
                    .setShortDescription(shortDescription)
                    .setLongDescription(longDescription)
                    .setProductSpecification(new ProductSpecification())
                    .submit();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void createManufacturer(String name) throws SQLException, DuplicateEntryException {
        new ManufacturingInformationBuilder()
                .setName(name)
                .setSupportPhoneNumber("12 23 34 45")
                .setSupportMail(name.toLowerCase() + "@company.com")
                .submit();
    }

    public void createCategory(String name, String parent) throws SQLException, CategoryNotFoundException, DuplicateEntryException {
        new ProductCategoryBuilder()
                .setName(name)
                .setCategoryParent(parent)
                .submit();
    }

    public void clearAll() throws SQLException, NotFoundException, DuplicateEntryException {
        for (ProductCategory category : pim.getAllCategories()) {
            pim.deleteProductCategoryByName(category.getName());
        }

        for (ManufacturingInformation manufacturer : pim.getAllManufactures()) {
            pim.deleteManufactureByName(manufacturer.getName());
        }

        for (ProductCategory category : pim.getAllCategories()) {
            pim.deleteProductCategoryByName(category.getName());
        }
    }

}
