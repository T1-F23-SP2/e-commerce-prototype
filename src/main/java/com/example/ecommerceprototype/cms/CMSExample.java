package com.example.ecommerceprototype.cms;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Random;

public class CMSExample extends Application{

    public static void main(String[] args) {
        launch();
    }


    @Override
    public void start(Stage stage) throws Exception {
        /*
        //Load page template
        Pane plate = CMS.getInstance().fetchComponent("ContentTemplate1");
        System.out.println(plate);

        //Load top banner onto template
        Pane top = (Pane) CMS.getInstance().find(plate, "topBannerPlaceholder_Pane");
        top.getChildren().add(CMS.getInstance().fetchComponent("TopBanner"));

        //Load sidebar onto template
        Pane side = (Pane) CMS.getInstance().find(plate, "sidebarPlaceholder_Pane");
        side.getChildren().add(CMS.getInstance().fetchComponent("ArticleSidebar"));

        //Load content into gridPane
        GridPane content = (GridPane) CMS.getInstance().find(plate, "contentPlaceholder_GridPane");
        Random random = new Random();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                Pane view = CMS.getInstance().fetchComponent("ProductView");

                ((Label) CMS.getInstance().find(view, "productName_Label")).setText("Product " + (i + j * 4));
                ((Label) CMS.getInstance().find(view, "productPrice_Label")).setText("$" + ((2 + random.nextInt(20)) * 5));
                ((Label) CMS.getInstance().find(view, "productStatus_Label")).setText(random.nextInt(2)==0?"Sold out":"In stock");
                ((TextArea) CMS.getInstance().find(view, "productDescription_TextArea")).setText("- This is an item!\n- You should buy this product!\n- Great quality!");
                Image productImage = new Image(getClass().getResourceAsStream("Placeholder.jpg"));
                ((ImageView) CMS.getInstance().find(view, "productImage_ImageView")).setImage(productImage);

                GridPane.setColumnIndex(view, i);
                GridPane.setRowIndex(view, j);

                content.getChildren().add(view);
            }
        }
        */

        /*Pane plate = CMS.getInstance().fetchComponent("ContentTemplate2");

        Pane top = (Pane) CMS.getInstance().find(plate, "topBannerPlaceholder_Pane");
        top.getChildren().add(CMS.getInstance().fetchComponent("TopBanner"));

        Pane content = (Pane) CMS.getInstance().find(plate, "contentPlaceholder_Pane");
        content.getChildren().add(CMS.getInstance().fetchComponent("ArticlePage"));
*/
        Pane plate = CMS.getInstance().fetchComponent("articlecrud/CRUDArticle");
        Scene scene = new Scene(plate, 1920, 1080);
        stage.setTitle("Arnes ElectroShop!");
        stage.setScene(scene);
        stage.show();
    }
}
