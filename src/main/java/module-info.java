module com.example.ecommerceprototype {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.ecommerceprototype to javafx.fxml;
    exports com.example.ecommerceprototype;
}