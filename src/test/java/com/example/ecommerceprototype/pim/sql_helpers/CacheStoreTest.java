package com.example.ecommerceprototype.pim.sql_helpers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CacheStoreTest {
    private static class ExampleClass {
        public int id;
    }

    private static class AnotherExample {
        public int id;
    }

    @Test
    void getCacheStore() {
        CacheStore<Integer, ExampleClass> cacheStoreForExampleClass = CacheStore.getCacheStore(ExampleClass.class);

        // Assert that the two value are strictly the same. E.g. have same memory pointer
        assertSame(cacheStoreForExampleClass, CacheStore.getCacheStore(ExampleClass.class));

        // Assert that CacheStore returns another object, when another class is passed.
        assertNotSame(cacheStoreForExampleClass, CacheStore.getCacheStore(AnotherExample.class));
    }

    @Test
    void testPutInCacheStore() {
        int id = 1;

        // First assert that an object with 'id' is not in the cache store.
        assertFalse(CacheStore.isInCacheStore(id, ExampleClass.class));

        ExampleClass testObj = new ExampleClass();
        testObj.id = id;

        CacheStore.putInCache(id, testObj);

        // Then assert that the object is in cachestore
        assertTrue(CacheStore.isInCacheStore(id, ExampleClass.class));
    }

    @Test
    void getFromCache() {
        int id = 2;

        ExampleClass testObj = new ExampleClass();
        testObj.id = id;

        CacheStore.putInCache(id, testObj);

        // Assert that the two object are strictly the same. E.g. point to same memory space.
        assertSame(testObj, CacheStore.getFromCache(id, ExampleClass.class));
    }


    @Test
    void removeFromCache() {
        int id = 3;

        ExampleClass testObj = new ExampleClass();
        testObj.id = id;

        CacheStore.putInCache(id, testObj);

        // Then assert that the object is in cachestore
        assertTrue(CacheStore.isInCacheStore(id, ExampleClass.class));

        CacheStore.removeFromCache(id, ExampleClass.class);

        // Then assert that the object is no longer in cache store.
        assertFalse(CacheStore.isInCacheStore(id, ExampleClass.class));
    }
}