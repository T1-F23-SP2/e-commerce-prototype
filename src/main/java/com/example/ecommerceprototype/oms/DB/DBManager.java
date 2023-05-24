package com.example.ecommerceprototype.oms.DB;

import com.example.ecommerceprototype.oms.MockShop.MockShopObject;
import com.mongodb.client.*;
import org.bson.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.mongodb.client.model.Indexes.descending;

public class DBManager {


    public static MongoCollection<org.bson.Document> databaseConn(String table) {
        Logger.getLogger("").setLevel(Level.WARNING);
        String uri = "mongodb+srv://Kristoffer:123456789A@testerinvoice.t8c16zx.mongodb.net/test";
        MongoClient mongoClient = MongoClients.create(uri);
        MongoDatabase database = mongoClient.getDatabase("StockDB");
        MongoCollection<Document> collection = database.getCollection(table);
        return collection;
    }


    public static Document queryDB(MongoCollection<Document> conn, String search) {

        org.bson.Document query = new org.bson.Document("UUID", search);
        org.bson.Document results = conn.find(query).first();

        return results;
    }


    // New method for query
    // This is more flexible
    public static Document queryDBFlex(MongoCollection<Document> conn, String searchTitle, String search) {

        org.bson.Document query = new org.bson.Document(searchTitle, search);
        org.bson.Document results = conn.find(query).first();

        return results;
    }


    public static ArrayList<Integer> queryDBAllId(MongoCollection<Document> conn) {

        var filter = new Document();

        // Define a projection to only retrieve the _id field of each document
        var projection = new Document("_id", 1);

        // Execute the query and retrieve a cursor over the resulting documents
        var cursor = conn.find(filter).projection(projection).iterator();

        // Iterate over the cursor and add each document ID to a list
        var documentIds = new ArrayList<Integer>();
        while (cursor.hasNext()) {
            var document = cursor.next();
            documentIds.add(document.getInteger("_id"));
        }

        return documentIds;
    }

    public static boolean UpdateStock(MockShopObject mockShopObject) {
        MongoCollection<org.bson.Document> documentMongoCollection;
        documentMongoCollection = databaseConn("Item");

        // Checks for every key if amount to that key is higher than qty in the database
        // If it is higher it stops the method by returning false
        for (String key : mockShopObject.getMap().keySet()) {
            int qtyAmount = queryDB(documentMongoCollection, key).getInteger("QTY");
            if (!(qtyAmount >= mockShopObject.getMap().get(key))) {
                return false;
            }
        }

        // Checks the database if the keys amounts is lower than in the database
        // then decrements the database by the amount
        for (String key : mockShopObject.getMap().keySet()) {
            int qtyAmount = queryDB(documentMongoCollection, key).getInteger("QTY");
            if (qtyAmount >= mockShopObject.getMap().get(key)) {
                decrementFieldByUUID(key, mockShopObject.getMap().get(key));
            }

        }

        return true;

    }


    public static Document queryStockDB(MongoCollection<Document> conn, String UUID){

        org.bson.Document query = new org.bson.Document("UUID", UUID);
        org.bson.Document results = conn.find(query).first();

        return results;
    }


    public static void decrementFieldByUUID(String uuid, int amount) {

        MongoCollection<Document> collection = databaseConn("Item");

        // Create a query that finds the document with the specified UUID
        Document query = queryDB(collection, uuid);
        // Create an update that decrements the "fieldToDecrement" field by 1
        Document update = new Document("$inc", new Document("QTY", -amount));

        // Update the document with the specified ID
        collection.updateOne(query, update);


    }

    public static int getHighestId (){
        MongoCollection<Document> collection = DBManager.databaseConn("OrderHistory");
        Document document = collection.find().sort(descending("_id")).first();
        int highestId = (document == null) ? 0 : document.getInteger("_id");
        return highestId;
    }


    public static String[] getUUIDInfo(int targetId, String whatSearch) {

        //Connect to database
        MongoCollection<Document> OrderHistoryCollection = databaseConn("OrderHistory");



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
