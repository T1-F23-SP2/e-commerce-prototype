package com.example.ecommerceprototype.oms.DB;

import com.example.ecommerceprototype.oms.MockShop.MockShopObject;
import com.example.ecommerceprototype.oms.OrderStatus.OrderManager;
import com.example.ecommerceprototype.oms.HelloController;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
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

        String UUIDString = String.join(", ", mockShopObject.getMap().keySet());


        // Code to add id and false to the list
        MongoCollection<Document> collection = DBManager.databaseConn("OrderHistory");
        Document document = collection.find().sort(descending("_id")).first();
        int highestId = (document == null) ? 0 : document.getInteger("_id");

        int id = highestId+1;


        // Code to update the ui in OrderGUI
        // Add to list
//        HelloController.orderMap.put(id, false);
        HelloController.idList.clear();
        HelloController.statusList.clear();
        HelloController.UUIDList.clear();
        HelloController.idList.add(id);
        HelloController.statusList.add("Not processed");
        HelloController.UUIDList.add(UUIDString);


        MongoCollection<Document> collectionConn = DBManager.databaseConn("OrderHistory");

        ArrayList<Integer> dbIdList = DBManager.queryDBAllId(collectionConn);


        // Code to make the rest of the list, from the database.
        for (int i = 0; i < dbIdList.size(); i++) {
            HelloController.idList.add(dbIdList.get(i));
            HelloController.statusList.add("Processed");

            // Placeholder just displays UUID from inst 1
            HelloController.UUIDList.add(String.join(", ", mockShopObject.getMap().keySet()));


////             Fix this code to display the correct UUID by _id from the database
//            Document documentObj = DBManager.queryDBFlex(collectionConn, "_id", String.valueOf(dbIdList.get(i)));
//            // Find the document that matches the query
////            Document result = collectionConn.find(documentObj).first();
//
//            String UUIDString2 = documentObj.getString("UUID");
//
//            HelloController.UUIDList.add(UUIDString2);
        }





        // Code to place in database Code to process
        OrderManager.sendOrder(mockShopObject);






        //-------------------------------------------------------------------
        // Code to update the ui in OrderGUI Code to update the false to true
//        HelloController.orderMap.put(id, true);




        // Maybe
        // Code to update the orderConfirmation


        // Code to generate the orderConfirmation




    }


}
