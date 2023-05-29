package com.example.ecommerceprototype.oms;

import com.example.ecommerceprototype.oms.DB.DBManager;
import com.example.ecommerceprototype.oms.DB.StockInterface;
import com.example.ecommerceprototype.oms.MockShop.PlaceholderInstShop;
import org.bson.Document;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderGUIControllerOMSTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void bottomCheckDB() {
    }

    @Test
    void addOrderMock() {
    }

    @Test
    void initialize() {
    }

    @Test
    void updateMethod() {


        StockInterface.newOrderList.add(PlaceholderInstShop.getInstShop1());

        OrderGUIControllerOMS orderGUIControllerOMS = new OrderGUIControllerOMS();

        orderGUIControllerOMS.updateMethod();

        assertTrue(StockInterface.newOrderList.isEmpty());


        var collection = DBManager.databaseConn("OrderHistory");

        collection.deleteOne(new Document("_id", DBManager.getHighestId()));





    }

    @Test
    void testInitialize() {

    }
}