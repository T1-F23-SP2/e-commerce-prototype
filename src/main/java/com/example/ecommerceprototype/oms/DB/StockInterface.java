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
    List<MockShopObject> newOrderList = new LinkedList<>();

    // method to retrieve Quantity of an item from the database Item
    static int getStockValue(String UUID) {
        int qtyAmount = DBManager.queryDB(DBManager.databaseConn("Item"), UUID).getInteger("QTY");
        return qtyAmount;
    }

    // method to add order to OrderGUI list
    static void sendOrderOMSNew(MockShopObject mockShopObject) {
        //retrieving the uuid and amount from a shop object and storing it in UUIDString
        String UUIDString = String.join(", ", mockShopObject.getMap().keySet());


        // Gets the highest _id in the database
        // Increments the id by one
        int highestId = DBManager.getHighestId();



        int id = highestId + 1;

        // Adds the mockShopObject to a list called newOrderList.
        newOrderList.add(mockShopObject);


        // Lambda function??????
//        Platform.runLater(()->{
            // Code to clear the currently shown list on the UI in OrderGUI
            OrderGUIControllerOMS.idList.clear();
            OrderGUIControllerOMS.statusList.clear();
            OrderGUIControllerOMS.UUIDList.clear();


            // Code to update the list shown on the UI in OrderGUI
            for (int i = 0; i < newOrderList.size(); i++) {
                OrderGUIControllerOMS.idList.add(id + i);
                OrderGUIControllerOMS.statusList.add("Not processed");
                OrderGUIControllerOMS.UUIDList.add(String.join(", ", newOrderList.get(i).getMap().keySet()));

            }

            // Retrieves all the id's from the database 'orderHistory',
            // and saves them in the Arraylist "dbIdList"
            MongoCollection<Document> collectionConn = DBManager.databaseConn("OrderHistory");
            ArrayList<Integer> dbIdList = DBManager.queryDBAllId(collectionConn);


            // Updates the rest of the lists.
            for (int i = 0; i < dbIdList.size(); i++) {
                OrderGUIControllerOMS.idList.add(dbIdList.get(i));
                OrderGUIControllerOMS.statusList.add("Processed");

                OrderGUIControllerOMS.UUIDList.add(String.join(", ", DBManager.getUUIDInfo(dbIdList.get(i), "UUID")));

            }

//        });


    }


}
