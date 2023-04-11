package com.example.ecommerceprototype.cms;

import javafx.scene.layout.Pane;

public interface ICMS {
    Pane fetch(String id);
    //Pane fetchWithProduct(String id, Product p); //Product should be defined by shop
    String[] getAllComponentId(Pane component);
    String[] getAllButtonId(Pane component);
}
