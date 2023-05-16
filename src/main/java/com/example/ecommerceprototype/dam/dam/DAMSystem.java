package com.example.ecommerceprototype.dam.dam;

import com.example.ecommerceprototype.dam.constants.Category;
import com.example.ecommerceprototype.dam.constants.Constants;
import com.example.ecommerceprototype.dam.constants.FileFormat;
import com.example.ecommerceprototype.dam.constants.Type;

public class DAMSystem {
    FileSystem FileConn;
    DBSystem DBConn;
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
        FileSystem fileConn = FileSystem.getInstance();
        DBSystem DBConn = DBSystem.getInstance();
    }


    public void addAsset(String name, Type type, Category category, FileFormat format, String uuid)
    {
        String url = FileConn.uploadFile(type, category);
        Asset asset = new Asset(type, name, url, format, category, uuid);
        DBConn.addAsset(asset);
    }


}
