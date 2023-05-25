package com.example.ecommerceprototype.shop.components;

import com.example.ecommerceprototype.cms.CMS;
import com.example.ecommerceprototype.cms.FXMLLoadFailedException;
import com.example.ecommerceprototype.pim.exceptions.NotFoundException;
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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class Sidebar {

    ShopController controller;
    CMS cms; 

    public Sidebar(ShopController controller) {
        this.controller = controller;
        this.cms = controller.getCMSInstance();
    }

    public void loadSidebar(Stage window, Pane plate) throws Exception {
        //Load sidebar onto template
        Pane sidebar = cms.loadComponent("CategorySidebar");
        cms.loadOnto(plate, sidebar, "sidebarPlaceholder_Pane");

        ((Button) cms.findNode(sidebar, "articles_Button")).setOnAction(actionEvent -> {
            try {
                controller.getArticlePage().loadPage(window);}
            catch (Exception e) {System.out.println(e.getMessage());}
        });

        // Load categories
        VBox categoryList = (VBox) cms.findNode(sidebar, "categoryList_VBox");

        filter(categoryList, "All categories", "", controller.getPIMDriverInstance().getAllProducts());

        FilterableArrayList<ProductCategory> allCategories = controller.getPIMDriverInstance().getAllCategories();
        for (int i = 0; i < allCategories.size(); i++) {
            filter(categoryList, "Type", allCategories.get(i).getName(), controller.getPIMDriverInstance().getProductsByCategoryName(allCategories.get(i).getName()));
        }

        // filtering by manufacturer
        FilterableArrayList<ManufacturingInformation> allManufacturers = controller.getPIMDriverInstance().getAllManufactures();
        for (int i = 0; i < allManufacturers.size(); i++) {
            filter(categoryList, "Manufacturer", allManufacturers.get(i).getName(), controller.getPIMDriverInstance().getProductsByManufactureName(allManufacturers.get(i).getName()));
        }

        // filtering by price
        List<Integer> priceRange = Arrays.asList(0, 100, 500, 1000, 2000, 5000, 10000);
        List<ProductList> ranges = new ArrayList<>();
        for (int i = 0; i < priceRange.size() - 1; i++) {
            ProductList productOfPrice = new ProductList();
            for (ProductInformation product : controller.getPIMDriverInstance().getAllProducts()) {
                if (product.getPriceInformation().getPrice().compareTo(BigDecimal.valueOf(priceRange.get(i))) > 0 && product.getPriceInformation().getPrice().compareTo(BigDecimal.valueOf(priceRange.get(i+1))) < 0) {
                    productOfPrice.add(product);
                }
            }
            ranges.add(productOfPrice);
        }
        for (int i = 0; i < priceRange.size() - 1; i++) {
            filter(categoryList, "Price", priceRange.get(i) + " - " + priceRange.get(i+1), ranges.get(i));
        }
    }

    public void filter(VBox categoryList, String filterName, String categoryName, ProductList products) throws Exception {
        VBox categoryItem = (VBox) cms.loadComponent("CategoryItem");
        Button b = (Button) cms.findNode(categoryItem, "categoryItem_Button");
        setCategoryButtonOnAction(categoryItem, actionEvent -> {
            try {
                reloadShopPageWithCategory(products);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
        b.setText(filterName + ": " + categoryName);
        categoryList.getChildren().add(b);
    }
    public <E> void createCategory(VBox categoryList, String filterName, String categoryName, List<E> category, ProductList products) throws Exception {
        for (int i = 0; i < category.size(); i++) {
            VBox categoryItem = (VBox) cms.loadComponent("CategoryItem");
            Button b = (Button) cms.findNode(categoryItem, "categoryItem_Button");
            int finalI = i;
            setCategoryButtonOnAction(categoryItem, actionEvent -> {
                try {
                    reloadShopPageWithCategory(products);
                } catch (Exception e) {
                    System.out.println(e.getMessage());;
            }});
            b.setText(filterName + ": " + categoryName);
            categoryList.getChildren().add(b);
        }

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
