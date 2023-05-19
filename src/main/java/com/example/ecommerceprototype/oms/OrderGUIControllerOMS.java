package com.example.ecommerceprototype.oms;

import com.example.ecommerceprototype.oms.DB.DBManager;
import com.example.ecommerceprototype.oms.MockShop.MockShopObject;
import com.example.ecommerceprototype.oms.MockShop.PlaceholderInstShop;
import com.example.ecommerceprototype.oms.OrderStatus.OrderManager;
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

public class OrderGUIControllerOMS {
    @FXML
    private ListView<Integer> ordersListId;

    @FXML
    private ListView<String> ordersListStatus;

    @FXML
    private ListView<String> ordersListUUID;

    @FXML
    private Button addOrderMock;


    public static ObservableList<Integer> idList = FXCollections.observableArrayList(1, 2, 3);

    public static ObservableList<String> statusList = FXCollections.observableArrayList("Processed", "Processed", "Processed");

    public static ObservableList<String> UUIDList = FXCollections.observableArrayList("UI1", "UI2", "UI3");



    public static HashMap orderMap = new HashMap<Integer, Boolean>();


    @FXML
    private Button updateButton;


    // Mock object from SHOP
    MockShopObject mockShopObject = PlaceholderInstShop.getInstShop1();
    String UUIDString = String.join(", ", mockShopObject.getMap().keySet());

    @FXML
    void bottomCheckDB(ActionEvent event) {


        idList.clear();
        statusList.clear();
        UUIDList.clear();


        idList.addAll(DBManager.queryDBAllId(DBManager.databaseConn("OrderHistory")));
        MongoCollection<Document> collectionConn = DBManager.databaseConn("OrderHistory");


        for (int i = 0; i < idList.size(); i++) {
            statusList.add("Processed");
//Todo: Fix this?
            // Get the object from com.example.ecommerceprototype.OMS.DB with specific id.
//            Document documentObj = DBManager.queryDBFlex(collectionConn, "_id", idList.get(i).toString());
//            String UUIDString = documentObj.getString("UUID");
            OrderGUIControllerOMS.UUIDList.add(UUIDString);




        }

    }

    @FXML
    void addOrderMock(ActionEvent event) {

        MongoCollection<Document> collection = DBManager.databaseConn("OrderHistory");
        Document document = collection.find().sort(descending("_id")).first();
        int highestId = (document == null) ? 0 : document.getInteger("_id");

        int id = highestId+1;


        // Code to update the ui in OrderGUI
        // Add to list
//        OrderGUIControllerOMS.orderMap.put(id, false);
        OrderGUIControllerOMS.idList.clear();
        OrderGUIControllerOMS.statusList.clear();
        OrderGUIControllerOMS.UUIDList.clear();
        OrderGUIControllerOMS.idList.add(id);
        OrderGUIControllerOMS.statusList.add("Not processed");
        OrderGUIControllerOMS.UUIDList.add(UUIDString);

        ArrayList<Integer> dbIdList = DBManager.queryDBAllId(DBManager.databaseConn("OrderHistory"));
        // Collection to database made
        MongoCollection<Document> collectionConn = DBManager.databaseConn("OrderHistory");

        for (int i = 0; i < dbIdList.size(); i++) {
            OrderGUIControllerOMS.idList.add(dbIdList.get(i));
            OrderGUIControllerOMS.statusList.add("Processed");
            //Todo: fix this?

            // Code to get information out of com.example.ecommerceprototype.OMS.DB
//            Document documentObj = DBManager.queryDBFlex(collectionConn, "_id", String.valueOf(idList.get(i)));
//            OrderGUIControllerOMS.UUIDList.add(documentObj.getString("UUID"));

            OrderGUIControllerOMS.UUIDList.add(UUIDString);

        }

        // Code to place in database Code to process
        OrderManager.sendOrder(mockShopObject);
    }

    public void initialize() {
        ordersListId.setItems(idList);
        ordersListStatus.setItems(statusList);
        ordersListUUID.setItems(UUIDList);
    }

}