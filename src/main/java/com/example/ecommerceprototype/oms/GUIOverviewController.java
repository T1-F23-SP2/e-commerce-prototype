package com.example.ecommerceprototype.oms;

import com.example.ecommerceprototype.oms.DB.DBManager;
import com.example.ecommerceprototype.oms.DB.StockInterface;
import com.example.ecommerceprototype.oms.MockShop.MockShopObject;
import com.example.ecommerceprototype.oms.MockShop.PlaceholderInstShop;
import com.example.ecommerceprototype.oms.OrderStatus.OrderManager;
import com.example.ecommerceprototype.oms.Visuals.InventoryDisplayGenerator;
import com.example.ecommerceprototype.oms.Visuals.OrderConfirmationGenerator;
import com.example.ecommerceprototype.oms.Visuals.SalesReportGenerator;
import com.mongodb.client.MongoCollection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import org.bson.Document;

import java.io.File;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static com.mongodb.client.model.Indexes.descending;

public class GUIOverviewController {


    @FXML
    private Button SalesReportBtn;

    @FXML
    private Button StockOverviewBtn;
    @FXML
    private Label textLabel;

    @FXML
    void SalesReportBtnClicked(ActionEvent event) {
        SalesReportGenerator.pdfMaker();
        textLabel.setText("Sales report generated");
    }

    @FXML
    void StockOverviewBtnClicked(ActionEvent event) {
        InventoryDisplayGenerator.stockOverviewgen();
        textLabel.setText("Stock Overview generated");
    }





}