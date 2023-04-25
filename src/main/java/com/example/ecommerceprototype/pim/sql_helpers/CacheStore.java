package com.example.ecommerceprototype.pim.sql_helpers;

import java.util.HashMap;

public class CacheStore<K, V> extends HashMap<K, V> {
    private static final HashMap<Class<?>, CacheStore<?, ?>> cacheMap = new HashMap<>();

    public static <K, V> CacheStore<K, V> getCacheStore(Class<?> type) {
        if (!cacheMap.containsKey(type)) cacheMap.put(type, new CacheStore<K, V>());

        return (CacheStore<K, V>) cacheMap.get(type);
    }
}