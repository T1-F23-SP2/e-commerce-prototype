package ComputedOverviews;

import DB.DBManager;
import com.mongodb.client.MongoCollection;
import mockPIM.PriceInformation;
import mockPIM.ProductInformation;
import org.bson.Document;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.*;

public class SalesReport {




// TODO
//      fix trigger
    // qty trigger



    // Those are static
    double turnover;
    double wages;
    double interestIncome;
    double rentalIncome;
    double taxes;
    double productionCost;



    //qty trigger
    int lowInventory; // qty


    public SalesReport(double turnover, double wages, double interestIncome, double rentalIncome, double taxes, double productionCost, int orders, int lowInventory) {
        this.turnover = turnover;
        this.wages = wages;
        this.interestIncome = interestIncome;
        this.rentalIncome = rentalIncome;
        this.taxes = taxes;
        this.productionCost = productionCost;
        this.lowInventory = lowInventory;
    }



    public double calcGrossDomesticIncome (){

        double gdiValue;
        gdiValue = wages + turnover + interestIncome + rentalIncome + taxes - productionCost;

        return gdiValue;
    }




    public static int getOrders(PriceInformation priceInformation){
        // TODO: Use a uuid to get all the orders made with that uuid from our database
        MongoCollection<Document> finder = DBManager.databaseConn("SalesOverview");

        List<org.bson.Document> result = finder.find().into(new ArrayList<>());

        List<String> Word = new ArrayList<>();
        for (org.bson.Document ser : result) {
            String UUIDs = ser.getString("UUID");
            Word.add(UUIDs);
        }
            //TODO: Get input from user? to get UUID? or object? Mabye bword is passed as object?

        String bWord = Word.get(0);
        System.out.println(bWord);
        Document query = new Document("AmountSold", new Document("$eq", bWord));
        // Placeholder
        return 1;
    }

    public static BigDecimal calcMargin(PriceInformation priceInformation){

        BigDecimal one = new BigDecimal("1");
        // TODO: This returns the margin for 1 item
        return one.subtract(priceInformation.getPrice().divide(priceInformation.getBuyPrice()));
    }





    public static int getAmountOfOrders(String UUID) {


        /*MongoCollection<Document> finder = DBManager.databaseConn("SalesOverview");
        // Build query to retrieve documents with matching UUID
        Document query = new Document("UUID", UUID);

        // Project only the "AmountSold" field
        Document projection = new Document("AmountSold", 1);

        // Sort the results by descending order based on the "AmountSold" field
        Document sort = new Document("AmountSold", 1);

        // Retrieve the first document (i.e. the one with the highest "AmountSold" value)
        Document result = finder.find(query)
                .projection(projection)
                .sort(sort)
                .first();
        System.out.println(query);
        System.out.println(projection);
        System.out.println(sort);
        System.out.println(result);

        if (result != null) {
            int amountOfOrders = result.getInteger("AmountSold");
            return amountOfOrders;
        } else {
            // No matching documents found
            return 0;
        }*/


            // TODO: Query database for amount of orders of a specific product(UUID)


            org.bson.Document result = DBManager.queryDB(DBManager.databaseConn("SalesOverview"), "AmountSold");
            int amountOfOrders = result.getInteger("AmountSold");

            return amountOfOrders;


    }



    public static String getFavoriteProduct(){

        // TODO: Query database for product with most "items quantity" and return UUID
        MongoCollection<Document> finder = DBManager.databaseConn("SalesOverview");

        List<org.bson.Document> result = finder.find().into(new ArrayList<>());

        List<Integer> Max = new ArrayList<>();
        for (org.bson.Document ser : result) {
            int amount = ser.getInteger("AmountSold");
            Max.add(amount);
        }

        Max.sort(Collections.reverseOrder());
        int maxVal = Max.get(0);

        Document query = new Document("AmountSold", new Document("$eq", maxVal));

        // Retrieve the first document (i.e. the one with the highest "AmountSold" value)
        Document result2 = finder.find(query).first();

        String GetUUID = (String) (result2 != null ? result2.get("UUID") : null);
        int GetAmount = (int) Objects.requireNonNull(result2).get("AmountSold");

       if (Max != null)
       {
           return "UUID: " + GetUUID + " Amount: " + GetAmount + "";
       }
        return null;
    }















}
