package com.example.ecommerceprototype.cms;

import com.example.ecommerceprototype.cms.articlecrud.CRUDArticleController;
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
        //Load page template (Template 1 has space for a topbanner, a sidebar and content to be arranged in a grid)
        Pane plate = CMS.getInstance().loadComponent("ContentTemplate1");


        //Load top banner onto template and set home button functionality
        Pane topBanner = CMS.getInstance().loadComponent("TopBanner");
        ((Button) CMS.getInstance().findNode(topBanner, "home_Button")).setOnAction(actionEvent -> {
            try {loadShopPage();}
            catch (Exception e) {System.out.println(e.getMessage());}
        });
        CMS.getInstance().loadOnto(plate, topBanner, "topBannerPlaceholder_Pane");


        //Load sidebar onto template
        Pane sidebar = (Pane) CMS.getInstance().loadComponent("CategorySidebar");
        CMS.getInstance().loadOnto(plate, sidebar, "sidebarPlaceholder_Pane");

        //Set action for article button (on the sidebar)
        ((Button) CMS.getInstance().findNode(sidebar, "articlesButton_Button")).setOnAction(actionEvent -> {
            try {loadArticlePage();}
            catch (Exception e) {System.out.println(e.getMessage());}
        });

        //Put some categories into the category sidebar (for testing)
        VBox categoryList = (VBox) CMS.getInstance().findNode(sidebar, "categoryList_VBox");

        String[] categories = {"Computers", "Laptops", "Phones", "Watches", "Parts"};
        for (int i = 0; i < categories.length; i++) {
            VBox categoryItem = (VBox) CMS.getInstance().loadComponent("CategoryItem");
            Button b = (Button) CMS.getInstance().findNode(categoryItem, "categoryItem_Button");
            b.setText(categories[i]);
            categoryList.getChildren().add(b);
        }



        //Load product views into gridPane
        Random random = new Random();

        //Loading 12 product views into a 3x4 grid
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                Pane view = CMS.getInstance().loadComponent("ProductView");

                //Placeholder values
                ((Label) CMS.getInstance().findNode(view, "productName_Label")).setText("Product " + (i + j * 4));
                ((Label) CMS.getInstance().findNode(view, "productPrice_Label")).setText("$" + ((2 + random.nextInt(20)) * 5));
                ((Label) CMS.getInstance().findNode(view, "productStatus_Label")).setText(random.nextInt(2)==0?"Sold out":"In stock");
                ((TextArea) CMS.getInstance().findNode(view, "productDescription_TextArea")).setText("- This is an item!\n- You should buy this product!\n- Great quality!");
                Image productImage = new Image(getClass().getResourceAsStream("Placeholder.jpg"));
                ((ImageView) CMS.getInstance().findNode(view, "productImage_ImageView")).setImage(productImage);

                GridPane.setColumnIndex(view, i);
                GridPane.setRowIndex(view, j);

                CMS.getInstance().loadOnto(plate, view, "contentPlaceholder_GridPane");
            }
        }

        window.setScene(new Scene(plate, 1920, 1080));
    }

    public void loadArticlePage() throws Exception{
        //Load page template (Template 2 has space for a top banner and some content pane)
        Pane plate = CMS.getInstance().loadComponent("ContentTemplate2");

        //Load top banner onto template and set home button functionality
        Pane topBanner = CMS.getInstance().loadComponent("TopBanner");
        ((Button) CMS.getInstance().findNode(topBanner, "home_Button")).setOnAction(actionEvent -> {
            try {loadShopPage();}
            catch (Exception e) {System.out.println(e.getMessage());}
        });
        CMS.getInstance().loadOnto(plate, topBanner, "topBannerPlaceholder_Pane");

        //Load article page onto template (article page has functionality by default, since CMS already has all the files for displaying the needed information)
        CMS.getInstance().loadOnto(plate, CMS.getInstance().loadComponent("ArticlePage"), "contentPlaceholder_Pane");

        window.setScene(new Scene(plate, 1920, 1080));
    }
}
