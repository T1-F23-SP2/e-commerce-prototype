import org.bson.types.ObjectId;

import java.util.UUID;

public class Items {


    int id;
    String name;
    int qty;
    double price;


    public Items(int id, String name, int qty, double price) {
        this.id = id;
        this.name = name;
        this.qty = qty;
        this.price = price;
    }



}


