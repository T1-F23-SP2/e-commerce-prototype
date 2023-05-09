package MockShop;

import Customers.Customer;
import mockPIM.PlaceHolderInstGet;

import java.util.HashMap;

public class PlaceholderInstShop {

    static HashMap<String, Integer> map = new HashMap<String, Integer>();
    static Customer customer = new Customer("Danieal", "Dani@Email.com", 12345, "Odensevej", 5000);



    static {
        map.put(PlaceHolderInstGet.getInst1().getProductUUID(), 1);
        map.put(PlaceHolderInstGet.getInst2().getProductUUID(), 2);
        map.put(PlaceHolderInstGet.getInst3().getProductUUID(), 55);
    }
    static MockShopObject instShop1 = new MockShopObject(map, customer);

    public static MockShopObject getInstShop1() {
        return instShop1;
    }
}
