package com.example.ecommerceprototype.shop;

import com.example.ecommerceprototype.cms.CMS;
import com.example.ecommerceprototype.pim.product_information.PIMDriver;
import com.example.ecommerceprototype.shop.components.*;
import com.example.ecommerceprototype.shop.pages.*;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ShopController {

    Stage window;
    ArticlePage articlePage;
    CartPage cartPage;
    PaymentPage paymentPage;
    ProductPage productPage;
    PurchasePage purchasePage;
    ShopPage shopPage;
    ProductView productView;
    Search search;
    Sidebar sidebar;
    TopBanner topBanner;
    Cart cart;
    CartItem cartItem;
    PIMDriver PIMDriverInstance;

    public ShopController(Stage stage) throws Exception {

        PIMDriverInstance = new PIMDriver();
        articlePage = new ArticlePage(this);
        cartPage = new CartPage(this);
        paymentPage = new PaymentPage(this);
        productPage = new ProductPage(this);
        purchasePage = new PurchasePage(this);
        shopPage = new ShopPage(this);
        productView = new ProductView(this);
        search = new Search(this);
        sidebar = new Sidebar(this);
        topBanner = new TopBanner(this);
        cart = new Cart(this);
        cartItem = new CartItem(this);

        window = stage;
        shopPage.loadPage(window);

        stage.setTitle("Arnes ElectroShop!");
        stage.show();

    }

    public Stage getWindow() {
        return window;
    }

    public ArticlePage getArticlePage() {
        return articlePage;
    }

    public CartPage getCartPage() {
        return cartPage;
    }

    public PaymentPage getPaymentPage() {
        return paymentPage;
    }

    public ProductPage getProductPage() {
        return productPage;
    }


    public PurchasePage getPurchasePage() {
        return purchasePage;
    }

    public ShopPage getShopPage() {
        return shopPage;
    }

    public ProductView getProductView() {
        return productView;
    }

    public Search getSearch() {
        return search;
    }

    public Sidebar getSidebar() {
        return sidebar;
    }

    public TopBanner getTopBanner() {
        return topBanner;
    }

    public Cart getCart() {
        return cart;
    }

    public CartItem getCartItem() {
        return cartItem;
    }

    public PIMDriver getPIMDriverInstance() {
        return PIMDriverInstance;
    }

    public CMS getCMSInstance() {
        return CMS.getInstance();
    }

    public void setScene(Pane plate) {
        window.setScene(new Scene(plate, 1920, 1080));
    }
}
