package com.example.ecommerceprototype.shop.components;

import com.example.ecommerceprototype.pim.product_information.ProductInformation;
import com.example.ecommerceprototype.shop.ShopController;

import java.util.HashMap;

public class Cart {

    ShopController controller;

    public Cart(ShopController controller) {
        this.controller = controller;
    }

    HashMap<ProductInformation, Integer> cart = new HashMap<ProductInformation, Integer>();

    public void addToCart(ProductInformation product) {
        cart.put(product, 1);
        try {
            controller.getCartPage().loadPage(controller.getWindow());
        }
        catch (Exception e) {System.out.println(e.getMessage());}
    }

    public void deleteFromCart(ProductInformation product) {
        cart.remove(product);
        try {
            controller.getCartPage().loadPage(controller.getWindow());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void clearCart() {
        cart.clear();
    }

    public HashMap<ProductInformation, Integer> getContents() {
        return cart;
    }
}
