package DB;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.logging.Level;
import java.util.logging.Logger;

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


}
