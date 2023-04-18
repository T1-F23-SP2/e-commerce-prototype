package DB;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Collation;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import javax.print.Doc;
import java.awt.*;
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

        org.bson.Document query = new org.bson.Document(search, new org.bson.Document("$gt", 0));
//        List<org.bson.Document> results = conn.find(query).into(new ArrayList<>());
        org.bson.Document results = conn.find(query).first();

        return results;
    }







}
