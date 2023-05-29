package com.example.ecommerceprototype.oms.OrderStatus;

import com.example.ecommerceprototype.oms.ComputedOverviews.SalesReport;
import com.example.ecommerceprototype.oms.DB.DBManager;
import com.example.ecommerceprototype.oms.MockShop.PlaceholderInstShop;
import com.example.ecommerceprototype.oms.mockPIM.PlaceHolderInstGet;
import org.bson.Document;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class OrderManagerTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void sendOrder() {

        var collection = DBManager.databaseConn("OrderHistory");

        int firstHighest = DBManager.getHighestId();
        OrderManager.sendOrder(PlaceholderInstShop.getInstShop1());
        int secondHighest = DBManager.getHighestId();

        assertNotEquals(firstHighest, secondHighest);

        collection.deleteOne(new Document("_id", secondHighest));


    }

}