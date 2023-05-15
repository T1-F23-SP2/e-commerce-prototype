package com.example.ecommerceprototype.pim.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FilterTest {
    @Test
    void byName() {
        class ExampleName implements Nameable {
            private String name;

            public ExampleName(String name) {
                this.name = name;
            }

            public String getName() {
                return this.name;
            }
        }

        ExampleName val1 = new ExampleName("Test");
        ExampleName val2 = new ExampleName("Something else");


        Filter<Nameable> filter = Filter.byName("Test");

        assertTrue(filter.filter(val1));
        assertFalse(filter.filter(val2));
    }
}