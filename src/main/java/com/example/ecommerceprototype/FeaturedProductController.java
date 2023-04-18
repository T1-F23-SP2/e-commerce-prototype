package com.example.ecommerceprototype;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class FeaturedProductController implements Initializable {
    @FXML
    Button productBtn_Button, right_Button, left_Button;
    @FXML
    ImageView productImage_ImageView;

    Image[] images;

    int currentImage = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        images = new Image[3];
        for(int i = 0; i <images.length; i++){
            images[i] = new Image(getClass().getResourceAsStream("cms/TestImages/ComputerImage" + (i+1) + ".jpg"));
        }

        productImage_ImageView.setImage(images[currentImage]);
        left_Button.setDisable(true);
        left_Button.setOpacity(0);
    }

    public void NextImageRight(){
        currentImage+=1;
        productImage_ImageView.setImage(images[currentImage]);
        if(currentImage == images.length-1) {
            right_Button.setDisable(true);
            right_Button.setOpacity(0);
        }
        if(left_Button.isDisabled() == true){
            left_Button.setDisable(false);
            left_Button.setOpacity(100);
        }
    }

    public void NextImageLeft(){
        currentImage-=1;
        productImage_ImageView.setImage(images[currentImage]);
        if(currentImage == 0) {
            left_Button.setDisable(true);
            left_Button.setOpacity(0);
        }
        if(right_Button.isDisabled() == true){
            right_Button.setDisable(false);
            right_Button.setOpacity(100);
        }
    }
}
