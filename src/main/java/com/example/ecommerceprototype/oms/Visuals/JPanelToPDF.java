package com.example.ecommerceprototype.oms.Visuals;

import com.itextpdf.awt.DefaultFontMapper;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.FileOutputStream;


public class JPanelToPDF {
    /* chapter12/MyJTable.java */
    public void createPdf(boolean shapes) {
        Document document = new Document();
        try {
            PdfWriter writer;
            if (shapes)
                writer = PdfWriter.getInstance(document,
                        new FileOutputStream("my_jtable_shapes.pdf"));
            else
                writer = PdfWriter.getInstance(document,
                        new FileOutputStream("my_jtable_fonts.pdf"));
            document.open();
            PdfContentByte cb = writer.getDirectContent();
            PdfTemplate tp = cb.createTemplate(500, 500);
            Graphics2D g2;
            if (shapes)

                g2 = tp.createGraphicsShapes(500, 500);
            else
                g2 = tp.createGraphics(500, 500);
            //table.print(g2);
            g2.dispose();
            cb.addTemplate(tp, 30, 300);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        document.close();
    }
    public static JFreeChart getBarChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.setValue(57, "students", "Asia");
        dataset.setValue(36, "students", "Africa");
        dataset.setValue(29, "students", "S-America");
        dataset.setValue(17, "students", "N-America");
        dataset.setValue(12, "students", "Australia");
        return ChartFactory.createBarChart("T.U.F. Students",
                "continent", "number of students", dataset,
                PlotOrientation.VERTICAL, false, true, false);
    }
    public static JFreeChart getPieChart() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Europe", 302);
        dataset.setValue("Asia", 57);
        dataset.setValue("Africa", 17);
        dataset.setValue("S-America", 29);
        dataset.setValue("N-America", 17);
        dataset.setValue("Australia", 12);
        return ChartFactory.createPieChart("Students per continent",
                dataset, true, true, false);
    }
    public static void convertToPdf(JFreeChart chart,
                                    int width, int height, String filename) {
        Document document = new Document(new com.itextpdf.text.Rectangle(width, height));
        try {
            PdfWriter writer;
            writer = PdfWriter.getInstance(document, new FileOutputStream(filename));
            document.open();
            PdfContentByte cb = writer.getDirectContent();
            PdfTemplate tp = cb.createTemplate(width, height);
            Graphics2D g2d = tp.createGraphics(width, height, new DefaultFontMapper());
            Rectangle2D r2d = new Rectangle2D.Double(0, 0, width, height);
            chart.draw(g2d, r2d);
            g2d.dispose();
            cb.addTemplate(tp, 0, 0);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        document.close();
    }
}