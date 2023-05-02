package com.example.ecommerceprototype.dam;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.*;

public class Asset {
    public String filepath;
    public int id;
    private String filename;
    private String filetype;
    private int filesize;
    private String uuid;
    private boolean isWatermarked;
    private Date date_added;


//Constructor

    public Asset(String filename, String filepath, String filetype,
                 int filesize, String uuid, boolean isWatermarked, Date date_added) {
        this.filename = filename;
        this.filepath = filepath;
        this.filetype = filetype;
        this.filesize = filesize;
        this.uuid = uuid;
        this.isWatermarked = isWatermarked;
        this.date_added = date_added;
    }

    public Asset(int id, String filename, String filepath, String filetype,
                 int filesize, String uuid, boolean isWatermarked, Date date_added) {
        this.id = id;
        this.filename = filename;
        this.filepath = filepath;
        this.filetype = filetype;
        this.filesize = filesize;
        this.uuid = uuid;
        this.isWatermarked = isWatermarked;
        this.date_added = date_added;
    }


    //Getters and setters

    public int getId() {
        return id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }

    public int getFilesize() {
        return filesize;
    }

    public void setFilesize(int filesize) {
        this.filesize = filesize;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public boolean getIsWatermarked() {
        return isWatermarked;
    }

    public void setWatermarked(boolean watermarked) {
        isWatermarked = watermarked;
    }

    public Date getDate_added() {
        return date_added;
    }

    private String getFileType(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex == -1) {
            return "";
        } else {
            return fileName.substring(dotIndex + 1);
        }
    }

    public void setDate_added(Date date_added) {
        this.date_added = date_added;
    }

}
