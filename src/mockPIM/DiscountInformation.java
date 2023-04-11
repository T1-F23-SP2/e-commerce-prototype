package mockPIM;

import java.time.LocalDate;

public class DiscountInformation {

    String name;
    LocalDate startingDate;
    LocalDate expiringDate;


    public DiscountInformation(String name, LocalDate startingDate, LocalDate expiringDate) {
        this.name = name;
        this.startingDate = startingDate;
        this.expiringDate = expiringDate;
    }

    public int calculateDiscountPeriod(){
        return 1;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(LocalDate startingDate) {
        this.startingDate = startingDate;
    }

    public LocalDate getExpiringDate() {
        return expiringDate;
    }

    public void setExpiringDate(LocalDate expiringDate) {
        this.expiringDate = expiringDate;
    }
}
