package com.example.ecommerceprototype.cms;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public interface ICMS {
    Pane fetchComponent(String id);
    //Pane fetchComponentWithProduct(String fxid, Product p); //Product should be defined by shop
    ArrayList<String> getComponentList(Pane component);
    Button getButtonOnComponent(Pane component, String fxid);
    ArrayList<Button> getButtonsOnComponent(Pane component);
    Node find(Pane component, String fxid);
}
