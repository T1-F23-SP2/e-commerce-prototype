package DB;

import MockShop.MockShopObject;
import MockShop.PlaceholderInstShop;
import OrderStatus.OrderManager;
import com.example.testcopypastetest.HelloController;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public interface StockInterface {

    /*
    String uri = "mongodb+srv://Kristoffer:123456789A@testerinvoice.t8c16zx.mongodb.net/test";
    MongoClient mongoClient = MongoClients.create(uri);
    MongoDatabase database = mongoClient.getDatabase("StockDB");
    MongoCollection<Document> itemCollection = database.getCollection("Item");

    int qtyAmount = itemCollection(UUID).getInteger("QTY");


    public static int getStock (String UUID1){
        Document result = (Document) itemCollection.find(Filters.eq("UUID", UUID1)).first();
    }
     */
    public static MongoCollection<org.bson.Document> databaseConn(String table) {
        Logger.getLogger("").setLevel(Level.WARNING);
        String uri = "mongodb+srv://Kristoffer:123456789A@testerinvoice.t8c16zx.mongodb.net/test";
        MongoClient mongoClient = MongoClients.create(uri);
        MongoDatabase database = mongoClient.getDatabase("StockDB");
        MongoCollection<Document> collection = database.getCollection(table);
        return collection;
    }
    public static Document queryStockDB(MongoCollection<Document> conn, String UUID){

        org.bson.Document query = new org.bson.Document("UUID", UUID);
        org.bson.Document results = conn.find(query).first();

        return results;
    }
    public static int getStockValue(String UUID) {
        int qtyAmount = queryStockDB(databaseConn("Item"), UUID).getInteger("QTY");
        return qtyAmount;
    }


    public static void sendOrderOMS(MockShopObject mockShopObject){

        // Code to run all the code in OMS that requires
        //--


        // Code to place in database
        OrderManager.sendOrder(mockShopObject);



        int listLength = 0;

        // Code to update the ui in OrderGUI
        List<Document> listDocument=DBManager.getRecentObjects("OrderHistory");
        for (Document inst: listDocument) {
            listLength +=1;

            HelloController.idList.add(inst.getInteger("_id"));
            HelloController.statusList.add(false);

            // Timer fuck thing
            Timer timer = new Timer();
            int finalListLength = listLength;
            timer.schedule(new TimerTask() {
                public void run() {
                    // Code to execute after a delay
                    HelloController.statusList.remove(finalListLength -1);
                    HelloController.statusList.add(true);

                }
            }, 500);
        }

        // Maybe method




        // Maybe
        // Code to update the orderConfirmation


        // Code to generate the orderConfirmation




    }


}
