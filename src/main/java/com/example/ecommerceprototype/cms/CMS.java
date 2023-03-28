package com.example.ecommerceprototype.cms;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
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
    public Pane fetch(String id) {
        //String loadString = "com/example/ecommerceprototype/CMS/"+ id +".fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(id + ".fxml"));

        Pane pane = new Pane();
        try { pane = loader.load(); }
        catch (IOException ioe) { System.out.println(ioe.getMessage()); }
        return pane;
    }

    @Override
    public String[] getAllComponentId(Pane component) {
        ArrayList<String> nodes = new ArrayList<>();
        for(Node n : component.getChildren()) {
            if (n.getId().length() > 0)
                nodes.add(n.getId());
        }

        String[] result = new String[nodes.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = nodes.get(i);
        }

        return result;
    }

    @Override
    public String[] getAllButtonId(Pane component) {
        ArrayList<String> nodes = new ArrayList<>();
        for(Node n : component.getChildren()) {
            if (n.getId().length() > 0 && n instanceof Button)
                nodes.add(n.getId());
        }

        String[] result = new String[nodes.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = nodes.get(i);
        }

        return result;
    }
}
