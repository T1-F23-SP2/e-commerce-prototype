package com.example.ecommerceprototype.shop;

import java.util.ArrayList;
import java.util.HashMap;

public class Basket {

    private int currentPage;
    private int pageSize = 5;
    private ArrayList<BasketEntry> products;



    public ArrayList<BasketEntry> getPage(int page) {
        return (ArrayList<BasketEntry>) products.subList(page * pageSize, page * pageSize + pageSize);
    }

    public ArrayList<BasketEntry> getPage(int page, boolean updateCurrentPage) {
        if (updateCurrentPage)
            currentPage = page;
        return (ArrayList<BasketEntry>) products.subList(page * pageSize, page * pageSize + pageSize);

    }

    public ArrayList<BasketEntry> getNextPage() {
        currentPage++;
        return getPage(currentPage);
    }

    public ArrayList<BasketEntry> getPreviousPage() {
        currentPage--;
        return getPage(currentPage);
    }

    private Basket basket = new Basket();

    public Basket getInstance() {
        return basket;
    }

    public HashMap<String, Integer> convertToHashMap() {
        HashMap<String, Integer> map = new HashMap<>();
        for (BasketEntry product : products) {
            map.put(product.getUUID(), product.getQuantity());
        }
        return map;
    }

}
