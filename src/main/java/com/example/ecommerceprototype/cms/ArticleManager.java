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
    public Pane fetchArticle(String id) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(defaultArticleURL));

        File infile = new File("src/main/resources/com/example/ecommerceprototype/cms/Articles/"+id+".txt");
        String title = "";
        String date = "";
        String text = "";

        try(Scanner sc = new Scanner(infile)) {
            title = sc.nextLine();
            date = sc.nextLine();
            while (sc.hasNextLine())
                text += sc.nextLine() + "\n";
        }
        catch (FileNotFoundException fnfe) {System.out.println(fnfe.getMessage());}

        Pane pane = new Pane();
        try {
            pane = loader.load();

            Label titleLabel = (Label) CMS.getInstance().find(pane, "articleTitle_Label");
            titleLabel.setText(title);

            Label dateLabel = (Label) CMS.getInstance().find(pane, "articleDate_Label");
            dateLabel.setText(date);

            TextArea textTextArea = (TextArea) CMS.getInstance().find(pane, "articletText_TextArea");
            textTextArea.setText(text);

        }
        catch (IOException ioe) { System.out.println(ioe.getMessage()); }
        return pane;
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
    public ArrayList<ArticleData> getArticlesByName() {
        ArrayList<ArticleData> allArticles = createArticleDataList();

        return null;
    }

    @Override
    public ArrayList<ArticleData> getArticlesByDate() {
        ArrayList<ArticleData> allArticles = createArticleDataList();

        return null;
    }

    private ArrayList<ArticleData> createArticleDataList() {
        File infile = new File("src/main/resources/com/example/ecommerceprototype/cms/Articles");
        if (!infile.exists())
            return null;

        File[] allFiles = infile.listFiles();
        ArrayList<ArticleData> allArticles = new ArrayList<>();

        for (File f : allFiles) {
            if (!f.getName().endsWith(".txt"))
                continue;

            String articleId = "";
            String title = "";
            String author = "";
            String modDate = "";

            try (Scanner sc = new Scanner(f)) {
                articleId = sc.nextLine();
                title = sc.nextLine();
                author = sc.nextLine();
                modDate = sc.nextLine();
            }
            catch (FileNotFoundException fnfe) {System.out.println(fnfe.getMessage());};

            allArticles.add(new ArticleData(articleId, title, author, modDate));
        }
        return allArticles;
    }
}
