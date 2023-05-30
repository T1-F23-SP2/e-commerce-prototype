package com.example.ecommerceprototype.dam.system;


import com.example.ecommerceprototype.dam.constants.CategoryNonProduct;
import com.example.ecommerceprototype.dam.constants.CategoryProduct;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class NewFileController implements Initializable {
    private String name;
    private String type;
    private String category;
    private String uuid;


    @FXML
    private TextField nameField;
    @FXML
    private ChoiceBox<String> typeChoice;
    @FXML
    private ChoiceBox<String> categoryChoice;
    @FXML
    private TextField uuidField;


    private final String[] types = {
            "Product image",
            "Product file",
            "Image",
            "File"
    };

    private final String[] categoriesProduct = Arrays.stream(CategoryProduct.values()).map(CategoryProduct:: getValue).toArray(String[]::new);

    private final String[] categoriesNonProduct = Arrays.stream(CategoryNonProduct.values()).map(CategoryNonProduct:: getValue).toArray(String[]::new);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        typeChoice.getItems().addAll(types);
        typeChoice.setOnAction(this::getTypeChoice);
        categoryChoice.setOnAction(this::getCategoryChoice);
    }

    private void getTypeChoice(ActionEvent actionEvent)
    {
        String type_in = typeChoice.getValue();
        setType(type_in);

        switch (type) {
            case "Product image", "Product file" -> {
                categoryChoice.getItems().clear();
                categoryChoice.getItems().addAll(categoriesProduct);
                uuidField.setDisable(false);
            }
            case "File", "Image" -> {
                categoryChoice.getItems().clear();
                categoryChoice.getItems().addAll(categoriesNonProduct);
                uuidField.setDisable(true);
                uuidField.clear();
            }
        }
    }

    private void getCategoryChoice(ActionEvent actionEvent)
    {
        String cat_in = categoryChoice.getValue();
        setCategory(cat_in);
    }

    public void updateUUIDLabel(ActionEvent actionEvent)
    {
        String uuid_in = uuidField.getText();
        setUuid(uuid_in);
    }


    public void updateNameLabel(ActionEvent actionEvent)
    {
        String name_in = nameField.getText();
        setName(name_in);
    }


    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    private void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    private void setCategory(String category) {
        this.category = category;
    }

    public String getUuid() {
        return uuid;
    }

    private void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
