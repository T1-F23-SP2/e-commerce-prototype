package mockPIM;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PriceInformation {

    BigDecimal price;
    BigDecimal buyPrice;
    LocalDate priceCreation;
    DiscountInformation discountInformation;

    public PriceInformation(BigDecimal price, BigDecimal buyPrice, LocalDate priceCreation, DiscountInformation discountInformation) {
        this.price = price;
        this.buyPrice = buyPrice;
        this.priceCreation = priceCreation;
        this.discountInformation = discountInformation;
    }

    public BigDecimal getPrice() {
        return price;
    }
    public BigDecimal getBuyPrice(){return buyPrice;}

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDate getPriceCreation() {
        return priceCreation;
    }

    public void setPriceCreation(LocalDate priceCreation) {
        this.priceCreation = priceCreation;
    }

    public DiscountInformation getDiscountInformation() {
        return discountInformation;
    }

    public void setDiscountInformation(DiscountInformation discountInformation) {
        this.discountInformation = discountInformation;
    }


//    public ProductInformation fromDB(ValueExtraxtor ve){
//        // Get something from the fucking database
//        return null;
//    }




}
