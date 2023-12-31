package com.example.ecommerceprototype.oms.Visuals;

import com.example.ecommerceprototype.oms.ComputedOverviews.SalesReport;
import com.itextpdf.awt.DefaultFontMapper;
import com.itextpdf.text.pdf.PdfAction;

import java.awt.Font;
import java.awt.geom.Rectangle2D;

import com.itextpdf.text.*;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.*;
import com.example.ecommerceprototype.oms.mockPIM.PlaceHolderInstGet;
import com.example.ecommerceprototype.oms.mockPIM.ProductInformation;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import java.awt.*;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import static com.example.ecommerceprototype.oms.ComputedOverviews.SalesReport.report;


public class SalesReportGenerator {
    static BaseColor color2 = new BaseColor(48, 213, 200);


    public static PdfPTable Table_Table() {
        // Change this to the right column names
        String[] columnHeaders = {"Name", "UUID", "AmountOfOrders", "Price", "Buy Price", "CalcMargin (%)", "CalcMargin (KR)", "QTY", "Revenue", "Star"};

        PdfPTable table = new PdfPTable(columnHeaders.length);
        table.setWidthPercentage(100);

        for (String columnHeader : columnHeaders) {
            PdfPCell headerCell = new PdfPCell(new Phrase(columnHeader));
            headerCell.setBackgroundColor(color2);
            table.addCell(headerCell);
        }

        // Cell mode
        for (int j = 0; j < PlaceHolderInstGet.productArray.length; j++) {

            PdfPCell cell_name = new PdfPCell(new Phrase(PlaceHolderInstGet.productArray[j].getName()));
            table.addCell(cell_name);


            PdfPCell cell_UUID = new PdfPCell(new Phrase(PlaceHolderInstGet.productArray[j].getProductUUID()));
            table.addCell(cell_UUID);

            PdfPCell cell_AoO = new PdfPCell(new Phrase(Integer.toString(SalesReport.getAmountOfOrders(PlaceHolderInstGet.productArray[j].getProductUUID()))));
//            cell_AoO.setBackgroundColor(color);
            table.addCell(cell_AoO);


            PdfPCell cell_price = new PdfPCell(new Phrase(PlaceHolderInstGet.productArray[j].getPriceInformation().getPrice().toString()));
//            cell_price.setBackgroundColor(color);

            table.addCell(cell_price);

            PdfPCell cell_BuyPrice = new PdfPCell(new Phrase((PlaceHolderInstGet.productArray[j].getPriceInformation().getBuyPrice().toString())));
            table.addCell(cell_BuyPrice);
            PdfPCell cell_calcMargin = new PdfPCell(new Phrase(((SalesReport.calcMargin(PlaceHolderInstGet.productArray[j].getPriceInformation())).toString())));
            table.addCell(cell_calcMargin);

            PdfPCell cell_calcMarginKR = new PdfPCell(new Phrase(((SalesReport.calcMarginKR(PlaceHolderInstGet.productArray[j].getPriceInformation())).toString())));
            table.addCell(cell_calcMarginKR);

            PdfPCell cell_itemQTY = new PdfPCell(new Phrase(((SalesReport.getQTY(PlaceHolderInstGet.productArray[j].getProductUUID())).toString())));
            table.addCell(cell_itemQTY);

            PdfPCell cell_rev = new PdfPCell(new Phrase(SalesReport.rev(PlaceHolderInstGet.productArray[j]).toString()));
            table.addCell(cell_rev);

            if (SalesReport.getFavoriteProduct().equals(PlaceHolderInstGet.productArray[j].getProductUUID())) {
                PdfPCell Fav_Product = new PdfPCell(new Phrase("Star"));
                table.addCell(Fav_Product);
            }
            else {
                PdfPCell Fav = new PdfPCell(new Phrase(""));
                table.addCell(Fav);
            }
        }



        table.setSpacingAfter(30f);
        return table;
    }
    public static PdfPTable Table_GDI() {
        // Change this to the right column names
        String[] columnHeaders = {"turnover", "wages", "interestIncome", "rentalIncome", "Taxes", "Production Cost", "low Inventory", "GDI"};

        PdfPTable GDI_table = new PdfPTable(columnHeaders.length);
        GDI_table.setWidthPercentage(100);

        for (String columnHeader : columnHeaders) {
            PdfPCell headerCell = new PdfPCell(new Phrase(columnHeader));
            headerCell.setBackgroundColor(color2);

            GDI_table.addCell(headerCell);
        }

            PdfPCell Turnover = new PdfPCell(new Phrase(String.valueOf(report.getTurnover())));
            GDI_table.addCell(Turnover);

            PdfPCell wages = new PdfPCell(new Phrase((String.valueOf(report.getWages()))));
            GDI_table.addCell(wages);
            PdfPCell InterestIncome = new PdfPCell(new Phrase(String.valueOf(report.getInterestIncome())));
            GDI_table.addCell(InterestIncome);
            PdfPCell RentalIncome = new PdfPCell(new Phrase(String.valueOf(report.getRentalIncome())));
            GDI_table.addCell(RentalIncome);
            PdfPCell Taxes = new PdfPCell(new Phrase(String.valueOf(report.getTaxes())));
            GDI_table.addCell(Taxes);
            PdfPCell ProductionCost = new PdfPCell(new Phrase(String.valueOf(report.getProductionCost())));
            GDI_table.addCell(ProductionCost);
            PdfPCell LowInventory = new PdfPCell(new Phrase(String.valueOf(report.getLowInventory())));
            GDI_table.addCell(LowInventory);
            PdfPCell calcGrossDomesticIncome = new PdfPCell(new Phrase(String.valueOf(report.calcGrossDomesticIncome())));
            GDI_table.addCell(calcGrossDomesticIncome);


        return GDI_table;
    }


