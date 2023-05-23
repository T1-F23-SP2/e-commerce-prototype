package com.example.ecommerceprototype.cms;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class InterfaceLoadTests extends Application{
    @BeforeAll
    public static void initializeJavaFX() {
        launch();
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

    @Override
    public void start(Stage stage) throws Exception {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                loadComponentCorrectly();
                loadComponentIncorrectly();

                loadArticlePage();
                loadCartPage();
                loadCartProductView();
                loadCategoryItem();
                loadCategorySidebar();
                loadPaymentPage();
                loadProductPage();
                loadProductView();
                loadPurchaseCompletePage();
                loadTopBanner();

                loadContentTemplate1();
                loadContentTemplate2();
                loadContentTemplate3();
            }
        });
        Platform.exit();
    }
}