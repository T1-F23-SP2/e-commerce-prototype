package MockShop;

import mockPIM.PlaceHolderInstGet;

import java.util.HashMap;

public class PlaceholderInstShop {

    static HashMap<String, Integer> map = new HashMap<String, Integer>();



    static {
        map.put(PlaceHolderInstGet.getInst1().getProductUUID(), 4);
        map.put(PlaceHolderInstGet.getInst2().getProductUUID(), 2);
        map.put(PlaceHolderInstGet.getInst3().getProductUUID(), 1);
    }
    static MockShopObject instShop1 = new MockShopObject(map);

    public static MockShopObject getInstShop1() {
        return instShop1;
    }
}
