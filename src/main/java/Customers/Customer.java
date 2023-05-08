package Customers;

public class Customer {

    String name;
    String email;
    int phone;
    String address;
    int zipcode;

    //LocalDate date;

    Customer(String name, String email, int phone, String address, int zipcode)
    {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.zipcode = zipcode;
    }


    // Getters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhone() {
        return phone;
    }



    // Setters
    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

}
