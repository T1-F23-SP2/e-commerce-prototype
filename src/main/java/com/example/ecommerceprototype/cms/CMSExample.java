package com.example.ecommerceprototype.cms;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Random;

public class CMSExample extends Application{

    public static void main(String[] args) {
        launch();
    }


    @Override
    public void start(Stage stage) throws Exception {
        //Load page template
        Pane plate = CMS.getInstance().fetchComponent("Template1");

        //Load top banner onto template
        Pane top = (Pane) CMS.getInstance().find(plate, "topBannerPlaceholder_Pane");
        top.getChildren().add(CMS.getInstance().fetchComponent("TopBanner"));

        //Load sidebar onto template
        Pane side = (Pane) CMS.getInstance().find(plate, "sidebarPlaceholder_Pane");
        side.getChildren().add(CMS.getInstance().fetchComponent("ArticleSidebar"));

        //Create new content layout, and load productViews into layout
        ScrollPane content = (ScrollPane) CMS.getInstance().find(plate, "contentPlaceholder_ScrollPane");
        Random random = new Random();

        VBox vbox = new VBox();
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setSpacing(30);

        for (int i = 0; i < 5; i++) {
            HBox hbox = new HBox();
            hbox.minHeight(450);
            hbox.minWidth(1740);
            hbox.setAlignment(Pos.TOP_CENTER);
            hbox.setSpacing(15);

            for (int j = 0; j < 4; j++) {
                Pane view = CMS.getInstance().fetchComponent("ProductView");
                ((Label) CMS.getInstance().find(view, "productName_Label")).setText("Product " + (j + 4 * i));
                ((Label) CMS.getInstance().find(view, "productPrice_Label")).setText("$" + ((2 + random.nextInt(20)) * 5));
                ((Label) CMS.getInstance().find(view, "productStatus_Label")).setText(random.nextInt(2)==0?"Sold out":"In stock");
                ((TextArea) CMS.getInstance().find(view, "productDescription_TextArea")).setText("- This is an item!\n- You should buy this product!\n- Great quality!");
                Image productImage = new Image(getClass().getResourceAsStream("Placeholder.jpg"));
                ((ImageView) CMS.getInstance().find(view, "productImage_ImageView")).setImage(productImage);

                hbox.getChildren().add(view);
            }
            vbox.getChildren().add(hbox);
        }
        content.setContent(vbox);

        Scene scene = new Scene(plate, 1920, 1080);
        stage.setTitle("Arnes ElectroShop!");
        stage.setScene(scene);
        stage.show();
    }
}
