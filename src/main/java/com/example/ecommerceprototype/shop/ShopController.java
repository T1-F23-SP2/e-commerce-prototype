package com.example.ecommerceprototype.shop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class ShopController {



    public ProductListViewHandler productListViewHandler = ProductListViewHandler.getInstance();

    public int currentProductPage = 0;

    public Basket basket = Basket.getInstance();


    /*
     * Java FX Vars
     */

    @FXML
    private Button buttonBasketFirst;

    @FXML
    private Button buttonBasketLast;

    @FXML
    private Button buttonBasketNext;

    @FXML
    private Button buttonBasketPrevious;

    @FXML
    private Button buttonProductsFirst;

    @FXML
    private Button buttonProductsLast;

    @FXML
    private Button buttonProductsNext;

    @FXML
    private Button buttonProductsPrevious;

    @FXML
    private TextField currentBasketPage;

    @FXML
    private TextField currentProductsPage;

    @FXML
    private Text labelBasketPageAmount;

    @FXML
    private Text labelProductsPageAmount;

    @FXML
    private TabPane mainTabPane;

    @FXML
    private Pane paginationBasket;

    @FXML
    private Pane paginationProducts;

    @FXML
    private Button prod1AddToCart;

    @FXML
    private ImageView prod1Image;

    @FXML
    private Label prod1Name;

    @FXML
    private Label prod1Price;

    @FXML
    private Label prod1Stock;

    @FXML
    private Button prod2AddToCart;

    @FXML
    private ImageView prod2Image;

    @FXML
    private Label prod2Name;

    @FXML
    private Label prod2Price;

    @FXML
    private Label prod2Stock;

    @FXML
    private Button prod3AddToCart;

    @FXML
    private ImageView prod3Image;

    @FXML
    private Label prod3Name;

    @FXML
    private Label prod3Price;

    @FXML
    private Label prod3Stock;

    @FXML
    private Button prod4AddToCart;

    @FXML
    private ImageView prod4Image;

    @FXML
    private Label prod4Name;

    @FXML
    private Label prod4Price;

    @FXML
    private Label prod4Stock;

    @FXML
    private Tab tabProducts;



    @FXML
    void tabProductsSelectionChanged(ActionEvent event) {

        if(tabProducts.isSelected()) {
            ArrayList<ProductInformation> currentPageProducts = productListViewHandler.getPage(currentProductPage);
            currentProductsPage.setText(String.valueOf(currentProductPage + 1)); // Array page list is 0-indexed.
            populateProducts(currentPageProducts);


        }

    }



    public void populateProducts(ArrayList<ProductInformation> products) {

        populateProduct1(products.get(0));
        populateProduct2(products.get(1));
        populateProduct3(products.get(2));
        populateProduct4(products.get(3));

    }

    public void populateProduct1(ProductInformation productInformation) {
        // TODO: DAM Integration
        // TODO: OMS Integration - We need info
        prod1Name.setText(productInformation.getName());
        prod1Price.setText(productInformation.getPriceInformation().getPrice() + "kr");
    }

    public void populateProduct2(ProductInformation productInformation) {
        // TODO: DAM Integration
        // TODO: OMS Integration - We need info
        prod2Name.setText(productInformation.getName());
        prod2Price.setText(productInformation.getPriceInformation().getPrice() + "kr");
    }

    public void populateProduct3(ProductInformation productInformation) {
        // TODO: DAM Integration
        // TODO: OMS Integration - We need info
        prod3Name.setText(productInformation.getName());
        prod3Price.setText(productInformation.getPriceInformation().getPrice() + "kr");
    }

    public void populateProduct4(ProductInformation productInformation) {
        // TODO: DAM Integration
        // TODO: OMS Integration - We need info
        prod3Name.setText(productInformation.getName());
        prod3Price.setText(productInformation.getPriceInformation().getPrice() + "kr");
    }


    @FXML
    public void initialize() {


        // Get Singleton Instances

        // Populate Mock Dataset of test meme products
        productListViewHandler.populateTestDataset();
        //populateProducts(productListViewHandler.getPage(0));


    }

}
