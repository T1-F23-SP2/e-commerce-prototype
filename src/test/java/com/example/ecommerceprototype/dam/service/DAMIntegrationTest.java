package com.example.ecommerceprototype.dam.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DAMIntegrationTest {

    @Test
    void downloadFilesFromUUIDWithExistingUUID() {
        var di = new DAMIntegration();
        assertTrue(di.downloadFilesFromUUID("essay"));
    }
}