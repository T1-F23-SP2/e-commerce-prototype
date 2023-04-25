package com.example.ecommerceprototype.pim.sql_helpers;

import com.example.ecommerceprototype.pim.product_information.ProductInformation;

import java.util.HashMap;

public class CacheStore<K, V> extends HashMap<K, V> {
    private static final HashMap<Class<?>, CacheStore<?, ?>> cacheMap = new HashMap<>();


    // Cache store for different object types are automatically generated.
    public static <K, V> CacheStore<K, V> getCacheStore(Class<?> type) {
        if (!cacheMap.containsKey(type)) cacheMap.put(type, new CacheStore<K, V>());

        return (CacheStore<K, V>) cacheMap.get(type);
    }



    static public <K, V> boolean isInCacheStore(K id, Class<V> type) {
        CacheStore<K, V> cacheStore = CacheStore.getCacheStore(type);
        return cacheStore.containsKey(id);
    }

    static public <K, V> V getFromCache(K id, Class<V> type) {
        CacheStore<K, V> cacheStore = CacheStore.getCacheStore(type);
        return cacheStore.get(id);
    }

    static public <K, V> V putInCache(K id, V object) {
        CacheStore<K, V> cacheStore = CacheStore.getCacheStore(object.getClass());
        return cacheStore.put(id, object);
    }




    public static void main(String[] args) {
        int productId = 2;

        // Check that an object with given id and type, is present in cache
        if (CacheStore.isInCacheStore(productId, ProductInformation.class)) {
            System.out.println("id is present in cache");

            // Get pi object from cache store
            ProductInformation pi = CacheStore.getFromCache(productId, ProductInformation.class);

        } else {
            ProductInformation pi = new ProductInformation();

            // Put created ProductInformation object in cache store
            CacheStore.putInCache(productId, pi);
        }
    }
}