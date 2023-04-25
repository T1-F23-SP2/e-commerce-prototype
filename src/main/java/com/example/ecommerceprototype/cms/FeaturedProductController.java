package com.example.ecommerceprototype.cms;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FeaturedProductController implements Initializable {
    @FXML
    public Button productBtn_Button, right_Button, left_Button;
    @FXML
    public ImageView productImage_ImageView;

    ArrayList<Image> images = new ArrayList<>();
    int currentImage;


    //Pane p = CMS.getInstance().fetchComponent("FeaturedProduct");
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentImage = 0;

        //For every file in the directory "FeaturedProductImages" add that image to the arraylist
        for(int i = 0; i < new File("src/main/resources/com/example/ecommerceprototype/cms/FeaturedProductImages").list().length; i++){
            images.add(new Image(CMS.class.getResourceAsStream("FeaturedProductImages/featuredProduct" + (i) + ".jpg")));
        }
        //Sets the first image from the arraylist
        productImage_ImageView.setImage(images.get(currentImage));
    }

    public void NextImageRight(){
        //Circular array going up
        currentImage = (currentImage+1)%(images.size());
        productImage_ImageView.setImage(images.get(currentImage));
    }

    public void NextImageLeft(){
        //Circular array going down
        currentImage-=1;
        if(currentImage%images.size() <= -1){
            currentImage = images.size()-1;
        }
        productImage_ImageView.setImage(images.get(currentImage));
    }
}
