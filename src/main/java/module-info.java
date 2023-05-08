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


    opens com.example.testcopypastetest to javafx.fxml;
    exports com.example.testcopypastetest;
}