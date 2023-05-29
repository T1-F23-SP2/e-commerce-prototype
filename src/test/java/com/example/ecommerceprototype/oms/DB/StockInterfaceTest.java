package com.example.ecommerceprototype.oms.DB;

import com.mongodb.client.MongoClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.junit.jupiter.api.Test;

import javax.print.Doc;

import java.nio.channels.DatagramChannel;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.*;

class StockInterfaceTest {

    MongoCollection<Document> collection;

    @BeforeEach
    void setUp() {
        collection = DBManager.databaseConn("TestTable");

        //Inserting test data into test collection in database
        Document testDoc1 = new Document("UUID", "12345").append("QTY", "10");
        Document testDoc2 = new Document("UUID", "54321").append("QTY", "20");
        collection.insertMany(List.of(testDoc1, testDoc2));
    }

    @AfterEach
    void tearDown() {
        collection = DBManager.databaseConn("TestTable");
    collection.deleteMany(new Document());



    }

    @Test
    void queryStockDB() {
    assertNotNull(DBManager.queryDB(DBManager.databaseConn("TestTable"), "12345"));
    }

    @Test
    void getStockValue() {
        collection = DBManager.databaseConn("Item");
        Document testDocX = new Document("UUID", "12345").append("QTY", 10);
        collection.insertOne(testDocX);
        assertEquals(StockInterface.getStockValue("12345"), 10);
        collection.findOneAndDelete(testDocX);
    }

    @Test
    void sendOrderOMSNew() {
        ObservableList<Integer> firstList = FXCollections.observableArrayList(1);
        ObservableList<Integer> secondList = FXCollections.observableArrayList(1);

        OrderGUIControllerOMS.idList.clear();
        OrderGUIControllerOMS.idList.addAll(DBManager.queryDBAllId(DBManager.databaseConn("OrderHistory")));

        firstList.addAll(OrderGUIControllerOMS.idList);
        StockInterface.sendOrderOMSNew(PlaceholderInstShop.getInstShop1());

        secondList.addAll(OrderGUIControllerOMS.idList);

        assertNotEquals(firstList, secondList);

    }

}