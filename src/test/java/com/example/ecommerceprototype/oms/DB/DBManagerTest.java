package com.example.ecommerceprototype.oms.DB;

import com.example.ecommerceprototype.oms.MockShop.MockShopObject;
import com.example.ecommerceprototype.oms.MockShop.PlaceholderInstShop;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Indexes.descending;
import static org.junit.jupiter.api.Assertions.*;

class DBManagerTest {


    private static MongoCollection<Document> collection;

    MongoDatabase database;
//    MongoClient client;


    @BeforeEach
    void setUp() {
        collection = DBManager.databaseConn("TestTable");
        Document stock1 = new Document("id", "1");
        Document stock2 = new Document("UUID", "12345");
        collection.insertMany(List.of(stock1, stock2));

    }

    @AfterEach
    void tearDown() {

        collection = DBManager.databaseConn("TestTable");
        collection.deleteMany(new Document());

    }

    @Test
    void databaseConnOrderHistory() {

        // Done
        MongoCollection<Document> collection1 = DBManager.databaseConn("OrderHistory");
        assertNotNull(collection1);
        assertEquals("OrderHistory", collection1.getNamespace().getCollectionName());

    }

    @Test
    void databaseConnItems() {

        // Done
        collection = DBManager.databaseConn("Item");
        assertNotNull(collection);
        assertEquals("Item", collection.getNamespace().getCollectionName());

    }

    @Test
    void queryDB() {
        // Done

        assertNotNull(DBManager.queryDB(collection, "12345"));

    }

    @Test
    void queryDBFlex() {
        // Done
        assertNotNull(DBManager.queryDBFlex(collection, "UUID", "12345"));


    }

    @Test
    void queryDBAllId() {

        // Done
        // Tests whether the
        assertNotNull(DBManager.queryDBAllId(DBManager.databaseConn("OrderHistory")));


    }

    @Test
    void updateStock() {
        // Done
        collection = DBManager.databaseConn("Item");
        Document testx = new Document("UUID", "12345").append("QTY", 10);
        collection.insertOne(testx);

        HashMap<String, Integer> map = new HashMap<>();
        map.put("12345", 5);
        MockShopObject mockShopObject = new MockShopObject(map, PlaceholderInstShop.getCustomer());
        assertEquals(DBManager.UpdateStock(mockShopObject), true);
        Document testy = new Document("UUID", "12345").append("QTY", 5);
        collection.findOneAndDelete(testy);


    }

    @Test
    void decrementFieldByUUID() {
        // Done
        collection = DBManager.databaseConn("Item");
        Document testx = new Document("UUID", "12345").append("QTY", 10);
        collection.insertOne(testx);

        DBManager.decrementFieldByUUID("12345", 5);
        assertEquals(DBManager.queryDB(DBManager.databaseConn("Item"), "12345").getInteger("QTY"), 5);

        Document testy = new Document("UUID", "12345").append("QTY", 5);
        collection.findOneAndDelete(testy);
    }

    @Test
    void getUUIDInfo() {


        assertNotNull(DBManager.getUUIDInfo(10, "Amount"));

    }

    @Test
    void getHighestId() {
        MongoCollection<Document> collection = DBManager.databaseConn("OrderHistory");

        Document TestOrdre = new Document("_id", 9999)
                .append("UUID", "96070253-78dc-4503-a0ac-f09e00d3b418")
                .append("Date", LocalDate.now())
                .append("Amount", "[1, 2]");

        collection.insertOne(TestOrdre);

        int highestId = DBManager.getHighestId();

        assertEquals(highestId, 9999);

        // Delete the "TestOrdre" document
        Bson filter = eq("_id", 9999);
        collection.deleteOne(filter);
    }


}