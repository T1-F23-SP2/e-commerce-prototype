module Application {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.mongodb.bson;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.driver.core;
    requires java.desktop;
    requires java.sql;
    requires itextpdf;
    requires org.jfree.jfreechart;
    requires org.junit.jupiter.api;


    opens Controllers to javafx.fxml;
    exports Controllers;
}