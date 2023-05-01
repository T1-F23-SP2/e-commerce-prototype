package com.example.ecommerceprototype.cms;

import javafx.scene.layout.Pane;

import java.io.File;
import java.util.ArrayList;

public interface IArticle {
    int getArticleCount();
    ArrayList<String> getArticleNames();
    ArrayList<File> getArticleFiles();
}
