package com.example.ecommerceprototype.oms.MockShop;

import com.example.ecommerceprototype.oms.Customers.Customer;

import java.util.HashMap;

public class MockShopObject {

    HashMap<String, Integer> map = new HashMap<String, Integer>();
    Customer customer;


    public MockShopObject(HashMap<String, Integer> map, Customer customer) {
        this.map = map;
        this.customer = customer;
    }


    public HashMap<String, Integer> getMap() {
        return map;
    }

}
