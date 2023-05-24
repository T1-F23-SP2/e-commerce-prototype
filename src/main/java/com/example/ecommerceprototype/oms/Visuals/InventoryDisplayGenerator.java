package com.example.ecommerceprototype.oms.Visuals;

import com.example.ecommerceprototype.oms.ComputedOverviews.SalesReport;
import com.example.ecommerceprototype.oms.mockPIM.PlaceHolderInstGet;
import com.itextpdf.awt.DefaultFontMapper;
import com.itextpdf.text.*;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.*;
import org.jfree.chart.JFreeChart;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.FileOutputStream;
import java.util.List;

public class InventoryDisplayGenerator {


    static BaseColor color2 = new BaseColor(222, 213, 12);

    public static PdfPTable O_Table() {
        // Change this to the right column names
        String[] columnHeaders = {"Name", "QTY", "UUID"};

        PdfPTable O_table = new PdfPTable(columnHeaders.length);
        O_table.setWidthPercentage(100);

        for (String columnHeader : columnHeaders) {
            PdfPCell headerCell = new PdfPCell(new Phrase(columnHeader));
            headerCell.setBackgroundColor(color2);
            O_table.addCell(headerCell);
        }

        for (int j = 0; j < PlaceHolderInstGet.productArray.length; j++) {

            PdfPCell cell_name = new PdfPCell(new Phrase(PlaceHolderInstGet.productArray[j].getName()));
            O_table.addCell(cell_name);

            PdfPCell cell_itemQTY = new PdfPCell(new Phrase(((SalesReport.getQTY(PlaceHolderInstGet.productArray[j].getProductUUID())).toString())));
            O_table.addCell(cell_itemQTY);

            PdfPCell cell_UUID = new PdfPCell(new Phrase(PlaceHolderInstGet.productArray[j].getProductUUID()));
            O_table.addCell(cell_UUID);


        }
        return O_table;
    }

    public static void convertToPdf(int width, int height, String filename) {
        //Initialize document
        Document document = new Document(PageSize.A4);
        try {
            // Initialize writer
            PdfWriter O_Writer;
            // Calls instance of document with the file output
            O_Writer = PdfWriter.getInstance(document, new FileOutputStream(filename));
            //Open document to write to the pdf
            document.open();


            // Adding the FUCKTable to the pdf - (CellTable)
            PdfPTable table = O_Table();

            document.add(table);

        } catch (Exception e) {
            e.printStackTrace();
        }
        //Close document
        document.close();


    }

    public static void main(String[] args) {
        int width = 300;
        int height = 350;
        String fileName = "assets/oms/out/Overview.pdf";
        convertToPdf(width, height, fileName);
    }


    public static void stockOverviewgen(){
        int width = 300;
        int height = 350;
        String fileName = "assets/oms/out/Overview.pdf";
        convertToPdf(width, height, fileName);

    }
}

