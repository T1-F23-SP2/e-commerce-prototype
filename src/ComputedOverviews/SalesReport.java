package ComputedOverviews;

import DB.DBManager;
import com.mongodb.client.MongoCollection;
import mockPIM.PlaceHolderInstGet;
import mockPIM.PriceInformation;
import org.bson.Document;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
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


    public static boolean getOrders(){
        // TODO: Get all orders from orderHistory database table, and save it 
        MongoCollection<Document> finder = DBManager.databaseConn("Item");

        List<org.bson.Document> result = finder.find().into(new ArrayList<>());

        List<Integer> QTY = new ArrayList<>();
        for (org.bson.Document ser : result) {
            int QTYs = ser.getInteger("QTY");
            QTY.add(QTYs);
        }
            //TODO: Get input from user? to get UUID? or object? Mabye bword is passed as object?


        System.out.println(QTY);
        Document query = new Document("QTY", new Document("$eq", 0));


        return false;
    }

    public static BigDecimal calcMargin(PriceInformation priceInformation){
        MathContext ones = new MathContext(1);
        BigDecimal one = new BigDecimal("1");
        BigDecimal hundred = new BigDecimal("100");
        // TODO: This returns the margin for 1 item

        BigDecimal Res = priceInformation.getPrice().divide(priceInformation.getBuyPrice());
        BigDecimal Res2 = one.divide(Res, new MathContext(3));
        return Res2.multiply(hundred.round(ones));
    }

    public static BigDecimal calcMarginKR(PriceInformation priceInformation){
        MathContext ones = new MathContext(1);
        BigDecimal one = new BigDecimal("1");
        BigDecimal hundred = new BigDecimal("100");
        // TODO: This returns the margin for 1 item

        return priceInformation.getPrice().subtract(priceInformation.getBuyPrice());
    }

    public static BigDecimal rev(PriceInformation priceInformation) {
        //TODO Skal ikke bruge instances til calc
        BigDecimal qRev = BigDecimal.valueOf(getAmountOfOrders(PlaceHolderInstGet.getInst2().getProductUUID())).multiply(priceInformation.getPrice());
        BigDecimal PRev =getQTY(PlaceHolderInstGet.getInst2().getProductUUID()).multiply(priceInformation.getBuyPrice());
        BigDecimal tRev = qRev.subtract(PRev);
        return tRev;
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

        // Fix this, it has to get the right value and not 55 everytime

            org.bson.Document result = DBManager.queryDB(DBManager.databaseConn("SalesOverview"), UUID);
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

    public static BigDecimal getQTY(String UUID)
    {

        org.bson.Document result = DBManager.queryDB(DBManager.databaseConn("Item"), UUID);
        BigDecimal QTY = BigDecimal.valueOf(result.getInteger("QTY"));

        return QTY;


    }














}
