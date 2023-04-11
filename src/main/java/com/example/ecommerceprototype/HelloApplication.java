package com.example.ecommerceprototype;

import com.example.ecommerceprototype.cms.CMS;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("cms/ArticlePage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1920, 1000);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        Pane test = CMS.getInstance().fetchComponent("ArticlePage");
        ArrayList<String> compList = CMS.getInstance().getComponentList(test);
        for(String s : compList)
            System.out.println(s);

        System.out.println("\n");

        System.out.println("\n");

        System.out.println("ArticleCount: " + CMS.getInstance().articles.getArticleCount());

        stage.setScene(new Scene(CMS.getInstance().articles.fetchArticle("DefaultArticle"), 1920, 1000));
    }

    public static void main(String[] args) {
        launch();
    }
}