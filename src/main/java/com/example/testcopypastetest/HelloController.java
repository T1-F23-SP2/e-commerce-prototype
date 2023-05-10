package com.example.testcopypastetest;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.HashMap;

public class HelloController {
    @FXML
    private ListView<Integer> ordersListId;

    @FXML
    private ListView<Boolean> ordersListStatus;


    public static ObservableList<Integer> idList = FXCollections.observableArrayList(1, 2, 3);

    public static ObservableList<Boolean> statusList = FXCollections.observableArrayList(false, false, false);


    public static HashMap orderMap = new HashMap<Integer, Boolean>();



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