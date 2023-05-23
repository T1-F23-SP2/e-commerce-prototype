package com.example.ecommerceprototype.shop;

import com.example.ecommerceprototype.oms.DB.StockInterface;
import com.example.ecommerceprototype.oms.Customers.Customer;

import com.example.ecommerceprototype.cms.CMS;
import com.example.ecommerceprototype.oms.MockShop.MockShopObject;
import com.example.ecommerceprototype.oms.OrderStatus.OrderManager;
import com.example.ecommerceprototype.pim.product_information.PIMDriver;

import com.example.ecommerceprototype.pim.product_information.ProductCategory;
import com.example.ecommerceprototype.pim.product_information.ProductInformation;
import com.example.ecommerceprototype.pim.product_information.PriceInformation;
import com.example.ecommerceprototype.pim.util.FilterableArrayList;
import com.example.ecommerceprototype.pim.util.ProductList;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;



public class ShopCMSView extends Application implements StockInterface {

    private PIMDriver pimDriverInstance;
    OrderManager orderManager;

    HashMap<ProductInformation, Integer> cart;

    Stage window;
    public static void main(String[] args) {
        launch();
    }


    @Override
    public void start(Stage stage) throws Exception {
        pimDriverInstance = new PIMDriver();
        orderManager = new OrderManager();
        //stockInterfaceInstance = new StockInterface();
        cart = new HashMap<ProductInformation, Integer>();

        window = stage;
        loadShopPage();

        stage.setTitle("Arnes ElectroShop!");
        stage.show();
    }

    public void loadShopPage() throws Exception {

        //Load page template (Template 1 has space for a topbanner, a sidebar and content to be arranged in a grid)
        Pane plate = CMS.getInstance().loadComponent("ContentTemplate1");

        loadTopBanner(plate);
        loadSidebar(plate);
        loadProducts(plate, pimDriverInstance.getAllProducts());

        window.setScene(new Scene(plate, 1920, 1080));
    }

    public void loadProducts(Pane plate, ProductList products) throws Exception {
        Random random = new Random();

        for (int i = 0; i < products.size(); i++) {
            Pane view = CMS.getInstance().loadComponent("ProductView");

            ((Label) CMS.getInstance().findNode(view, "productName_Label")).setText(products.get(i).getName());
            if (products.get(i).getPriceInformation() == null) {
                ((Label) CMS.getInstance().findNode(view, "productPrice_Label")).setText("$" + (products.get(i).getPriceInformation()));
            } else {
                ((Label) CMS.getInstance().findNode(view, "productPrice_Label")).setText("$" + (products.get(i).getPriceInformation().getPrice()));
            }
            // ((Label) CMS.getInstance().findNode(view, "productStatus_Label")).setText(String.valueOf(StockInterface.getStockValue("12345")));
            // ((Label) CMS.getInstance().findNode(view, "productStatus_Label")).setText(StockInterface.getStockValue(products.get(i).getProductUUID()) > 0 ? "In stock" : "Sold out");
            ((TextArea) CMS.getInstance().findNode(view, "productDescription_TextArea")).setText(products.get(i).getShortDescription());
            Image productImage = new Image(getClass().getResourceAsStream("Placeholder.jpg"));
            ((ImageView) CMS.getInstance().findNode(view, "productImage_ImageView")).setImage(productImage);
            int finalI = i;
            ((Button) CMS.getInstance().findNode(view, "productImage_Button")).setOnAction(actionEvent -> {
                try {
                    loadProductPage(products.get(finalI));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            });

            GridPane.setColumnIndex(view, i % 3);
            GridPane.setRowIndex(view, (int) Math.floor(i / 3)); // floor(n/3) is the integer sequence for 0, 0, 0, 1, 1, 1, 2, 2, 2... (https://oeis.org/A002264)

            CMS.getInstance().loadOnto(plate, view, "contentPlaceholder_GridPane");
        }
    }

    public void loadSidebar(Pane plate) throws Exception {
        //Load sidebar onto template
        Pane sidebar = CMS.getInstance().loadComponent("CategorySidebar");
        CMS.getInstance().loadOnto(plate, sidebar, "sidebarPlaceholder_Pane");

        //Set action for article button (on the sidebar)
        ((Button) CMS.getInstance().findNode(sidebar, "articles_Button")).setOnAction(actionEvent -> {
            try {loadArticlePage();}
            catch (Exception e) {System.out.println(e.getMessage());}
        });

        // Load categories
        VBox categoryList = (VBox) CMS.getInstance().findNode(sidebar, "categoryList_VBox");
        VBox allCategoryItem = (VBox) CMS.getInstance().loadComponent("CategoryItem");
        Button allCategoryButton = (Button) CMS.getInstance().findNode(allCategoryItem, "categoryItem_Button");
        ((Button) CMS.getInstance().findNode(allCategoryItem, "categoryItem_Button")).setOnAction(actionEvent -> {
            try {loadShopPage();}
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
                try {reloadShopPageWithCategory(allCategories.get(finalI).getName());}
                catch (Exception e) {System.out.println(e.getMessage());}
            });
            b.setText(allCategories.get(i).getName());
            categoryList.getChildren().add(b);
        }
    }

