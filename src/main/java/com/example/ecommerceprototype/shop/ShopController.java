package com.example.ecommerceprototype.shop;

import javafx.event.ActionEvent;
import javafx.event.Event;
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
    private Pane prod1Pane;

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
    private Pane prod2Pane;

    @FXML
    private Label prod2Price;

    @FXML
    private Label prod2Stock;

    @FXML
    private Button prod3AddToCart;

    @FXML
    private Button searchGoButton;

    @FXML
    private TextField searchText;


    @FXML
    private ImageView prod3Image;

    @FXML
    private Label prod3Name;

    @FXML
    private Pane prod3Pane;

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
    private Pane prod4Pane;

    @FXML
    private Label prod4Price;

    @FXML
    private Label prod4Stock;

    @FXML
    private Tab tabProducts;


    void productInit() {
        ArrayList<ProductInformation> currentPageProducts = productListViewHandler.getPage(productListViewHandler.getCurrentPage());
        currentProductsPage.setText(String.valueOf(productListViewHandler.getCurrentPage() + 1)); // Array page list is 0-indexed.
        labelProductsPageAmount.setText("of " + productListViewHandler.getPageCount());

        // This usually happens if there's no search results. That's a "stuff's broken - do not display anything" scenario.
        if (productListViewHandler.getPage(productListViewHandler.getCurrentPage()).size() == 0) {
            buttonProductsNext.setDisable(true);
            buttonProductsPrevious.setDisable(true);
            buttonProductsLast.setDisable(true);
            buttonProductsFirst.setDisable(true);
            prod1Pane.setVisible(false);
            prod2Pane.setVisible(false);
            prod3Pane.setVisible(false);
            prod4Pane.setVisible(false);
            return;
        }
        populateProducts(currentPageProducts);


        System.out.println(productListViewHandler.getCurrentPage());

        if (productListViewHandler.getCurrentPage() == 0) {
            buttonProductsFirst.setDisable(true);
            buttonProductsPrevious.setDisable(true);
            buttonProductsNext.setDisable(false);
            buttonProductsLast.setDisable(false);

        }
        if (productListViewHandler.getCurrentPage() == productListViewHandler.getPageCount() -1) {
            buttonProductsNext.setDisable(true);
            buttonProductsLast.setDisable(true);
            buttonProductsFirst.setDisable(false);
            buttonProductsPrevious.setDisable(false);

        }

    }

    @FXML
    void tabProductsSelectionChanged(Event event) {

        if(tabProducts.isSelected())
            productInit();

    }


    @FXML
    void product1AddToBasket(ActionEvent event) {
        ProductInformation info = productListViewHandler.getPage(productListViewHandler.getCurrentPage()).get(0);
        BasketEntry basketEntry = new BasketEntry(info.getProductUUID(), 1);
        basket.addProduct(basketEntry);
        System.out.println(basket.getProducts().size());
    }

    @FXML
    void product2AddToBasket(ActionEvent event) {
        ProductInformation info = productListViewHandler.getPage(productListViewHandler.getCurrentPage()).get(1);
        BasketEntry basketEntry = new BasketEntry(info.getProductUUID(), 1);
        basket.addProduct(basketEntry);
        System.out.println(basket.getProducts().size());

    }

    @FXML
    void product3AddToBasket(ActionEvent event) {

        ProductInformation info = productListViewHandler.getPage(productListViewHandler.getCurrentPage()).get(2);
        BasketEntry basketEntry = new BasketEntry(info.getProductUUID(), 1);
        basket.addProduct(basketEntry);
        System.out.println(basket.getProducts().size());

    }

    @FXML
    void product4AddToBasket(ActionEvent event) {
        ProductInformation info = productListViewHandler.getPage(productListViewHandler.getCurrentPage()).get(3);
        BasketEntry basketEntry = new BasketEntry(info.getProductUUID(), 1);
        basket.addProduct(basketEntry);
        System.out.println(basket.getProducts().size());

    }

    @FXML
    void productsFirstPage(ActionEvent event) {
        productListViewHandler.getPage(0, true);
        productInit();
    }

    @FXML
    void productsLastPage(ActionEvent event) {
        productListViewHandler.getPage(productListViewHandler.getPageCount() - 1, true);
        productInit();
    }

    @FXML
    void productsNextPage(ActionEvent event) {

        productListViewHandler.getNextPage();
        productInit();

    }

    @FXML
    void productsPreviousPage(ActionEvent event) {

        productListViewHandler.getPreviousPage();
        productInit();

    }


    public void populateProducts(ArrayList<ProductInformation> products) {
        try {
            populateProduct1(products.get(0));
            prod1Pane.setVisible(true);
            populateProduct2(products.get(1));
            prod2Pane.setVisible(true);
            populateProduct3(products.get(2));
            prod3Pane.setVisible(true);
            populateProduct4(products.get(3));
            prod4Pane.setVisible(true);
        } catch (IndexOutOfBoundsException e) {
            String[] wrongedMessage = e.getMessage().split(" ");
            int lastGoodIndex = Integer.parseInt(wrongedMessage[1]) -1;
            switch (lastGoodIndex) {
                case -1:
                    productListViewHandler.getPreviousPage();
                    currentProductPage--;
                    productInit(); // Return to last page, as there's no good indices.
                    break;
                case 0:
                    prod2Pane.setVisible(false);
                    prod3Pane.setVisible(false);
                    prod4Pane.setVisible(false);
                    break;
                case 1:
                    prod3Pane.setVisible(false);
                    prod4Pane.setVisible(false);
                    break;
                case 2:
                    prod4Pane.setVisible(false);
                    break;
            }
        }

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
        prod4Name.setText(productInformation.getName());
        prod4Price.setText(productInformation.getPriceInformation().getPrice() + "kr");
    }


    @FXML
    void searchGo(ActionEvent event) {
        ArrayList<ProductInformation> results = productListViewHandler.search(searchText.getText());

        productListViewHandler.setDisplayedProducts(results);
        productListViewHandler.getPage(0, true);
        productInit();
    }

    @FXML
    public void initialize() {


        // Get Singleton Instances

        // Populate Mock Dataset of test meme products
        productListViewHandler.populateTestDataset();
        //populateProducts(productListViewHandler.getPage(0));



    }

}
