
import java.lang.Math;



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

    public static void main(String[] args) {
        System.out.println();

    }

}





