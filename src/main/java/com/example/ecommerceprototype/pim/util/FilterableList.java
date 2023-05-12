package com.example.ecommerceprototype.pim.util;

import java.util.List;

public interface FilterableList<E> extends List<E> {

    public default <R extends FilterableList<E>> R filterElements(Filter<E> filter, R output){
        this.forEach(e -> {
            if (filter.filter(e)) output.add(e);
        });
        return output;
    }
}