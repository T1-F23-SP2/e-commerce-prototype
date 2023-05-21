package com.example.ecommerceprototype.dam.dam;

import com.example.ecommerceprototype.dam.constants.Category;
import com.example.ecommerceprototype.dam.constants.Constants;
import com.example.ecommerceprototype.dam.constants.Type;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class DAMSystem {
    FileSystem FileConn;
    DBSystem DBConn;

    public Connection getConn() {

       return DBConn.getConn();
    }



    private static DAMSystem instance;

    DAMSystem() {
        initializeDAMSystem();
    }

    public static DAMSystem getInstance() {
        if (instance == null) {
            instance = new DAMSystem();
        }
        return instance;
    }

    private void initializeDAMSystem()
    {
        FileConn = FileSystem.getInstance();
        DBConn = DBSystem.getInstance();
    }


    public void addAsset(String name, Type type, Category category, String format, String uuid, String oripath)
    {
        String url = FileConn.uploadFile(name, type, category, uuid, oripath);
        Asset asset = new Asset(type, name, url, format, category, uuid);
        DBConn.addAsset(asset);
    }


    public void deleteAsset(int id_in, String filename_in, String type_in, String category_in, String uuid_in)
    {
        DBConn.deleteAsset(id_in);
        boolean completed = FileConn.deleteFile(filename_in, type_in, category_in, uuid_in);

        System.out.println("Delete completed: " + completed);
    }


    public void tagAssignment (int assetID_in, String tagname_in)
    {
        int tagID = DBConn.addTag(tagname_in);
        DBConn.addTagAssignment(assetID_in, tagID);
    }

    public List<String> getTagsForAssetID (int assetid_in)
    {
        return DBConn.getTagsForAssetID(assetid_in);
    }


    public void downloadAsset(String filename_in, String type_in, String category_in, String uuid_in)
    {
        FileConn.downloadFile(filename_in, type_in, category_in, uuid_in);
    }


    public boolean watermarkpt1(String filename_in, String type_in, String category_in, String uuid_in)
    {
       return FileConn.watermarkpt1(filename_in, type_in, category_in, uuid_in);

    }


    private Category extractCategory(String cat_in)
    {
        Category cat = Category.valueOf(cat_in.toUpperCase());
        return cat;
    }

    private String extractFileFormat(String name_in)
    {
        String formatString = name_in.substring(name_in.lastIndexOf(".") + 1);

        return formatString;
    }


}



















