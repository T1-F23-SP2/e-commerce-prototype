module com.example.ecommerceprototype {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.postgresql.jdbc;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.bson;
    requires java.logging;
    requires itextpdf;
    requires org.jfree.jfreechart;
    requires java.desktop;
    requires org.mongodb.driver.core;
    requires org.mybatis;

    opens com.example.ecommerceprototype to javafx.fxml;
    exports com.example.ecommerceprototype;

    opens com.example.ecommerceprototype.shop;
    exports com.example.ecommerceprototype.shop to javafx.fxml;

    exports com.example.ecommerceprototype.oms;
    opens com.example.ecommerceprototype.oms to javafx.fxml;

    exports com.example.ecommerceprototype.pim.product_information;
    exports com.example.ecommerceprototype.pim.presentation;
    exports com.example.ecommerceprototype.pim.exceptions;
    exports com.example.ecommerceprototype.pim.sql_helpers;
    opens com.example.ecommerceprototype.pim.presentation to javafx.fxml;

    exports com.example.ecommerceprototype.cms;
    opens com.example.ecommerceprototype.cms to javafx.fxml;
    exports com.example.ecommerceprototype.cms.crud.article;
    opens com.example.ecommerceprototype.cms.crud.article to javafx.fxml;
    exports com.example.ecommerceprototype.cms.crud;
    opens com.example.ecommerceprototype.cms.crud to javafx.fxml;
    exports com.example.ecommerceprototype.cms.crud.generic;
    opens com.example.ecommerceprototype.cms.crud.generic to javafx.fxml;
    exports com.example.ecommerceprototype.cms.controllers;
    opens com.example.ecommerceprototype.cms.controllers to javafx.fxml;
    exports com.example.ecommerceprototype.cms.exceptions;
    opens com.example.ecommerceprototype.cms.exceptions to javafx.fxml;

    exports com.example.ecommerceprototype.shop.components;
    opens com.example.ecommerceprototype.shop.components to javafx.fxml;
    exports com.example.ecommerceprototype.shop.pages;
    opens com.example.ecommerceprototype.shop.pages to javafx.fxml;

}
