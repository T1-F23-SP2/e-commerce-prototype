package com.example.ecommerceprototype.shop.mockShop;

import com.example.ecommerceprototype.shop.mockShop.Basket;
import com.example.ecommerceprototype.shop.mockShop.BasketEntry;
import com.example.ecommerceprototype.shop.mockShop.ProductInformation;
import com.example.ecommerceprototype.shop.mockShop.ProductListViewHandler;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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
    private Button basket1Remove;

    @FXML
    private Text basket1ShortDesc;

    @FXML
    private Text basket1WarehouseStatus;

    @FXML
    private Button basket2Remove;

    @FXML
    private Text basket2ShortDesc;

    @FXML
    private Text basket2WarehouseStatus;

    @FXML
    private Button basket3Remove;

    @FXML
    private Text basket3ShortDesc;

    @FXML
    private Text basket3WarehouseStatus;

    @FXML
    private Button basket4Remove;

    @FXML
    private Text basket4ShortDesc;

    @FXML
    private Text basket4WarehouseStatus;

    @FXML
    private TextField basketEntry1Amount;

    @FXML
    private Button basketEntry1Decrease;

    @FXML
    private Button basketEntry1Increase;

    @FXML
    private Text basketEntry1Name;

    @FXML
    private TextField basketEntry2Amount;

    @FXML
    private Button basketEntry2Decrease;

    @FXML
    private Button basketEntry2Increase;

    @FXML
    private Text basketEntry2Name;

    @FXML
    private TextField basketEntry3Amount;

    @FXML
    private Button basketEntry3Decrease;

    @FXML
    private Button basketEntry3Increase;

    @FXML
    private Text basketEntry3Name;

    @FXML
    private TextField basketEntry4Amount;

    @FXML
    private Button basketEntry4Decrease;

    @FXML
    private Button basketEntry4Increase;

    @FXML
    private Text basketEntry4Name;

    @FXML
    private Pane basketPaneEntry1;

    @FXML
    private Pane basketPaneEntry2;

    @FXML
    private Pane basketPaneEntry3;

    @FXML
    private Pane basketPaneEntry4;

    @FXML
    private Text basketPrice1;

    @FXML
    private Text basketPrice2;

    @FXML
    private Text basketPrice3;

    @FXML
    private Text basketPrice4;

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
    private Button searchGoButton;

    @FXML
    private TextField searchText;


    @FXML
    private Tab tabBasket;

    @FXML
    private Tab tabContentPages;

    @FXML
    private Tab tabHome;

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
        else if (productListViewHandler.getCurrentPage() == productListViewHandler.getPageCount() -1) {
            buttonProductsNext.setDisable(true);
            buttonProductsLast.setDisable(true);
            buttonProductsFirst.setDisable(false);
            buttonProductsPrevious.setDisable(false);

        }
        else {
            buttonProductsNext.setDisable(false);
            buttonProductsLast.setDisable(false);
            buttonProductsFirst.setDisable(false);
            buttonProductsPrevious.setDisable(false);
        }

    }



    void basketInit() {


        ArrayList<BasketEntry> currentPageBasket = basket.getPage(basket.getCurrentPage());
        currentBasketPage.setText(String.valueOf(basket.getCurrentPage() + 1)); // Array page list is 0-indexed.
        labelBasketPageAmount.setText("of " + basket.getPageCount());

        // This usually happens if there's no basket results. That's a "stuff's broken - do not display anything" scenario.
        if (basket.getPage(basket.getCurrentPage()).size() == 0) {
            buttonBasketNext.setDisable(true);
            buttonBasketPrevious.setDisable(true);
            buttonBasketLast.setDisable(true);
            buttonBasketFirst.setDisable(true);
            basketPaneEntry1.setVisible(false);
            basketPaneEntry2.setVisible(false);
            basketPaneEntry3.setVisible(false);
            basketPaneEntry4.setVisible(false);
            return;
        }


        populateBasket(currentPageBasket);

        System.out.println(basket.getCurrentPage() + "/" + basket.getPageCount());

        if (basket.getCurrentPage() == 0) {
            buttonBasketFirst.setDisable(true);
            buttonBasketPrevious.setDisable(true);
            buttonBasketNext.setDisable(false);
            buttonBasketLast.setDisable(false);

        }
        else if (basket.getCurrentPage() == basket.getPageCount() -1) {
            buttonBasketNext.setDisable(true);
            buttonBasketLast.setDisable(true);
            buttonBasketFirst.setDisable(false);
            buttonBasketPrevious.setDisable(false);

        }
        else {
            buttonBasketFirst.setDisable(false);
            buttonBasketPrevious.setDisable(false);
            buttonBasketNext.setDisable(false);
            buttonBasketLast.setDisable(false);
        }




    }




    @FXML
    void basket1RemoveAction(ActionEvent event) {
        basket.removeProduct(basket.getPage(basket.getCurrentPage()).get(0).getUUID());
        basketInit();

    }

    @FXML
    void basket2RemoveAction(ActionEvent event) {
        basket.removeProduct(basket.getPage(basket.getCurrentPage()).get(1).getUUID());
        basketInit();

    }

    @FXML
    void basket3RemoveAction(ActionEvent event) {
        basket.removeProduct(basket.getPage(basket.getCurrentPage()).get(2).getUUID());
        basketInit();

    }

    @FXML
    void basket4RemoveAction(ActionEvent event) {
        basket.removeProduct(basket.getPage(basket.getCurrentPage()).get(3).getUUID());
        basketInit();

    }

    @FXML
    void basketEntry1DecreaseAction(ActionEvent event) {

        basketEntry1Amount.setText(String.valueOf(basket.getPage(basket.getCurrentPage()).get(0).getQuantity() - 1));
        commitBasketChanges();

    }

    @FXML
    void basketEntry1IncreaseAction(ActionEvent event) {
        basketEntry1Amount.setText(String.valueOf(basket.getPage(basket.getCurrentPage()).get(0).getQuantity() + 1));
        commitBasketChanges();


    }

    @FXML
    void basketEntry2DecreaseAction(ActionEvent event) {

        basketEntry1Amount.setText(String.valueOf(basket.getPage(basket.getCurrentPage()).get(1).getQuantity() - 1));
        commitBasketChanges();

    }

    @FXML
    void basketEntry2IncreaseAction(ActionEvent event) {
        basketEntry1Amount.setText(String.valueOf(basket.getPage(basket.getCurrentPage()).get(1).getQuantity() + 1));
        commitBasketChanges();
    }

    @FXML
    void basketEntry3DecreaseAction(ActionEvent event) {
        basketEntry1Amount.setText(String.valueOf(basket.getPage(basket.getCurrentPage()).get(2).getQuantity() - 1));
        commitBasketChanges();
    }

    @FXML
    void basketEntry3IncreaseAction(ActionEvent event) {

        basketEntry1Amount.setText(String.valueOf(basket.getPage(basket.getCurrentPage()).get(2).getQuantity() + 1));
        commitBasketChanges();

    }

    @FXML
    void basketEntry4DecreaseAction(ActionEvent event) {

        basketEntry1Amount.setText(String.valueOf(basket.getPage(basket.getCurrentPage()).get(3).getQuantity() - 1));
        commitBasketChanges();

    }

    @FXML
    void basketEntry4IncreaseAction(ActionEvent event) {

        basketEntry1Amount.setText(String.valueOf(basket.getPage(basket.getCurrentPage()).get(3).getQuantity() + 1));
        commitBasketChanges();

    }

    @FXML
    void basketFirstPage(ActionEvent event) {
        commitBasketChanges();
        basket.getPage(0, true);
        basketInit();

    }

    @FXML
    void basketLastPage(ActionEvent event) {
        commitBasketChanges();
        basket.getPage(basket.getPageCount() - 1, true);
        basketInit();

    }


    @FXML
    void basketNextPage(ActionEvent event) {
        commitBasketChanges();
        basket.getNextPage();
        basketInit();
    }

    @FXML
    void basketPreviousPage(ActionEvent event) {
        commitBasketChanges();
        basket.getPreviousPage();
        basketInit();
    }

    @FXML
    void tabProductsSelectionChanged(Event event) {

        if(tabBasket.isSelected()) {
            basket.getPage(0, true);
            basketInit();
        }

        if(tabProducts.isSelected())
            productInit();



    }


    void commitBasketChanges() {
        boolean visualsChanged = false;
        TextField basketEntries[] = {basketEntry1Amount, basketEntry2Amount, basketEntry3Amount, basketEntry4Amount};
        int originalValues[] = {0,0,0,0};
        for (int i = 0; i < basket.getPage(basket.getCurrentPage()).size(); i++) {
            try {
                originalValues[i] = basket.getPage(basket.getCurrentPage()).get(i).getQuantity();
                basket.getPage(basket.getCurrentPage()).get(i).setQuantity(Integer.parseInt(basketEntries[i].getText()));
            } catch (NumberFormatException e) {
                // If amount contains nonsense value, prevent nonsense from being stored.
                basket.getPage(basket.getCurrentPage()).get(i).setQuantity(originalValues[i]);
            }
            if(basket.getPage(basket.getCurrentPage()).get(i).getQuantity() <= 0) {
                basket.removeProduct(basket.getPage(basket.getCurrentPage()).get(i).getUUID());
                visualsChanged = true;
            }
        }



        if (visualsChanged)
            basketInit();

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


    public void populateBasket(ArrayList<BasketEntry> products) {
        try {
            populateBasket1();
            basketPaneEntry1.setVisible(true);
            populateBasket2();
            basketPaneEntry2.setVisible(true);
            populateBasket3();
            basketPaneEntry3.setVisible(true);
            populateBasket4();
            basketPaneEntry4.setVisible(true);
        } catch (IndexOutOfBoundsException e) {
            String[] wrongedMessage = e.getMessage().split(" ");
            int lastGoodIndex = Integer.parseInt(wrongedMessage[1]) -1;
            switch (lastGoodIndex) {
                case -1:
                    basket.getPreviousPage();
                    basketInit(); // Return to last page, as there's no good indices.
                    break;
                case 0:
                    basketPaneEntry2.setVisible(false);
                    basketPaneEntry3.setVisible(false);
                    basketPaneEntry4.setVisible(false);
                    break;
                case 1:
                    basketPaneEntry3.setVisible(false);
                    basketPaneEntry4.setVisible(false);
                    break;
                case 2:
                    basketPaneEntry4.setVisible(false);
                    break;
            }
        }

    }

    public void populateProduct1(ProductInformation productInformation) {
        // TODO: DAM Integration
        // TODO: OMS Integration - We need info
        prod1Name.setText(productInformation.getName());
        prod1Price.setText(productInformation.getPriceInformation().getPrice() + " kr");
    }

    public void populateProduct2(ProductInformation productInformation) {
        // TODO: DAM Integration
        // TODO: OMS Integration - We need info
        prod2Name.setText(productInformation.getName());
        prod2Price.setText(productInformation.getPriceInformation().getPrice() + " kr");
    }

    public void populateProduct3(ProductInformation productInformation) {
        // TODO: DAM Integration
        // TODO: OMS Integration - We need info
        prod3Name.setText(productInformation.getName());
        prod3Price.setText(productInformation.getPriceInformation().getPrice() + " kr");
    }

    public void populateProduct4(ProductInformation productInformation) {
        // TODO: DAM Integration
        // TODO: OMS Integration - We need info
        prod4Name.setText(productInformation.getName());
        prod4Price.setText(productInformation.getPriceInformation().getPrice() + " kr");
    }


    public void populateBasket1() {
        // TODO: DAM Integration
        // TODO: OMS Integration - We need info

        ProductInformation productInformation = basket.getProductInformation(basket.getCurrentPage(), 0);

        basketEntry1Name.setText(productInformation.getName());
        basket1ShortDesc.setText(productInformation.getShortDescription());
        basketPrice1.setText(productInformation.getPriceInformation().getPrice() + " kr");
        basketEntry1Amount.setText(Integer.toString(basket.getPage(basket.getCurrentPage()).get(0).getQuantity()));


    }

    public void populateBasket2() {
        // TODO: DAM Integration
        // TODO: OMS Integration - We need info

        ProductInformation productInformation = basket.getProductInformation(basket.getCurrentPage(), 1);

        basketEntry2Name.setText(productInformation.getName());
        basket2ShortDesc.setText(productInformation.getShortDescription());
        basketPrice2.setText(productInformation.getPriceInformation().getPrice() + " kr");
        basketEntry2Amount.setText(Integer.toString(basket.getPage(basket.getCurrentPage()).get(1).getQuantity()));


    }

    public void populateBasket3() {
        // TODO: DAM Integration
        // TODO: OMS Integration - We need info

        ProductInformation productInformation = basket.getProductInformation(basket.getCurrentPage(), 2);

        basketEntry3Name.setText(productInformation.getName());
        basket3ShortDesc.setText(productInformation.getShortDescription());
        basketPrice3.setText(productInformation.getPriceInformation().getPrice() + " kr");
        basketEntry3Amount.setText(Integer.toString(basket.getPage(basket.getCurrentPage()).get(2).getQuantity()));


    }

    public void populateBasket4() {
        // TODO: DAM Integration
        // TODO: OMS Integration - We need info

        ProductInformation productInformation = basket.getProductInformation(basket.getCurrentPage(), 3);

        basketEntry4Name.setText(productInformation.getName());
        basket4ShortDesc.setText(productInformation.getShortDescription());
        basketPrice4.setText(productInformation.getPriceInformation().getPrice() + " kr");
        basketEntry4Amount.setText(Integer.toString(basket.getPage(basket.getCurrentPage()).get(3).getQuantity()));


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
