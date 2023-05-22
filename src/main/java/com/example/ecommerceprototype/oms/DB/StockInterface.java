package com.example.ecommerceprototype.oms.DB;

import com.example.ecommerceprototype.oms.MockShop.MockShopObject;
import com.example.ecommerceprototype.oms.OrderGUIControllerOMS;
import com.example.ecommerceprototype.oms.OrderStatus.OrderManager;
import com.example.ecommerceprototype.oms.Visuals.OrderConfirmationGenerator;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.application.Platform;
import org.bson.Document;

import java.io.File;
import java.sql.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
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



    static ArrayList<MockShopObject> orderList = new ArrayList<>();
    static List<MockShopObject> newOrderList = new LinkedList<>();
    //MockShopObject[] newOrderList = {};

    // MCGPT

    // TODO: Missing for loop check sendOrderOMS method
    public static void sendOrderOMSNew(MockShopObject mockShopObject) {
        String UUIDString = String.join(", ", mockShopObject.getMap().keySet());

        // Code to add id and false to the list
        MongoCollection<Document> collection = DBManager.databaseConn("OrderHistory");
        Document document = collection.find().sort(descending("_id")).first();
        int highestId = (document == null) ? 0 : document.getInteger("_id");

        int id = highestId+1;

        if(newOrderList.isEmpty()){
            //id +=1;
        }else {
            //id = highestId + 1;
            for (int i = 0; i < newOrderList.size(); i++) {
                System.out.println(newOrderList.size());

                //id += i;
                //System.out.println("Yes "+id);
            }

        }


        newOrderList.add(mockShopObject);


        // Code to update the UI in OrderGUI


            OrderGUIControllerOMS.idList.clear();
            OrderGUIControllerOMS.statusList.clear();
            OrderGUIControllerOMS.UUIDList.clear();


            for (int i = 0; i < newOrderList.size(); i++) {
                OrderGUIControllerOMS.idList.add(id+i);
                OrderGUIControllerOMS.statusList.add("Not processed");
                OrderGUIControllerOMS.UUIDList.add(String.join(", ", newOrderList.get(i).getMap().keySet()));

            }
            MongoCollection<Document> collectionConn = DBManager.databaseConn("OrderHistory");

            ArrayList<Integer> dbIdList = DBManager.queryDBAllId(collectionConn);


            //System.out.println("Hej");
            // Code to make the rest of the list, from the database.
            for (int i = 0; i < dbIdList.size(); i++) {
                OrderGUIControllerOMS.idList.add(dbIdList.get(i));
                OrderGUIControllerOMS.statusList.add("Processed");

                // Placeholder just displays UUID from inst 1
                OrderGUIControllerOMS.UUIDList.add(String.join(", ", DBManager.getUUIDInfo(dbIdList.get(i), "UUID")));

////             Fix this code to display the correct UUID by _id from the database
                //       Document documentObj = DBManager.queryDBFlex(collectionConn, "_id", String.valueOf(dbIdList.get(i)));
                // Find the document that matches the query
////            Document result = collectionConn.find(documentObj).first();
//
//            String UUIDString2 = documentObj.getString("UUID");
//
//            OrderGUIControllerOMS.UUIDList.add(UUIDString2);
            }







        /*
        // Start a new thread to process the order
        Thread processingThread = new Thread(() -> {
            // Code to process the order
            //OrderManager.sendOrder(mockShopObject);


            // Code to update the status in the UI
            Platform.runLater(() -> {
                //OrderGUIControllerOMS.statusList.set(index, "Not Processed");


                // From here
                MongoCollection<Document> collectionConn = DBManager.databaseConn("OrderHistory");

                ArrayList<Integer> dbIdList = DBManager.queryDBAllId(collectionConn);


                System.out.println("Hej");
                // Code to make the rest of the list, from the database.
                for (int i = 0; i < dbIdList.size()-1; i++) {
                    OrderGUIControllerOMS.idList.add(dbIdList.get(i));
                    OrderGUIControllerOMS.statusList.add("Processed");

                    // Placeholder just displays UUID from inst 1
                    OrderGUIControllerOMS.UUIDList.add(String.join(", ", mockShopObject.getMap().keySet()));

////             Fix this code to display the correct UUID by _id from the database
    //       Document documentObj = DBManager.queryDBFlex(collectionConn, "_id", String.valueOf(dbIdList.get(i)));
           // Find the document that matches the query
////            Document result = collectionConn.find(documentObj).first();
//
//            String UUIDString2 = documentObj.getString("UUID");
//
//            OrderGUIControllerOMS.UUIDList.add(UUIDString2);
                }
            });


            Platform.runLater(() -> {
                ThreadClass waitThread = new ThreadClass();
                waitThread.start();

                try {
                    waitThread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                try {
                    waitThread.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                int index = OrderGUIControllerOMS.idList.indexOf(id);
                OrderGUIControllerOMS.statusList.set(index, "Processed");
            });




            // TODO: Code to add to orderlist
            // Code to add to orderList
            orderList.add(mockShopObject);
            //orderList = new ArrayList<>();

            // Code to generate order confirmation if no more orders are being processed
            if (!isAnyOrderProcessing()) {
                for (MockShopObject element : orderList) {
                    Platform.runLater(() -> {
                        //OrderConfirmationGenerator.generateOCPDF();
                        OrderConfirmationGenerator.fileFormatter();
                        OrderConfirmationGenerator.generateOCPDF(new File("assets/oms/out/Order_confirmation #" + OrderConfirmationGenerator.getOrderConfirmationNumber() + 1 + ".pdf"), mockShopObject);
                    });
                }
                // generateOrderConfirmation();
            }
        });*/

        //processingThread.start();



        // Thread OcThread = new Thread(() -> {
        // });
        // processingThread.start();
    }

    /*
    // New sendOrderOMS
    public static void sendOrderOMSNew(MockShopObject mockShopObject) {
        String UUIDString = String.join(", ", mockShopObject.getMap().keySet());

        // Code to add id and false to the list
        MongoCollection<Document> collection = DBManager.databaseConn("OrderHistory");
        Document document = collection.find().sort(descending("_id")).first();
        int highestId = (document == null) ? 0 : document.getInteger("_id");
        int id = highestId + 1;

        // Code to update the UI in OrderGUI

        OrderGUIControllerOMS.idList.clear();
        OrderGUIControllerOMS.statusList.clear();
        OrderGUIControllerOMS.UUIDList.clear();
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

            // TODO: Code to add to orderlist
            // Code to add to orderList
            orderList.add(mockShopObject);
            //orderList = new ArrayList<>();



            // Code to generate order confirmation if no more orders are being processed
            if (!isAnyOrderProcessing()) {
                for (MockShopObject element : orderList) {
                    //OrderConfirmationGenerator.generateOCPDF();
                    OrderConfirmationGenerator.generateOCPDF(new File("assets/oms/out/Order_confirmation #" + OrderConfirmationGenerator.getOrderConfirmationNumber() + ".pdf"), mockShopObject);


                }
//                generateOrderConfirmation();
            }

        });


        processingThread.start();

        try {
            processingThread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }



        //Thread OcThread = new Thread(() -> {


        //});

        //processingThread.start();
    }*/

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
