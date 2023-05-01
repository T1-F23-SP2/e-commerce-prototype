package com.example.ecommerceprototype.cms;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class ArticleController implements Initializable {

    @FXML
    VBox articleList_VBox;
    @FXML
    Label articleTitle_Label;
    @FXML
    Label articleAuthor_Label;
    @FXML
    Label articleDate_Label;
    @FXML
    TextArea articletText_TextArea;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<String> articleNames = CMS.articles.getArticleNames();
        for (int i = 0; i < articleNames.size(); i++) {
            int index = i;
            Button b = new Button(articleNames.get(i));
            b.setPrefWidth(160);
            b.setPrefHeight(60);
            b.setStyle("-fx-font-size:14");
            b.setTextAlignment(TextAlignment.CENTER);
            b.setWrapText(true);
            b.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    loadArticle(findArticle(articleNames.get(index)));
                }
            });

            articleList_VBox.getChildren().add(b);
        }
        loadArticle(findArticle("Welcome to Articles"));
    }

    public File findArticle(String name) {
        ArrayList<File> articleFiles = CMS.articles.getArticleFiles();
        for (int i = 0; i < articleFiles.size(); i++) {
            try (Scanner sc = new Scanner(articleFiles.get(i))) {
                if (sc.nextLine().equals(name))
                    return articleFiles.get(i);
            }
            catch (FileNotFoundException fnfe) {System.out.println(fnfe.getMessage());}
        }
        return null;
    }
    public void loadArticle(File f) {
        try (Scanner sc = new Scanner(f)) {

            articleTitle_Label.setText(sc.nextLine());
            articleAuthor_Label.setText(sc.nextLine());
            articleDate_Label.setText(sc.nextLine());

            String articleText = "";
            while (sc.hasNextLine()) {
                articleText += sc.nextLine() + "\n";
            }
            articletText_TextArea.setText(articleText);
        }
        catch (FileNotFoundException fnfe) {
            System.out.println(fnfe.getMessage());
        }
    }
}
