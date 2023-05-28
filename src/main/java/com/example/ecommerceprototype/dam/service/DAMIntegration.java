package com.example.ecommerceprototype.dam.service;

import com.example.ecommerceprototype.dam.dam.DAMSystem;
import javafx.scene.image.Image;

import java.io.File;
import java.util.List;

public class DAMIntegration {
    DAMSystem dam = DAMSystem.getInstance();


    //Input uuid, and it will return every image associated with that uuid
    public List<Image> getImageListFromUUID(String uuid_in)
    {
        return dam.getImageListFromUUID(uuid_in);
    }



    //Input uuid. Will create a folder in the download path and put all files inside the folder. Files do not include images.
    //Files include everything but the images, that is associated with the uuid.
    //If a folder already exist with the same name, it will download into the folder.
    public boolean downloadFilesFromUUID (String UUID)
    {
        //Default download path is the system download folder. Use code:
        //"String desiredDownloadPath = System.getProperty("user.home") + File.separator + "Downloads"+File.separator;".
        //Can be changed to whatever folder you want.

        String desiredDownloadPath = System.getProperty("user.home") + File.separator + "Downloads"+File.separator;

        return dam.downloadFilesFromUUID(UUID, desiredDownloadPath);
    }





}
