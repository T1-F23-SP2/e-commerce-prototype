package com.example.ecommerceprototype.cms;

import javafx.scene.layout.Pane;

import java.util.ArrayList;

public interface IArticle {
    Pane fetchArticle(String id);
    int getArticleCount();
    ArrayList<ArticleData> getArticlesByName();
    ArrayList<ArticleData> getArticlesByDate();
}
