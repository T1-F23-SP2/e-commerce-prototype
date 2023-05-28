package com.example.ecommerceprototype.pim.util;

import com.example.ecommerceprototype.pim.product_information.ProductCategory;
import com.example.ecommerceprototype.pim.product_information.ProductInformation;
import com.example.ecommerceprototype.pim.product_information.ProductInformationBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class ProductListTest {
    private ProductInformation createProductWithCategory(ProductCategory productCategory) {
        ProductInformationBuilder productInformationBuilder;
        productInformationBuilder = new ProductInformationBuilder();
        productInformationBuilder.setProductCategory(productCategory);

        return productInformationBuilder.getProductInformation();
    }

    @Test
    void testFilterByCategory() {
        ProductCategory targetCategory = new ProductCategory();
        ProductCategory otherCategory = new ProductCategory();

        ProductList productList = new ProductList();

        productList.add(createProductWithCategory(targetCategory));
        productList.add(createProductWithCategory(targetCategory));
        productList.add(createProductWithCategory(otherCategory));
        productList.add(createProductWithCategory(otherCategory));
        productList.add(createProductWithCategory(otherCategory));

        ProductList filteredList = productList.filterByCategory(targetCategory);
        assertEquals(2, filteredList.size());

        filteredList.forEach(pi -> {
            // Assert that each product information object belongs to the target category.
            assertEquals(targetCategory, pi.getProductCategory());
        });
    }
}