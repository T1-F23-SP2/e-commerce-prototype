package com.example.ecommerceprototype.pim;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class PIMResourceRootTest {

    @Test
    void testGetPathStringWithArg() {
        String expected = "src/main/resources/com" + File.separatorChar + "example" + File.separatorChar + "ecommerceprototype" + File.separatorChar + "pim" + File.separatorChar + "test";
        assertEquals(expected, PIMResourceRoot.getPathString("test"));
    }

    @Test
    void testGetPathString() {

        String expected = "src/main/resources/com" + File.separatorChar + "example" + File.separatorChar + "ecommerceprototype" + File.separatorChar + "pim" + File.separatorChar + "";
        assertEquals(expected, PIMResourceRoot.getPathString());
    }
}