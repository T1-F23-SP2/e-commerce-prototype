package com.example.ecommerceprototype.dam;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileSaver {
    private static List<File> files = new ArrayList<>();

    public static void setFiles(List<File> files) {
        FileSaver.files = files;
    }

    public static List<File> getFiles() {
        return files;
    }
}
