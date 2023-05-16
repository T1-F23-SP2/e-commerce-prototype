package com.example.ecommerceprototype.dam.constants;

public enum FileFormat 
{
    JPEG ("JPEG"),
    JPG ("JPG"),
    PNG ("PNG"),
    PDF ("PDF"),
    DOCX ("DOCX"),
    TXT ("TXT"),
    GIF ("GIF");

    private final String value;

    private FileFormat(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
