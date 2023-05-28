package com.example.ecommerceprototype.dam.dam;

import com.example.ecommerceprototype.dam.constants.Category;
import com.example.ecommerceprototype.dam.constants.Type;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileSystemTest {

    @Test
    void uploadFile() {
        var fs = new FileSystem();
        String filename_in = "umltest.jpg";
        Type type_in = Type.PRODUCT_IMAGE;
        Category category_in = Category.GPU;
        String uuid_in = "uml";
        String oripath = "./bin/Diagrammer-UML.jpg";

        String urlActual = fs.uploadFile(filename_in,type_in,category_in,uuid_in,oripath);

        assertEquals("/productimages/gpu%2Fuml%2Fumltest.jpg", urlActual);
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