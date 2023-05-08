package OrderStatus;

import DB.DBManager;
import MockShop.MockShopObject;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.time.LocalDate;
import java.util.Arrays;

public class OrderManager {







    // Method to send order, shop has to call this method, when a order is sent.

    // First place in database, then ping database to get id and information(Ping the database for what???)
    // Then place in OrderGui



    public static void sendOrder(MockShopObject mockShopObject){

        int id = 11;



        // Code to convert
        String resultUUID = String.join(",", mockShopObject.getMap().keySet());


        // Code to convert the values(amount) to a list and comma seperate it to a string
        Integer[] amountArray = mockShopObject.getMap().values().toArray(new Integer[0]);
        String resultAmount = String.join(",", Arrays.toString(amountArray));


        MongoCollection<Document> connDB = DBManager.databaseConn("OrderHistory");
        connDB.insertOne(new Document().append("_id", id).append("UUID", resultUUID).append("Date", LocalDate.now()).append("Amount", resultAmount));




    }


    // Maybe
    public static void getOrder(){

    }




}
