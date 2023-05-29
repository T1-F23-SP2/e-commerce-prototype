package com.example.ecommerceprototype.oms.Visuals;

import com.example.ecommerceprototype.oms.ComputedOverviews.SalesReport;
import com.example.ecommerceprototype.oms.mockPIM.PlaceHolderInstGet;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.FileOutputStream;

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

    public static void convertToPdf(String filename) {
        //Initialize document
        Document document = new Document(PageSize.A4);
        try {
            // Calls instance of document with the file output
            PdfWriter.getInstance(document, new FileOutputStream(filename));
            //Open document to write to the pdf
            document.open();


            // Adding the O_table to the pdf - (CellTable)
            PdfPTable table = O_Table();

            document.add(table);

        } catch (Exception e) {
            e.printStackTrace();
        }
        //Close document
        document.close();


    }


    public static void stockOverviewGen(){
        String fileName = "assets/oms/out/Overview.pdf";
        convertToPdf(fileName);

    }

    public static void main(String[] args) {
        stockOverviewGen();
    }
}

