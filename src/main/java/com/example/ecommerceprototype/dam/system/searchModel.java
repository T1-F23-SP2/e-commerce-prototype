package com.example.ecommerceprototype.dam.system;


public class searchModel {
    int id;
    String name;
    String format;
    String category;
    String type;
    String uuid;
    String  tags;
    String path;

    public searchModel (int id, String name, String format, String category, String type, String uuid, String tags, String path) {
        this.id = id;
        this.name = name;
        this.format = format;
        this.category = category;
        this.type = type;
        this.uuid = uuid;
        this.tags = tags;
        this.path = path;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
