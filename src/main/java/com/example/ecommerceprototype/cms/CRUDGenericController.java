package com.example.ecommerceprototype.cms;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class CRUDGenericController {

    private String fileName;

    private double size;


    @FXML
    private Label fileLabel;
    @FXML
    private TextField IDField;
    @FXML
    private TextField sizeField;
    @FXML
    private TextField contentField;
    @FXML
    private Label testLabel;
    @FXML
    private RadioButton boldRadio;

    @FXML
    public void updateTestLabel(){
        double v = Double.parseDouble(sizeField.getText());
        String font = "SYSTEM";
        if (boldRadio.isSelected()){
            testLabel.setFont(Font.font(font, FontWeight.BOLD, v));
        }else{
            testLabel.setFont(Font.font(font, v));
        }
        testLabel.setText(contentField.getText());
    }

}
