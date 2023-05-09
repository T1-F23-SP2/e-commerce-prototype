module com.example.ecommerceprototype {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.postgresql.jdbc;


    opens com.example.ecommerceprototype to javafx.fxml;
    opens com.example.ecommerceprototype.shop to javafx.fxml;
    exports com.example.ecommerceprototype;
    exports com.example.ecommerceprototype.shop;
}