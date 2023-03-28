import org.bson.types.ObjectId;

public class Items {


    ObjectId _id;
    String name;
//    double price;
//    int stock;

    String city;
    int phone;
    String address;
    int zipCode;


    public Items(ObjectId _id, String name, String city, int phone, String address, int zipCode) {
        this._id = _id;
        this.name = name;
        this.city = city;
        this.phone = phone;
        this.address = address;
        this.zipCode = zipCode;
    }
}


