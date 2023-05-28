package com.example.ecommerceprototype.dam.dam;

import com.example.ecommerceprototype.dam.constants.Category;
import com.example.ecommerceprototype.dam.constants.Type;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DBSystemTest {


    @Test
    void getURLsByUUID() {
        var db = new DBSystem();
        List<String> expected = new ArrayList<>();
        expected.add("/productfiles/harddisk%2Funittest%2Funittest.sql");

        assertIterableEquals(expected, db.getURLsByUUID("unittest"));
    }

    //Please run this before deleteAssetTest
    @Test
    void addAsset() {
        var db = new DBSystem();
        Asset testAsset = new Asset(
                Type.PRODUCT_FILE,
                "testAsset",
                "/path/to/test.test",
                "test",
                Category.CABLES,
                "testUUID");
        int assetID = db.addAsset(testAsset);
        System.out.println(assetID);
        assertTrue(0<assetID);
    }

    @Test
    void deleteAsset() {
        var db = new DBSystem();
        assertTrue(db.deleteAsset(57));
    }

    @Test
    void addTag() {
        var db = new DBSystem();
        int tagID = db.addTag("test");
        System.out.println(tagID);
        assertTrue(0<tagID);
    }

    @Test
    void addTagAssignment() {
        var db = new DBSystem();
        assertTrue(db.addTagAssignment(57,21));
    }

    @Test
    void getAllTags() {
        //this test does not actually test the function directly.
        //is will call the method and put it into a list and then the test
        // will check for a known tag.
        var db = new DBSystem();
        List<String> actual = db.getAllTags();
        assertTrue(actual.contains("cpu"));

    }

    @Test
    void getTagsForAssetID() {
        //asset57 is has a known tag "test"
        var db = new DBSystem();
        List<String> actual = db.getTagsForAssetID(57);
        List<String> expected = new ArrayList<>();
        expected.add("test");

        assertIterableEquals(actual,expected);
    }

}