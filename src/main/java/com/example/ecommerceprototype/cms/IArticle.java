package com.example.ecommerceprototype.cms;

import javafx.scene.layout.Pane;

import java.io.File;
import java.util.ArrayList;

public interface IArticle {
    ArrayList<File> getArticleFiles();
    int getArticleCount();
    ArrayList<String> getArticleNames();
    File getArticle(String articleName);
}
