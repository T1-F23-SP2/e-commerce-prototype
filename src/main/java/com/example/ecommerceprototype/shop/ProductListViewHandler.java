package com.example.ecommerceprototype.shop;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ProductListViewHandler {

    private ProductListViewHandler(int pageSize) {
        this.currentPage = 0;
        this.pageSize = pageSize;
        this.products = new ArrayList<>();
        this.displayedProducts = products;
    }

    private int currentPage;
    private int pageSize = 4;

    private static int defaultPageSize = 4;
    private ArrayList<ProductInformation> products;

    private ArrayList<ProductInformation> displayedProducts;



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

        resetDisplayedProducts();
    }



    public void setProducts(ArrayList<ProductInformation> products) {
        this.products = products;
    }

    public ArrayList<ProductInformation> getProducts() {
        return products;
    }


    public ArrayList<ProductInformation> search(String searchTerm) {
      if (searchTerm != "") {

          ArrayList<ProductInformation> result = new ArrayList<>();

          for (ProductInformation product : products) {
              if (product.getName().contains(searchTerm)) {
                  result.add(product);
                  continue;
              }
              if (product.getLongDescription().contains(searchTerm)) {
                  result.add(product);
                  continue;
              }
              if (product.getShortDescription().contains(searchTerm)) {
                  result.add(product);
                  continue;
              }
              if (product.getProductCategory().name != null)
                if (product.getProductCategory().name.contains(searchTerm)) {
                      result.add(product);
                      continue;
              }
              if (product.getProductUUID() == searchTerm) {
                  result.add(product);
                  continue;
              }
              if (product.getSerialNumber() == searchTerm) {
                  result.add(product);
                  continue;
              }
              if(product.getManufcaturingInformation().getName() != null)
                if (product.getManufcaturingInformation().getName().contains(searchTerm)) {
                      result.add(product);
                }

          }

          return result;

      }
          return products;
    }

    public void setDisplayedProducts(ArrayList<ProductInformation> displayedProducts) {
        this.displayedProducts = displayedProducts;
    }

    public void resetDisplayedProducts() {
        this.displayedProducts = products;
    }


    public ProductInformation getProductInformationFromUUID(String UUID) {
        for (ProductInformation product : products) {
            if (product.getProductUUID() == UUID) {
                return product;
            }
        }
        return null;
    }

    public ArrayList<ProductInformation> getPage(int page) {
        if (page < 0)
            page = 0; // Genuinely a bug I encountered. It shouldn't ever go below 0 anyway, so might as well just make it a lower bound.
        ArrayList<ProductInformation> dupedList;
        int pageMinIndex = page * pageSize;
        int pageMaxIndex =  page * pageSize + pageSize;
        if (pageMaxIndex <= displayedProducts.size())
            dupedList = new ArrayList<>(displayedProducts.subList(pageMinIndex, pageMaxIndex));
        else {
            //System.out.println("Disp Product Size:" + displayedProducts.size());
            dupedList = new ArrayList<>(displayedProducts.subList(pageMinIndex, displayedProducts.size()));
        }
        return dupedList;
        // This is a hack to get around the fact that subList is its own distinct type and we need arraylist
    }

    public ArrayList<ProductInformation> getPage(int page, boolean updateCurrentPage) {
        if (updateCurrentPage)
            currentPage = page;
        return getPage(page);

    }

    public ArrayList<ProductInformation> getNextPage() {
        if (currentPage + 1 <= getPageCount())
            currentPage++;
        return getPage(currentPage); // Return current page if we're on last page.
    }

    public int getPageCount() {
        // If amount of products matches exactly with page size, just return it. Otherwise add 1 for overflowing products.
        return displayedProducts.size() % pageSize == 0 ? (displayedProducts.size()/pageSize) : (displayedProducts.size()/pageSize) + 1;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
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
