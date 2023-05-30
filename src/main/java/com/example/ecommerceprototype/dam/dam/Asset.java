package com.example.ecommerceprototype.dam.dam;

import com.example.ecommerceprototype.dam.constants.Category;
import com.example.ecommerceprototype.dam.constants.Type;

public class Asset {

    private Type type;
    private String name;
    private String path;
    private String format;
    private Category category;
    private String uuid;

    public Asset(Type type, String name, String path, String format, Category category, String uuid) {
        this.type = type;
        this.name = name;
        this.path = path;
        this.format = format;
        this.category = category;
        this.uuid = uuid;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
