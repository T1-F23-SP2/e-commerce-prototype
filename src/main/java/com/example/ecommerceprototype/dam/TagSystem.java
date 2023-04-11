package com.example.ecommerceprototype.dam;

import java.util.*;

public class TagSystem {
    private Map<String, List<String>> tagMap;

    public TagSystem() {
        tagMap = new HashMap<>();
    }

    public void addTags(String item, List<String> tags) {
        for (String tag : tags) {
            if (!tagMap.containsKey(tag)) {
                tagMap.put(tag, new ArrayList<String>());
            }
            tagMap.get(tag).add(item);
        }
    }

    public List<String> searchTags(List<String> tags) {
        Set<String> resultSet = new HashSet<>();
        for (String tag : tags) {
            if (tagMap.containsKey(tag)) {
                resultSet.addAll(tagMap.get(tag));
            }
        }
        return new ArrayList<>(resultSet);
    }
}

