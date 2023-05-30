package com.example.ecommerceprototype.pim.util;

import com.example.ecommerceprototype.pim.product_information.ProductCategory;
import com.example.ecommerceprototype.pim.product_information.ProductInformation;

public class ProductList extends FilterableArrayList<ProductInformation> {
    public ProductList() {
        super();
    }

    public ProductList(int size) {
        super(size);
    }


    public ProductList filterByCategory(ProductCategory pc) {
        return this.filterElements(e -> e.getProductCategory().equals(pc), new ProductList(this.size()));
    }
}