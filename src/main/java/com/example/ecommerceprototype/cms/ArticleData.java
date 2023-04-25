package com.example.ecommerceprototype.cms;

public class ArticleData {
    public String articleId;
    public String title;
    public String author;
    public String modifiedDate;

    public ArticleData(String articleId, String title, String author, String modifiedDate) {
        this.articleId = articleId;
        this.title = title;
        this.author = author;
        this.modifiedDate = modifiedDate;
    }
}
