package com.example.ecommerceprototype.oms.ComputedOverviews;

import com.example.ecommerceprototype.oms.DB.DBManager;
import com.mongodb.client.MongoCollection;
import com.example.ecommerceprototype.oms.mockPIM.PlaceHolderInstGet;
import com.example.ecommerceprototype.oms.mockPIM.PriceInformation;
import org.bson.Document;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

public class SalesReport {








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

        BigDecimal Res = priceInformation.getPrice().divide(priceInformation.getBuyPrice());
        BigDecimal Res2 = one.divide(Res, new MathContext(3));
        return hundred.subtract(Res2.multiply(hundred.round(ones)));
    }

    public static BigDecimal calcMarginKR(PriceInformation priceInformation){
        MathContext ones = new MathContext(1);
        BigDecimal one = new BigDecimal("1");
        BigDecimal hundred = new BigDecimal("100");

        return priceInformation.getPrice().subtract(priceInformation.getBuyPrice());
    }

    public static BigDecimal rev(PriceInformation priceInformation) {
        int j = 0;
        //TODO Skal ikke bruge instances til calc
        BigDecimal qRev = BigDecimal.valueOf(SalesReport.getAmountOfOrders(PlaceHolderInstGet.productArray[j].getProductUUID())).multiply(priceInformation.getPrice());
        BigDecimal PRev =getQTY(PlaceHolderInstGet.productArray[j].getProductUUID()).multiply(priceInformation.getBuyPrice());
        BigDecimal tRev = qRev.subtract(PRev);
        j++;
        return tRev;
    }

    public static int getAmountOfOrders(String UUID) {

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

        if (Max != null)
        {
            return GetUUID;
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
