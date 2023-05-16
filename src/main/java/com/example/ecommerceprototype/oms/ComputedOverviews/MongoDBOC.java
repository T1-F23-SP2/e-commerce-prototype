package com.example.ecommerceprototype.oms.ComputedOverviews;

import com.mongodb.client.*;
import org.bson.Document;

import java.util.ArrayList;

public class MongoDBOC {
    public static String[] getUUIDInfo(int targetId, String whatSearch) {

        //Connect to database
        String uri = "mongodb+srv://Kristoffer:123456789A@testerinvoice.t8c16zx.mongodb.net/test";
        MongoClient mongoClient = MongoClients.create(uri);
        MongoDatabase database = mongoClient.getDatabase("StockDB");
        MongoCollection<Document> OrderHistoryCollection = database.getCollection("OrderHistory");

        //Retrieve data form orderHistory and put it in an arraylist
        ArrayList<Document> resultList = new ArrayList<>();

        MongoCursor<Document> cursor = OrderHistoryCollection.find().iterator();
        while (cursor.hasNext()) {
            Document document = cursor.next();
            resultList.add(document);
        }
        cursor.close();

        //Retrieving specific data from the arraylist
        Document targetDocument = null;

        for (Document document : resultList) {
            int documentId = document.getInteger("_id");

            if (documentId == (targetId)) {
                targetDocument = document;
                break;
            }
        }

        if (targetDocument != null) {
            // Do something with the target document
            String whatSearchValue = targetDocument.getString(whatSearch);// whatSearch = UUID eller Amount
            String[] whatSearchLines = whatSearchValue.split(",");
            return whatSearchLines;

        } else {
            System.out.println("Document not found");
        }
        return new String[0];
    }
}