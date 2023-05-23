package com.example.ecommerceprototype.dam.service;

import com.example.ecommerceprototype.dam.dam.DAMSystem;
import javafx.scene.image.Image;

import java.util.List;

public class DAMIntegration {
    DAMSystem dam = DAMSystem.getInstance();


    //Input uuid, and it will return every image associated with that uuid
    public List<Image> getImageListFromUUID(String uuid_in)
    {
        return dam.getImageListFromUUID(uuid_in);
    }


}
