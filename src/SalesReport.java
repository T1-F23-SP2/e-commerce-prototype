public class SalesReport {


    float margin;
    float price;
    // qty trigger

    float turnover;
    float wages;
    float interestIncome;
    float rentalIncome;
    float taxes;
    float productionCost;


    int orders;
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







}
