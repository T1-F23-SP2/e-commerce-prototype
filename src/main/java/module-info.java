module com.example.testcopypastetest {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.bson;
    requires java.logging;
    requires itextpdf;
    requires org.jfree.jfreechart;
    requires java.desktop;
    requires org.mongodb.driver.core;
    requires org.junit.jupiter.api;


    exports com.example.ecommerceprototype.oms;
    opens com.example.ecommerceprototype.oms to javafx.fxml;
}