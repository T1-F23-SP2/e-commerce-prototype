package ComputedOverviews;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

public class OrderConfirmationDJ {

    static int AmountOfOrders = 1;
    static int OrderConfirmationNumber = 10000000;

    public static void main(String[] args) {

        while (AmountOfOrders > 0) {

            try {
                // Create a new PDF document
                Document document = new Document();

                // Create a PdfWriter object to write the document to a file
                PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(String.valueOf("#" + (OrderConfirmationNumber += 1) + ".pdf")));

                // Set the page size and margins
                document.setPageSize(PageSize.A4);
                document.setMargins(0, 0, 36, 36);

                // Create a header table to hold the banner text
                PdfPTable header = new PdfPTable(1);
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
                PdfPTable rabatFooter = new PdfPTable(1);
                rabatFooter.setTotalWidth(document.getPageSize().getWidth());
                rabatFooter.setLockedWidth(true);
                rabatFooter.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                rabatFooter.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

                // Create a PdfPCell for the footer text
                PdfPCell cell1 = new PdfPCell(new Phrase("Få 10% rabat på din næste ordre med koden:" + Chunk.NEWLINE + "" + Chunk.NEWLINE + "''NYHJEMMESIDE''", new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.WHITE)));
                PdfPCell cell2 = new PdfPCell(new Phrase("Fake adresse og cvr nummer", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE)));

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
                Image image = Image.getInstance("assets/check for OC.png");

                //Centering the picture
                image.setAbsolutePosition(150, 550); // set position in points (x,y)
                image.scaleAbsolute(300, 200); // set size in points (width, height)

                // Adding picture to the document
                document.add(image);


                //Create Scheme for order info
                // Define column headers
                String[] columnHeaders = {"navn", "antal", "pris"};

                // Create the table
                PdfPTable table = new PdfPTable(columnHeaders.length);

                // Add column headers to the table
                for (String columnHeader : columnHeaders) {
                    PdfPCell headerCell = new PdfPCell(new Phrase(columnHeader, new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
                    table.addCell(headerCell).setBackgroundColor(new BaseColor(186, 225, 255));
                }

                // Add row data to the table
                table.addCell("Produkt 1");
                table.addCell("2");
                table.addCell("25 kr");
                table.addCell("Produkt 2");
                table.addCell("1");
                table.addCell("50 kr");
                table.addCell("Produkt 3");
                table.addCell("3");
                table.addCell("10 kr");
                table.addCell("Produkt 4");
                table.addCell("3");
                table.addCell("15 kr");
                table.addCell("Produkt 5");
                table.addCell("3");
                table.addCell("30 kr");
                table.addCell("Produkt 6");
                table.addCell("3");
                table.addCell("20 kr");

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
                Phrase phrase0NameS = new Paragraph("Oliver Homo Larsen" +"\n");
                Phrase phraseEmail = new Paragraph("Email:" +"\n", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD));
                Phrase phraseEmailS = new Paragraph("Oliver1703dk@hotmail.dk" +"\n");
                Phrase phraseTLF = new Paragraph("tlf:" +"\n", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD));
                Phrase phraseTLFS = new Paragraph("45+ 12 34 56 78" +"\n");

                Phrase phrase1 = new Paragraph("Adresse:"  +"\n", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD));
                Phrase phrase1S = new Paragraph("Adresse from shop" +"\n");
                Phrase phrase2 = new Paragraph("Leverings metode:" +"\n", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD));
                Phrase phrase2S = new Paragraph("Leverings metode from shop" +"\n");
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



                // adding product to OC

                // Create the table
                PdfPTable productTable = new PdfPTable(columnHeaders.length);

















                // Close the document
                document.close();

                // Close the document
                document.close();
                System.out.println("Order confirmation file #" + OrderConfirmationNumber + " created successfully.");
                AmountOfOrders -= 1;
            } catch (DocumentException | IOException e) {
                e.printStackTrace();
            }

        }
    }
}


// Skal vide hvilken ordre og ordre antal der kommer

// Tænker vi tager den nemme løsning, og starter med en INT på 10000000, også +1 for hvert ordre
// Så vi for et nemt ordre nummer system

// Tænker jeg skal havde et array eller list (automatisk tilpasser sig, så når jeg sletter index 0
// efter at havde hentet den nødvendig data fra den, så kan jeg blive med at hente data fra index 0
// Men for information for hvert nyt odre

//Shop kunne evt oprette en database, hvor de skrive hvert ordre og det tilhørende info ind
//også give os entet adgang til databasen, eller et UUID

// Følg op på linje 90
