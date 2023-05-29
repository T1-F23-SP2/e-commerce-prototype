package com.example.ecommerceprototype.oms.visuals;


import com.example.ecommerceprototype.oms.Visuals.InventoryDisplayGenerator;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.jfree.chart.JFreeChart;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static com.example.ecommerceprototype.oms.Visuals.InventoryDisplayGenerator.stockOverviewGen;

import static org.junit.jupiter.api.Assertions.*;

class InventoryGeneratorTest {

    String uri = "mongodb+srv://Kristoffer:123456789A@testerinvoice.t8c16zx.mongodb.net/test";
    MongoClient mongoClient = MongoClients.create(uri);
    MongoDatabase database = mongoClient.getDatabase("StockDB");
    MongoCollection<Document> collection = database.getCollection("Item");

    @BeforeEach
    void setUp() {
        System.out.println("Running test in InventoryGenerator");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void O_Table() {
        // Tester at der er indhold i pdf
        assertTrue(InventoryDisplayGenerator.O_Table().isContent());
    }


    @Test
    void convertToPdf() {
        long Starttime = System.currentTimeMillis();

        stockOverviewGen();

        long EndTime = System.currentTimeMillis();
        long generationTime = EndTime - Starttime;
        assertLessThanOrEqual(generationTime, 10000);
    }

    private void assertLessThanOrEqual(long actual, long expected) {
        if (actual > expected) {
            throw new AssertionError("Expected generation time to be less than or equal to " + expected + "ms, but it took " + actual + "ms.");
        }
    }

}