package com.example.ecommerceprototype.cms;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CMS implements ICMS{
    private static CMS instance;
    public static final ArticleManager articles = ArticleManager.getInstance();


    private CMS() {}; //Zero-arg constructor

    public static CMS getInstance() {
        if (instance == null)
            instance = new CMS();
        return instance;
    }

    @Override
    public Pane loadComponent(String id) throws FXMLLoadFailedException {
        String errorMessage;
        FXMLLoader loader = new FXMLLoader(CMS.class.getResource(id + ".fxml"));
        try {
            return loader.load();
        }
        catch (IOException ioe) { errorMessage = ioe.getMessage(); }
        throw new FXMLLoadFailedException(errorMessage);
    }

    /*@Override
    public Pane fetchComponentWithProduct(String fxid, ProductInformation prod) {
        Pane p = fetchComponent(fxid);

        ((Label) CMS.getInstance().find(p, "productName_Label")).setText(prod.getName());
        ((Label) CMS.getInstance().find(p, "productPrice_Label")).setText(prod.getPriceInformation.getPrice());
        ((TextArea) CMS.getInstance().find(p, "productDescription_TextArea")).setText(prod.getShortDescription);
    }*/

    @Override
    public ArrayList<String> getNodeList(Pane component) {
        ArrayList<String> nodes = new ArrayList<>();

        for(Node n : component.getChildren()) {
            //Recursive
            if (n instanceof Pane) {
                Pane p = (Pane) n;
                if (p.getChildren().size() > 0)
                    nodes.addAll(getNodeList(p));
            }

            if (n instanceof ScrollPane) {
                ScrollPane sp = (ScrollPane) n;
                if (sp.getContent() != null && sp.getContent().getId() != null)
                    nodes.add(sp.getContent().getId());
                if (sp.getContent() != null && sp.getContent() instanceof Pane)
                    nodes.addAll(getNodeList((Pane) sp.getContent()));

            }

            if (n.getId() != null)
                nodes.add(n.getId());
        }

        return nodes;
    }

    @Override
    public ArrayList<String> getComponentList() {
        ArrayList<String> result = new ArrayList<>();

        File infile = new File("src/main/resources/com/example/ecommerceprototype/cms");
        if (!infile.exists())
            return result;

        File[] allFiles = infile.listFiles();

        for (File f : allFiles) {
            if (f.getName().startsWith("CRUD"))
                continue;
            if (f.getName().endsWith(".fxml"))
                result.add(f.getName());
        }

        return result;
    }


    @Override
    public Button getButtonOnComponent(Pane component, String fxid) {
        Node n = findNode(component, fxid);
        if (n instanceof Button)
            return (Button) n;
        return null;
    }

    @Override
    public ArrayList<Button> getButtonsOnComponent(Pane component) {
        ArrayList<Button> buttons = new ArrayList<>();
        ArrayList<String> ids = getNodeList(component);
        for (String id : ids) {
            Button b = getButtonOnComponent(component, id);
            if (b != null)
                buttons.add(b);
        }

        return buttons;
    }

    @Override
    public Node findNode(Pane component, String fxid) {
        for (Node n : component.getChildren()) {
            if (n.getId() != null && n.getId().equals(fxid)) {
                return n;
            }
            else if (n instanceof Pane) {
                Node rn = findNode((Pane) n, fxid);
                if (rn != null)
                    return rn;
            }
            else if (n instanceof ScrollPane)
                if (((ScrollPane) n).getContent().getId().equals(fxid))
                    return ((ScrollPane) n).getContent();
                else if (((ScrollPane) n).getContent() instanceof Pane) {
                    Node rn = findNode((Pane) ((ScrollPane) n).getContent(), fxid);
                    if (rn != null)
                        return rn;
                }
        }
        return null;
    }
}
