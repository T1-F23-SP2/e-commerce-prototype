package com.example.ecommerceprototype.oms.visuals;

import com.example.ecommerceprototype.oms.Visuals.SalesReportGenerator;
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


import static com.example.ecommerceprototype.oms.Visuals.SalesReportGenerator.pdfMaker;
import static org.junit.jupiter.api.Assertions.*;

class SalesReportGeneratorTest {

    String uri = "mongodb+srv://Kristoffer:123456789A@testerinvoice.t8c16zx.mongodb.net/test";
    MongoClient mongoClient = MongoClients.create(uri);
    MongoDatabase database = mongoClient.getDatabase("StockDB");
    MongoCollection<Document> collection = database.getCollection("Item");

    PdfPTable table = new PdfPTable(2);
    PdfPCell headerCell = new PdfPCell();

    @BeforeEach
    void setUp() {
        System.out.println("Running test in SalesReportGenerator");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void DB() {
        //Tester at der bliver hentet UUID fra database
        Document result = collection.find(Filters.eq("UUID", "1U2U3I4D1")).first();
        System.out.println(result.toJson());
        assertEquals("1U2U3I4D1", result.getString("UUID"));
    }

    @Test
    void Table_Table() {
        // Tester at der er indhold i pdf
        table.addCell(headerCell);
        assertTrue(table.isContent());
    }

    @Test
    void Chart_BARchart() {
        JFreeChart chart = SalesReportGenerator.Chart_BARchart();

        assertNotNull(chart);
    }

    @Test
    void Chart_PIEchart() {
        JFreeChart chart = SalesReportGenerator.Chart_PIEchart();

        assertNotNull(chart);
    }


    @Test
    void convertToPdf() {
        long Starttime = System.currentTimeMillis();

        pdfMaker();

        long EndTime = System.currentTimeMillis();
        long generationTime = EndTime - Starttime;
        assertLessThanOrEqual(generationTime, 15000);
    }

    private void assertLessThanOrEqual(long actual, long expected) {
        if (actual > expected) {
            throw new AssertionError("Expected generation time to be less than or equal to " + expected + "ms, but it took " + actual + "ms.");
        }
    }

}