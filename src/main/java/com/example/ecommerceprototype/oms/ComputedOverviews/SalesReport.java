package com.example.ecommerceprototype.oms.ComputedOverviews;

import com.example.ecommerceprototype.oms.DB.DBManager;
import com.example.ecommerceprototype.oms.mockPIM.ProductInformation;
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

    public static BigDecimal rev(ProductInformation productInformation) {
        int j = 0;
        BigDecimal qRev = BigDecimal.valueOf(SalesReport.getAmountOfOrders(productInformation.getProductUUID())).multiply(productInformation.getPriceInformation().getBuyPrice());
        BigDecimal PRev =getQTY(productInformation.getProductUUID()).multiply(productInformation.getPriceInformation().getBuyPrice());
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
