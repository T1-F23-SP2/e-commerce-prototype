package com.example.ecommerceprototype.oms;

import com.example.ecommerceprototype.oms.DB.DBManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

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
        ArrayList<Integer> idList = DBManager.queryDBAllId(DBManager.databaseConn("OrderHistory"));

    }





}