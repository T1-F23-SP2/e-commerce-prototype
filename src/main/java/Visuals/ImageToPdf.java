package Visuals;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

public class ImageToPdf {
    public static void main(String[] args) {
        Document document = new Document(PageSize.A4, 20, 20, 20, 20);
        try {
            PdfWriter.getInstance(document, new FileOutputStream("image.pdf"));
            document.open();
            Image image = Image.getInstance("C:\\Users\\krist\\Desktop\\SM2_Project\\SMP2_Test\\assets\\img.png");
            image.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight());
            document.add(image);
            document.close();
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
