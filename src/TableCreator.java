import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.util.ArrayList;

public class TableCreator {

//    static ArrayList<Items> listOfItems = new ArrayList<>();

    static public void showTable(){
        JFrame frame = new JFrame("Table Example");
        Items item1 = new Items(1, "Tv LG", 10, 499);
        Items item2 = new Items(2, "Macbook pro 4k", 53, 13684);
        Items item3 = new Items(3, "Iphone 24", 15, 29999);

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
        Object[][] data = {
                {item1.id, item1.name, item1.qty, item1.price},
                {item2.id, item2.name, item2.qty, item2.price},
                {item3.id, item3.name, item3.qty, item3.price}
//                {"John", "Doe", 28},
//                {"Jane", "Smith", 32},
//                {"Tom", "Johnson", 41}
        };

        // Column headers
        String[] columnNames = {"UUID", "Name", "Qty", "Price"};

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
