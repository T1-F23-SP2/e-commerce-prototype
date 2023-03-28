public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        Inventory inventory1 = new Inventory("Marcel", 6);
        Inventory inventory2 = new Inventory("Danieal", 100);


        System.out.println(inventory1.calcMargin());
        System.out.println(inventory2.calcMargin());


    }
}