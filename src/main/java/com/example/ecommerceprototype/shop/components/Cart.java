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

    HashMap<ProductInformation, Integer> cart = new HashMap<ProductInformation, Integer>();

    public void addToCart(ProductInformation product) {
        boolean isAlreadyInCart = false;
        // every ProductInformation is a unique object, so the UUIDs instead of the objects are compared
        for (ProductInformation key : cart.keySet()) {
            if (Objects.equals(key.getProductUUID(), product.getProductUUID())) {
                int value = cart.get(key);
                cart.remove(key);
                cart.put(key, value + 1);

                isAlreadyInCart = true;
                break;
            }
            System.out.print("item");
        }
        if (!isAlreadyInCart) {
            cart.put(product, 1);
        }
    }

    public void deleteFromCart(ProductInformation product) {
        cart.remove(product);
    }

    public void clearCart() {
        cart.clear();
    }

    public HashMap<ProductInformation, Integer> getContents() {
        return cart;
    }
}
