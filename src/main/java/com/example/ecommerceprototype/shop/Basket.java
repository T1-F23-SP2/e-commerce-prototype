package com.example.ecommerceprototype.shop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Basket {


    private Basket(int pageSize) {
        this.currentPage = 0;
        this.pageSize = pageSize;
        this.products = new ArrayList<>();
    }

    private static int defaultPageSize = 4;

    private int currentPage;
    private int pageSize = 4;
    private ArrayList<BasketEntry> products;



    public void addProduct(BasketEntry newEntry) {
        boolean productFound = false;
        for (BasketEntry entry : products) {
            if (entry.getUUID() == newEntry.getUUID()) {
                entry.setQuantity(entry.getQuantity() + newEntry.getQuantity());
                productFound = true;
                break;
            }
        }
        if (!productFound)
            products.add(newEntry);
    }



    public void setProducts(ArrayList<BasketEntry> products) {
        this.products = products;
    }

    public ArrayList<BasketEntry> getProducts() {
        return products;
    }


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

    private static Basket basket = new Basket(defaultPageSize);

    public static Basket getInstance() {

        if(basket == null)
            basket = new Basket(defaultPageSize);

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