    private void reloadShopPageWithCategory(String category) throws Exception {
        //Load page template (Template 1 has space for a topbanner, a sidebar and content to be arranged in a grid)
        Pane plate = CMS.getInstance().loadComponent("ContentTemplate1");

        loadTopBanner(plate);
        loadSidebar(plate);
        loadProducts(plate, pimDriverInstance.getProductsByCategoryName(category));
        System.out.println(category);

        window.setScene(new Scene(plate, 1920, 1080));
    }

    private void reloadShopPageWithCustomProducts(ProductList products) throws Exception {
        //Load page template (Template 1 has space for a topbanner, a sidebar and content to be arranged in a grid)
        Pane plate = CMS.getInstance().loadComponent("ContentTemplate1");

        loadTopBanner(plate);
        loadSidebar(plate);
        loadProducts(plate, products);

        window.setScene(new Scene(plate, 1920, 1080));
    }

    public void search(String searchTerm) throws Exception {
        ProductList result = new ProductList();
        if (searchTerm != "") {

            ProductList products = pimDriverInstance.getAllProducts();

            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getName().contains(searchTerm)) {
                    result.add(products.get(i));
                    continue;
                }
                if (products.get(i).getLongDescription().contains(searchTerm)) {
                    result.add(products.get(i));
                    continue;
                }
                if (products.get(i).getShortDescription().contains(searchTerm)) {
                    result.add(products.get(i));
                    continue;
                }
                if (products.get(i).getProductCategory().getName() != null)
                    if (products.get(i).getProductCategory().getName().contains(searchTerm)) {
                        result.add(products.get(i));
                        continue;
                    }
                if (products.get(i).getProductUUID() == searchTerm) {
                    result.add(products.get(i));
                    continue;
                }
                if (products.get(i).getSerialNumber() == searchTerm) {
                    result.add(products.get(i));
                    continue;
                }
                if(products.get(i).getManufacturingInformation().getName() != null)
                    if (products.get(i).getManufacturingInformation().getName().contains(searchTerm)) {
                        result.add(products.get(i));
                    }

            }
        }
        reloadShopPageWithCustomProducts(result);
    }


    public void loadTopBanner(Pane plate) throws Exception {
        //Load top banner onto template and set home button functionality
        Pane topBanner = CMS.getInstance().loadComponent("TopBanner");
        ((Button) CMS.getInstance().findNode(topBanner, "home_Button")).setOnAction(actionEvent -> {
            try {loadShopPage();}
            catch (Exception e) {System.out.println(e.getMessage());}
        });
        ((Button) CMS.getInstance().findNode(topBanner, "search_Button")).setOnAction(actionEvent -> {
            try {search(((TextField) CMS.getInstance().findNode(topBanner, "search_TextField")).getText());}
            catch (Exception e) {System.out.println(e.getMessage());}
        });
        ((Button) CMS.getInstance().findNode(topBanner, "cart_Button")).setOnAction(actionEvent -> {
            try {loadCartPage();}
            catch (Exception e) {System.out.println(e.getMessage());}
        });
        CMS.getInstance().loadOnto(plate, topBanner, "topBannerPlaceholder_Pane");
    }

    public void loadArticlePage() throws Exception{
        System.out.println("");
        //Load page template (Template 2 has space for a top banner and some content pane)
        Pane plate = CMS.getInstance().loadComponent("ContentTemplate2");

        loadTopBanner(plate);

        //Load article page onto template (article page has functionality by default, since CMS already has all the files for displaying the needed information)
        CMS.getInstance().loadOnto(plate, CMS.getInstance().loadComponent("ArticlePage"), "contentPlaceholder_Pane");

        window.setScene(new Scene(plate, 1920, 1080));
    }

    public void loadProductPage(ProductInformation product) throws Exception {
        //Load page template (Template 2 has space for a top banner and some content pane)
        Pane plate = CMS.getInstance().loadComponent("ContentTemplate3");

        loadTopBanner(plate);
        loadSidebar(plate);

        Pane productPage = CMS.getInstance().loadComponent("ProductPage");
        Image productImage = new Image(getClass().getResourceAsStream("Placeholder.jpg"));
        ((ImageView) CMS.getInstance().findNode(productPage, "primaryProductImage_ImageView")).setImage(productImage);
        ((ImageView) CMS.getInstance().findNode(productPage, "secondaryProductImage_ImageView")).setImage(productImage);

        ((Label) CMS.getInstance().findNode(productPage, "productName_Label")).setText(product.getName());
        ((Label) CMS.getInstance().findNode(productPage, "productPrice_Label")).setText("$" +(product.getPriceInformation().getPrice()));
        ((TextArea) CMS.getInstance().findNode(productPage, "productDescription_TextArea")).setText(product.getLongDescription());
        ((TextArea) CMS.getInstance().findNode(productPage, "productSpecification_TextArea")).setText(product.getShortDescription());
        ((Button) CMS.getInstance().findNode(productPage, "addToCart_Button")).setOnAction(actionEvent -> {
            try {addToCart(product);}
            catch (Exception e) {System.out.println(e.getMessage());}
        });

        CMS.getInstance().loadOnto(plate, productPage, "contentPlaceholder_Pane");

        window.setScene(new Scene(plate, 1920, 1080));
    }

    public void addToCart(ProductInformation product) {
        cart.put(product, 1);
        try {loadCartPage();}
        catch (Exception e) {System.out.println(e.getMessage());}
    }

    public void deleteFromCart(ProductInformation product) {
        cart.remove(product);
        try {loadCartPage();}
        catch (Exception e) {System.out.println(e.getMessage());}
    }

    public void clearCart() {
        cart.clear();
    }

    public void loadCartPage() throws Exception{
        //Load page template (Template 2 has space for a top banner and some content pane)
        Pane plate = CMS.getInstance().loadComponent("ContentTemplate3");

        loadTopBanner(plate);
        loadSidebar(plate);

        Pane cartPage = CMS.getInstance().loadComponent("CartPage");
        CMS.getInstance().loadOnto(plate, cartPage, "contentPlaceholder_Pane");

        BigDecimal total = BigDecimal.valueOf(0);
        for (ProductInformation product : cart.keySet()) {
            Pane item = CMS.getInstance().loadComponent("CartProductView");
            CMS.getInstance().loadOnto(cartPage, item, "cartProductView_Vbox");
            Image productImage = new Image(getClass().getResourceAsStream("Placeholder.jpg"));
            ((ImageView) CMS.getInstance().findNode(item, "productImage_ImageView")).setImage(productImage);
            ((Label) CMS.getInstance().findNode(item, "productName_Label")).setText(product.getName());
            ((Label) CMS.getInstance().findNode(item, "price_Label")).setText("$" + (product.getPriceInformation().getPrice()));
            //((Spinner) CMS.getInstance().findNode(item, "amount_Spinner")).getValueFactory().setValue(cart.get(product));
            ((Button) CMS.getInstance().findNode(cartPage, "remove_Button")).setOnAction(actionEvent -> {
                try {deleteFromCart(product);}
                catch (Exception e) {System.out.println("!!!" + e.getMessage());}
            });

            total = total.add(product.getPriceInformation().getPrice());
        }

        ((Label) CMS.getInstance().findNode(cartPage, "priceExclTax_Label")).setText("$" + total);
        ((Label) CMS.getInstance().findNode(cartPage, "priceTax_Label")).setText("$" + total.multiply(BigDecimal.valueOf(0.25)));
        ((Label) CMS.getInstance().findNode(cartPage, "priceTotal_Label")).setText("$" + total.multiply(BigDecimal.valueOf(0.25)).add(total));

        ((Button) CMS.getInstance().findNode(cartPage, "pay_Button")).setOnAction(actionEvent -> {
            try {loadPaymentPage();}
            catch (Exception e) {System.out.println("!!!" + e.getMessage());}
        });


        window.setScene(new Scene(plate, 1920, 1080));
    }

    public void loadPaymentPage() throws Exception{
        //Load page template (Template 2 has space for a top banner and some content pane)
        Pane plate = CMS.getInstance().loadComponent("ContentTemplate3");

        loadTopBanner(plate);
        loadSidebar(plate);

        Pane paymentPage = CMS.getInstance().loadComponent("paymentPage");
        CMS.getInstance().loadOnto(plate, paymentPage, "contentPlaceholder_Pane");

        ((Button) CMS.getInstance().findNode(paymentPage, "finish_Button")).setOnAction(actionEvent -> {
            String name = ((TextField) CMS.getInstance().findNode(paymentPage, "fullName_TextField")).getText();
            String email = ((TextField) CMS.getInstance().findNode(paymentPage, "email_TextField")).getText();
            int phone = Integer.parseInt(((TextField) CMS.getInstance().findNode(paymentPage, "phoneNumber_TextField")).getText());
            String address = ((TextField) CMS.getInstance().findNode(paymentPage, "address_TextField")).getText();
            int zipcode = Integer.parseInt(((TextField) CMS.getInstance().findNode(paymentPage, "ZIPCode_TextField")).getText());

            HashMap<String, Integer> order = new HashMap<>();

            for (ProductInformation product : cart.keySet()) {
                order.put(product.getProductUUID(), cart.get(product));
            }

            Customer customer = new Customer(name, email, phone, address, zipcode);
            MockShopObject orderInfo = new MockShopObject(order, customer);
            StockInterface.sendOrderOMSNew(orderInfo);
            OrderManager.sendOrder(orderInfo);


            try {
                clearCart();
                loadPurchaseComplete();
            }
            catch (Exception e) {System.out.println(e.getMessage());}
        });

        window.setScene(new Scene(plate, 1920, 1080));
    }

    public void loadPurchaseComplete() throws Exception{
        //Load page template (Template 2 has space for a top banner and some content pane)
        Pane plate = CMS.getInstance().loadComponent("ContentTemplate3");

        loadTopBanner(plate);
        loadSidebar(plate);

        Pane paymentPage = CMS.getInstance().loadComponent("PurchaseCompletePage");
        CMS.getInstance().loadOnto(plate, paymentPage, "contentPlaceholder_Pane");

        window.setScene(new Scene(plate, 1920, 1080));
    }
}