package com.example.ecommerceprototype.oms.DB;

import com.example.ecommerceprototype.oms.MockShop.MockShopObject;
import com.example.ecommerceprototype.oms.OrderGUIControllerOMS;
import com.example.ecommerceprototype.oms.OrderStatus.OrderManager;
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
    public static Document queryStockDB(MongoCollection<Document> conn, String UUID){

        org.bson.Document query = new org.bson.Document("UUID", UUID);
        org.bson.Document results = conn.find(query).first();

        return results;
    }
    public static int getStockValue(String UUID) {
        int qtyAmount = queryStockDB(DBManager.databaseConn("Item"), UUID).getInteger("QTY");
        return qtyAmount;
    }



    // New sendOrderOMS
    public static void sendOrderOMSNew(MockShopObject mockShopObject) {
        String UUIDString = String.join(", ", mockShopObject.getMap().keySet());

        // Code to add id and false to the list
        MongoCollection<Document> collection = DBManager.databaseConn("OrderHistory");
        Document document = collection.find().sort(descending("_id")).first();
        int highestId = (document == null) ? 0 : document.getInteger("_id");
        int id = highestId + 1;

        // Code to update the UI in OrderGUI

        OrderGUIControllerOMS.idList.add(id);
        OrderGUIControllerOMS.statusList.add("Not processed");
        OrderGUIControllerOMS.UUIDList.add(UUIDString);

        // Start a new thread to process the order
        Thread processingThread = new Thread(() -> {
            // Code to process the order
            OrderManager.sendOrder(mockShopObject);

            // Code to update the status in the UI
            int index = OrderGUIControllerOMS.idList.indexOf(id);
            OrderGUIControllerOMS.statusList.set(index, "Processed");

            // Code to generate order confirmation if no more orders are being processed
            if (!isAnyOrderProcessing()) {
//                generateOrderConfirmation();
            }
        });

        processingThread.start();
    }

    // Check if any order is currently being processed
    private static boolean isAnyOrderProcessing() {
        for (String status : OrderGUIControllerOMS.statusList) {
            if (status.equals("Not processed")) {
                return true;
            }
        }
        return false;
    }





    public static void sendOrderOMS(MockShopObject mockShopObject){

        boolean orderBool = false;
        // SendOrderOMS boolean, set true if order received, list of orders, add list and set bool (false)
        // receive order(bool=true), end of loop (set bool = false),

        //UpdateStock(method in DBManager) needs to be called first (if (xxxx))

        String UUIDString = String.join(", ", mockShopObject.getMap().keySet());


        // Code to add id and false to the list
        MongoCollection<Document> collection = DBManager.databaseConn("OrderHistory");
        Document document = collection.find().sort(descending("_id")).first();
        int highestId = (document == null) ? 0 : document.getInteger("_id");

        int id = highestId+1;


        // Code to update the ui in OrderGUI
        // Add to list
//        OrderGUIControllerOMS.orderMap.put(id, false);
        OrderGUIControllerOMS.idList.clear();
        OrderGUIControllerOMS.statusList.clear();
        OrderGUIControllerOMS.UUIDList.clear();
        OrderGUIControllerOMS.idList.add(id);
        OrderGUIControllerOMS.statusList.add("Not processed");
        OrderGUIControllerOMS.UUIDList.add(UUIDString);


        MongoCollection<Document> collectionConn = DBManager.databaseConn("OrderHistory");

        ArrayList<Integer> dbIdList = DBManager.queryDBAllId(collectionConn);


        // Code to make the rest of the list, from the database.
        for (int i = 0; i < dbIdList.size(); i++) {
            OrderGUIControllerOMS.idList.add(dbIdList.get(i));
            OrderGUIControllerOMS.statusList.add("Processed");

            // Placeholder just displays UUID from inst 1
            OrderGUIControllerOMS.UUIDList.add(String.join(", ", mockShopObject.getMap().keySet()));


////             Fix this code to display the correct UUID by _id from the database
//            Document documentObj = DBManager.queryDBFlex(collectionConn, "_id", String.valueOf(dbIdList.get(i)));
//            // Find the document that matches the query
////            Document result = collectionConn.find(documentObj).first();
//
//            String UUIDString2 = documentObj.getString("UUID");
//
//            OrderGUIControllerOMS.UUIDList.add(UUIDString2);
        }





        // Code to place in database Code to process
        OrderManager.sendOrder(mockShopObject);






        //-------------------------------------------------------------------
        // Code to update the ui in OrderGUI Code to update the false to true
//        OrderGUIControllerOMS.orderMap.put(id, true);




        // Maybe
        // Code to update the orderConfirmation


        // Code to generate the orderConfirmation




    }


}