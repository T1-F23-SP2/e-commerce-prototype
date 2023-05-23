module com.example.ecommerceprototype {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.postgresql.jdbc;


    opens com.example.ecommerceprototype to javafx.fxml;
    exports com.example.ecommerceprototype;
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
}