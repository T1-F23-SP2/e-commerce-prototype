package com.example.ecommerceprototype.cms;

import com.example.ecommerceprototype.cms.exceptions.FXMLLoadFailedException;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class InterfaceFindTests {
    @BeforeAll
    public static void initializeJavaFX() {
        CMSDummyApplication.initApp();
    }

    @Test
    void findNodeInTopBanner() {
        Node n = null;
        try {
            Pane p = CMS.getInstance().loadComponent("TopBanner");
            n = CMS.getInstance().findNode(p, "home_Button");
        } catch (FXMLLoadFailedException flfe) {
            System.out.println("!!!: " + flfe.getMessage());
        }
        assertNotNull(n);
    }

    @Test
    void findNodeInProductView() {
        Node n = null;
        try {
            Pane p = CMS.getInstance().loadComponent("ProductView");
            n = CMS.getInstance().findNode(p, "productDescription_TextArea");
        } catch (FXMLLoadFailedException flfe) {
            System.out.println("!!!: " + flfe.getMessage());
        }
        assertNotNull(n);
    }

    @Test
    void findNodeInCartPage() {
        Node n = null;
        try {
            Pane p = CMS.getInstance().loadComponent("CartPage");
            n = CMS.getInstance().findNode(p, "priceTax_Label");
        } catch (FXMLLoadFailedException flfe) {
            System.out.println("!!!: " + flfe.getMessage());
        }
        assertNotNull(n);
    }

    @Test
    void findNodeIncorrectly() {
        Node n = null;
        try {
            Pane p = CMS.getInstance().loadComponent("CartPage");
            n = CMS.getInstance().findNode(p, "Incorrect Component");
        } catch (FXMLLoadFailedException flfe) {
            System.out.println("!!!: " + flfe.getMessage());
        }
        assertNull(n);
    }
}