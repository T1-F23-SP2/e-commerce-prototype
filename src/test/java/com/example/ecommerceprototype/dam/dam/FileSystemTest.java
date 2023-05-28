package com.example.ecommerceprototype.dam.dam;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileSystemTest {

    @Test
    void uploadFile() {
    }

    @Test
    void deleteFile() {
    }

    @Test
    void downloadFile() {
    }

    @Test
    void watermark() {
    }

    @Test
    void downloadImageFromURL() {
    }

    @Test
    void downloadFileFromURL() {
    }

    @Test
    void extractName() {
        var fs = new FileSystem();
        Assertions.assertEquals("i7-1322700k.jpg", fs.extractName("/productimages/cpu%2Fi7-13700k%2Fi7-1322700k.jpg","i7-13700k"));
    }

}