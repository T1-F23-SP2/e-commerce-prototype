module com.example.ecommerceprototype {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.postgresql.jdbc;


    opens com.example.ecommerceprototype to javafx.fxml;
    exports com.example.ecommerceprototype;
    exports com.example.ecommerceprototype.cms;
    opens com.example.ecommerceprototype.cms to javafx.fxml;
}