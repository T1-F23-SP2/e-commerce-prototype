package com.example.testcopypastetest;

import DB.DBManager;
import MockShop.MockShopObject;
import MockShop.PlaceholderInstShop;
import OrderStatus.OrderManager;
import com.mongodb.client.MongoCollection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import org.bson.Document;

import java.util.ArrayList;
import java.util.HashMap;

import static com.mongodb.client.model.Indexes.descending;

public class HelloController {
    @FXML
    private ListView<Integer> ordersListId;

    @FXML
    private ListView<Boolean> ordersListStatus;

    @FXML
    private Button addOrderMock;


    public static ObservableList<Integer> idList = FXCollections.observableArrayList(1, 2, 3);

    public static ObservableList<Boolean> statusList = FXCollections.observableArrayList(false, false, false);


    public static HashMap orderMap = new HashMap<Integer, Boolean>();


    @FXML
    private Button updateButton;

    @FXML
    void bottomCheckDB(ActionEvent event) {

        idList.clear();
        statusList.clear();


        idList.addAll(DBManager.queryDBAllId(DBManager.databaseConn("OrderHistory")));

        for (int i = 0; i < idList.size(); i++) {
            statusList.add(Boolean.TRUE);
        }

//        initialize();


    }



    @FXML
    void addOrderMock(ActionEvent event) {

        MockShopObject mockShopObject = PlaceholderInstShop.getInstShop1();

        MongoCollection<Document> collection = DBManager.databaseConn("OrderHistory");
        Document document = collection.find().sort(descending("_id")).first();
        int highestId = (document == null) ? 0 : document.getInteger("_id");

        int id = highestId+1;


        // Code to update the ui in OrderGUI
        // Add to list
//        HelloController.orderMap.put(id, false);
        HelloController.idList.clear();
        HelloController.statusList.clear();
        HelloController.idList.add(id);
        HelloController.statusList.add(Boolean.FALSE);

        ArrayList<Integer> dbIdList = DBManager.queryDBAllId(DBManager.databaseConn("OrderHistory"));

        for (int i = 0; i < dbIdList.size(); i++) {
            HelloController.idList.add(dbIdList.get(i));
            HelloController.statusList.add(Boolean.TRUE);
        }





        // Code to place in database Code to process
        OrderManager.sendOrder(mockShopObject);
    }
    
    public void setLists(){

        idList.clear();
        statusList.clear();
//        idList.addAll(orderMap.keySet().stream().toArray());

        for (int i = 0; i < orderMap.size(); i++) {
            idList.add((Integer) orderMap.keySet().stream().toArray()[i]);

        }
        for (int i = 0; i < idList.size(); i++) {
            statusList.add((Boolean) orderMap.get(idList.get(i)));
        }

        initialize();
        
        
    }



//    public static



    public void initialize() {
        ordersListId.setItems(idList);
        ordersListStatus.setItems(statusList);
    }






    public static void updateTables(){
        idList.addAll(orderMap.keySet());

        statusList.removeAll(true);

        for (int i = 0; i < idList.size(); i++) {
            statusList.add((Boolean) orderMap.get(i));
        }


    }









}