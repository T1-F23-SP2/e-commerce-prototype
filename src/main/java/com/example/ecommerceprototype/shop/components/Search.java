package com.example.ecommerceprototype.shop.components;

import com.example.ecommerceprototype.pim.product_information.PIMDriver;
import com.example.ecommerceprototype.pim.util.ProductList;
import com.example.ecommerceprototype.shop.pages.ShopPage;
import com.example.ecommerceprototype.shop.ShopRefactor;

public class Search {

    static PIMDriver pimDriverInstance = new PIMDriver();
    public static void search(String searchTerm) throws Exception {
        ProductList result = new ProductList();
        if (searchTerm != "") {

            ProductList products = pimDriverInstance.getAllProducts();

            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getName().contains(searchTerm)) {
                    result.add(products.get(i));
                    continue;
                }
                if (products.get(i).getLongDescription().contains(searchTerm)) {
                    result.add(products.get(i));
                    continue;
                }
                if (products.get(i).getShortDescription().contains(searchTerm)) {
                    result.add(products.get(i));
                    continue;
                }
                if (products.get(i).getProductCategory().getName() != null)
                    if (products.get(i).getProductCategory().getName().contains(searchTerm)) {
                        result.add(products.get(i));
                        continue;
                    }
                if (products.get(i).getProductUUID() == searchTerm) {
                    result.add(products.get(i));
                    continue;
                }
                if (products.get(i).getSerialNumber() == searchTerm) {
                    result.add(products.get(i));
                    continue;
                }
                if(products.get(i).getManufacturingInformation().getName() != null)
                    if (products.get(i).getManufacturingInformation().getName().contains(searchTerm)) {
                        result.add(products.get(i));
                    }

            }
        }
        ShopPage.reloadProductView(ShopRefactor.getRootWindow(), result);
    }

}
