import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

class BarGraphExample extends JFrame {
    private Integer[] data;
    int price;


    public void DB() {
        String uri = "mongodb+srv://Kristoffer:123456789A@testerinvoice.t8c16zx.mongodb.net/test";
        MongoClient mongoClient = MongoClients.create(uri);
        MongoDatabase database = mongoClient.getDatabase("TesterInvoice");
        MongoCollection<Document> collection = database.getCollection("Products");

        Document query = new Document("Price", new Document("$gt", 200));
        List<Document> results = collection.find(query).into(new ArrayList<>());

        List<Integer> dataList = new ArrayList<>();
        for (Document doc : results) {
            int price = doc.getInteger("Price");
            dataList.add(price);
        }
        data = dataList.toArray(new Integer[0]);
        System.out.println(data[0].intValue());
        System.out.println("");
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
                int barWidth = 200;
                int maxDataValue = 80000;
                for (int i = 0; i < data.length; i++) {
                    maxDataValue = Math.max(maxDataValue, data[i].intValue());
                    System.out.println(" Height:  " + height);
                    System.out.println(" Max:  " + maxDataValue);
                    System.out.println("");


                }
                System.out.println(" Height:  " + height);
                System.out.println(" Max:  " + maxDataValue);

                int scale = (int) Math.ceil((double) height / maxDataValue);

                System.out.println(" Scale:  " + scale);
                g2.setColor(Color.BLUE);
                for (int i = 0; i < data.length; i++) {
                    int x = i * barWidth;
                    int y = height - (int) (data[i].intValue() * scale);
                    int barHeight = (int) (data[i].intValue() * scale);
                    g2.fillRect(x, y, barWidth, barHeight);


                System.out.println(" i: " + i);
                    System.out.println(" X: " + x);

                    System.out.println(" Y:  " + y);

                    System.out.println(" barWidth:  " + barWidth);

                    System.out.println(" barHeight:  " + barHeight);



                }
            }

        };
        panel.setBackground(Color.WHITE);
        getContentPane().add(panel);
        panel.repaint();
        setSize(720, 720);
        setVisible(true);
    }

    public static void main(String[] args) {
        new BarGraphExample();
    }
}
