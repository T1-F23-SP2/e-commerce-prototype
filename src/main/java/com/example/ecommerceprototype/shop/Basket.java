package com.example.ecommerceprototype.shop;

import java.util.ArrayList;
import java.util.HashMap;

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


    public int getCurrentPage() {
        return currentPage;
    }

    public int getPageCount() {
        // If amount of products matches exactly with page size, just return it. Otherwise add 1 for overflowing products.
        return products.size() % pageSize == 0 ? (products.size()/pageSize) : (products.size()/pageSize) + 1;
    }

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
        if (page < 0)
            page = 0; // Genuinely a bug I encountered. It shouldn't ever go below 0 anyway, so might as well just make it a lower bound.
        ArrayList<BasketEntry> dupedList;
        int pageMinIndex = page * pageSize;
        int pageMaxIndex =  page * pageSize + pageSize;
        if (pageMaxIndex <= products.size())
            dupedList = new ArrayList<>(products.subList(pageMinIndex, pageMaxIndex));
        else {
            //System.out.println("Disp Product Size:" + displayedProducts.size());
            dupedList = new ArrayList<>(products.subList(pageMinIndex, products.size()));
        }
        return dupedList;
        // This is a hack to get around the fact that subList is its own distinct type and we need arraylist
    }

    public ArrayList<BasketEntry> getPage(int page, boolean updateCurrentPage) {
        if (updateCurrentPage)
            currentPage = page;
        return getPage(page);

    }

    public ProductInformation getProductInformation(int page, int entry) {
        String productUUID = getPage(page).get(entry).getUUID();

        // This is a temporary workaround. We haven't gotten anything from the PIM that actually queries the DB. This is for mock data purposes.

        return ProductListViewHandler.getInstance().getProductInformationFromUUID(productUUID);

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
