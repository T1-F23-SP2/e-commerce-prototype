package com.example.ecommerceprototype.shop.pages;

import com.example.ecommerceprototype.cms.CMS;
import com.example.ecommerceprototype.oms.DB.StockInterface;
import com.example.ecommerceprototype.oms.MockShop.MockShopObject;
import com.example.ecommerceprototype.oms.OrderStatus.OrderManager;
import com.example.ecommerceprototype.shop.ShopController;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class PurchasePage {

    ShopController controller;
    CMS cms;

    public PurchasePage(ShopController controller) {

        this.controller = controller;
        this.cms = controller.getCMSInstance();
    }

    public void loadPurchaseComplete(Stage window, MockShopObject orderInfo) throws Exception{
        Pane plate = cms.loadComponent("ContentTemplate2");

        controller.getTopBanner().loadTopBannerHome(plate);

        Pane paymentPage = cms.loadComponent("PurchaseCompletePage");
        cms.loadOnto(plate, paymentPage, "contentPlaceholder_Pane");

        controller.setScene(plate);
        sendOrder(orderInfo);
    }

    public void sendOrder(MockShopObject orderInfo) {
        StockInterface.sendOrderOMSNew(orderInfo);
        OrderManager.sendOrder(orderInfo);
    }
}
