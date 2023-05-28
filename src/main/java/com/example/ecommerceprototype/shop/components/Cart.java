package com.example.ecommerceprototype.shop.components;

import com.example.ecommerceprototype.pim.product_information.ProductInformation;
import com.example.ecommerceprototype.shop.ShopController;

import java.util.HashMap;
import java.util.Objects;

public class Cart {

    ShopController controller;

    public Cart(ShopController controller) {
        this.controller = controller;
    }

    HashMap<String, Integer> cart = new HashMap<>();

    public void addToCart(ProductInformation product) {
        System.out.print("item");
        System.out.print(product.getProductUUID());
        System.out.print(cart);
        boolean isAlreadyInCart = false;
        // every ProductInformation is a unique object, so the UUIDs instead of the objects are compared
        for (String key : cart.keySet()) {
            if (Objects.equals(key, product.getProductUUID())) {
                int value = cart.get(key);
                cart.remove(key);
                cart.put(key, value + 1);

                isAlreadyInCart = true;
                break;
            }
            System.out.print("item");
        }
        if (!isAlreadyInCart) {
            cart.put(product.getProductUUID(), 1);
        }
    }

    public void deleteFromCart(ProductInformation product) {
        cart.remove(product.getProductUUID());
    }

    public void clearCart() {
        cart.clear();
    }

    public HashMap<String, Integer> getContents() {
        return cart;
    }
}