    public static JFreeChart Chart_BARchart() {

        DefaultCategoryDataset BarData = new DefaultCategoryDataset();
        for (int i = 0; i < PlaceHolderInstGet.productArray.length; i++) {
            BarData.setValue(SalesReport.getAmountOfOrders(PlaceHolderInstGet.productArray[i].getProductUUID()), "matt", PlaceHolderInstGet.productArray[i].getName());

        }

        JFreeChart chart = ChartFactory.createBarChart("SALES REPORT",
                "Names", "Amount Sold", BarData,
                PlotOrientation.VERTICAL, false, true, true);

        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        CategoryAxis axis = plot.getDomainAxis();
        Font font = new Font("SansSerif", Font.PLAIN, 8);
        axis.setTickLabelFont(font); // change the font size of the y-axis labels

        return chart;
    }

    public static JFreeChart Chart_PIEchart() {


        DefaultPieDataset PieData = new DefaultPieDataset();
        for (int i = 0; i < PlaceHolderInstGet.productArray.length; i++) {
            PieData.setValue(PlaceHolderInstGet.productArray[i].getName(), SalesReport.getAmountOfOrders(PlaceHolderInstGet.productArray[i].getProductUUID()));

        }

        JFreeChart chart = ChartFactory.createPieChart("Amount of orders",
                PieData, true, true, true);

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

            Image image = Image.getInstance("assets/oms/travel and tour - Made with PosterMyWall.jpg");
            image.scaleToFit(PageSize.A4.getWidth(), 125);
            Paragraph paragraph = new Paragraph();
            paragraph.add(image);
            paragraph.setSpacingAfter(20);
            document.add(paragraph);


            // Adding the Table to the pdf - (CellTable)
            PdfPTable table = Table_Table();
            PdfPTable table_GDI = Table_GDI();
            document.add(table);
            document.add(table_GDI);
            //user positioned text and graphic contents of a page | how to apply the proper font encoding.
            PdfContentByte cb = writer.getDirectContent();
            //Set template
            PdfTemplate tp = cb.createTemplate(width * 2, height);
            //Set Graphics (
            Graphics2D g2d = tp.createGraphics(width * 2, height, new DefaultFontMapper());
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

            int j = 0;
            // Loop through the product array
            for (ProductInformation product : PlaceHolderInstGet.productArray) {
                // Create a table for the page
                PdfPTable Gentable = new PdfPTable(4);
                table.setWidthPercentage(100);

// Add column headers to the table
                Chunk chunk = new Chunk(PlaceHolderInstGet.productArray[j].getName());
                //PdfPCell nameCell = new PdfPCell(new Phrase("Name"));
                PdfAction action = PdfAction.gotoLocalPage(1, new PdfDestination(0), writer);
                //Gentable.addCell(nameCell);
                Gentable.addCell(new PdfPCell(new Phrase("Name")));
                Gentable.addCell(new PdfPCell(new Phrase("Price")));
                Gentable.addCell(new PdfPCell(new Phrase("Buy Price")));
                Gentable.addCell(new PdfPCell(new Phrase("Amount of Orders")));

// Populate the table with data for the current product
                Gentable.addCell(new PdfPCell(new Phrase(chunk)));
                chunk.setAction(action);
                Gentable.addCell(new PdfPCell(new Phrase((PlaceHolderInstGet.productArray[j].getPriceInformation().getPrice().toString()))));
                Gentable.addCell(new PdfPCell(new Phrase(product.getPriceInformation().getBuyPrice().toString())));
                Gentable.addCell(new PdfPCell(new Phrase(Integer.toString(SalesReport.getAmountOfOrders(product.getProductUUID())))));

                document.add(Gentable);

                // Create a graph for the page
                DefaultPieDataset GenChart = new DefaultPieDataset<>();
                GenChart.setValue("AmountOfOrders", SalesReport.getAmountOfOrders(product.getProductUUID()));
                GenChart.setValue("QTY", SalesReport.getQTY(PlaceHolderInstGet.productArray[j].getProductUUID()));
                JFreeChart Genchart = ChartFactory.createPieChart("Amount sold VS QTY", GenChart);


                PdfContentByte GencontentByte = writer.getDirectContent();
                PdfTemplate template = GencontentByte.createTemplate(500, 300);
                Graphics2D graphics2d = template.createGraphics(500, 300, new DefaultFontMapper());
                Rectangle2D rectangle2d = new Rectangle2D.Double(0, 0, 500, 300);
                Genchart.draw(graphics2d, rectangle2d);
                graphics2d.dispose();
                GencontentByte.addTemplate(template, 0, 0);

                document.newPage();
                j++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Close document
        document.close();

    }

    public static void pdfMaker() {
        List<JFreeChart> charts = new ArrayList<>();
        charts.add(Chart_BARchart());
        charts.add(Chart_PIEchart());
        int width = 250;
        int height = 300;
        String fileName = "assets/oms/out/SalesReport.pdf";
        convertToPdf(charts, width, height, fileName);
    }


    public static void main(String[] args) {
        pdfMaker();
    }
}
