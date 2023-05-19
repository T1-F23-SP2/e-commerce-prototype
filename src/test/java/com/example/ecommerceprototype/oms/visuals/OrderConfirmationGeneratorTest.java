package com.example.ecommerceprototype.oms.visuals;

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
        // Tester at der er indhold i pdf
        OrderConfirmationGenerator.fileFormatter();
        assertNotNull(OrderConfirmationGenerator.getRabatFooter());
    }

    @Test
    void generateOCPDF() throws BadElementException, IOException {
        Image image = Image.getInstance("assets/oms/check for OC.png");
        assertNotNull(image);
    }

    @Test
    void main() {
    }

}