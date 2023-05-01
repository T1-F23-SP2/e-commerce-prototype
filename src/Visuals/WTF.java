package Visuals;

import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;


public class WTF {
    public static PdfPTable dude()
    {
        PdfPTable table = new PdfPTable(3); // Create a PdfPTable with 3 columns
// Add data to the table
        table.addCell("Name");
        table.addCell("Age");
        table.addCell("Gender");
        table.addCell("John Smith");
        table.addCell("35");
        table.addCell("Male");
        table.addCell("Jane Doe");
        table.addCell("28");
        table.addCell("Female");

        for (int i = 0; i < table.getRows().size(); i++) {
            PdfPCell cell = table.getRow(i).getCells()[0]; // Get the first row of data
            System.out.println(cell.getPhrase().getContent() + ",");
            System.out.println(cell.getPhrase().getContent()+ ",");
            System.out.println(cell.getPhrase().getContent()+ ",");
        }



// Now the firstRow array contains the PdfPCell objects that represent the cells in the first row


        return table;
    }

    public static void main(String[] args) {
        dude();
    }

}
