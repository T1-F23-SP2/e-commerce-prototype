package com.example.ecommerceprototype.pim.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FilterableArrayListTest {
    private static class Example {
        public boolean test;
        public Example(boolean test) {
            this.test = test;
        }
    }

    @Test
    void filterElements() {
        FilterableArrayList<Example> list = new FilterableArrayList<>();
        Example val1 = new Example(true);
        Example val2 = new Example(true);
        Example val3 = new Example(false);
        list.add(val1);
        list.add(val2);
        list.add(val3);
        FilterableArrayList<Example> filteredList = list.filterElements(e -> {
           return e.test;
        });

        assertEquals(2, filteredList.size());

        assertTrue(filteredList.contains(val1));
        assertTrue(filteredList.contains(val2));
        assertFalse(filteredList.contains(val3));
    }
}