package com.example.ecommerceprototype.oms;


import com.example.ecommerceprototype.oms.Visuals.InventoryDisplayGenerator;
import com.example.ecommerceprototype.oms.Visuals.SalesReportGenerator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


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
        InventoryDisplayGenerator.stockOverviewGen();
        textLabel.setText("Stock Overview generated");
    }





}