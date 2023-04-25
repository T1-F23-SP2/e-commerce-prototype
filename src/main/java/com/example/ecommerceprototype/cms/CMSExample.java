package com.example.ecommerceprototype.cms;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Random;

public class CMSExample extends Application{

    Stage window;
    public static void main(String[] args) {
        launch();
    }


    @Override
    public void start(Stage stage) throws Exception {
        window = stage;
        loadShopPage();

        stage.setTitle("Arnes ElectroShop!");
        stage.show();
    }

    public void loadShopPage() throws Exception {
        //Load page template
        Pane plate = CMS.getInstance().fetchComponent("ContentTemplate1");
        System.out.println(plate);

        //Load top banner onto template
        Pane topPlaceholder = (Pane) CMS.getInstance().find(plate, "topBannerPlaceholder_Pane");
        Pane topBanner = CMS.getInstance().fetchComponent("TopBanner");
        ((Button) CMS.getInstance().find(topBanner, "home_Button")).setOnAction(actionEvent -> {
            try {loadShopPage();}
            catch (Exception e) {System.out.println(e.getMessage());}
        });
        topPlaceholder.getChildren().add(topBanner);

        //Load sidebar onto template
        Pane sidePlaceholder = (Pane) CMS.getInstance().find(plate, "sidebarPlaceholder_Pane");
        Pane sidebar = (Pane) CMS.getInstance().fetchComponent("CategorySidebar");

        //Set action for article button
        ((Button) CMS.getInstance().find(sidebar, "articlesButton_Button")).setOnAction(actionEvent -> {
            try {loadArticlePage();}
            catch (Exception e) {System.out.println(e.getMessage());}
        });

        //Put some categories into the category sidebar
        VBox categoryList = (VBox) CMS.getInstance().find(sidebar, "categoryList_VBox");

        String[] categories = {"Computers", "Laptops", "Phones", "Watches", "Parts"};
        for (int i = 0; i < categories.length; i++) {
            VBox categoryItem = (VBox) CMS.getInstance().fetchComponent("CategoryItem");
            Button b = (Button) CMS.getInstance().find(categoryItem, "categoryItem_Button");
            b.setText(categories[i]);
            categoryList.getChildren().add(b);
        }

        sidePlaceholder.getChildren().add(sidebar);

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
        window.setScene(new Scene(plate, 1920, 1080));
    }

    public void loadArticlePage() throws Exception{
        Pane plate = CMS.getInstance().fetchComponent("ContentTemplate2");

        Pane top = (Pane) CMS.getInstance().find(plate, "topBannerPlaceholder_Pane");
        Pane topBanner = CMS.getInstance().fetchComponent("TopBanner");
        ((Button) CMS.getInstance().find(topBanner, "home_Button")).setOnAction(actionEvent -> {
            try {loadShopPage();}
            catch (Exception e) {System.out.println(e.getMessage());}
        });
        top.getChildren().add(topBanner);

        Pane content = (Pane) CMS.getInstance().find(plate, "contentPlaceholder_Pane");
        content.getChildren().add(CMS.getInstance().fetchComponent("ArticlePage"));

        window.setScene(new Scene(plate, 1920, 1080));
    }
}
