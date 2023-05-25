package com.example.ecommerceprototype.cms;

import com.example.ecommerceprototype.cms.exceptions.FXMLLoadFailedException;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class InterfaceLoadTests {
    @BeforeAll
    public static void initializeJavaFX() {
        CMSDummyApplication.initApp();
    }

    @Test
    void loadComponentCorrectly() {
        Pane p = null;
        try {
            p = CMS.getInstance().loadComponent("TopBanner");
        } catch (FXMLLoadFailedException flfe) {
            System.out.println("!!!: " + flfe.getMessage());
        }
        assertNotNull(p);
    }

    @Test
    void loadComponentIncorrectly() {
        assertThrows(IllegalStateException.class, () -> {
            Pane p;
            p = CMS.getInstance().loadComponent("Incorrect");
        });
    }

    @Test
    void loadTopBanner() {
        Pane p = null;
        try {
            p = CMS.getInstance().loadComponent("TopBanner");
        } catch (FXMLLoadFailedException flfe) {
            System.out.println("!!!: " + flfe.getMessage());
        }
        assertNotNull(p);
    }

    @Test
    void loadProductView() {
        Pane p = null;
        try {
            p = CMS.getInstance().loadComponent("ProductView");
        } catch (FXMLLoadFailedException flfe) {
            System.out.println("!!!: " + flfe.getMessage());
        }
        assertNotNull(p);
    }

    @Test
    void loadArticlePage() {
        Pane p = null;
        try {
            p = CMS.getInstance().loadComponent("ArticlePage");
        } catch (FXMLLoadFailedException flfe) {
            System.out.println("!!!: " + flfe.getMessage());
        }
        assertNotNull(p);
    }

    @Test
    void loadCartPage() {
        Pane p = null;
        try {
            p = CMS.getInstance().loadComponent("CartPage");
        } catch (FXMLLoadFailedException flfe) {
            System.out.println("!!!: " + flfe.getMessage());
        }
        assertNotNull(p);
    }

    @Test
    void loadCartProductView() {
        Pane p = null;
        try {
            p = CMS.getInstance().loadComponent("CartProductView");
        } catch (FXMLLoadFailedException flfe) {
            System.out.println("!!!: " + flfe.getMessage());
        }
        assertNotNull(p);
    }

    @Test
    void loadCategoryItem() {
        Pane p = null;
        try {
            p = CMS.getInstance().loadComponent("CategoryItem");
        } catch (FXMLLoadFailedException flfe) {
            System.out.println("!!!: " + flfe.getMessage());
        }
        assertNotNull(p);
    }

    @Test
    void loadCategorySidebar() {
        Pane p = null;
        try {
            p = CMS.getInstance().loadComponent("CategorySidebar");
        } catch (FXMLLoadFailedException flfe) {
            System.out.println("!!!: " + flfe.getMessage());
        }
        assertNotNull(p);
    }

    @Test
    void loadContentTemplate1() {
        Pane p = null;
        try {
            p = CMS.getInstance().loadComponent("ContentTemplate1");
        } catch (FXMLLoadFailedException flfe) {
            System.out.println("!!!: " + flfe.getMessage());
        }
        assertNotNull(p);
    }

    @Test
    void loadContentTemplate2() {
        Pane p = null;
        try {
            p = CMS.getInstance().loadComponent("ContentTemplate2");
        } catch (FXMLLoadFailedException flfe) {
            System.out.println("!!!: " + flfe.getMessage());
        }
        assertNotNull(p);
    }

    @Test
    void loadContentTemplate3() {
        Pane p = null;
        try {
            p = CMS.getInstance().loadComponent("ContentTemplate3");
        } catch (FXMLLoadFailedException flfe) {
            System.out.println("!!!: " + flfe.getMessage());
        }
        assertNotNull(p);
    }

    @Test
    void loadPaymentPage() {
        Pane p = null;
        try {
            p = CMS.getInstance().loadComponent("PaymentPage");
        } catch (FXMLLoadFailedException flfe) {
            System.out.println("!!!: " + flfe.getMessage());
        }
        assertNotNull(p);
    }

    @Test
    void loadProductPage() {
        Pane p = null;
        try {
            p = CMS.getInstance().loadComponent("ProductPage");
        } catch (FXMLLoadFailedException flfe) {
            System.out.println("!!!: " + flfe.getMessage());
        }
        assertNotNull(p);
    }

    @Test
    void loadPurchaseCompletePage() {
        Pane p = null;
        try {
            p = CMS.getInstance().loadComponent("PurchaseCompletePage");
        } catch (FXMLLoadFailedException flfe) {
            System.out.println("!!!: " + flfe.getMessage());
        }
        assertNotNull(p);
    }
}