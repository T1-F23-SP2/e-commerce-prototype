package com.example.ecommerceprototype.dam;
import java.util.*;

public class FileSystem {
    private Map<String, List<String>> fileTagMap;
    private TagSystem tagSystem;

    public FileSystem() {
        fileTagMap = new HashMap<>();
        tagSystem = new TagSystem();
    }

    public boolean addFile(String fileName, List<String> tags) {
        fileTagMap.put(fileName, tags);
        tagSystem.addTags(fileName, tags);
        return true;
    }

    public List<String> searchFiles(List<String> tags) {
        return tagSystem.searchTags(tags);
    }
}


