package com.example.testcopypastetest;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class HelloController {
    @FXML
    private ListView<Integer> ordersListId;

    @FXML
    private ListView<Boolean> ordersListStatus;


    public static ObservableList<Integer> idList = FXCollections.observableArrayList(1, 2, 3);

    public static ObservableList<Boolean> statusList = FXCollections.observableArrayList(false, false, false);


    public void initialize() {
        ordersListId.setItems(idList);
        ordersListStatus.setItems(statusList);
    }








}