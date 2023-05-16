package com.example.ecommerceprototype.cms;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
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
    TextArea articleText_TextArea;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<String> articleNames = CMS.articles.getArticleNames();
        for (int i = 0; i < articleNames.size(); i++) {
            int index = i;
            Button b = new Button(articleNames.get(i));
            b.setPrefWidth(180);
            b.setPrefHeight(35);
            b.setStyle("-fx-font-size:14; -fx-background-radius: 0;");
            b.setTextAlignment(TextAlignment.CENTER);
            b.setWrapText(true);
            b.setOnAction(actionEvent -> loadArticle(CMS.articles.getArticle(articleNames.get(index))));

            articleList_VBox.getChildren().add(b);
        }
        loadArticle(new File("src/main/resources/com/example/ecommerceprototype/cms/Articles/DefaultFile.txt"));
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
            articleText_TextArea.setText(articleText);
        }
        catch (FileNotFoundException fnfe) {
            System.out.println(fnfe.getMessage());
        }
    }
}
