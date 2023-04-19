import DB.DBManager;
import Visuals.TableCreator;
import com.mongodb.client.MongoCollection;
import mockPIM.PlaceHolderInstGet;
import org.bson.Document;
import ComputedOverviews.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        Inventory inventory1 = new Inventory("Marcel", 6);
        Inventory inventory2 = new Inventory("Danieal", 100);



        // Setup of database

//        MongoCollection<org.bson.Document> connDBItem = DBManager.databaseConn("Item");
//        connDBItem.insertOne(new Document()
//                .append("UUID", PlaceHolderInstGet.getInst1().getProductUUID())
//                .append("QTY", 10)
//        );
//        connDBItem.insertOne(new Document()
//                .append("UUID", PlaceHolderInstGet.getInst2().getProductUUID())
//                .append("QTY", 15)
//        );
//        connDBItem.insertOne(new Document()
//                .append("UUID", PlaceHolderInstGet.getInst3().getProductUUID())
//                .append("QTY", 55)
//        );


//        MongoCollection<org.bson.Document> connDBSalesOverview = DBManager.databaseConn("SalesOverview");
//        connDBSalesOverview.insertOne(new Document()
//                .append("UUID", PlaceHolderInstGet.getInst1().getProductUUID())
//                .append("QTY", 10)
//        );



//        MongoCollection<org.bson.Document> connDBSalesOverview = DBManager.databaseConn("SalesOverview");
//        connDBSalesOverview.insertOne(new Document()
//                .append("UUID", PlaceHolderInstGet.getInst1().getProductUUID())
//                .append("AmountSold", 12)
//                .append("Margin", 0.3)
//        );
//        connDBSalesOverview.insertOne(new Document()
//                .append("UUID", PlaceHolderInstGet.getInst2().getProductUUID())
//                .append("AmountSold", 22)
//                .append("Margin", 0.35)
//        );
//        connDBSalesOverview.insertOne(new Document()
//                .append("UUID", PlaceHolderInstGet.getInst3().getProductUUID())
//                .append("AmountSold", 24)
//                .append("Margin", 0.4)
//        );

//        MongoCollection<org.bson.Document> connDBSalesOrderHistory = DBManager.databaseConn("OrderHistory");
//        connDBSalesOrderHistory.insertOne(new Document("_id", 1)
//                .append("UUID", PlaceHolderInstGet.getInst1().getProductUUID())
//                .append("Date", LocalDate.now().atTime(LocalTime.now()))
//                .append("Amount", 5)
//        );
//        connDBSalesOrderHistory.insertOne(new Document("_id", 2)
//                .append("UUID", PlaceHolderInstGet.getInst2().getProductUUID())
//                .append("Date", LocalDate.now().atTime(LocalTime.now()).plusDays(1))
//                .append("Amount", 2)
//        );
//        connDBSalesOrderHistory.insertOne(new Document("_id", 3)
//                .append("UUID", PlaceHolderInstGet.getInst3().getProductUUID())
//                .append("Date", LocalDate.now().atTime(LocalTime.now()).plusDays(5))
//                .append("Amount", 1)
//        );


        //System.out.println(SalesReport.getAmountOfOrders(PlaceHolderInstGet.getInst1().getProductUUID()));
        //System.out.println(SalesReport.getAmountOfOrders(PlaceHolderInstGet.getInst2().getProductUUID()));
        //System.out.println(SalesReport.getAmountOfOrders(PlaceHolderInstGet.getInst3().getProductUUID()));


        System.out.println(SalesReport.getFavoriteProduct());

        //System.out.println(inventory1.calcMargin());
        //System.out.println(inventory2.calcMargin());
        //TableCreator.showTable();



    }
}