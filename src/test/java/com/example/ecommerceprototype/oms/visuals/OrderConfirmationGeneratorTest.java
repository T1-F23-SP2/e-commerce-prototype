package com.example.ecommerceprototype.oms.visuals;

import com.example.ecommerceprototype.oms.MockShop.PlaceholderInstShop;
import com.example.ecommerceprototype.oms.Visuals.OrderConfirmationGenerator;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.example.ecommerceprototype.oms.Visuals.SalesReportGenerator.pdfMaker;
import static org.junit.jupiter.api.Assertions.*;

class OrderConfirmationGeneratorTest {



    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void fileFormatter() {
        // Tests if there is anything inside of the pdf
        OrderConfirmationGenerator.fileFormatter();
        assertNotNull(OrderConfirmationGenerator.getRabatFooter());
    }

    @Test
    void generateOCPDF() throws BadElementException, IOException {
        // Tests whether the image is not null
        Image image = Image.getInstance("assets/oms/check for OC.png");
        assertNotNull(image);
    }

    @Test
    void generateOCPDFTime() {

        // Tests whether the orderconfirmation is generated sub 10 sec
        long startTime = System.currentTimeMillis();

        OrderConfirmationGenerator.fileFormatter();
        OrderConfirmationGenerator.generateOCPDF(new File("assets/oms/out/Orderconfirmation.pdf"), PlaceholderInstShop.getInstShop1(), 1);

        long EndTime = System.currentTimeMillis();
        long generationTime = EndTime - startTime;
        assertLessThanOrEqual(generationTime, 10000);
    }


    private void assertLessThanOrEqual(long actual, long expected) {
        if (actual > expected) {
            throw new AssertionError("Expected generation time to be less than or equal to " + expected + "ms, but it took " + actual + "ms.");
        }
    }

    @Test
    void main() {
    }

}