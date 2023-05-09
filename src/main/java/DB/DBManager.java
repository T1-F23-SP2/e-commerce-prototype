package DB;

import MockShop.MockShopObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBManager {




    public static MongoCollection<org.bson.Document> databaseConn(String table){
        Logger.getLogger("").setLevel(Level.WARNING);
        String uri = "mongodb+srv://Kristoffer:123456789A@testerinvoice.t8c16zx.mongodb.net/test";
        MongoClient mongoClient = MongoClients.create(uri);
        MongoDatabase database = mongoClient.getDatabase("StockDB");
        MongoCollection<Document> collection = database.getCollection(table);
        return collection;
    }


    // This matches something and finds the match
//    public static String queryDB(MongoCollection<Document> conn, String search, String searchValue){
//
//        org.bson.Document query = new org.bson.Document(search, new org.bson.Document("$gt", 0));
////        List<org.bson.Document> results = conn.find(query).into(new ArrayList<>());
//        Document results = (Document) conn.find(Filters.eq(search, searchValue)).first();
//
//        return results.toJson();
//    }

    public static Document queryDB(MongoCollection<Document> conn, String search){

        org.bson.Document query = new org.bson.Document("UUID", search);
//        List<org.bson.Document> results = conn.find(query).into(new ArrayList<>());
        org.bson.Document results = conn.find(query).first();

        return results;
    }


    // New method for query
    public static Document queryDBFlex(MongoCollection<Document> conn, String searchTitle, String search){

        org.bson.Document query = new org.bson.Document(searchTitle, search);
//        List<org.bson.Document> results = conn.find(query).into(new ArrayList<>());
        org.bson.Document results = conn.find(query).first();

        return results;
    }


    // Method to take everything that is max one day ago
    public static List<Document> getRecentObjects(String database) {

        MongoCollection<Document> collection = databaseConn(database);

        LocalDate oneDayAgo = LocalDate.now().minusDays(1);
        Document query = new Document("Date", new Document("$gte", oneDayAgo));
        List<Document> recentObjects = collection.find(query).into(new ArrayList<>());
        return recentObjects;
    }



    public static boolean updateStock(MockShopObject mockShopObject) {
        MongoCollection<org.bson.Document> documentMongoCollection;
        documentMongoCollection = databaseConn("Item");

        // Checks for every key if amount to that key is higher than qty in the database
        // If it is higher it stops the method by returning false
        for (String key : mockShopObject.getMap().keySet()) {
            int qtyAmount = queryDB(documentMongoCollection,key).getInteger("QTY");
            if(!(qtyAmount >= mockShopObject.getMap().get(key))) {
                return false;
            }
        }

        // Checks the database if the keys amounts is lower than in the database
        // then decrements the database by the amount
        for (String key : mockShopObject.getMap().keySet()) {
            int qtyAmount = queryDB(documentMongoCollection,key).getInteger("QTY");
            if(qtyAmount >= mockShopObject.getMap().get(key)) {
                decrementFieldByUUID(key, mockShopObject.getMap().get(key));
            }

        }

        return true;

    }


    public static void decrementFieldByUUID(String uuid, int amount) {

        MongoCollection<Document> collection = databaseConn("Item");

        // Create a query that finds the document with the specified UUID
//        Document query = new Document("_id", id);
        Document query = queryDB(collection, uuid);
        // Create an update that decrements the "fieldToDecrement" field by 1
        Document update = new Document("$inc", new Document("QTY", -amount));

        // Update the document with the specified ID
        collection.updateOne(query, update);

        // Close the MongoDB connection
//        collection.close();
    }


    //public static Document dbForRealConnection() {
       // connection = DriverManager.getConnection("")
       // PreparedStatement insertStatement =
    // databaseConn("Item").up
    //}
}
