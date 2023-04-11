package com.example.ecommerceprototype.cms;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.ArrayList;

public class CMS implements ICMS{
    private static CMS instance;
    public final ArticleManager articles = ArticleManager.getInstance();


    private CMS() {}; //Zero-arg constructor

    public static CMS getInstance() {
        if (instance == null)
            instance = new CMS();
        return instance;
    }

    @Override
    public Pane fetchComponent(String id) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(id + ".fxml"));
        try {
            return loader.load();
        }
        catch (IOException ioe) { System.out.println(ioe.getMessage()); }
        return null;
    }

    @Override
    public ArrayList<String> getComponentList(Pane component) {
        ArrayList<String> nodes = new ArrayList<>();

        for(Node n : component.getChildren()) {
            //Recursive
            if (n instanceof Pane) {
                Pane p = (Pane) n;
                if (p.getChildren().size() > 0)
                    nodes.addAll(getComponentList(p));
            }

            if (n instanceof ScrollPane) {
                ScrollPane sp = (ScrollPane) n;
                if (sp.getContent() != null && sp.getContent().getId() != null)
                    nodes.add(sp.getContent().getId());
            }

            if (n.getId() != null)
                nodes.add(n.getId());
        }

        return nodes;
    }


    @Override
    public Button getButtonOnComponent(Pane component, String fxid) {
        Node n = find(component, fxid);
        if (n instanceof Button)
            return (Button) n;
        return null;
    }

    @Override
    public ArrayList<Button> getButtonsOnComponent(Pane component) {
        ArrayList<Button> buttons = new ArrayList<>();
        ArrayList<String> ids = getComponentList(component);
        for (int i = 0; i < ids.size(); i++) {
            Button b = getButtonOnComponent(component, ids.get(i));
            if (b != null)
                buttons.add(b);
        }

        return buttons;
    }

    @Override
    public Node find(Pane component, String fxid) {
        for (Node n : component.getChildren()) {
            System.out.println(n.getId() + " : " + fxid);
            if (n.getId() != null && n.getId().equals(fxid)) {
                return n;
            }
            else if (n instanceof Pane)
                find((Pane) n, fxid);
            else if (n instanceof ScrollPane && ((ScrollPane) n).getContent().getId().equals(fxid))
                return ((ScrollPane) n).getContent();
        }
        return null;
    }
}
