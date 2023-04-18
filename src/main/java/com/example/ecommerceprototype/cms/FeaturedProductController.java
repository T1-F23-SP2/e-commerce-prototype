package com.example.ecommerceprototype.cms;

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
    @FXML
    Image[] images;

    int currentImage = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        images = new Image[2];
        try{
            for(int i = 1; i <images.length; i++){
                images[i] = new Image(getClass().getResource("src/main/resources/com/example/ecommerceprototype/cms/TestImages/ComputerImage" + i + ".png").toURI().toString());
            }
        }        catch(URISyntaxException ue){
            System.out.println(ue);
        }
        productImage_ImageView.setImage(images[currentImage]);
        left_Button.setDisable(true);
        left_Button.setOpacity(0);
    }

    public void NextImageRight(){
        currentImage+=1;
        productImage_ImageView.setImage(images[currentImage]);
        if(currentImage == images.length-1){
            right_Button.setDisable(true);
            right_Button.setOpacity(0);
        }
    }
}
