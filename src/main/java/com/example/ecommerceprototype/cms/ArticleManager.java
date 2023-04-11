package com.example.ecommerceprototype.cms;

import javafx.scene.layout.Pane;

import java.io.File;

public class ArticleManager implements IArticle{

    private static ArticleManager instance;
    private final String defaultArticlePage = "PLACEHOLDER";

    private ArticleManager() {} //Zero-arg constructor
    static protected ArticleManager getInstance() {
        if (instance == null)
            instance = new ArticleManager();
        return instance;
    }


    @Override
    public Pane fetch(String id) {
        return null;
    }

    @Override
    public int getCount() { //Counts specifically text files in a directory
        File f = new File("com/example/ecommerceprototype/cms/Articles");
        if (!f.exists())
            return 0;

        File[] allFiles = f.listFiles();

        int count = 0;
        for (int i = 0; i < allFiles.length; i++) {
            if (allFiles[i].getName().endsWith(".txt"))
                count++;
        }

        return count;
    }

    @Override
    public ArticleData[] getArticlesByName() {
        return new ArticleData[0];
    }

    @Override
    public ArticleData[] getArticlesByDate() {
        return new ArticleData[0];
    }
}
