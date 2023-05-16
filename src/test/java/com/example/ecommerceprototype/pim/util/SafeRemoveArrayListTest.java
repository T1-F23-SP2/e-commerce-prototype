package com.example.ecommerceprototype.pim.util;

import com.example.ecommerceprototype.pim.product_information.ProductCategory;
import com.example.ecommerceprototype.pim.product_information.ProductCategoryUpdater;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SafeRemoveArrayListTest {

    @DisplayName("Test for safely adding to remove list")
    @Test
    void testSafeRemoveAdd() {
        SafeRemoveArrayList<ProductCategoryUpdater> safeRemoveArrayList = new SafeRemoveArrayList<>();
        ProductCategoryUpdater productCategoryUpdater = (ProductCategoryUpdater) new ProductCategoryUpdater(new ProductCategory()).setName("TestName");

        safeRemoveArrayList.safeRemoveAdd(productCategoryUpdater);

        assertEquals(productCategoryUpdater, safeRemoveArrayList.getToRemove().get(0));
        assertEquals("TestName", safeRemoveArrayList.getToRemove().get(0).getProductCategory().getName());

        safeRemoveArrayList.remove(productCategoryUpdater);

        assertEquals(0, safeRemoveArrayList.size());
    }

    @DisplayName("Test for safely removing all from remove-list")
    @Test
    void testSafeRemoveSubmit() {
        SafeRemoveArrayList<ProductCategoryUpdater> safeRemoveArrayList = new SafeRemoveArrayList<>();
        ProductCategoryUpdater productCategoryUpdater1 = (ProductCategoryUpdater) new ProductCategoryUpdater(new ProductCategory()).setName("TestName1");
        ProductCategoryUpdater productCategoryUpdater2 = (ProductCategoryUpdater) new ProductCategoryUpdater(new ProductCategory()).setName("TestName2");

        safeRemoveArrayList.safeRemoveAdd(productCategoryUpdater1);
        safeRemoveArrayList.safeRemoveAdd(productCategoryUpdater2);

        safeRemoveArrayList.safeRemoveSubmit();

        assertEquals(0, safeRemoveArrayList.size());
    }
}