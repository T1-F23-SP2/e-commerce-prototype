package com.example.ecommerceprototype.cms;

import com.example.ecommerceprototype.cms.exceptions.FXMLLoadFailedException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class InterfaceMiscTests extends Application {
    Thread t;

    @BeforeAll
    public static void initializeJavaFX() {
        launch();
    }

    @Test
    void getNodeListWithComponent() {
        ArrayList<Node> nodes = null;
        try {
            Pane p = CMS.getInstance().loadComponent("TopBanner");
            nodes = CMS.getInstance().getNodeList(p);
        } catch (FXMLLoadFailedException flfe) {
            System.out.println("!!!: " + flfe.getMessage());
        }
        assertTrue(nodes != null && nodes.size() > 0);
    }
    @Test
    void getNodeListWithNull() {
        ArrayList<Node> nodes = null;
        nodes = CMS.getInstance().getNodeList(null);
        assertTrue(nodes != null && nodes.size() == 0);
    }

    @Test
    void getComponentList() {
        ArrayList<String> comps = CMS.getInstance().getComponentList();
        assertTrue(comps != null && comps.size() > 0);
    }

    @Test
    void getButtonOnTopBanner() {
        Button b = null;
        try {
            Pane p = CMS.getInstance().loadComponent("TopBanner");
            b = CMS.getInstance().getButtonOnComponent(p,"home_Button");
        } catch (FXMLLoadFailedException flfe) {
            System.out.println("!!!: " + flfe.getMessage());
        }
        assertNotNull(b);
    }

    @Test
    void getButtonOnProductView() {
        Button b = null;
        try {
            Pane p = CMS.getInstance().loadComponent("ProductView");
            b = CMS.getInstance().getButtonOnComponent(p,"addToCart_Button");
        } catch (FXMLLoadFailedException flfe) {
            System.out.println("!!!: " + flfe.getMessage());
        }
        assertNotNull(b);
    }

    @Test
    void getButtonsOnTopBanner() {
        ArrayList<Button> b = null;
        try {
            Pane p = CMS.getInstance().loadComponent("TopBanner");
            b = CMS.getInstance().getButtonsOnComponent(p);
        } catch (FXMLLoadFailedException flfe) {
            System.out.println("!!!: " + flfe.getMessage());
        }
        assertTrue(b != null && b.size() == 3);
    }

    @Test
    void getButtonsOnPaymentPage() {
        ArrayList<Button> b = null;
        try {
            Pane p = CMS.getInstance().loadComponent("PaymentPage");
            b = CMS.getInstance().getButtonsOnComponent(p);
        } catch (FXMLLoadFailedException flfe) {
            System.out.println("!!!: " + flfe.getMessage());
        }
        assertTrue(b != null &&  b.size() == 1);
    }

    @Test
    void loadTopBannerOntoTemplate1() {
        Pane plate = null;
        try {
            plate = CMS.getInstance().loadComponent("ContentTemplate1");
            Pane topBanner = CMS.getInstance().loadComponent("TopBanner");
            CMS.getInstance().loadOnto(plate, topBanner, "topBannerPlaceholder_Pane");
        } catch (FXMLLoadFailedException flfe) {
            System.out.println(flfe.getMessage());
        }
        assertNotNull(CMS.getInstance().findNode(plate, "home_Button"));
    }

    @Test
    void loadPaymentPageOntoTemplate3() {
        Pane plate = null;
        try {
            plate = CMS.getInstance().loadComponent("ContentTemplate3");
            Pane topBanner = CMS.getInstance().loadComponent("PaymentPage");
            CMS.getInstance().loadOnto(plate, topBanner, "contentPlaceholder_Pane");
        } catch (FXMLLoadFailedException flfe) {
            System.out.println(flfe.getMessage());
        }
        assertNotNull(CMS.getInstance().findNode(plate, "email_TextField"));
    }

    @Override
    public void start(Stage stage) throws Exception {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                getNodeListWithComponent();
                getNodeListWithNull();

                getComponentList();

                getButtonOnTopBanner();
                getButtonOnProductView();

                getButtonsOnTopBanner();
                getButtonsOnPaymentPage();

                loadTopBannerOntoTemplate1();
            }
        });
        Platform.exit();
    }
}