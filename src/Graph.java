import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class BarGraphExample extends JFrame {
    private Integer[] data;
    int price;
   private String[] names;


    public void DB() {
        String uri = "mongodb+srv://Kristoffer:123456789A@testerinvoice.t8c16zx.mongodb.net/test";
        MongoClient mongoClient = MongoClients.create(uri);
        MongoDatabase database = mongoClient.getDatabase("TesterInvoice");
        MongoCollection<Document> collection = database.getCollection("Products");

        Document query = new Document("Price", new Document("$gt", 200));
        List<Document> results = collection.find(query).into(new ArrayList<>());

        List<Integer> dataList = new ArrayList<>();
        List<String> NameList = new ArrayList<>();
        for (Document doc : results) {
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
                        String priceLabel = "$" + ( data[i]);
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

        panel.setBackground(Color.BLACK);
        getContentPane().add(panel);
        panel.repaint();
        setSize(720, 960);
        setVisible(true);
    }

    public static void main(String[] args) {
        new BarGraphExample();
    }
}
