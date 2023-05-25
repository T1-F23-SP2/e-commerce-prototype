package com.example.ecommerceprototype.shop.components;

import com.example.ecommerceprototype.pim.product_information.PIMDriver;
import com.example.ecommerceprototype.pim.product_information.ProductInformation;

import java.util.Objects;

public class ProductFinder {

    // Occasionally given a ProductInformation object the getPriceInformation() object will return null
    // This utility class is an iffy fix that re-fetches the same object but from a ProductList returned by PIMDriver.getAllProducts which *somehow* never returns null
    static PIMDriver pimDriverInstance = new PIMDriver();
    public static ProductInformation findProduct(ProductInformation product) throws Exception {
        for (ProductInformation each : pimDriverInstance.getAllProducts()) {
            if (Objects.equals(product.getProductUUID(), each.getProductUUID())) {
                return each;
            }
        }
        return null;
    }
}
