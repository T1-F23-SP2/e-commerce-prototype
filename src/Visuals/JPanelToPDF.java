package Visuals;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

public class JPanelToPDF {

    public static void main(String[] args) throws Exception {
        // Create a new PDF document
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("C:/Users/krist/Desktop/SM2_Project/SMP2_Test/src/TEST.pdf"));
        document.open();

        // Create a JPanel that you want to add to the PDF
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Hello, world!");
        Font font = new Font("Helvetica", Font.PLAIN, 72);
        label.setFont(font);
        panel.add(label);

        // Add the panel to a JFrame and make it visible
        JFrame frame = new JFrame();
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);

        // Get the dimensions of the panel
        int width = 700;
        int height = 1620;

        // Set the preferred size of the panel to the desired dimensions
        panel.setPreferredSize(new java.awt.Dimension(width, height));

        // Convert the JPanel to an image
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();
        panel.paint(g2);

        // Add the image to the PDF
        Image pdfImage = Image.getInstance(writer, image, 1);
        document.add(pdfImage);

        // Close the document
        document.close();
    }

}

