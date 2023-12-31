package com.example.ecommerceprototype.oms.MockShop;

import com.example.ecommerceprototype.oms.Customers.Customer;
import com.example.ecommerceprototype.oms.mockPIM.PlaceHolderInstGet;

import java.util.HashMap;

public class PlaceholderInstShop {

    static HashMap<String, Integer> map = new HashMap<String, Integer>();




    static {
        map.put(PlaceHolderInstGet.getInst1().getProductUUID(), 1);
        map.put(PlaceHolderInstGet.getInst2().getProductUUID(), 2);
        map.put(PlaceHolderInstGet.getInst3().getProductUUID(), 55);
    }

    public static Customer getCustomer() {
        return customer;
    }

    static Customer customer = new Customer("Anders Rasmussen", "Aras1985@gmail.com", 78956453, "Arnegade 45, ", 5000);



    static MockShopObject instShop1 = new MockShopObject(map, customer);

    public static MockShopObject getInstShop1() {
        return instShop1;
    }

    public static Customer getInstShop2() {return customer;}
}
