module com.example.ecommerceprototype {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires org.postgresql.jdbc;
    requires java.desktop;
    requires javafx.media;
    opens com.example.ecommerceprototype to javafx.fxml;
    opens com.example.ecommerceprototype.dam to javafx.graphics;
    exports com.example.ecommerceprototype;
}
