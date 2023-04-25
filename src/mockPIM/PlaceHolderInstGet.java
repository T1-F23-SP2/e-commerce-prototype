package mockPIM;

import mockPIM.PriceInformation;
import mockPIM.ProductInformation;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PlaceHolderInstGet {


    // Mock data of PIMS classes (Without Product category, Product specification, DiscountInformation and ManufacturingInformation)
    static ProductInformation inst1 = new ProductInformation("1U2U3I4D1", "Iphone v", "123456789", "An amazing iphone v Short Desc", "This iphone v can this and this Long Desc", false, null, null, null, new PriceInformation(new BigDecimal("15999"), new BigDecimal("15000"), LocalDate.of(2023, 3, 10), null));
    static ProductInformation inst2 = new ProductInformation("1U2U3I4D2", "Macbook 24", "123456798", "An amazing Macbook 24 Short Desc", "This Macbook 24 can this and this Long Desc", false, null, null, null, new PriceInformation(new BigDecimal("29999"), new BigDecimal("25000"), LocalDate.of(2023, 4, 11), null));
    static ProductInformation inst3 = new ProductInformation("1U2U3I4D3", "LG Tv", "123456987", "An amazing LG Tv Short Desc", "This LG Tv can this and this Long Desc", false, null, null, null, new PriceInformation(new BigDecimal("24999"), new BigDecimal("20000"), LocalDate.of(2023, 5, 12), null));

    public static ProductInformation[] productArray = {inst1, inst2, inst3};

    public static ProductInformation getInst1() {
        return inst1;
    }

    public static ProductInformation getInst2() {
        return inst2;
    }

    public static ProductInformation getInst3() {
        return inst3;
    }
}
