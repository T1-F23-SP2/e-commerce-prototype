package Visuals;

import com.itextpdf.awt.PdfGraphics2D;
import com.itextpdf.text.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import org.bson.*;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfContentByte.*;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


import javax.swing.*;
import java.awt.*;
import java.awt.Font;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


class BarGraphExample extends JFrame {
    private Integer[] data;
    int price;
    private String[] names;
    JPanel jPanel = new JPanel();
    // Skriv herinde til PDF
    JLabel jLabel = new JLabel("e");
    Document document1iText = new Document();
    org.bson.Document doc = new org.bson.Document();


    public void DB() {

        String uri = "mongodb+srv://Kristoffer:123456789A@testerinvoice.t8c16zx.mongodb.net/test";
        MongoClient mongoClient = MongoClients.create(uri);
        MongoDatabase database = mongoClient.getDatabase("TesterInvoice");
        MongoCollection<org.bson.Document> collection = database.getCollection("Products");

        org.bson.Document query = new org.bson.Document("Price", new org.bson.Document("$gt", 200));
        List<org.bson.Document> results = collection.find(query).into(new ArrayList<>());
//        String results = collection.find();

        List<Integer> dataList = new ArrayList<>();
        List<String> NameList = new ArrayList<>();
        for (org.bson.Document doc : results) {
            int price = doc.getInteger("Price");
            String name = doc.getString("Name");
            dataList.add(price);
            NameList.add(name);
        }
        data = dataList.toArray(new Integer[0]);
        names = NameList.toArray(new String[0]);
        System.out.println(data[0].intValue());
        System.out.println("");
        System.out.println(names[0]);
    }

