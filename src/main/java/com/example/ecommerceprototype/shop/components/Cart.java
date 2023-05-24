package com.example.ecommerceprototype.shop.components;

import com.example.ecommerceprototype.pim.product_information.ProductInformation;
import com.example.ecommerceprototype.shop.ShopRefactor;
import com.example.ecommerceprototype.shop.pages.CartPage;

import java.util.HashMap;

public class Cart {

    public static HashMap<ProductInformation, Integer> cart = new HashMap<ProductInformation, Integer>();

    public static void addToCart(ProductInformation product) {
        cart.put(product, 1);
        try {CartPage.loadPage(ShopRefactor.getRootWindow());}
        catch (Exception e) {System.out.println(e.getMessage());}
    }

    public static void deleteFromCart(ProductInformation product) {
        cart.remove(product);
        try {CartPage.loadPage(ShopRefactor.getRootWindow());}
        catch (Exception e) {System.out.println(e.getMessage());}
    }

    public static void clearCart() {
        cart.clear();
    }
}
