package com.example.ecommerceprototype.shop.mockShop;

import java.util.HashMap;

public class BasketEntry {
    private String UUID;

    private int quantity;

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getUUID() {
        return UUID;
    }

    public BasketEntry(String UUID, int quantity) {
        this.UUID = UUID;
        this.quantity = quantity;
    }




}
