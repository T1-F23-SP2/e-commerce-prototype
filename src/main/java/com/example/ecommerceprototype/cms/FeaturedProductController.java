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
    public Button productBtn_Button, right_Button, left_Button;
    @FXML
    public ImageView productImage_ImageView;

    Image[] images;
    int currentImage = 0;


    //Pane p = CMS.getInstance().fetchComponent("FeaturedProduct");
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        images = new Image[3];
        for(int i = 0; i <images.length; i++){
            images[i] = new Image(CMS.class.getResourceAsStream("TestImages/ComputerImage" + (i+1) + ".jpg"));
        }
        productImage_ImageView.setImage(images[currentImage]);
    }

    public void NextImageRight(){
        currentImage = (currentImage+1)%(images.length);
        productImage_ImageView.setImage(images[currentImage]);
    }

    public void NextImageLeft(){
        currentImage-=1;
        if(currentImage%images.length <= -1){
            currentImage = images.length-1;
        }
        productImage_ImageView.setImage(images[currentImage]);
    }
}
