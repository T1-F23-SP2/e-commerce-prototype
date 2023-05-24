package com.example.ecommerceprototype.oms.OrderStatus;

import com.example.ecommerceprototype.oms.DB.DBManager;
import com.example.ecommerceprototype.oms.MockShop.MockShopObject;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.time.LocalDate;
import java.util.Arrays;

import static com.mongodb.client.model.Indexes.descending;

public class OrderManager {

    public static void sendOrder(MockShopObject mockShopObject){

        // Get highest id out and plus id by one
        MongoCollection<Document> collection = DBManager.databaseConn("OrderHistory");
        Document document = collection.find().sort(descending("_id")).first();
        int highestId = (document == null) ? 0 : document.getInteger("_id");

        int id = highestId+1;


        // Code to convert
        String resultUUID = String.join(",", mockShopObject.getMap().keySet());


        // Code to convert the values(amount) to a list and comma seperate it to a string
        Integer[] amountArray = mockShopObject.getMap().values().toArray(new Integer[0]);
        String resultAmount = String.join(",", Arrays.toString(amountArray));


        MongoCollection<Document> connDB = DBManager.databaseConn("OrderHistory");
        connDB.insertOne(new Document().append("_id", id).append("UUID", resultUUID).append("Date", LocalDate.now()).append("Amount", resultAmount));
    }
   }
