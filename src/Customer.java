public class Customer {
    String name;
    String email;
    int phone;
    String adress;
    int postnumber;

    Customer(String name, String email, int phone, String adress, int postnumber) 
    {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.adress = adress;
        this.postnumber = postnumber;
    }

    Customer pCustomer1 = new Customer("Hans", "Hans@gmail.com", 78965412, "Skolevej 7", 7010);
    Customer pCustomer2 = new Customer("Peter", "Peter@gmail.com", 96582365, "vej 8", 9856);
    Customer pCustomer3 = new Customer("Grete", "Grete@gmail.com", 45867245, "gade 21", 5000);
    Customer pCustomer4 = new Customer("Kurt", "Kurt@gmail.com", 96453287, "Skivevej 58", 6333);
    Customer pCustomer5 = new Customer("Jens", "Jens@gmail.com", 99335544, "Odensevej 9", 1234);
    
}
