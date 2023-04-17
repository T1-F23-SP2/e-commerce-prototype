import OutDatedClasses.Items;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.util.ArrayList;
import java.util.List;

public class TableCreator {

//    static ArrayList<OutDatedClasses.Items> listOfItems = new ArrayList<>();

    static public void showTable(){
       JFrame frame = new JFrame("Table Example");

       //OutDatedClasses.Items item1 = new OutDatedClasses.Items(1, "Tv LG", 10, 499);
        //OutDatedClasses.Items item2 = new OutDatedClasses.Items(2, "Macbook pro 4k", 53, 13684);
        //OutDatedClasses.Items item3 = new OutDatedClasses.Items(3, "Iphone 24", 15, 29999);

//        listOfItems.add(item1);
//        listOfItems.add(item2);
//        listOfItems.add(item3);

//        Object[][] data;

//        for(int i = 0; i<listOfItems.size(); i++){
//            for(int j = 0; j<4; j++){
//                data[i][j] =
//            };
//        };

        // Data for the table
        Items item = null;
        String uri = "mongodb+srv://Kristoffer:123456789A@testerinvoice.t8c16zx.mongodb.net/test";
        MongoClient mongoClient = MongoClients.create(uri);
        MongoDatabase database = mongoClient.getDatabase("TesterInvoice");
        MongoCollection<Document> collection = database.getCollection("Products");

        Document query = new Document("Price", new Document("$gt", 0.00));
        List<Document> results = collection.find(query).into(new ArrayList<>());

        List<Object[]> dataList = new ArrayList<>();
        for (Document doc : results) {
            String name = doc.getString("Name");
            String Colour = doc.getString("Colour");
            double Price = doc.getInteger("Price");
            String Size = doc.getString("Size");
            int Stock = doc.getInteger("Stock");

            Object[] itemData = new Object[] { name, Colour, Price };
            dataList.add(itemData);


        }
        Object[][] data = dataList.toArray(new Object[0][]);

        // Column headers
        String[] columnNames = { "Name", "Colour", "Price"};

        // Create the table
        JTable table = new JTable(data, columnNames);

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);

        // Add the scroll pane to the frame
        frame.add(scrollPane);

        // Set the frame properties
        frame.setSize(500, 300);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }



}
