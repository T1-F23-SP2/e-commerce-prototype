package com.example.ecommerceprototype.cms;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ArticleManager implements IArticle{
    private static ArticleManager instance;
    private final String defaultArticleURL = "ArticlePage.fxml";

    private ArticleManager() {} //Zero-arg constructor
    static protected ArticleManager getInstance() {
        if (instance == null)
            instance = new ArticleManager();
        return instance;
    }




    @Override
    public int getArticleCount() { //Counts specifically text files in a directory
        File infile = new File("src/main/resources/com/example/ecommerceprototype/cms/Articles");
        if (!infile.exists())
            return 0;

        File[] allFiles = infile.listFiles();

        int count = 0;
        for (File f : allFiles) {
            if (f.getName().endsWith(".txt"))
                count++;
        }

        return count;
    }

    @Override
    public ArrayList<String> getArticleNames() {
        ArrayList<String> results = new ArrayList<>();
        File infile = new File("src/main/resources/com/example/ecommerceprototype/cms/Articles");
        if (!infile.exists())
            return results;

        File[] allFiles = infile.listFiles();

        for (File f : allFiles) {
            try (Scanner sc = new Scanner(f)) {
                results.add(sc.nextLine());
            }
            catch (FileNotFoundException fnfe) {
                System.out.println(fnfe.getMessage());
            }
        }

        return results;
    }
}
