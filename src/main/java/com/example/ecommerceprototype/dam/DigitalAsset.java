package com.example.ecommerceprototype.dam;

import java.util.Date;

public class DigitalAsset {
    private int id;
    private String filename;
    private String filepath;
    private String filetype;
    private int filesize;
    private String uuid;


    private Date date_added;


    //Constructor

    public DigitalAsset(String filename, String filepath, String filetype,
                        int filesize, String uuid, Date date_added) {
        this.filename = filename;
        this.filepath = filepath;
        this.filetype = filetype;
        this.filesize = filesize;
        this.uuid = uuid;
        this.date_added = date_added;
    }


    //Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Date getDate_added() {
        return date_added;
    }

    public void setDate_added(Date date_added) {
        this.date_added = date_added;
    }

}
