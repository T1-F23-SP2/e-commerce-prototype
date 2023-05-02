package ComputedOverviews;

public class SalesReportObject {
    int AmountofOrders;
    double MarginKR;
    int MarginPR;
    int ItemQTY;

    int Price;
    String name;
    double revenue;


    SalesReportObject(int AmountofOrders, double MarginKR, int MarginPR, int ItemQTY, int Price, String name, double revenue)
    {
        //GetOrders
        this.AmountofOrders = SalesReport.getAmountOfOrders("UUID");
        //Computed Overviews
        this.MarginKR = MarginKR;
        //Computed Overviews
        this.MarginPR = MarginPR;
        //DB ItemQTY
        this.ItemQTY = ItemQTY;
        //PlaceHolderinstGet
        this.Price = Price;
        //PlaceHolderinstGet
        this.name = name;
        //Computed Overviews
        this.revenue = revenue;
    }
}
