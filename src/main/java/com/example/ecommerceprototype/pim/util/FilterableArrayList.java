package com.example.ecommerceprototype.pim.util;

import java.util.ArrayList;

public class FilterableArrayList<E> extends ArrayList<E> implements FilterableList<E> {

    public FilterableArrayList(int size) {
        super(size);
    }

    public FilterableArrayList() {
        super();
    }

    public FilterableArrayList<E> filterElements(Filter<E> filter) {
        return this.filterElements(filter, new FilterableArrayList<>(this.size()));
    }
}