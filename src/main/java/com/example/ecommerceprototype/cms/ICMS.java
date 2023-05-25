package com.example.ecommerceprototype.cms;


import com.example.ecommerceprototype.cms.exceptions.FXMLLoadFailedException;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public interface ICMS {
    Pane loadComponent(String id) throws FXMLLoadFailedException;
    Pane loadComponent(String id, boolean isCRUD) throws FXMLLoadFailedException;
    //Pane fetchComponentWithProduct(String fxid, ProductInformation p);
    ArrayList<Node> getNodeList(Pane component);
    ArrayList<String> getComponentList();
    Button getButtonOnComponent(Pane component, String fxid);
    ArrayList<Button> getButtonsOnComponent(Pane component);
    Node findNode(Pane component, String fxid);
    Node findNode(Pane component, int index);
    void loadOnto(Pane plate, Pane component, String replaces);
}
