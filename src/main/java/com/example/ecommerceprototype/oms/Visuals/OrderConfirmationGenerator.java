package com.example.ecommerceprototype.oms.Visuals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import com.example.ecommerceprototype.oms.MockShop.MockShopObject;
import com.example.ecommerceprototype.oms.MockShop.PlaceholderInstShop;
import com.example.ecommerceprototype.oms.mockPIM.ProductInformation;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.example.ecommerceprototype.oms.mockPIM.PlaceHolderInstGet;

import static com.example.ecommerceprototype.oms.DB.DBManager.getUUIDInfo;
import static com.example.ecommerceprototype.oms.MockShop.PlaceholderInstShop.getInstShop2;

public class OrderConfirmationGenerator {
public static final int CVR = 53319637;
    static int AmountOfOrders = 1;

    public static int getOrderConfirmationNumber() {
        return OrderConfirmationNumber;
    }

    static int OrderConfirmationNumber = 10000000;
    static PdfPTable header;
    static PdfPTable rabatFooter;
    public static PdfPTable getRabatFooter() {
        return rabatFooter;
    }
    static Document document;

   public static String[] Delivery = {"Afhentning", "Udbringelse", "Hurtigt Udbringelse"};









    public static void fileFormatter() {
        // Create a new PDF document
        document = new Document();
        // Set the page size and margins
        document.setPageSize(PageSize.A4);
        document.setMargins(0, 0, 36, 36);

        // Create a header table to hold the banner text
        header = new PdfPTable(1);
        header.setTotalWidth(document.getPageSize().getWidth());
        header.setLockedWidth(true);
        header.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        header.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

        // Create a PdfPCell for the banner text and set the background color to light blue
        PdfPCell cell = new PdfPCell(new Phrase("Arne's Electronics", new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.WHITE)));
        cell.setBackgroundColor(new BaseColor(186, 225, 255));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPaddingTop(20);
        cell.setPaddingBottom(20);
        header.addCell(cell);

        // Create a footer table to hold the rabat text
        rabatFooter = new PdfPTable(1);
        rabatFooter.setTotalWidth(document.getPageSize().getWidth());
        rabatFooter.setLockedWidth(true);
        rabatFooter.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        rabatFooter.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

