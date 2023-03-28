package com.example.ecommerceprototype.cms;

import javafx.scene.layout.Pane;

public interface IArticle {
    Pane fetch(String id);
    int getCount();
    ArticleData[] getArticlesByName();
    ArticleData[] getArticlesByDate();
}
