package com.example.ecommerceprototype.cms;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class CMSTest extends Application{
    @BeforeAll
    public static void initializeJavaFX() {
        launch();
    }

    @Test
    void loadComponentCorrectly() {
        Thread thread = new Thread(() -> {
            Pane p = null;
            try {
                p = CMS.getInstance().loadComponent("TopBanner");
            } catch (com.example.ecommerceprototype.cms.exceptions.FXMLLoadFailedException flfe) {
                System.out.println("!!!: " + flfe.getMessage());
            }
            assertNotNull(p);
        });
        thread.start();
    }
    @Test
    void loadComponentIncorrectly() {
        Thread thread = new Thread(() -> {
            assertThrows(IllegalStateException.class, () -> {
                Pane p;
                p = CMS.getInstance().loadComponent("Incorrect");
            });

        });
        thread.start();
    }

    @Test
    void findNode() {
        Thread thread = new Thread(() -> {
            Node n = null;
            try {
                Pane p = CMS.getInstance().loadComponent("TopBanner");
                n = CMS.getInstance().findNode(p, "home_Button");
            } catch (com.example.ecommerceprototype.cms.exceptions.FXMLLoadFailedException flfe) {
                System.out.println("!!!: " + flfe.getMessage());
            }
            assertNotNull(n);
        });
        thread.start();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                }
                catch (InterruptedException ie) {
                    System.out.println(ie.getMessage());
                }
                Platform.exit();
            }
        });
    }
}