    public BarGraphExample() {
        super("Bar Graph Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DB();

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                int width = getWidth();
                int height = getHeight();
                //int barWidth = width / data.length;
                int marginSize = 50;
                int barWidth = (int) ((getWidth() - marginSize * 2) / (double) data.length) - 50;
                // Add margin to top and bottom
                int margin = 50;
                height -= (2 * margin);

                int maxDataValue = 80000;
                for (int i = 0; i < data.length; i++) {
                    maxDataValue = Math.max(maxDataValue, data[i].intValue());
                }

                int scale = (int) Math.ceil((double) height / maxDataValue);

                // Draw bars
                Random rand = new Random();
                for (int i = 0; i < data.length; i++) {
                    int x = marginSize + i * (barWidth + 50);
                    int y = height - data[i] * scale;
                    int barHeight = data[i] * scale;
                    Color color = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
                    g2.setColor(color);
                    g2.fillRect(x, y, barWidth, barHeight);

                    // Add labels on x axis
                    g2.setColor(Color.RED);
                    g2.setFont(new Font("Arial", Font.BOLD, 12));

                    String productName = "Product " + (names[i]);
                    int productNameWidth = g2.getFontMetrics().stringWidth(productName);
                    int productNameX = x + (barWidth - productNameWidth) / 2;
                    int productNameY = getHeight() - margin / 2;
                    g2.drawString(productName, productNameX, productNameY);

                    // Add labels on y axis
                    g2.setColor(Color.RED);
                    g2.setFont(new Font("Arial", Font.BOLD, 12));
                    String priceLabel = "$" + (data[i]);
                    int priceLabelWidth = g2.getFontMetrics().stringWidth(priceLabel);
                    int priceLabelX = marginSize - priceLabelWidth - 5;
                    int priceLabelY = y + barHeight / 2 + 5;
                    g2.drawString(priceLabel, priceLabelX, priceLabelY);

                }

                // Draw x and y axis labels
                g2.setColor(Color.RED);
                g2.setFont(new Font("Arial", Font.BOLD, 14));
                String xAxisLabel = "Product Names";
                int xAxisLabelWidth = g2.getFontMetrics().stringWidth(xAxisLabel);
                int xAxisLabelX = getWidth() / 2 - xAxisLabelWidth / 2;
                int xAxisLabelY = getHeight() - margin / 4;
                g2.drawString(xAxisLabel, xAxisLabelX, xAxisLabelY);

                String yAxisLabel = "Price";
                int yAxisLabelWidth = g2.getFontMetrics().stringWidth(yAxisLabel);
                int yAxisLabelX = margin / 4;
                int yAxisLabelY = getHeight() / 2 + yAxisLabelWidth / 2;
                AffineTransform at = new AffineTransform();
                at.rotate(-Math.PI / 2);
                g2.setTransform(at);
                g2.drawString(yAxisLabel, -yAxisLabelY, yAxisLabelX);
                print(g);

            }
        };
        panel.setBackground(Color.WHITE);
        getContentPane().add(panel);
        panel.repaint();
        setSize(720, 960);
        setVisible(true);

    }

    /* public void printPDF(File file) {
        com.itextpdf.text.Document document1 = null;
        document1 = new com.itextpdf.text.Document();
        //just to ensure that the panel has content...
        jPanel.add(jLabel);
        jPanel.setSize(100,100);
//so that even if the label doesnt get added...
//i can see that the panel does
        jPanel.setBackground(Color.red);

//the frame containing the panel
        JFrame f = new JFrame();
        f.add(jPanel);
        f.setVisible(true);
        f.setSize(100,100);

//print the panel to pdf
        try {
            PdfWriter writer = PdfWriter.getInstance(document1, new FileOutputStream("C:/Users/krist/Desktop/SM2_Project/SMP2_Test/src/output.pdf"));
            document1.open();
            PdfContentByte contentByte = writer.getDirectContent();
            PdfTemplate template = contentByte.createTemplate(500, 500);
            Graphics2D g2 = template.createGraphics(500, 500);
            jPanel.print(g2);
            g2.dispose();
            contentByte.addTemplate(template, 30, 300);
            jPanel.setVisible(true);
            System.out.println(g2);
        } catch (Exception e) {
            System.out.println("hello2");
            e.printStackTrace();
        }
        finally{
            if(document1 != null) {
                document1.close();
                System.out.println("Hello");
            }

        }
    }*/

    /*public void PDFer(File file) throws IOException, DocumentException {
        data = new Integer[] { 10000, 20000, 30000, 40000, 500000 };
        names = new String[] {"Price", "Ding", "Wrong"};
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        com.itextpdf.text.Document doc = null;
        doc = new com.itextpdf.text.Document();
        PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream("C:/Users/krist/Desktop/SM2_Project/SMP2_Test/src/TEST.pdf"));
        doc.open();

        JPanel panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                int width = 1080;
                int height = 1920;
                //int barWidth = width / data.length;
                int marginSize = 50;
                int barWidth = (int) ((getWidth() - marginSize * 2) / (double) data.length) - 50;
                // Add margin to top and bottom
                int margin = 50;
                height -= (2 * margin);

                int maxDataValue = 80000;
                for (int i = 0; i < data.length; i++) {
                    maxDataValue = Math.max(maxDataValue, data[i].intValue());
                }

                int scale = (int) Math.ceil((double) height / maxDataValue);

                // Draw bars
                Random rand = new Random();
                for (int i = 0; i < data.length; i++) {
                    int x = marginSize + i * (barWidth + 50);
                    int y = height - data[i] * scale;
                    int barHeight = data[i] * scale;
                    Color color = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
                    g2.setColor(color);
                    g2.fillRect(x, y, barWidth, barHeight);

                    // Add labels on x axis
                    g2.setColor(Color.RED);
                    g2.setFont(new Font("Arial", Font.BOLD, 12));

                    String productName = "Product " + (names[i]);
                    int productNameWidth = g2.getFontMetrics().stringWidth(productName);
                    int productNameX = x + (barWidth - productNameWidth) / 2;
                    int productNameY = getHeight() - margin / 2;
                    g2.drawString(productName, productNameX, productNameY);

                    // Add labels on y axis
                    g2.setColor(Color.RED);
                    g2.setFont(new Font("Arial", Font.BOLD, 12));
                    String priceLabel = "$" + (data[i]);
                    int priceLabelWidth = g2.getFontMetrics().stringWidth(priceLabel);
                    int priceLabelX = marginSize - priceLabelWidth - 5;
                    int priceLabelY = y + barHeight / 2 + 5;
                    g2.drawString(priceLabel, priceLabelX, priceLabelY);

                }

                // Draw x and y axis labels
                g2.setColor(Color.RED);
                g2.setFont(new Font("Arial", Font.BOLD, 14));
                String xAxisLabel = "Product Names";
                int xAxisLabelWidth = g2.getFontMetrics().stringWidth(xAxisLabel);
                int xAxisLabelX = getWidth() / 2 - xAxisLabelWidth / 2;
                int xAxisLabelY = getHeight() - margin / 4;
                g2.drawString(xAxisLabel, xAxisLabelX, xAxisLabelY);

                String yAxisLabel = "Price";
                int yAxisLabelWidth = g2.getFontMetrics().stringWidth(yAxisLabel);
                int yAxisLabelX = margin / 4;
                int yAxisLabelY = getHeight() / 2 + yAxisLabelWidth / 2;
                AffineTransform at = new AffineTransform();
                at.rotate(-Math.PI / 2);
                g2.setTransform(at);
                g2.drawString(yAxisLabel, -yAxisLabelY, yAxisLabelX);
            }
        };

        int width = 700;
        int height = 1620;
// Create PdfContentByte object
        // Create PdfContentByte object
        PdfContentByte cb = writer.getDirectContent();

// Draw the JPanel onto the PdfContentByte object
        Graphics2D g2 = cb.createGraphics(width, height);
        panel.paint(g2);
        g2.dispose();

// Close the document
        doc.close();

    }
*/
    public static void main(String[] args) throws DocumentException, IOException {

        BarGraphExample graph = new BarGraphExample();
        //graph.PDFer(null);
    }



}
