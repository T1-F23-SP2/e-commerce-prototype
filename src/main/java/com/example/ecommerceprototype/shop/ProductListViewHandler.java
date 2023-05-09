package com.example.ecommerceprototype.shop;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class ProductListViewHandler {

    private ProductListViewHandler(int pageSize) {
        this.currentPage = 0;
        this.pageSize = pageSize;
        this.products = new ArrayList<>();
    }

    private int currentPage;
    private int pageSize = 4;

    private static int defaultPageSize = 4;
    private ArrayList<ProductInformation> products;


    public void populateTestDataset() {
        ProductCategory testCategory = new ProductCategory();
        ProductSpecification testSpecification = new ProductSpecification();
        ManufacturingInformation testManufacturingInfo = new ManufacturingInformation();
        DiscountInformation testDiscountInfo = new DiscountInformation();
        PriceInformation testPrice = new PriceInformation(BigDecimal.valueOf(1337), LocalDate.now(),testDiscountInfo);
        ProductInformation productOne;
        productOne = new ProductInformation("124829350-23dab341","Test","141095-124","Test Product",
                "Tell me. For whom do you fight?",
                false,testCategory,testSpecification,testManufacturingInfo,testPrice);

        products.add(productOne);
        products.add(new ProductInformation("39238592ths23-32859790", "Test 2", "420-69", "Test Product 2",
                "Hmph! How very glib. And do you believe in Eorzea? Eorzea's unity is forged of falsehoods. " +
                        "Its city-states are built on deceit. And its faith is an instrument of deception.",
                false, testCategory, testSpecification, testManufacturingInfo, new PriceInformation(BigDecimal.valueOf(420.69),LocalDate.now(),testDiscountInfo)));

        products.add(new ProductInformation("5810251290u219-805kæ2135i1j", "Overleaf is gone!", "0xDEADBEEF", "Gaius!",
                "It is naught but a cobweb of lies. To believe in Eorzea is to believe in nothing. " +
                        "In Eorzea, the beast tribes often summon gods to fight in their stead--though your comrades only rarely respond in kind. Which is strange, is it not?",
                false, testCategory, testSpecification, testManufacturingInfo, new PriceInformation(BigDecimal.valueOf(420.69),LocalDate.now(),testDiscountInfo)));


        products.add(new ProductInformation("ehr2oi4h5oui3498", "Overleaf is gone! 4", "0xDEAD", "Cid nan *bloody* Garlond",
                "Are the \"Twelve\" otherwise engaged? I was given to understand they were your protectors. If you truly believe them your guardians," +
                        " why do you not repeat the trick that served you so well at Carteneau, and call them down? They will answer--so long as you lavish them with" +
                        " crystals and gorge them on aether. Your gods are no different than those of the beasts--eikons every one. " +
                        "Accept but this, and you will see how Eorzea's faith is bleeding the land dry",
                false, testCategory, testSpecification, testManufacturingInfo, new PriceInformation(BigDecimal.valueOf(420.69),LocalDate.now(),testDiscountInfo)));

        products.add(new ProductInformation("52h2uitoh23589", "Overleaf is gone! 5", "0xDEEAAD", "Control must be maintained",
                "Nor is this unknown to your masters. Which prompts the question: Why do they cling to these false deities? " +
                        "What drives even men of learning--even the great Louisoix--to grovel at their feet? The answer? Your masters lack the strength to do otherwise! " +
                        "For the world of man to mean anything, man must own the world. To this end, he hath fought ever to raise himself through conflict--" +
                        "to grow rich through conquest. And when the dust of battle settles, is it ever the strong who dictate the fate of the weak.",
                false, testCategory, testSpecification, testManufacturingInfo, new PriceInformation(BigDecimal.valueOf(420.69),LocalDate.now(),testDiscountInfo)));


        products.add(new ProductInformation("2i292p52hj3524lk523uo2", "Overleaf is gone! 6", "&HADC0FFEE",
                "You've committed the cardinal sin of boring me, and thus I retire to the shade.",
                "Knowing this, but a single path is open to the impotent ruler--that of false worship. A path which leads to enervation and death. " +
                        "Only a man of power can rightly steer the course of civilization. And in this land of creeping mendacity, " +
                        "that one truth will prove its salvation.",
                false, testCategory, testSpecification, testManufacturingInfo, new PriceInformation(BigDecimal.valueOf(420.69),LocalDate.now(),testDiscountInfo)));

        products.add(new ProductInformation("2u5j2io34hn32oiru3oi54h", "Overleaf is gone! 7", "0xDEADD00D",
                "One man's ruse is another man's keikaku!",
                "Come, champion of Eorzea, face me! Your defeat shall serve as proof of my readiness to rule! " +
                        "It is only right that I should take your realm. For none among you has the power to stop me!",
                false, testCategory, testSpecification, testManufacturingInfo, new PriceInformation(BigDecimal.valueOf(420.69),LocalDate.now(),testDiscountInfo)));
    }



    public void setProducts(ArrayList<ProductInformation> products) {
        this.products = products;
    }

    public ArrayList<ProductInformation> getProducts() {
        return products;
    }

    public ArrayList<ProductInformation> getPage(int page) {
        return (ArrayList<ProductInformation>) products.subList(page * pageSize, page * pageSize + pageSize);
    }

    public ArrayList<ProductInformation> getPage(int page, boolean updateCurrentPage) {
        if (updateCurrentPage)
            currentPage = page;
        return (ArrayList<ProductInformation>) products.subList(page * pageSize, page * pageSize + pageSize);

    }

    public ArrayList<ProductInformation> getNextPage() {
        currentPage++;
        return getPage(currentPage);
    }

    public ArrayList<ProductInformation> getPreviousPage() {
        currentPage--;
        return getPage(currentPage);
    }

    private static ProductListViewHandler instance = new ProductListViewHandler(defaultPageSize);

    public static ProductListViewHandler getInstance() {

        if(instance == null)
            instance = new ProductListViewHandler(defaultPageSize);

        return instance;
    }

}
