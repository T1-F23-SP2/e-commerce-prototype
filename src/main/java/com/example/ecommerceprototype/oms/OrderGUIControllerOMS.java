package com.example.ecommerceprototype.oms;

import com.example.ecommerceprototype.oms.DB.DBManager;
import com.example.ecommerceprototype.oms.DB.StockInterface;
import com.example.ecommerceprototype.oms.MockShop.MockShopObject;
import com.example.ecommerceprototype.oms.MockShop.PlaceholderInstShop;
import com.example.ecommerceprototype.oms.OrderStatus.OrderManager;
import com.example.ecommerceprototype.oms.Visuals.OrderConfirmationGenerator;
import com.mongodb.client.MongoCollection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import org.bson.Document;

import java.io.File;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

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


    public void updateMethod(){
        idList.clear();
        statusList.clear();
        UUIDList.clear();

        for (int i = 0; i < StockInterface.newOrderList.size(); i++) {
            OrderManager.sendOrder(StockInterface.newOrderList.get(i));
        }


        idList.addAll(DBManager.queryDBAllId(DBManager.databaseConn("OrderHistory")));

        for (int i = 0; i < idList.size(); i++) {
            statusList.add("Processed");

            UUIDString = String.join(", ", DBManager.getUUIDInfo(idList.get(i), "UUID"));
            OrderGUIControllerOMS.UUIDList.add(UUIDString);

        }

        StockInterface.newOrderList.clear();

    }

    @FXML
    void bottomCheckDB(ActionEvent event) {

        //System.out.println(StockInterface.newOrderList.size()+"thisfuckgindg");

        List<MockShopObject> tempOrderList = new LinkedList<>(StockInterface.newOrderList);

        updateMethod();


        for (int i = 0; i < tempOrderList.size(); i++) {
            OrderConfirmationGenerator.fileFormatter();
            OrderConfirmationGenerator.generateOCPDF(new File("assets/oms/out/"+"Order_Confirmation"+(i+1)+".pdf"), tempOrderList.get(i), DBManager.getHighestId()+i-tempOrderList.size());
        }

    }

    @FXML
    void addOrderMock(ActionEvent event) {
        StockInterface.sendOrderOMSNew(mockShopObject);
  }

    public void initialize() {
        ordersListId.setItems(idList);
        ordersListStatus.setItems(statusList);
        ordersListUUID.setItems(UUIDList);
        updateMethod();
    }

}