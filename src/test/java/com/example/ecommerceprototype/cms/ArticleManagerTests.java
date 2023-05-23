package com.example.ecommerceprototype.cms;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class ArticleManagerTests extends Application {
    @BeforeAll
    public static void initializeJavaFX() {
        launch();
    }

    @Test
    void getArticleFilesList() {
        ArrayList<File> articleFiles = CMS.articles.getArticleFiles();
        assertTrue(articleFiles.size() > 0);
    }

    @Test
    void getArticleNamesList() {
        ArrayList<String> articleNames = CMS.articles.getArticleNames();
        assertTrue(articleNames.size() > 0);
    }

    @Test
    void getDefaultArticleFile() {
        File f = CMS.articles.getArticle("Welcome to Articles");
        assertNotNull(f);
    }

    @Test
    void getIncorrectArticleFile() {
        File f = CMS.articles.getArticle("Non EXISTING!!! <>--");
        assertNull(f);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                getArticleFilesList();
                getArticleNamesList();
                getIncorrectArticleFile();
                getDefaultArticleFile();
            }
        });
        Platform.exit();
    }
}