package com.example.ecommerceprototype.cms;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class ArticleManagerTests {
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
}