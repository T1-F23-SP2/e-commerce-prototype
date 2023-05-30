package com.example.ecommerceprototype.shop.components;

import com.example.ecommerceprototype.cms.CMS;
import com.example.ecommerceprototype.pim.product_information.ManufacturingInformation;
import com.example.ecommerceprototype.pim.product_information.PIMDriver;
import com.example.ecommerceprototype.pim.product_information.ProductCategory;
import com.example.ecommerceprototype.pim.product_information.ProductInformation;
import com.example.ecommerceprototype.pim.util.FilterableArrayList;
import com.example.ecommerceprototype.pim.util.ProductList;
import com.example.ecommerceprototype.shop.ShopController;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sidebar {

    ShopController controller;
    CMS cms;
    PIMDriver pim;

    public Sidebar(ShopController controller) {
        this.controller = controller;
        this.cms = controller.getCMSInstance();
        this.pim = controller.getPIMDriverInstance();
    }

    public void loadSidebar(Stage window, Pane plate) throws Exception {

        Pane sidebar = cms.loadComponent("CategorySidebar");
        cms.loadOnto(plate, sidebar, "sidebarPlaceholder_Pane");

        ((Button) cms.findNode(sidebar, "articles_Button")).setOnAction(actionEvent -> {
            try {
                controller.getArticlePage().loadPage(window);
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });


        VBox categoryList = (VBox) cms.findNode(sidebar, "categoryList_VBox");

        ProductList allProducts = pim.getAllProducts();
        createFilter(categoryList, "All categories", "", allProducts);

        FilterableArrayList<ProductCategory> allCategories = pim.getAllCategories();
        for (ProductCategory allCategory : allCategories) {
            createFilter(categoryList, "Type: ", allCategory.getName(), pim.getProductsByCategoryName(allCategory.getName()));
        }


        FilterableArrayList<ManufacturingInformation> allManufacturers = pim.getAllManufactures();
        for (ManufacturingInformation allManufacturer : allManufacturers) {
            createFilter(categoryList, "Manufacturer: ", allManufacturer.getName(), pim.getProductsByManufactureName(allManufacturer.getName()));
        }

        List<Integer> priceRange = Arrays.asList(0, 100, 500, 1000, 2000, 5000, 10000);
        List<ProductList> ranges = new ArrayList<>();
        for (int i = 0; i < priceRange.size() - 1; i++) {
            ProductList productOfPrice = new ProductList();
            for (ProductInformation product : pim.getAllProducts()) {
                if (product.getPriceInformation().getPrice().compareTo(BigDecimal.valueOf(priceRange.get(i))) > 0 && product.getPriceInformation().getPrice().compareTo(BigDecimal.valueOf(priceRange.get(i+1))) < 0) {
                    productOfPrice.add(product);
                }
            }
            ranges.add(productOfPrice);
        }
        for (int i = 0; i < priceRange.size() - 1; i++) {
            createFilter(categoryList, "Price: ", priceRange.get(i) + " - " + priceRange.get(i+1), ranges.get(i));
        }
    }

    public void createFilter(VBox categoryList, String filterName, String categoryName, ProductList products) throws Exception {
        VBox categoryItem = (VBox) cms.loadComponent("CategoryItem");
        Button b = (Button) cms.findNode(categoryItem, "categoryItem_Button");
        setCategoryButtonOnAction(categoryItem, actionEvent -> {
            try {
                reloadShopPageWithCategory(products);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
        b.setText(filterName + categoryName);
        categoryList.getChildren().add(b);
    }

    public void setCategoryButtonOnAction(VBox categoryItem, EventHandler function) {
        ((Button) cms.findNode(categoryItem, "categoryItem_Button")).setOnAction(function);
    }

    public void reloadShopPageWithCategory(ProductList products) {
        try {
            controller.getShopPage().reloadProductView(controller.getWindow(), products);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
