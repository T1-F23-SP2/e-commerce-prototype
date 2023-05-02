package Visuals;

import ComputedOverviews.SalesReport;
import DB.DBManager;
import com.itextpdf.awt.DefaultFontMapper;

import java.awt.Font;
import java.awt.geom.Rectangle2D;

import com.itextpdf.text.*;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import mockPIM.PlaceHolderInstGet;
import mockPIM.PriceInformation;
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

    static BaseColor color = BaseColor.LIGHT_GRAY;
    static BaseColor color2 = new BaseColor(48, 213,200);
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



            // Array/List
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

    }
    public static PdfPTable FUCKTable() {
        // Change this to the right column names
        String[] columnHeaders = {"Name", "UUID", "AmountOfOrders", "Price", "Buy Price", "CalcMargin (%)", "CalcMargin (KR)", "ItemQTY", "Revenue"};

        PdfPTable table = new PdfPTable(columnHeaders.length);
        table.setWidthPercentage(100);

        for (String columnHeader : columnHeaders) {
            PdfPCell headerCell = new PdfPCell(new Phrase(columnHeader));
            headerCell.setBackgroundColor(color2);
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




        // Cell mode
        for (int j = 0; j < PlaceHolderInstGet.productArray.length; j++) {


            PdfPCell cell_name = new PdfPCell(new Phrase(PlaceHolderInstGet.productArray[j].getName().toString()));
            table.addCell(cell_name);


            PdfPCell cell_UUID = new PdfPCell(new Phrase(PlaceHolderInstGet.productArray[j].getProductUUID().toString()));
            table.addCell(cell_UUID);

            PdfPCell cell_AoO = new PdfPCell(new Phrase(Integer.toString(SalesReport.getAmountOfOrders(PlaceHolderInstGet.productArray[j].getProductUUID()))));
//            cell_AoO.setBackgroundColor(color);
            table.addCell(cell_AoO);


            PdfPCell cell_price = new PdfPCell(new Phrase(PlaceHolderInstGet.productArray[j].getPriceInformation().getPrice().toString()));
//            cell_price.setBackgroundColor(color);

            table.addCell(cell_price);

            PdfPCell cell_BuyPrice = new PdfPCell(new Phrase((PlaceHolderInstGet.getInst2().getPriceInformation().getBuyPrice().toString())));
            table.addCell(cell_BuyPrice);
            PdfPCell cell_calcMargin = new PdfPCell(new Phrase(((SalesReport.calcMargin(PlaceHolderInstGet.productArray[j].getPriceInformation())).toString())));
            table.addCell(cell_calcMargin);

            PdfPCell cell_calcMarginKR = new PdfPCell(new Phrase(((SalesReport.calcMarginKR(PlaceHolderInstGet.productArray[j].getPriceInformation())).toString())));
            table.addCell(cell_calcMarginKR);

            PdfPCell cell_itemQTY = new PdfPCell(new Phrase(((SalesReport.getQTY(PlaceHolderInstGet.productArray[j].getProductUUID())).toString())));
            table.addCell(cell_itemQTY);

            PdfPCell cell_rev = new PdfPCell(new Phrase(SalesReport.rev(PlaceHolderInstGet.productArray[j].getPriceInformation()).toString()));
            table.addCell(cell_rev);







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

            //Pay no attention to this:
            document.newPage();



            PdfPTable tables = new PdfPTable(names.length);
            table.setWidthPercentage(100);



           for (int i = 0; i < table.getRows().size(); i++) {
                PdfPCell cell = table.getRow(i).getCells()[0]; // Get the first row of data

                String f_RowData = cell.getPhrase().getContent();
                System.out.println(f_RowData);
               System.out.println(table.getRows().size());
                PdfAction action = PdfAction.gotoLocalPage(1, new PdfDestination(0), writer);
                Chunk chunk = new Chunk(f_RowData);
                chunk.setAction(action);
                document.add(chunk);
//                Paragraph para = new Paragraph("dx");
//                document.add(para);
                document.newPage();

            }








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
        String fileName = "TEST.pdf";
        convertToPdf(charts, width, height, fileName);
    }
}
