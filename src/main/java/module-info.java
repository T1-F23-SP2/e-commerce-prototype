module com.example.ecommerceprototype {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires org.postgresql.jdbc;
    requires java.desktop;
    requires javafx.media;
    requires com.azure.storage.blob;
    requires org.mybatis;


    opens com.example.ecommerceprototype to javafx.fxml;
    opens com.example.ecommerceprototype.dam;
    exports com.example.ecommerceprototype;

<<<<<<< src/main/java/module-info.java
    exports com.example.ecommerceprototype.dam;
    exports com.example.ecommerceprototype.dam.system;
    opens com.example.ecommerceprototype.dam.system;
    exports com.example.ecommerceprototype.dam.dam;
    opens com.example.ecommerceprototype.dam.dam;


=======
exports com.example.ecommerceprototype.dam;
    exports com.example.ecommerceprototype.dam.system;
    opens com.example.ecommerceprototype.dam.system;
    exports com.example.ecommerceprototype.dam.dam;
    opens com.example.ecommerceprototype.dam.dam;

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
>>>>>>> src/main/java/module-info.java

    exports com.example.ecommerceprototype.pim.product_information;
    exports com.example.ecommerceprototype.pim.presentation;
    exports com.example.ecommerceprototype.pim.exceptions;
    exports com.example.ecommerceprototype.pim.sql_helpers;
    opens com.example.ecommerceprototype.pim.presentation to javafx.fxml;
}
