package com.example.ecommerceprototype.shop.components;

import com.example.ecommerceprototype.cms.CMS;
import com.example.ecommerceprototype.pim.product_information.ManufacturingInformation;
import com.example.ecommerceprototype.pim.product_information.PIMDriver;
import com.example.ecommerceprototype.pim.product_information.ProductCategory;
import com.example.ecommerceprototype.pim.product_information.ProductInformation;
import com.example.ecommerceprototype.pim.util.FilterableArrayList;
import com.example.ecommerceprototype.pim.util.ProductList;
import com.example.ecommerceprototype.shop.pages.ArticlePage;
import com.example.ecommerceprototype.shop.pages.ShopPage;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.math.BigDecimal;

public class Sidebar {

    static PIMDriver pimDriverInstance = new PIMDriver();

    public static void loadSidebar(Stage window, Pane plate) throws Exception {
        //Load sidebar onto template
        Pane sidebar = CMS.getInstance().loadComponent("CategorySidebar");
        CMS.getInstance().loadOnto(plate, sidebar, "sidebarPlaceholder_Pane");

        ((Button) CMS.getInstance().findNode(sidebar, "articles_Button")).setOnAction(actionEvent -> {
            try {
                ArticlePage.loadPage(window);}
            catch (Exception e) {System.out.println(e.getMessage());}
        });

        // Load categories
        VBox categoryList = (VBox) CMS.getInstance().findNode(sidebar, "categoryList_VBox");
        VBox allCategoryItem = (VBox) CMS.getInstance().loadComponent("CategoryItem");
        Button allCategoryButton = (Button) CMS.getInstance().findNode(allCategoryItem, "categoryItem_Button");
        ((Button) CMS.getInstance().findNode(allCategoryItem, "categoryItem_Button")).setOnAction(actionEvent -> {
            try {
                ShopPage.loadPage(window);}
            catch (Exception e) {System.out.println(e.getMessage());}
        });
        allCategoryButton.setText("All categories");
        categoryList.getChildren().add(allCategoryButton);


        FilterableArrayList<ProductCategory> allCategories = pimDriverInstance.getAllCategories();
        for (int i = 0; i < allCategories.size(); i++) {
            VBox categoryItem = (VBox) CMS.getInstance().loadComponent("CategoryItem");
            Button b = (Button) CMS.getInstance().findNode(categoryItem, "categoryItem_Button");
            int finalI = i;
            ((Button) CMS.getInstance().findNode(categoryItem, "categoryItem_Button")).setOnAction(actionEvent -> {
                try {
                    ShopPage.reloadProductView(window, pimDriverInstance.getProductsByCategoryName(allCategories.get(finalI).getName()));
                }
                catch (Exception e) {System.out.println(e.getMessage());}
            });
            b.setText("Type: " + allCategories.get(i).getName());
            categoryList.getChildren().add(b);
        }

        // filtering by manufacturer
        FilterableArrayList<ManufacturingInformation> allManufacturers = pimDriverInstance.getAllManufactures();
        for (int i = 0; i < allManufacturers.size(); i++) {
            VBox categoryItem = (VBox) CMS.getInstance().loadComponent("CategoryItem");
            Button b = (Button) CMS.getInstance().findNode(categoryItem, "categoryItem_Button");
            int finalI = i;
            ((Button) CMS.getInstance().findNode(categoryItem, "categoryItem_Button")).setOnAction(actionEvent -> {
                try {ShopPage.reloadProductView(window, pimDriverInstance.getProductsByManufactureName(allManufacturers.get(finalI).getName()));}
                catch (Exception e) {System.out.println(e.getMessage());}
            });
            b.setText("Manufacturer: " + allManufacturers.get(i).getName());
            categoryList.getChildren().add(b);
        }

        // filtering by price
        int[] priceRange = {0, 500, 1000, 2000, 5000, 10000};
        for (int i = 0; i < priceRange.length - 1; i++) {
            VBox categoryItem = (VBox) CMS.getInstance().loadComponent("CategoryItem");
            Button b = (Button) CMS.getInstance().findNode(categoryItem, "categoryItem_Button");
            ProductList productOfPrice = new ProductList();
            for (ProductInformation product : pimDriverInstance.getAllProducts()) {
                if (product.getPriceInformation().getPrice().compareTo(BigDecimal.valueOf(priceRange[i])) > 0 && product.getPriceInformation().getPrice().compareTo(BigDecimal.valueOf(priceRange[i+1])) < 0) {
                    productOfPrice.add(product);
                }
            }
            int finalI = i;
            ((Button) CMS.getInstance().findNode(categoryItem, "categoryItem_Button")).setOnAction(actionEvent -> {
                try {ShopPage.reloadProductView(window, productOfPrice);}
                catch (Exception e) {System.out.println(e.getMessage());}
            });
            b.setText("Price: " + priceRange[i] + " - " + priceRange[i+1]);
            categoryList.getChildren().add(b);
        }
    }
}