        // Create a PdfPCell for the footer text
        PdfPCell cell1 = new PdfPCell(new Phrase("Få 10% rabat på din næste ordre med koden:" + Chunk.NEWLINE + "" + Chunk.NEWLINE + "''NYHJEMMESIDE''", new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.WHITE)));
        PdfPCell cell2 = new PdfPCell(new Phrase("Odensevej 45   " + CVR, new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE)));

        // Setting the background color to light blue and grey + allignment
        cell1.setBackgroundColor(new BaseColor(186, 225, 255));
        cell2.setBackgroundColor(new BaseColor(128, 128, 128));

        cell1.setBorder(Rectangle.NO_BORDER);
        cell2.setBorder(Rectangle.NO_BORDER);

        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell2.setHorizontalAlignment(Element.ALIGN_CENTER);

        cell1.setPaddingTop(40);
        cell1.setPaddingBottom(40);
        cell1.setPaddingTop(10);
        cell1.setPaddingBottom(10);
        rabatFooter.addCell(cell1);
        rabatFooter.addCell(cell2);

    }

    public static void generateOCPDF(File file, MockShopObject mockShopObject) {


        for (int j = 0; j < 1; j++) {
            try {
                // Create a PdfWriter object to write the document to a file
                PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
                // Add the header and footer to the document
                writer.setPageEvent(new PdfPageEventHelper() {
                    public void onEndPage(PdfWriter writer, Document document) {
                        // Add the header to the document
                        header.writeSelectedRows(0, -1, document.leftMargin(), document.top() + document.topMargin(), writer.getDirectContent());

                        // Add the footer to the document
                        rabatFooter.writeSelectedRows(0, -1, document.leftMargin(), document.bottom() - document.bottomMargin() + rabatFooter.getTotalHeight(), writer.getDirectContent());
                    }
                });

                // Open the document
                document.open();


                // Add a new paragraph to the document
                document.add(new Paragraph(" ")); // lortet virker ikke uden denne linje kode

                Paragraph paragraph0 = new Paragraph("Tak fordi du valgte at handle med Arne's electronics.");
                Paragraph paragraph1 = new Paragraph("Forneden kan du se en detaljeret oversigt over dit køb.");
                paragraph0.setSpacingBefore(220);
                Paragraph paragraph2 = new Paragraph("Order confirmation #" + OrderConfirmationNumber, new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD));

                paragraph0.setAlignment(Element.ALIGN_CENTER);
                paragraph1.setAlignment(Element.ALIGN_CENTER);
                paragraph2.setAlignment(Element.ALIGN_CENTER);


                document.add(paragraph0);
                document.add(paragraph1);
                document.add(paragraph2);


                // Add picture to the document
                //Finding the picture
                Image image = Image.getInstance("assets/oms/check for OC.png");

                //Centering the picture
                image.setAbsolutePosition(150, 550); // set position in points (x,y)
                image.scaleAbsolute(300, 200); // set size in points (width, height)

                // Adding picture to the document
                document.add(image);


                //Create Scheme for order info
                // Define column headers
                String[] columnHeaders = {"Navn", "Antal", "Pris"};

                // Create the table
                PdfPTable table = new PdfPTable(columnHeaders.length);

                // Add column headers to the table
                for (String columnHeader : columnHeaders) {
                    PdfPCell headerCell = new PdfPCell(new Phrase(columnHeader, new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
                    table.addCell(headerCell).setBackgroundColor(new BaseColor(186, 225, 255));
                }

                String[] AmountArray = getUUIDInfo(10, "Amount");

                ArrayList<String> UUIDOrderList = new ArrayList<>(mockShopObject.getMap().keySet());


                for (int k = 0; k < UUIDOrderList.size(); k++) {
                    //table.addCell(PlaceHolderInstGet.productArray[k].getName());
                    //table.addCell(AmountArray[k]);
                    //table.addCell(PlaceHolderInstGet.productArray[k].getPriceInformation().getPrice().toString() + " DKK");


                    table.addCell(UUIDOrderList.get(k));
                    table.addCell(String.valueOf(mockShopObject.getMap().get(UUIDOrderList.get(k))));
                    table.addCell(UUIDOrderList.get(k)+"DKK");





                }

                // Add the table to the document + Setting the position of the table
                table.setTotalWidth(500);
                table.writeSelectedRows(0, -1, 50, 460, writer.getDirectContent());


                // Add delivery address + leverings metode + afsendelse dato
                Paragraph paragraph = new Paragraph();
                Paragraph paragraphLeft = new Paragraph();

                paragraph.setAlignment(Element.ALIGN_CENTER);
                paragraph.setIndentationLeft(40);
                paragraph.setSpacingBefore(200);
                paragraphLeft.setIndentationLeft(40);
                paragraphLeft.setSpacingBefore(0);


                Phrase phraseName = new Paragraph("Navn:" +"\n", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD));
                Phrase phrase0NameS = new Paragraph(mockShopObject.getCustomer().getName() +"\n");
                Phrase phraseEmail = new Paragraph("Email:" +"\n", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD));
                Phrase phraseEmailS = new Paragraph(mockShopObject.getCustomer().getEmail() +"\n");
                Phrase phraseTLF = new Paragraph("tlf:" +"\n", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD));
                Phrase phraseTLFS = new Paragraph(mockShopObject.getCustomer().getPhone() +"\n");

                Phrase phrase1 = new Paragraph("Adresse:"  +"\n", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD));
                Phrase phrase1S = new Paragraph(mockShopObject.getCustomer().getAddress() + mockShopObject.getCustomer().getZipcode() + "\n");
                Phrase phrase2 = new Paragraph("Leverings metode:" +"\n", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD));
                Phrase phrase2S = new Paragraph(Delivery[1] +"\n");
                Phrase phrase3 = new Paragraph("Afsendelse dato:" +"\n", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD));
                Phrase phrase3S = new Paragraph(LocalDate.now() +"\n");

                paragraphLeft.add(phraseName);
                paragraphLeft.add(phrase0NameS);
                paragraphLeft.add(phraseEmail);
                paragraphLeft.add(phraseEmailS);
                paragraphLeft.add(phraseTLF);
                paragraphLeft.add(phraseTLFS);

                paragraph.add(phrase1);
                paragraph.add(phrase1S);
                paragraph.add(phrase2);
                paragraph.add(phrase2S);
                paragraph.add(phrase3);
                paragraph.add(phrase3S);

                //document.add(paragraph);
                //document.add(paragraphLeft);

                // Create a table with two columns
                PdfPTable tableColum = new PdfPTable(2);

                // create the first paragraph and add it to the table
                PdfPCell cellp1 = new PdfPCell(paragraphLeft);
                cellp1.setBorder(Rectangle.NO_BORDER);
                tableColum.addCell(cellp1);

                // create the second paragraph and add it to the table
                PdfPCell cellp2 = new PdfPCell(paragraph);
                cellp2.setBorder(Rectangle.NO_BORDER);
                tableColum.addCell(cellp2);

                // add the table to the document
                tableColum.setTotalWidth(500);
                tableColum.writeSelectedRows(0, -1, 50, 220, writer.getDirectContent());

                // Close the document
                document.close();
                System.out.println("Order confirmation" + OrderConfirmationNumber + " created successfully.");
                AmountOfOrders -= 1;
            } catch (DocumentException | IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        fileFormatter();
        generateOCPDF(new File("assets/oms/out/Order_confirmation #" + OrderConfirmationNumber + ".pdf"), PlaceholderInstShop.getInstShop1());
    }


}
