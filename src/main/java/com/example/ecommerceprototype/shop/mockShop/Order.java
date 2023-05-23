package com.example.ecommerceprototype.shop.mockShop;

import java.util.ArrayList;
import java.util.HashMap;

public class Order {

    // Customer Info, productUUID, Quantity

    private String firstName;
    private String lastName;
    private String addressLine1;
    private String addressLine2;
    // Some countries include letters in postal codes, coded as string to support this
    private String postalCode;
    private String city;
    private String country;
    private int phoneNumber;

    private HashMap<String, Integer> products = new HashMap<>();



    public void orderItemsFromHashMap(HashMap<String, Integer> products) {
        this.products = products;
    }
    public String getFirstName() {
        return getFirstName();
    }

    public String getLastName() {
        return lastName;
    }
    public String getFullName() {
        return firstName + " " + lastName;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }



    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }




    public void addProduct(String UUID, int quantity) {
        if(products.containsKey(UUID)) {
            int newQuantity = products.get(UUID) + quantity;
            products.replace(UUID, newQuantity);
        }
        else
            products.put(UUID, quantity);
    }

    public void removeProduct(String UUID) {
        if(products.containsKey(UUID))
            products.remove(UUID);
    }

    public void replaceQuantity(String UUID, int quantity) {
        if (products.containsKey(UUID))
            products.replace(UUID,quantity);
    }

    public void incrementQuantity(String UUID){
        if (products.containsKey(UUID))
            products.replace(UUID, products.get(UUID) + 1);

    }

    public void incrementQuantity(String UUID, int quantity){
        if (products.containsKey(UUID))
            products.replace(UUID, products.get(UUID) + quantity);

    }

    public void decrementQuantity(String UUID){
        if (products.containsKey(UUID))
            products.replace(UUID, products.get(UUID) - 1);

    }

    public void decrementQuantity(String UUID, int quantity){
        if (products.containsKey(UUID))
            products.replace(UUID, products.get(UUID) - quantity);

    }


    public Order(String firstName, String lastName, HashMap<String, Integer> products, String addressLine1, String addressLine2,
                 String city, String postalCode, String country, int phoneNumber) {


        this.firstName = firstName;
        this.lastName = lastName;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.products = products;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
        this.phoneNumber = phoneNumber;
    }


    public Order(String firstName, String lastName, String addressLine1, String addressLine2,
                 String city, String postalCode, String country, int phoneNumber) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
        this.phoneNumber = phoneNumber;

    }
}
