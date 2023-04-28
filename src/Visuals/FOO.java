package Visuals;

import ComputedOverviews.SalesReport;
import DB.DBManager;
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
import mockPIM.PlaceHolderInstGet;
import mockPIM.ProductInformation;
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


//        org.bson.Document results2 = DBManager.queryDB(DBManager.databaseConn("SalesOverview"), "UUID");
//        System.out.println(results2);

//        org.bson.Document query2 = new org.bson.Document("UUID", new org.bson.Document("$gt", 0));
//        java.util.List<org.bson.Document> results2 = DBManager.databaseConn("SalesOverview").find(query2).into(new ArrayList<>());
//        results.addAll(results2);


        java.util.List<Integer> MoneyList = new ArrayList<>();
        List<String> NameList = new ArrayList<>();
        List<Object[]> tObjectList = new ArrayList<>();
        // Old code
        for (org.bson.Document doc : results) {
            int price = doc.getInteger("Price");
            String name = doc.getString("Name");
            String Colour = doc.getString("Colour");
            String Size = doc.getString("Size");
            int Stock = doc.getInteger("Stock");
            MoneyList.add(price);
            NameList.add(name);



            // Array/List måde
            Object[] itemData = new Object[] { name, Colour, price, Size, Stock };

            tObjectList.add(itemData);


        }

        // Oliver new code
//        for (ProductInformation obj : PlaceHolderInstGet.productArray) {
//            Object[] itemData = new Object[] {obj.getName(), obj.getPriceInformation().getPrice(), obj.getProductUUID(), Integer.toString(SalesReport.getAmountOfOrders(obj.getProductUUID()))};
//
//            tObjectList.add(itemData);
//
//        }


        data = MoneyList.toArray(new Integer[0]);
        names = NameList.toArray(new String[0]);
        // System.out.println(data[0].intValue());
        //System.out.println("");
        //System.out.println(names[0]);
        data2 = tObjectList.toArray(new Object[0]);

        System.out.println(data[0]);
    }
    public static PdfPTable FUCKTable() {
        // Change this to the right column names
        String[] columnHeaders = {"Name", "Price", "UUID", "AmountSold"};

        PdfPTable table = new PdfPTable(columnHeaders.length);
        table.setWidthPercentage(100);

        for (String columnHeader : columnHeaders) {
            PdfPCell headerCell = new PdfPCell(new Phrase(columnHeader));
            table.addCell(headerCell);
        }


//        List<Object[]> tObjectList2 = new ArrayList<>();
//        Object[] itemdata2 = new Object[] {PlaceHolderInstGet.getInst1().getName(), PlaceHolderInstGet.getInst1().getPriceInformation().getPrice()};
//        tObjectList2.add(itemdata2);
//        data2 = tObjectList2.toArray(new Object[0]);


//        for (int i = 0; i < columnHeaders.length; i++) {
//            Object[] rowData = (Object[]) data2[i];
//            for (int j = 0; j < rowData.length; j++) {
//
//
//                PdfPCell cell = new PdfPCell(new Phrase(rowData[j].toString()));
//                table.addCell(cell);
//
//
//            }
//        }




        // Cell måde
        for (int j = 0; j < PlaceHolderInstGet.productArray.length; j++) {


            PdfPCell cell = new PdfPCell(new Phrase(PlaceHolderInstGet.productArray[j].getName().toString()));
            table.addCell(cell);

            PdfPCell cell2 = new PdfPCell(new Phrase(PlaceHolderInstGet.productArray[j].getPriceInformation().getPrice().toString()));
            table.addCell(cell2);

            PdfPCell cell3 = new PdfPCell(new Phrase(PlaceHolderInstGet.productArray[j].getProductUUID().toString()));
            table.addCell(cell3);

            PdfPCell cell4 = new PdfPCell(new Phrase(Integer.toString(SalesReport.getAmountOfOrders(PlaceHolderInstGet.productArray[j].getProductUUID()))));
            table.addCell(cell4);
            //SalesReport.getAmountOfOrders(PlaceHolderInstGet.productArray[j].getProductUUID()))




//            // Name
//            PdfPCell cell = new PdfPCell(new Phrase(rowData[j].toString()));
//            table.addCell(cell);
//
//            // Price
//            PdfPCell cell = new PdfPCell(new Phrase(rowData[j].toString()));
//            table.addCell(cell);

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
        //Initialize document
        Document document = new Document(PageSize.A4);
        try {
            // Initialize writer
            PdfWriter writer;
            // Calls instance of document with the file output
            writer = PdfWriter.getInstance(document, new FileOutputStream(filename));
            //Open document to write to the pdf
            document.open();
            // Adding the FUCKTable to the pdf - (CellTable)
            PdfPTable table = FUCKTable();
            document.add(table);
            //user positioned text and graphic contents of a page | how to apply the proper font encoding.
            PdfContentByte cb = writer.getDirectContent();
            //Set template
            PdfTemplate tp = cb.createTemplate(width*2, height);
            //Set Graphics (
            Graphics2D g2d = tp.createGraphics(width*2, height, new DefaultFontMapper());
            //Define area to graphs
            Rectangle2D r2d1 = new Rectangle2D.Double(0, 0, width, height);
            Rectangle2D r2d2 = new Rectangle2D.Double(width, 0, width, height);
            //Draw
            charts.get(0).draw(g2d, r2d1);
            charts.get(1).draw(g2d, r2d2);
            //Remove content
            g2d.dispose();
            //Set X Y Values
            float x = 0;
            float y = 0;
            //Add Content to PdfContentByte
            cb.addTemplate(tp, x, y);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Close document
        document.close();
    }


    public static void main(String[] args) {
        List<JFreeChart> charts = new ArrayList<>();
        charts.add(FUCK());
        charts.add(FUCKPIE());
        int width = 300;
        int height = 300;
        JFreeChart chart1 = FUCKPIE();
        String fileName = "src/TEST.pdf";
        convertToPdf(charts, width, height, fileName);
    }
}
