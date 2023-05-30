package com.example.ecommerceprototype.dam.dam;

import com.example.ecommerceprototype.dam.constants.Category;
import com.example.ecommerceprototype.dam.constants.Constants;
import com.example.ecommerceprototype.dam.constants.Type;
import javafx.scene.image.Image;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
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


    public int addAsset(String name, Type type, Category category, String format, String uuid, String oripath)
    {
        boolean added = false;

        String url = FileConn.uploadFile(name, type, category, uuid, oripath);
        Asset asset = new Asset(type, name, url, format, category, uuid);
        return DBConn.addAsset(asset);
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


    public boolean watermark(String filename_in, String type_in, String category_in, String uuid_in)
    {
       return FileConn.watermark(filename_in, type_in, category_in, uuid_in);

    }

    public void deleteTagAssignment(int assetID_in, String tagName_in)
    {
        DBConn.deleteTagAssignment(assetID_in, tagName_in);
    }


    public Image getImageFromURL(String url)
    {
        return FileConn.downloadImageFromURL(url);
    }


    public List<Image> getImageListFromUUID(String UUID)
    {
        List<Image> images = new ArrayList<>();
        List<String> urls = DBConn.getURLsByUUID(UUID);

        for (String url : urls)
        {
            Image image = FileConn.downloadImageFromURL(url);
            images.add(image);
        }
        return images;
    }


    public boolean downloadFilesFromUUID(String UUID, String downloadPath)
    {
        List<String> urls = DBConn.getURLsByUUID(UUID);
        if (!urls.isEmpty()) {
            for (String url : urls)
            {

                FileConn.downloadFileFromURL(downloadPath, Constants.AZURE_Start_URL +url, UUID);
            }
            return true;
        } else
        {
            return false;
        }
    }




}



















