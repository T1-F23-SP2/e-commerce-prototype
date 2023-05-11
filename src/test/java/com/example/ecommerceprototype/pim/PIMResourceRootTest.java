package com.example.ecommerceprototype.pim;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PIMResourceRootTest {

    @Test
    void testGetPathStringWithArg() {
        String expected = "src/main/resources/com/example/ecommerceprototype/pim/test";
        assertEquals(expected, PIMResourceRoot.getPathString("test"));
    }

    @Test
    void testGetPathString() {
        String expected = "src/main/resources/com/example/ecommerceprototype/pim/";
        assertEquals(expected, PIMResourceRoot.getPathString());
    }
}