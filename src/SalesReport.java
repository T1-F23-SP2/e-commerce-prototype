import java.util.UUID;

class SalesReport {



    // This is from our database
    // remove this, and make static method to get orders from database
//    int orders;


    // We don't need those
    float margin;
    float price;
    // qty trigger



    // Those are static
    double turnover;
    double wages;
    float interestIncome;
    float rentalIncome;
    float taxes;
    float productionCost;



    //qty trigger
    int lowInventory; // qty


    public SalesReport(float margin, float price, float turnover, float wages, float interestIncome, float rentalIncome, float taxes, float productionCost, int orders, int lowInventory) {
        this.margin = margin;
        this.price = price;
        this.turnover = turnover;
        this.wages = wages;
        this.interestIncome = interestIncome;
        this.rentalIncome = rentalIncome;
        this.taxes = taxes;
        this.productionCost = productionCost;
        this.orders = orders;
        this.lowInventory = lowInventory;
    }



    public float calcGrossDomesticIncome (){

        float gdiValue;
        gdiValue = wages + turnover + interestIncome + rentalIncome + taxes - productionCost;

        return gdiValue;
    }


    public float calcMarginPerProduct(){


        return this.price * this.margin;
    }


    static int getOrders(UUID uuid){
        // Use a uuid to get all the orders made with that uuid from our database

        // Placeholder
        return 1;
    }

    static double calcMargin(){
        
    }











}
