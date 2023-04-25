package Visuals;

import com.itextpdf.awt.DefaultFontMapper;
import java.awt.geom.Rectangle2D;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import java.awt.*;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FOO {
    private static Integer[] data;
    int price;
    private static String[] names;
    private static Object[] data2;
    public static void DB() {

        String uri = "mongodb+srv://Kristoffer:123456789A@testerinvoice.t8c16zx.mongodb.net/test";
        MongoClient mongoClient = MongoClients.create(uri);
        MongoDatabase database = mongoClient.getDatabase("TesterInvoice");
        MongoCollection<org.bson.Document> collection = database.getCollection("Products");

        org.bson.Document query = new org.bson.Document("Price", new org.bson.Document("$gt", 200));
        java.util.List<org.bson.Document> results = collection.find(query).into(new ArrayList<>());


        java.util.List<Integer> MoneyList = new ArrayList<>();
        List<String> NameList = new ArrayList<>();
        List<Object[]> tObjectList = new ArrayList<>();
        for (org.bson.Document doc : results) {
            int price = doc.getInteger("Price");
            String name = doc.getString("Name");
            String Colour = doc.getString("Colour");
            String Size = doc.getString("Size");
            int Stock = doc.getInteger("Stock");
            MoneyList.add(price);
            NameList.add(name);

            Object[] itemData = new Object[] { name, Colour, price, Size, Stock };
            tObjectList.add(itemData);


        }
        data = MoneyList.toArray(new Integer[0]);
        names = NameList.toArray(new String[0]);
       // System.out.println(data[0].intValue());
        //System.out.println("");
        //System.out.println(names[0]);
        data2 = tObjectList.toArray(new Object[0]);

        System.out.println(data[0]);
    }
    public static PdfPTable FUCKTable() {
        String[] columnHeaders = {"Name", "Colour", "Price", "Size", "Stock"};

        PdfPTable table = new PdfPTable(columnHeaders.length);
        table.setWidthPercentage(100);

        for (String columnHeader : columnHeaders) {
            PdfPCell headerCell = new PdfPCell(new Phrase(columnHeader));
            table.addCell(headerCell);
        }


        for (int i = 0; i < columnHeaders.length; i++) {
            Object[] rowData = (Object[]) data2[i];
            for (int j = 0; j < rowData.length; j++) {

                PdfPCell cell = new PdfPCell(new Phrase(rowData[j].toString()));
                table.addCell(cell);
            }
        }

        return table;
    }


    public static JFreeChart FUCK() {
        DB();
        int price = 10;
        String name = "HappyPenis";
        DefaultCategoryDataset Penis = new DefaultCategoryDataset();
        for (int i = 0; i < data.length; i++)
        {
            Penis.setValue(data[i], "Shit",names[i] );

        }

        JFreeChart chart = ChartFactory.createBarChart("SALES REPORT",
                "Names", "Price", Penis,
                PlotOrientation.VERTICAL, false, true, false);

        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        CategoryAxis axis = plot.getDomainAxis();
        Font font = new Font("SansSerif", Font.PLAIN, 8);
        axis.setTickLabelFont(font); // change the font size of the y-axis labels

        return chart;
    }
    public static JFreeChart FUCKPIE() {
        DB();

        DefaultPieDataset Penis = new DefaultPieDataset();
        for (int i = 0; i < data.length; i++)
        {
            Penis.setValue(names[i],data[i]);

        }

        JFreeChart chart = ChartFactory.createPieChart("SALES REPORT",
                Penis, false, true, false);

        return chart;
    }



    public static void convertToPdf(List<JFreeChart> charts, int width, int height, String filename) {
        Document document = new Document(PageSize.A4);
        try {
            PdfWriter writer;
            writer = PdfWriter.getInstance(document, new FileOutputStream(filename));
            document.open();
            PdfPTable table = FUCKTable();
            document.add(table);
            PdfContentByte cb = writer.getDirectContent();
            PdfTemplate tp = cb.createTemplate(width*2, height);
            Graphics2D g2d = tp.createGraphics(width*2, height, new DefaultFontMapper());
            Rectangle2D r2d1 = new Rectangle2D.Double(0, 0, width, height);
            Rectangle2D r2d2 = new Rectangle2D.Double(width, 0, width, height);
            charts.get(0).draw(g2d, r2d1);
            charts.get(1).draw(g2d, r2d2);
            g2d.dispose();
            float x = 0;
            float y = 0;
            cb.addTemplate(tp, x, y);
        } catch (Exception e) {
            e.printStackTrace();
        }
        document.close();
    }


    public static void main(String[] args) {
        List<JFreeChart> charts = new ArrayList<>();
        charts.add(FUCK());
        charts.add(FUCKPIE());
        int width = 300;
        int height = 300;
        JFreeChart chart1 = FUCKPIE();
        String fileName = "src\\TEST.pdf";
        convertToPdf(charts, width, height, fileName);
    }
}
