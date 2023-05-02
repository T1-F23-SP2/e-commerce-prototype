package MockShop;

import java.util.HashMap;

public class MockShopObject {

    HashMap<String, Integer> map = new HashMap<String, Integer>();


    public MockShopObject(HashMap<String, Integer> map) {
        this.map = map;
    }


    public HashMap<String, Integer> getMap() {
        return map;
    }

}
