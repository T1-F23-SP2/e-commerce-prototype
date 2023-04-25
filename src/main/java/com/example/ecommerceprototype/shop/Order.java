package com.example.ecommerceprototype.shop;

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
    private ArrayList<Integer> quantities;

    private HashMap<String, Integer> products = new HashMap<>();

    private ArrayList<String> productUUIDs = new ArrayList<>();

    public String getFirstName() {
        return getFirstName();
    }

    public ArrayList<Integer> getQuantities() {
        return quantities;
    }

    public int getQuantity(int index) {
        return quantities.get(index);
    }

    public void setQuantity(int index, int quantity) {
        quantities.set(index, quantity);
    }

    public void setProductUUID(int index, String UUID) {
        productUUIDs.set(index, UUID);
    }

    public String getProductUUID(int index) {
      return productUUIDs.get(index);
    }

    public ArrayList<String> getProductUUIDs() {
        return productUUIDs;
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

    public void setProductUUID(ArrayList<String> productUUIDs) {
        this.productUUIDs = productUUIDs;
    }

    public void setQuantities(ArrayList<Integer> quantities) {
        this.quantities = quantities;
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


    // Secondary constructor for when you want to build up the product list manually. This is NOT data safe.
    // Pre-conditions: Quantities and productUUIDs lengths must mach
    // Assumptions made about arrays: The arrays are index matched. Quantity of Product A = quantities[indexof productA] and vice versa.
    // Example: Say you have product A at index 0 in productUUIDs, in the quantities array, you will find the quantity of product A ordered at index 0.
    public Order(String firstName, String lastName, ArrayList<Integer> quantities, ArrayList<String> productUUIDs, String addressLine1, String addressLine2,
                 String city, String postalCode, String country, int phoneNumber) {

        if (quantities.size() != productUUIDs.size()) {
            throw new IllegalArgumentException("The length of the quantity array and product ID array must match.");
        }

        this.firstName = firstName;
        this.lastName = lastName;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.quantities = new ArrayList<>();
        this.quantities = quantities;
        this.productUUIDs = productUUIDs;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
        this.phoneNumber = phoneNumber;

    }

    // Primary constructor. This will maintain data safety and is always preferable.
    public Order(String firstName, String lastName, int quantity, String productUUID, String addressLine1,
                 String city, String postalCode, String country, int phoneNumber) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.addressLine1 = addressLine1;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
        this.phoneNumber = phoneNumber;

        quantities = new ArrayList<>();
    }
}
