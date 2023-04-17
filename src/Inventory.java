
import java.lang.Math;
import java.util.Random;


public class Inventory {

    double max = 0.40;
    double min = 0.10;

    double range = (max - min);
    String name;
    double price;
    double margin = Math.random()*range+min;

    public Inventory(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public double calcMargin(){

        return price*margin;
    }


    static int getItemsQty(String UUID){

        // Query our database for item qty.


        return 0;
    }


    public static void main(String[] args) {
        System.out.println();

    }

}





