package com.example.ecommerceprototype.pim.util;

import java.util.List;

public interface FilterableList<E> extends List<E> {

    public default FilterableList<E> filterElements(Filter<E> filter){
        throw new UnsupportedOperationException("Not yet implemented");
    }
}