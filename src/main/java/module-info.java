module com.example.ecommerceprototype {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.postgresql.jdbc;
    requires org.mybatis;


    opens com.example.ecommerceprototype to javafx.fxml;
    opens com.example.ecommerceprototype.shop to javafx.fxml;
    exports com.example.ecommerceprototype;

    exports com.example.ecommerceprototype.shop;
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
}