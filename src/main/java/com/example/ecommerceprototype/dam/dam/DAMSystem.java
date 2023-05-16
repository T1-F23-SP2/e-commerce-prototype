package com.example.ecommerceprototype.dam.dam;

import com.example.ecommerceprototype.dam.constants.Constants;

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


    private String shortenURL (String url)
    {
        if (url.startsWith(Constants.AZURE_Start_URL))
        {
            url = url.substring(37);
            System.out.println(url);
        }
        return url;
    }

    public void addAsset()
    {


        //Asset asset = new Asset(name)

        //String url = FileConn.uploadFile()


        //DBConn.addAsset(asset);
    }


}
