module com.example.ecommerceprototype {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.postgresql.jdbc;
    requires java.desktop;
// :)

    opens com.example.ecommerceprototype to javafx.fxml;
    exports com.example.ecommerceprototype;
}