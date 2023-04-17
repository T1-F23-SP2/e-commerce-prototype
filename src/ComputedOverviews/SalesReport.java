package ComputedOverviews;

import mockPIM.PriceInformation;
import mockPIM.ProductInformation;

import java.math.BigDecimal;
import java.util.UUID;

class SalesReport {




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




    static int getOrders(PriceInformation priceInformation){
        // TODO: Use a uuid to get all the orders made with that uuid from our database

        // Placeholder
        return 1;
    }

    static BigDecimal calcMargin(PriceInformation priceInformation){

        BigDecimal one = new BigDecimal("1");
        // TODO: This returns the margin for 1 item
        return one.subtract(priceInformation.getPrice().divide(priceInformation.getBuyPrice()));
    }





    static int getAmountOfOrders(String UUID){

        // TODO: Query database for amount of orders of a specific product(UUID)

        return 0;
    }


    static String getFavoriteProduct(){

        // TODO: Query database for product with most "items quantity" and return UUID

        return null;
    }















}
