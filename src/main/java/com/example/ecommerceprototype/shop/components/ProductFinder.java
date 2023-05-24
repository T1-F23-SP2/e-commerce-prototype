package com.example.ecommerceprototype.shop.components;

import com.example.ecommerceprototype.pim.product_information.PIMDriver;
import com.example.ecommerceprototype.pim.product_information.ProductInformation;

import java.util.Objects;

public class ProductFinder {

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
