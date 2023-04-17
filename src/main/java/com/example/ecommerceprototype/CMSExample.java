package com.example.ecommerceprototype;

import com.example.ecommerceprototype.cms.CMS;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
        Pane plate = CMS.getInstance().fetchComponent("Template1");

        Pane top = (Pane) CMS.getInstance().find(plate, "topBannerPlaceholder_Pane");
        top.getChildren().add(CMS.getInstance().fetchComponent("TopBanner"));

        Pane side = (Pane) CMS.getInstance().find(plate, "sidebarPlaceholder_Pane");
        side.getChildren().add(CMS.getInstance().fetchComponent("ArticleSidebar"));

        Pane content = (VBox) CMS.getInstance().find(plate, "contentPlaceholder_Pane");
        Random random = new Random();

        for (int i = 0; i < 3; i++) {
            HBox hbox = new HBox();
            hbox.minHeight(450);
            hbox.minWidth(1740);
            hbox.setAlignment(Pos.TOP_CENTER);
            hbox.setSpacing(15);

            for (int j = 0; j < 4; j++) {
                Pane view = CMS.getInstance().fetchComponent("ProductView");
                ((Label) CMS.getInstance().find(view, "productName_Label")).setText("Product " + (j + 4 * i));
                ((Label) CMS.getInstance().find(view, "productPrice_Label")).setText("$" + random.nextInt(20) * 5);
                ((Label) CMS.getInstance().find(view, "productStatus_Label")).setText(random.nextInt(2)==0?"Sold out":"In stock");
                ((TextArea) CMS.getInstance().find(view, "productDescription_TextArea")).setText("This item goes hard!\nLiterally worth every buck!\n40 Volts fully autonomous!");
                ((ImageView) CMS.getInstance().find(view, "productImage_ImageView")).setImage(new Image("C:\\Users\\marcu\\Documents\\GitHub\\e-commerce-prototype\\src\\main\\resources\\com\\example\\ecommerceprototype\\cms\\Placeholder.jpg"));
                hbox.getChildren().add(view);
            }
            content.getChildren().add(hbox);
        }


        Scene scene = new Scene(plate, 1920, 1080);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
}
