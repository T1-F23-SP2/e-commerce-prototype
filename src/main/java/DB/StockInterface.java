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

import static com.mongodb.client.model.Indexes.descending;

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
        int listLength = 0;

        // Code to run all the code in OMS that requires
        //--

        // Code to add id and false to the list
        MongoCollection<Document> collection = DBManager.databaseConn("OrderHistory");
        Document document = collection.find().sort(descending("_id")).first();
        int highestId = (document == null) ? 0 : document.getInteger("_id");

        int id = highestId+1;


        // Code to update the ui in OrderGUI
        HelloController.orderMap.put(id, false);


//        HelloController.idList.toArray() = HelloController.orderMap.keySet().toArray();




//        List<Document> listDocument=DBManager.getRecentObjects("OrderHistory");
//        for (Document inst: listDocument) {
//            listLength +=1;
//
//            HelloController.idList.add(inst.getInteger("_id"));
//            HelloController.statusList.add(false);
//        }


        // Code to place in database Code to process
        OrderManager.sendOrder(mockShopObject);



//        int listLength = 0;



        //-------------------------------------------------------------------
        // Code to update the ui in OrderGUI Code to update the false to true
        HelloController.orderMap.put(id, true);


//        HelloController.statusList.removeAll();
//        for (int i = 0; i < listLength; i++) {
//
//            HelloController.statusList.add(true);
//        }


//        List<Document> listDocument=DBManager.getRecentObjects("OrderHistory");
//        for (Document inst: listDocument) {
//            listLength +=1;

//            HelloController.idList.add(inst.getInteger("_id"));
//            HelloController.statusList.add(true);


//            // Timer fuck thing
//            Timer timer = new Timer();
//            int finalListLength = listLength;
//            timer.schedule(new TimerTask() {
//                public void run() {
//                    // Code to execute after a delay
//                    HelloController.statusList.remove(finalListLength -1);
//                    HelloController.statusList.add(true);
//
//                }
//            }, 500);
//        }

        // Maybe method




        // Maybe
        // Code to update the orderConfirmation


        // Code to generate the orderConfirmation




    }


}
